/*
 * Copyright (c) 2020, https://github.com/911992 All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

 /*
WAsys_simple_generic_object_pool
File: Generic_Object_Pool_Safe_Guard.java
Created on: May 7, 2020 5:38:31 PM | last edit: May 9, 2020
    @author https://github.com/911992
  
History:
    0.3(20200509)
        •Locking the pool var, instead of current instance(this), since different Generic_Object_Pool_Safe_Guard instances would have one shared pool.
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
public class Generic_Object_Pool_Safe_Guard implements Object_Pool {

    final private Object_Pool pool;

    public Generic_Object_Pool_Safe_Guard(Object_Pool pool) {
        this.pool = pool;
    }

    @Override
    public Poolable_Object get_an_instance() {
        synchronized (pool) {
            return pool.get_an_instance();
        }
    }

    @Override
    public void release_an_instance(Poolable_Object arg_instance) {
        synchronized (pool) {
            pool.release_an_instance(arg_instance);
        }
    }

    @Override
    public int idle_objects_count() {
        synchronized (pool) {
            return pool.idle_objects_count();
        }
    }

    @Override
    public int available_objects_count() {
        synchronized (pool) {
            return pool.available_objects_count();
        }
    }

    @Override
    public int working_object_count() {
        synchronized (pool) {
            return pool.working_object_count();
        }
    }

    @Override
    public void shutdown_pool() {
        synchronized (pool) {
            pool.shutdown_pool();
        }
    }

    @Override
    public boolean pool_is_working() {
        synchronized (pool) {
            return pool.pool_is_working();
        }
    }

    @Override
    public boolean is_registered() {
        synchronized (pool) {
            return pool.is_registered();
        }
    }

    @Override
    public Generic_Object_Pool_Policy get_policy() {
        return pool.get_policy();
    }

    @Override
    public void close() throws Exception {
        synchronized (pool) {
            pool.close();
        }
    }

}
