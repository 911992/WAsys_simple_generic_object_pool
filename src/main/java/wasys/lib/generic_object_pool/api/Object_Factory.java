/*
 * Copyright (c) 2020, Arash All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

 /*
WAsys_simple_generic_object_pool
File: Object_Factory.java
Created on: May 6, 2020 10:34:51 PM
    @author https://github.com/911992
  
History:
    0.4.6(20200602)
        • Updated the documentation

    0.4.5(20200601)
        • Fixed some issues related to javadoc

    0.4(20200522)
        • Updated the header(this comment) part
        • Added some doc

    initial version: 0.1(20200506)
 */
package wasys.lib.generic_object_pool.api;

import wasys.lib.generic_object_pool.Object_Pool;

/**
 * Factory type, which is implemented to build related {@link Poolable_Object}
 * instances.
 * <p>{@link Object_Pool} needs this factory class, to generate
 * instances when needed</p>
 *
 * @author https://github.com/911992
 */
public interface Object_Factory {

    /**
     * Creates a {@link Poolable_Object} based on users need, and should return a non-{@code null} instance.
     * <p>
     * This method is called from an {@link Object_Pool}, when a new pool instance is required.
     * </p>
     * <p>
     * If a {@code null} instance is returned, then associated object pool should not count it as a real/working instance, and simply return the same {@code null} to caller.
     * </p>
     * @return the required/related {@link Poolable_Object} type
     */
    public Poolable_Object create_object();
}
