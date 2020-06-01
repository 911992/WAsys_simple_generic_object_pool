/*
 * Copyright (c) 2020, Arash All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

 /*
WAsys_simple_generic_object_pool
File: Full_Pool_Object_Creation_Policy.java
Created on: May 7, 2020 12:35:29 AM
    @author https://github.com/911992
 
History:
    0.4.5(20200601)
        • Fixed some issues related to javadoc

    0.4(20200522)
        • Updated the header(this comment) part
        • Added some doc

    initial version: 0.1(20200506)
 */
package wasys.lib.generic_object_pool;

import wasys.lib.generic_object_pool.api.Poolable_Object;

/**
 * Specifies the policy need to applied, when pool {@link Object_Pool} has no
 * more object to provide.
 * <p>
 * By term Full Pool, it means all created objects are
 * in use
 *</p>
 * @author https://github.com/911992
 */
public enum Full_Pool_Object_Creation_Policy {
    /**
     * Forces the pool return an {@code null} instance.
     */
    Return_Null,
    /**
     * Forces the pool to create and return a new
     * object({@link Poolable_Object}), but new instance <b>must not</b> be
     * counted as currently working objects.
     */
    Create_New_No_Pooling,
    /**
     * Forces the pool to create and return a new
     * object({@link Poolable_Object}), <u>and</u> expand the current object
     * pool's maximum possible objects.
     */
    Create_New_Extend_Pool_Size,
    /**
     * In multi-thread env, forces the pool to blocks the current thread, until
     * another thread releases an object, so the object could be used for the
     * thread-waiting request.
     * <p>
     * <b>Note:</b> make sure the target {@link Object_Pool} instance is a
     * <u>thread-safe</u> one.
     * </p>
     */
    Wait_Till_One_Free
}
