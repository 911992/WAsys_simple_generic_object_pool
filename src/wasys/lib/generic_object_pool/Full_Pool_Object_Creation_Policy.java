/*
 * Copyright (c) 2020, Arash All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

/**
 * WAsys_simple_generic_object_pool
 * File: Full_Pool_Object_Creation_Policy.java
 * Created on: May 7, 2020 12:35:29 AM | last edit: May 7, 2020
 *      @author https://github.com/911992
 * 
 * History:
 *  initial version: 0.1(20200506)
 */

package wasys.lib.generic_object_pool;

/**
 * 
 * @author https://github.com/911992
 */
public enum Full_Pool_Object_Creation_Policy {
    Return_Null,
    Create_New_No_Pooling,
    Create_New_Extend_Pool_Size,
    Wait_Till_One_Free
}
