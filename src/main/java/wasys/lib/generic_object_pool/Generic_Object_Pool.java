/*
 * Copyright (c) 2020, Arash (911992) All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

 /*
WAsys_simple_generic_object_pool
File: Generic_Object_Pool.java
Created on: May 6, 2020 10:09:17 PM
    @author https://github.com/911992
  
History:
    0.5.7(20200829)
        • Type is generic <A:Poolable_Object> (as it's super interface is)
        • Updates related to type-var(generic). Changed abstract Poolable_Object to type-var A
        • Two new new_pool_instance functions(and their aliases) for instansing
        • Marked the constructor protected to avoid confusion for end-users
        • Added an extra bool arg to constructor to control if pool needs to be filled by min object count or not
        • Removed is_registered, set_as_registered, and get_factory methods
        • Removed registered variable
        • shutdown_pool method won't check if the pool is registered (since Pool_Context is no more)
        • Marking essential members protected for allowing sub-classes conrol the stuffs
        • Some document update

    0.5.1(20200823)
        • Using wasys.lib.java_type_util.reflect.type_sig.Object_Factory instead of wasys.lib.generic_object_pool.api.Object_Factory
        • Fixed create_object(:Class) calls over wasys.lib.java_type_util.reflect.type_sig.Object_Factory, passing null as argument
        • Implemented Object_Factory functions, as Object_Pool extends from Object_Factory too
        • Updated documentation

    0.4.7(20200604)
        • Added mutex field
        • Locking object now, is mutex field, instead of current(this) object
    
    0.4.6(20200602)
        • Using ArrayList(as a non thread-safe context) instead of Vector
        • Important: shutdown_pool() method now notify() the current instance(if it's required), if any blocked-thread(s) are waiting for an object.
        • Renamed null_run to NULL_RUN
        • Field null_run(NULL_RUN) is static
        • Added more documentation

    0.4.5(20200601)
        • Fixed some issues related to javadoc

    0.4(20200522)
        • Updated the header(this comment) part
        • Added some doc
        • Poolable_Object.reset_state() method now is called when an instance is release(regardless if it could be backed to pool, or needs to be destroyed), to avoid any possible mem leak on object
        • Removed calling for Poolable_Object.reset_state() when an object is selected to return (as above covers it)

    0.2(20200508)
        •Added shutdown guard check(method shutdown_pool(void):void) before iterate over pooled object for destroy signal.
        •Added registered flag var(and it's setter)
        •Implemented Object_Pool::is_registered(void):bool method
        •Implemented Object_Pool::get_policy(void):Generic_Object_Pool_Policy method
        •shutdown_pool method calls pool_context class if it's registered for an unregister op

   initial version: 0.1(20200506)
 */
package wasys.lib.generic_object_pool;

import java.util.ArrayList;
import wasys.lib.java_type_util.reflect.type_sig.Object_Factory;
import wasys.lib.generic_object_pool.api.Poolable_Object;
import wasys.lib.java_type_util.reflect.type_sig.impl.Generic_Object_Factory;

/**
 * A <b>non-thread safe</b> implementation of {@link Object_Pool}.
 * <p>
 * It needs a {@link Object_Factory} for creating new poolable objects, besides
 * that depends on {@link Full_Pool_Object_Creation_Policy} type for fulled pool
 * situation decision making.</p>
 * <p>
 * It <b>does not</b> keep object instance(references) of objects in
 * use(gotten).</p>
 * <p>
 * <b>Note:</b> There is <b>NO</b> type check of releasing types, so make sure
 * an object is acquired and released to the correct object pool instance</p>
 * <p>
 * <b>Note:</b> Any {@code null} instance by associated {@link  Object_Factory}
 * <b>will not</b> be counted as a success object</p>
 * <p>
 * For instancing, check {@code new_pool_instance} methods.
 * </p>
 *
 * @author https://github.com/911992
 * @param <A> the type this pool object supposed to be for
 * @see #new_pool_instance(java.lang.Class, wasys.lib.generic_object_pool.Generic_Object_Pool_Policy, boolean) 
 * @see #new_pool_instance(wasys.lib.java_type_util.reflect.type_sig.Object_Factory, wasys.lib.generic_object_pool.Generic_Object_Pool_Policy, boolean) 
 */
public class Generic_Object_Pool<A extends Poolable_Object> implements Object_Pool<A> {

