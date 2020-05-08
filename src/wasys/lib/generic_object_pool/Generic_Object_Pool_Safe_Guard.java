/*
 * Copyright (c) 2020, https://github.com/911992 All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

/*
WAsys_simple_generic_object_pool
File: Generic_Object_Pool_Safe_Guard.java
Created on: May 7, 2020 5:38:31 PM | last edit: May 8, 2020
    @author https://github.com/911992
  
History:
    0.2(20200508)
       •Implemented Object_Pool::is_registered(void):bool method
       •Implemented Object_Pool::get_policy(void):Generic_Object_Pool_Policy method un-synchronized
       •Mark the close(void):void method synchronized
  
    initial version: 0.1(20200506)
 */

package wasys.lib.generic_object_pool;

import wasys.lib.generic_object_pool.api.Poolable_Object;


/**
 * 
 * @author https://github.com/911992
 */
public class Generic_Object_Pool_Safe_Guard implements Object_Pool{

    final private Object_Pool pool;

    public Generic_Object_Pool_Safe_Guard(Object_Pool pool) {
        this.pool = pool;
    }

    @Override
    synchronized public Poolable_Object get_an_instance() {
        return pool.get_an_instance();
    }

    @Override
    synchronized public void release_an_instance(Poolable_Object arg_instance) {
        pool.release_an_instance(arg_instance);
    }

    @Override
    synchronized public int idle_objects_count() {
        return pool.idle_objects_count();
    }

    @Override
    synchronized public int available_objects_count() {
        return pool.available_objects_count();
    }

    @Override
    synchronized public int working_object_count() {
        return pool.working_object_count();
    }

    @Override
    synchronized public void shutdown_pool() {
        pool.shutdown_pool();
    }

    @Override
    synchronized public boolean pool_is_working() {
        return pool.pool_is_working();
    }

    @Override
    synchronized public boolean is_registered() {
        return pool.is_registered();
    }
    
    @Override
    public Generic_Object_Pool_Policy get_policy() {
        return pool.get_policy();
    }

    @Override
    synchronized public void close() throws Exception {
        pool.close();
    }

}
