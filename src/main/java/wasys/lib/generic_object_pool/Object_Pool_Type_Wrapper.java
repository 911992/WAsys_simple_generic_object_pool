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

import wasys.lib.generic_object_pool.api.Object_Factory;
import wasys.lib.generic_object_pool.api.Poolable_Object;

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
     * Simple forward the call to {@code get_pool} method of type
     * {@link Pool_Context}
     *
     * @param arg_obj_factory the factory class is needed to creating the
     * objects
     * @param arg_policy pool initialize and working policy
     * @param arg_thread_safe if the pool need to be thread-safe
     * @param arg_register if the associated(real) pool need to registered or
     * not to default {@link Pool_Context}
     */
    public Object_Pool_Type_Wrapper(Object_Factory arg_obj_factory, Generic_Object_Pool_Policy arg_policy, boolean arg_thread_safe, boolean arg_register) {
        pool = Pool_Context.get_insatcne().get_pool(arg_obj_factory, arg_policy, arg_thread_safe, arg_register);
    }

    @Override
    public A get_an_instance() {
        return (A) pool.get_an_instance();
    }

    @Override
    public void release_an_instance(Poolable_Object arg_instance) {
        pool.release_an_instance(arg_instance);
    }

    @Override
    public int idle_objects_count() {
        return pool.idle_objects_count();
    }

    @Override
    public int available_objects_count() {
        return pool.available_objects_count();
    }

    @Override
    public int working_object_count() {
        return pool.working_object_count();
    }

    @Override
    public void shutdown_pool() {
        pool.shutdown_pool();
    }

    @Override
    public boolean pool_is_working() {
        return pool.pool_is_working();
    }

    @Override
    public boolean is_registered() {
        return pool.is_registered();
    }

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

}