    /**
     * Reference to a concreted, and non-{@code null} object factory, to be
     * called when a new instance is required.
     */
    protected final Object_Factory<A> factory;

    /**
     * Reference to a non-{@code null} policy instance, that tells the pool how
     * to behave.
     */
    private final Generic_Object_Pool_Policy policy;

    /**
     * Actual non-{@code synchronized} pool, that <b>idle</b> objects are stored.
     */
    protected ArrayList<A> pool;
    
    /**
     * Specifies the number of released objects are in used(by consumers).
     */
    protected int working_ins_count = 0;
    
    /**
     * Tells if the pool is working, or not(has been shutdown).
     */
    volatile protected boolean pool_working = true;

    /**
     * Private anon runnable/op, when {@code notify}ing blocked instances are requried.
     * <p>
     * Example: when a thread should be <b>blocked</b>, since there is no idel instance ready to get released, so it should be waited for one to get freed
     * </p>
     */
    protected final Runnable notify_thread_run = new Runnable() {
        @Override
        public void run() {
            synchronized (Generic_Object_Pool.this.mutex) {
                Generic_Object_Pool.this.mutex.notify();
            }
        }
    };
    
    /**
     * A private anon runnable that does nothing, considering a Null Pattern.
     */
    static protected final Runnable NULL_RUN = new Runnable() {
        @Override
        public void run() {

        }
    };

    /**
     * A pointer to a runnable should be called when an instance is freed/released.
     */
    final protected Runnable release_obj_run;
    
    /**
     * A mutex object, for thread-synchronization purpose(internal-impl) usage.
     * <p>
     * This is a lock/mutex object, which will be used for signaling between threads releasing objects, and those are waited for one.
     * </p>
     * <p>
     * Perior v0.4.7, object {@code this} was used, now this object instead to avoid any external(out-of-lib-scope) object signaling by 3rd. parties.
     * </p>
     * @since 0.4.7
     */
    final protected Object mutex=new Object();
    
    /**
     * Returns a concreted {@link Poolable_Object} related to type-arg({@code C}).
     * <p>
     * Returning {@link Object_Pool} could be either thread-safe or not based on given {@code arg_thread_safe} param.
     * </p>
     * @param <C> the type-var the concreted {@link Object_Pool}, and given POJO {@link Poolable_Object}({@code arg_obj_class}) should be
     * @param arg_obj_factory the factory class is needed to creating the objects
     * @param arg_policy pool initialize and working policy
     * @param arg_thread_safe if the pool need to be thread-safe    
     * @return An impl of {@link Object_Pool}, either a {@link Generic_Object_Pool} when thread-safety is not considered ({@code arg_thread_safe} is {@code false}), or a {@link Generic_Object_Pool_Safe_Guard} otherwise.
     * @see Generic_Object_Pool_Safe_Guard
     * @since 0.5.7
     */
    public static <C extends Poolable_Object> Object_Pool<C> new_pool_instance(Object_Factory<C> arg_obj_factory, Generic_Object_Pool_Policy arg_policy, boolean arg_thread_safe) {
        Object_Pool<C> _res=new Generic_Object_Pool(arg_obj_factory, arg_policy,true);
        if(arg_thread_safe){
            _res= new Generic_Object_Pool_Safe_Guard<C>(_res);
        }
        return _res;
    }
    
    /**
     * Returns a thread-safe concreted {@link Poolable_Object} related to type-arg({@code C}).
     * <p>
     * It calls another method that performs the real job. Explicitly asks for a thread-safe pool.
     * </p>
     * @param <C> the type-var the concreted {@link Object_Pool}, and given POJO {@link Poolable_Object}({@code arg_obj_class}) should be
     * @param arg_obj_factory the factory class is needed to creating the objects
     * @param arg_policy pool initialize and working policy
     * @return A thread-safe concreted {@link Object_Pool}
     * @see #new_pool_instance(wasys.lib.java_type_util.reflect.type_sig.Object_Factory, wasys.lib.generic_object_pool.Generic_Object_Pool_Policy, boolean) 
     * @since 0.5.7
     */
    public static <C extends Poolable_Object> Object_Pool<C> new_pool_instance(Object_Factory<C> arg_obj_factory, Generic_Object_Pool_Policy arg_policy){
        return new_pool_instance(arg_obj_factory, arg_policy, true);
    }
    
