/*
 * Copyright (c) 2020, Arash All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

/**
 * WAsys_simple_generic_object_pool
 * File: Poolable_Object_Adapter.java
 * Created on: May 6, 2020 10:34:03 PM | last edit: May 6, 2020
 *      @author https://github.com/911992
 * 
 * History:
 *  initial version: 0.1(20200506)
 */

package wasys.lib.generic_object_pool.api;

import wasys.lib.generic_object_pool.Object_Pool;

/**
 * 
 * @author https://github.com/911992
 */
public abstract class Poolable_Object_Adapter implements Poolable_Object,AutoCloseable{
    
    protected Object_Pool pool;

    @Override
    public void post_create() {}

    @Override
    public void pre_destroy() {}

    @Override
    public void set_pool(Object_Pool arg_pool) {
        this.pool = arg_pool;
    }

    @Override
    final public void close() throws Exception {
        pool.release_an_instance(this);
    }
    
    
    
}
