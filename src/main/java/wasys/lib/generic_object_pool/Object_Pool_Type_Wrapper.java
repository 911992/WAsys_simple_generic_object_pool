/*
 * Copyright (c) 2020, Arash All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

 /*
WAsys_simple_generic_object_pool
File: Object_Pool_Type_Wrapper.java
Created on: May 6, 2020 10:24:15 PM
    @author https://github.com/911992
 
History:
    0.5.1(20200823)
        • Using wasys.lib.java_type_util.reflect.type_sig.Object_Factory instead of wasys.lib.generic_object_pool.api.Object_Factory
        • Changed arg_obj_factory type from Object_Factory to Object_Factory<Fillable_Object> in constructor
        • Added a new constructor to instantiate a Generic_Object_Pool based on given Poolable_Object type
        • Implemented Object_Factory functions, as Object_Pool extends from Object_Factory too
        • Updated documentation

    0.4.6(20200602)
        • Updated the documentation

    0.4.5(20200601)
        • Fixed some issues related to javadoc

    0.4(20200522)
        • Updated the header(this comment) part
        • Added some doc

    0.2(20200508)
        •Implemented Object_Pool::is_registered(void):bool method
        •Implemented Object_Pool::get_policy(void):Generic_Object_Pool_Policy method
        •Removed constructor(:Object_Factory,arg_thread_safe:bool)
  
    initial version: 0.1(20200506)
 */
package wasys.lib.generic_object_pool;

import wasys.lib.java_type_util.reflect.type_sig.Object_Factory;
import wasys.lib.generic_object_pool.api.Poolable_Object;
import wasys.lib.java_type_util.reflect.type_sig.impl.Generic_Object_Factory;

/**
 * Type-wrapper for a concreted {@link Object_Pool} instance.
 * <p>Generic(autobox)
 * proxy class for a concreted {@link Object_Pool}, by casting the desired
 * {@link Poolable_Object} type This type has no any real pool functionality,
 * and depends to a real/implemented pool instance</p>
 * <p>By default it uses(asks for a
 * new one from {@link Pool_Context}) the {@link Generic_Object_Pool} as the
 * associated pool instance</p>
 *
 * @author https://github.com/911992
 * @param <A> the desired type, for easier casting
 */
public final class Object_Pool_Type_Wrapper<A extends Poolable_Object> implements Object_Pool {

    /**
     * Pointer to the real, non-{@code null} object pool.
     */
    final private Object_Pool pool;

    /**
     * Simple forward the call to {@code get_pool} method of type {@link Pool_Context}.
     *
     * @param arg_obj_factory the factory class is needed to creating the objects
     * @param arg_policy pool initialize and working policy
     * @param arg_thread_safe if the pool need to be thread-safe
     * @param arg_register if the associated(real) pool need to registered or not to default {@link Pool_Context}
     */
    public Object_Pool_Type_Wrapper(Object_Factory<A> arg_obj_factory, Generic_Object_Pool_Policy arg_policy, boolean arg_thread_safe, boolean arg_register) {
        pool = Pool_Context.get_insatcne().get_pool(arg_obj_factory, arg_policy, arg_thread_safe, arg_register);
    }
    
    /**
     * Creates an {@link Generic_Object_Factory} of given {@code arg_obj_class}, and forward it to {@code Object_Pool_Type_Wrapper(:Object_Factory<A>,:Generic_Object_Pool_Policy,:boolean,:boolean)} constructor.
     * <p>
     * The given {@code arg_obj_class} <b>MUST</b> have a public default constructor.
     * </p>
     * <p>
     * If instancing of {@code arg_obj_class} is a complex work, this is highly recommended to implement a user-defined {@link Object_Factory}, rather than asking for the generic one.
     * </p>
     * <p>
     * <b>Important note:</b> make sure the given {@code arg_obj_class} would not throw any exception during instancing, otherwise related {@link Object_Pool} will provide unexpected({@code null}) instances.
     * </p>
     * @param arg_obj_class the class/type should be pooled
     * @param arg_policy pool initialize and working policy
     * @param arg_thread_safe if the pool need to be thread-safe
     * @param arg_register if the associated(real) pool need to registered or not to default {@link Pool_Context}
     * @throws IllegalAccessException (thrown by {@link Generic_Object_Factory}) if given {@code arg_type}(type) is not a instance-able one(enum | interface | abstract-class), or the default constructor is not {@code public}
     * @throws NoSuchMethodException (thrown by {@link Generic_Object_Factory}) 
     * @since 0.5.1
     */
    public Object_Pool_Type_Wrapper(Class<A> arg_obj_class, Generic_Object_Pool_Policy arg_policy, boolean arg_thread_safe, boolean arg_register) throws IllegalAccessException, NoSuchMethodException{
        this(new Generic_Object_Factory<A>(arg_obj_class),arg_policy,arg_thread_safe,arg_register);
//        pool = Pool_Context.get_insatcne().get_pool(arg_obj_factory, arg_policy, arg_thread_safe, arg_register);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public A get_an_instance() {
        return (A) pool.get_an_instance();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void release_an_instance(Poolable_Object arg_instance) {
        pool.release_an_instance(arg_instance);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int idle_objects_count() {
        return pool.idle_objects_count();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int available_objects_count() {
        return pool.available_objects_count();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int working_object_count() {
        return pool.working_object_count();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void shutdown_pool() {
        pool.shutdown_pool();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean pool_is_working() {
        return pool.pool_is_working();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean is_registered() {
        return pool.is_registered();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Generic_Object_Pool_Policy get_policy() {
        return pool.get_policy();
    }
    
    /**
     * Calls the associated pool {@code close()} method
     * @throws Exception forwarded from associated pool.
     */
    @Override
    public void close() throws Exception {
        pool.close();
    }

    /**
     * Get an instance of underlying(real) {@link Object_Factory}, and return it.
     * {@inheritDoc}
     */
    @Override
    public Poolable_Object create_object(Class type) {
        return get_an_instance();
    }

}