    /**
     * Creates an {@link Generic_Object_Factory} of given {@code arg_obj_class}, and forward it to {@code new_pool_instance(:Object_Factory<A>,:Generic_Object_Pool_Policy,:boolean,:boolean)} constructor.
     * <p>
     * The given {@code arg_obj_class} <b>MUST</b> have a public default constructor.
     * </p>
     * <p>
     * If instancing of {@code arg_obj_class} is a complex work, this is highly recommended to implement a user-defined {@link Object_Factory}, rather than asking for the generic one.
     * </p>
     * <p>
     * <b>Important note:</b> make sure the given {@code arg_obj_class} would not throw any exception during instancing, otherwise related {@link Object_Pool} will provide unexpected({@code null}) instances.
     * </p>
     * @param <C> the type-var the concreted {@link Object_Pool}, and given POJO {@link Poolable_Object}({@code arg_obj_class}) should be
     * @param arg_obj_class the class/type should be pooled
     * @param arg_policy pool initialize and working policy
     * @param arg_thread_safe if the pool need to be thread-safe
     * @return a concreted thread-[safe/unsafe] {@link Object_Pool} for given POJO {@link Poolable_Object}({@code arg_obj_class})
     * @throws IllegalAccessException (thrown by {@link Generic_Object_Factory}) if given {@code arg_type}(type) is not a instance-able one(enum | interface | abstract-class), or the default constructor is not {@code public}
     * @throws NoSuchMethodException (thrown by {@link Generic_Object_Factory}) if the given class has no any public default constructor
     * @see #new_pool_instance(wasys.lib.java_type_util.reflect.type_sig.Object_Factory, wasys.lib.generic_object_pool.Generic_Object_Pool_Policy, boolean) 
     * @since 0.5.7
     */
    public static <C extends Poolable_Object> Object_Pool<C> new_pool_instance(Class<C> arg_obj_class, Generic_Object_Pool_Policy arg_policy, boolean arg_thread_safe) throws IllegalAccessException, NoSuchMethodException{
        return new_pool_instance(new Generic_Object_Factory<C>(arg_obj_class), arg_policy, arg_thread_safe);
    }
    
    /**
     * Creates a thread-safe concreted {@link Object_Pool} of given {@code arg_obj_class} (alias-function).
     * <p>
     * It calls another method that performs the real job. Explicitly asks for a thread-safe pool.
     * </p>
     * <p>
     * The given {@code arg_obj_class} <b>MUST</b> have a public default constructor.
     * </p>
     * <p>
     * If instancing of {@code arg_obj_class} is a complex work, this is highly recommended to implement a user-defined {@link Object_Factory}, rather than asking for the generic one.
     * </p>
     * <p>
     * <b>Important note:</b> make sure the given {@code arg_obj_class} would not throw any exception during instancing, otherwise related {@link Object_Pool} will provide unexpected({@code null}) instances.
     * </p>
     * @param <C> the type-var the concreted {@link Object_Pool}, and given POJO {@link Poolable_Object}({@code arg_obj_class}) should be
     * @param arg_obj_class the class/type should be pooled
     * @param arg_policy pool initialize and working policy
     * @return a concreted thread-safe {@link Object_Pool} for given POJO {@link Poolable_Object}({@code arg_obj_class})
     * @throws IllegalAccessException (thrown by {@link Generic_Object_Factory}) if given {@code arg_type}(type) is not a instance-able one(enum | interface | abstract-class), or the default constructor is not {@code public}
     * @throws NoSuchMethodException (thrown by {@link Generic_Object_Factory}) if the given class has no any public default constructor
     * @see #new_pool_instance(java.lang.Class, wasys.lib.generic_object_pool.Generic_Object_Pool_Policy, boolean) 
     * @since 0.5.7
     */
    public static <C extends Poolable_Object> Object_Pool<C> new_pool_instance(Class<C> arg_obj_class, Generic_Object_Pool_Policy arg_policy) throws IllegalAccessException, NoSuchMethodException{
        return new_pool_instance(arg_obj_class, arg_policy, true);
    }

