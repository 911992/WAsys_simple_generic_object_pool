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

/**
 * Defines the functionalities an object pool is required.
 *
 * @author https://github.com/911992
 */
public interface Object_Pool extends AutoCloseable {

    /**
     * Returns an object by either from the pool(already created), or asks the
     * associated @{link Object_Factory} to create one. If the pool is full(no
     * more objects ready to return, or cannot create new ones(limit)), then
     * pool may decide to either one of the followings • Return {@code null} •
     * Create a new object, but not extend the pool maximum obj numbers • Create
     * a new object, also increase the pool maximum obj numbers by one(or some)
     * • locks the thread until an object gets released and ready for reusing
     *
     * The above policy may be applied strictly(hard-coded) or customizable by
     * the user(impl-dependent) The {@code Pool_Object} factory/context may asks
     * user to specify the full pool policy by providing an instance of
     * {@link Full_Pool_Object_Creation_Policy}(or equivalent) The return value
     * may be created by either an object factory, or the pool itself. Pool may
     * or may not keep track of working objects.
     * <b>Note:</b> pool may or <u>may not</u> check type of releasing
     * objects(back to pool), so make sure to release an object from the same
     * pool it was acquired
     *
     * @return a {@link Poolable_Object} instance {
     * @see Generic_Object_Pool} {
     * @see Full_Pool_Object_Creation_Policy}
     */
    public Poolable_Object get_an_instance();

    /**
     * Releases the given instance. Releases the given instance of
     * {@link Poolable_Object} by restarting the object state (using calling
     * {@code reset_state()}), and then adds the object to the pool if
     * applicable(when there is some empty index for it) If there is no room for
     * the released object, pool may signal the {@code pre_destroy()} to the
     * {@link Poolable_Object}, and ignore to add it to its pool.
     *
     * @param arg_instance
     */
    public void release_an_instance(Poolable_Object arg_instance);

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
     * Asks the pool to shutdown. Pool will be able to create new objects when
     * asked, but pooling functionality will be stopped.
     */
    public void shutdown_pool();

    /**
     * @return if the pool is not closed/shutdown
     */
    public boolean pool_is_working();

    /**
     * @return if instance of this pool has added to the context(any), if yes,
     * so the same reference could be grabbed from the context somewhere else.
     * @see Pool_Context
     */
    public boolean is_registered();

    /**
     * @return the policy instance where given by pool creation time
     */
    public Generic_Object_Pool_Policy get_policy();
}
