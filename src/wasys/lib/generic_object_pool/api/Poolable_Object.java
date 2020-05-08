/*
 * Copyright (c) 2020, Arash All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

package wasys.lib.generic_object_pool.api;

import wasys.lib.generic_object_pool.Object_Pool;

/*
WAsys_simple_generic_object_pool
File: Poolable_Object.java
Created on: May 6, 2020 10:29:35 PM | last edit: May 6, 2020
    @author https://github.com/911992
  
History:
    initial version: 0.1(20200506)
 */
public interface Poolable_Object {
    public void post_create();
    public void pre_destroy();
    public void reset_state();
    public void set_pool(Object_Pool arg_pool);
}
