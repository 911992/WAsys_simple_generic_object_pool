/*
 * Copyright (c) 2020, Arash All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

/**
 * WAsys_simple_generic_object_pool
 * File: Object_Factory.java
 * Created on: May 6, 2020 10:34:51 PM | last edit: May 6, 2020
 *      @author https://github.com/911992
 * 
 * History:
 *  initial version: 0.1(20200506)
 */

package wasys.lib.generic_object_pool.api;

/**
 * 
 * @author https://github.com/911992
 */
public interface Object_Factory {
    public Poolable_Object create_object();
}
