/*
 * Copyright (c) 2020, Arash All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

/*
WAsys_simple_generic_object_pool
File: Object_Pool.java
Created on: May 7, 2020 3:01:55 AM | last edit: May 8, 2020
    @author https://github.com/911992
  
History:
    0.2(20200508)
       •Added is_registered(void):bool method
       •Added get_policy(void):Generic_Object_Pool_Policy method

    initial version: 0.1(20200506)
 */

package wasys.lib.generic_object_pool;

import wasys.lib.generic_object_pool.api.Poolable_Object;

/**
 * 
 * @author https://github.com/911992
 */
public interface Object_Pool extends AutoCloseable{
public Poolable_Object get_an_instance();
public void release_an_instance(Poolable_Object arg_instance);
public int idle_objects_count();
public int available_objects_count();
public int working_object_count();
public void shutdown_pool();
public boolean pool_is_working();
public boolean is_registered();
public Generic_Object_Pool_Policy get_policy();
}
