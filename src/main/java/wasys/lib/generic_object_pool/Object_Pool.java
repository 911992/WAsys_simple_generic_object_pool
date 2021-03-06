/*
 * Copyright (c) 2020, Arash All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

 /*
WAsys_simple_generic_object_pool
File: Object_Pool.java
Created on: May 7, 2020 3:01:55 AM
    @author https://github.com/911992
  
History:
    0.5.7(20200829)
        • Type is generic <A:Poolable_Object>
        • Changed return type of create_object(), and get_an_instance() methods from Poolable_Object to A(generic param type)
        • Changed the input param of release_an_instance method from Poolable_Object to A(generic param type)
        • Removed is_registered method

    0.5.1(20200823)
        • Extending from Object_Factory<Poolable_Object> interafce
        • Updated documentation

    0.4.6(20200602)
        • Updated the documentation

    0.4.5(20200601)
        • Fixed some issues related to javadoc

    0.4(20200522)
        • Updated the header(this comment) part
        • Added some doc

    0.2(20200508)
        •Added is_registered(void):bool method
        •Added get_policy(void):Generic_Object_Pool_Policy method

    initial version: 0.1(20200506)
 */
package wasys.lib.generic_object_pool;

import wasys.lib.generic_object_pool.api.Poolable_Object;
import wasys.lib.java_type_util.reflect.type_sig.Object_Factory;

/**
 * Defines the functionalities an object pool is required.
 * <p>
 * Since version 0.5.1, this interface is an {@link Object_Factory} too.
 * </p>
 * <p>
 * Since version 0.5.7, this interface is generic(type-var) too.
 * </p>
 * @author https://github.com/911992
 */
public interface Object_Pool<A extends Poolable_Object> extends AutoCloseable , Object_Factory<A> {

    /**
     * Returns an object by either from the pool(already created), or asks the
     * associated @{link Object_Factory} to create one.
     * <p>
     * If the pool is full(no more objects ready to return, or cannot create new
     * ones(limit)), then pool may decide to either one of the followings</p>
     * <ul>
     * <li>Return {@code null}</li>
     * <li>Create a new object, but not extend the pool maximum obj numbers</li>
     * <li>Create a new object, also increase the pool maximum obj numbers by
     * one(or some)</li>
     * <li>locks the thread until an object gets released and ready for
     * reusing</li>
     * </ul>
     * <p>
     * The above policy may be applied strictly(hard-coded) or customizable by
     * the user(impl-dependent, please see
     * {@link Full_Pool_Object_Creation_Policy})</p>
     * <p>
     * The {@code Pool_Object} factory/context may asks user to specify the full
     * pool policy by providing an instance of
     * {@link Full_Pool_Object_Creation_Policy}(or equivalent) The return value
     * may be created by either an object factory, or the pool itself.</p>
     * <p>
     * Pool may or may not keep track of working objects.</p>
     * <p>
     * <b>Note:</b> pool may or <u>may not</u> check type of releasing
     * objects(back to pool), so make sure to release an object from the same
     * pool it was acquired</p>
     *
     * @return a {@link Poolable_Object} instance
     */
    public A get_an_instance();

    /**
     * Releases the given instance.
     * <p>
     * Releases the given instance of {@link Poolable_Object} by restarting the
     * object state (using calling {@code reset_state()}), and then adds the
     * object to the pool if applicable(when there is some empty index for
     * it)</p>
     * <p>
     * If there is no room for the released object, pool may signal the
     * {@code pre_destroy()} to the {@link Poolable_Object}, and ignore to add
     * it to its pool.</p>
     * <p>
     * <b>Important note:</b> Releasing a {@link Poolable_Object} more than
     * once(while it's already released) might issue an inconsistency to current
     * pool instance(impl-dependent).
     * </p>
     * <p>
     * By default, pool should not check if a releasing instance really belongs
     * to this pool, or it has already in idle state and in pool, so please make
     * sure to not release an instance when it's in a idle state, or to a wrong
     * pool.
     * <br>
     * But also mind this is also possible that the implemented pool checks for
     * such data state.
     * </p>
     *
     * @param arg_instance the instance needed to be released
     */
    public void release_an_instance(A arg_instance);

    /**
     * @return the number of objects have created and idle(ready for use) in the
     * pool
     */
    public int idle_objects_count();

    /**
     * @return the number of objects could be asked for acquiring to not put the
     * pool into full pool state
     */
    public int available_objects_count();

    /**
     * @return number of objects were created and in use
     */
    public int working_object_count();

    /**
     * Asks the pool to shutdown.
     * <p>
     * Pool will be able to create new objects when asked, but pooling
     * functionality will be stopped.</p>
     * <p>
     * <b>Note:</b> Any blocked threads have been waiting for an object to gets
     * free, and reused them will be informed, so pool is able to provide a
     * out-of-pool scope object for them.
     * </p><p>
     * <b>Note:</b> If this pool instance has registered, so there should be a
     * call to related context for an unregister request.
     * </p>
     */
    public void shutdown_pool();

    /**
     * @return if the pool is not closed/shutdown
     */
    public boolean pool_is_working();

    /**
     * @return the policy instance where given by pool creation time
     */
    public Generic_Object_Pool_Policy get_policy();

    /**
     * Returns an instance of the pool.
     * <p>
     * Simply get an instance of {@code get_an_instance()} and return it back.
     * </p>
     * @param type (will be ignored), could be {@code null}.
     * @return and instance of pooled type
     * @since 0.5.1
     */
    @Override
    public A create_object(Class type);
    
    
}