    /**
     * Default constructor (for-sub-classes and internal-lib use only).
     * <p>
     * Once the instance is created, it will call for initialize the pool (pool-init-stage)
     * </p>
     * <p>
     * <b>Important Note:</b> this type is <b>NOT</b> thread-safe by heart. If thread-safety is an essential aspect to reach, so please check {@code new_pool_instance} static method(s).
     * </p>
     * @param factory the non-{@code null} object factor should provide the {@link Poolable_Object} instance.
     * @param policy the non-{@code null} pool policy to specify how this pool should behave
     * @param arg_also_init tells if the underlying pool(array list) needs to be initialized. if {@code true}, then pool will create the minimum number of required object by initialization.
     * @see #new_pool_instance(java.lang.Class, wasys.lib.generic_object_pool.Generic_Object_Pool_Policy, boolean) 
     * @see #new_pool_instance(wasys.lib.java_type_util.reflect.type_sig.Object_Factory, wasys.lib.generic_object_pool.Generic_Object_Pool_Policy, boolean) 
     * @since 0.5.7
     */
    protected Generic_Object_Pool(Object_Factory<A> factory, Generic_Object_Pool_Policy policy,boolean arg_also_init) {
        this.factory = factory;
        this.policy = policy;
        switch (policy.getFull_pool_instancing_policy()) {
            case Wait_Till_One_Free: {
                release_obj_run = notify_thread_run;
                break;
            }
            default: {
                release_obj_run = NULL_RUN;
            }
        }
        if(arg_also_init){
            init_pool();
        }
    }

    /**
     * Initializes the pool.
     * <p>Initialize the internal {@code pool} context.</p>
     * <p>Also creates the minimum number of objects need to be created by pool creation.</p>
     * <p>
     * <b>Note:</b> this method has no any state-check when called, so make sure to call it once and at the right time, otherwise considering UBs
     * </p>
     */
    protected void init_pool() {
        pool = new ArrayList<>(policy.getMax_object_count());
        for (int a = 0; a < policy.getMin_object_count(); a++) {
            A _ins = factory.create_object(null);
            _ins.set_pool(this);
            pool.add(_ins);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public A get_an_instance() {
        A _res;
        if (pool.size() > 0) {
            _res = pool.remove(pool.size() - 1);
            working_ins_count++;
        } else if (working_ins_count >= policy.getMax_object_count()) {
            switch (policy.getFull_pool_instancing_policy()) {
                case Create_New_Extend_Pool_Size: {
                    policy.setMax_object_count(policy.getMax_object_count() + 1);
                    working_ins_count++;
                }
                case Create_New_No_Pooling: {
                    _res = factory.create_object(null);
                    if (_res != null) {
                        _res.post_create();
                        _res.set_pool(this);
                    }
                    break;
                }
                case Return_Null: {
                    _res = null;
                    break;
                }
                case Wait_Till_One_Free: {
                    try {
                        while (pool.size() == 0) {
                            synchronized (this.mutex) {
                                this.mutex.wait();
                            }
                        }
                        return get_an_instance();
                    } catch (Exception e) {
                    }
                }
                default: {
                    throw new Error("Unhandled Return Policy! Fix The Code.");
                }
            }
        } else {
            _res = factory.create_object(null);
            if (_res != null) {
                _res.post_create();
                _res.set_pool(this);
                if (pool_working) {
                    working_ins_count++;
                }
            }
        }

        return _res;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void release_an_instance(A arg_instance) {
        if (arg_instance != null) {
            arg_instance.reset_state();
        }
        if (working_ins_count == 0 || pool_working == false) {
            if (arg_instance != null) {
                arg_instance.pre_destroy();
            }
            return;
        }
        working_ins_count--;
        if (arg_instance != null) {
            pool.add(arg_instance);
        }
        release_obj_run.run();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int idle_objects_count() {
        return pool.size();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int available_objects_count() {
        return policy.getMax_object_count() - working_ins_count;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int working_object_count() {
        return working_ins_count;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void shutdown_pool() {
        if (pool_working == false) {
            return;
        }
        pool_working = false;
        for (int a = 0; a < pool.size();) {
            pool.remove(a).pre_destroy();
        }
        if (policy.getFull_pool_instancing_policy() == Full_Pool_Object_Creation_Policy.Wait_Till_One_Free) {
            try {
                synchronized (this.mutex) {
                    this.mutex.notifyAll();
                }
            } catch (Exception e) {
            }
        }

    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean pool_is_working() {
        return pool_working;
    }

    /**
     * Simply calls {@code shutdown_pool()} method.
     * @throws Exception (supposed to be exception-free, but throws because of {@link AutoCloseable})
     */
    @Override
    public void close() throws Exception {
        shutdown_pool();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Generic_Object_Pool_Policy get_policy() {
        return policy;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public A create_object(Class type) {
       return get_an_instance();
    }
    
}
