/*
 * Copyright (c) 2020, Arash All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

/*
WAsys_simple_generic_object_pool
File: Object_Pool_Type_Wrapper.java
Created on: May 6, 2020 10:24:15 PM | last edit: May 8, 2020
    @author https://github.com/911992
 
History:
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
 * 
 * @author https://github.com/911992
 * @param <A> 
 */
public final class Object_Pool_Type_Wrapper<A extends Poolable_Object> implements Object_Pool{
    final private Object_Pool pool;
    
    public Object_Pool_Type_Wrapper(Object_Factory arg_obj_factory,Generic_Object_Pool_Policy arg_policy,boolean arg_thread_safe,boolean arg_register) {
        pool = Pool_Context.get_insatcne().get_pool(arg_obj_factory,arg_policy,arg_thread_safe,arg_register);
    }

    @Override
    public A get_an_instance() {
        return (A)pool.get_an_instance();
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

    @Override
    public void close() throws Exception {
        pool.close();
    } 
    
}
