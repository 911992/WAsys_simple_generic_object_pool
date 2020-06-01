/*
 * Copyright (c) 2020, Arash All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

 /*
WAsys_simple_generic_object_pool
File: Poolable_Object_Adapter.java
Created on: May 6, 2020 10:34:03 PM
    @author https://github.com/911992
  
History:
    0.4.5(20200601)
        • Fixed some issues related to javadoc

    0.4(20200522)
        • Updated the header(this comment) part
        • Added some doc

    0.2(20200508)
        •Check for associated pool null-check before call the release method

    initial version: 0.1(20200506)
 */
package wasys.lib.generic_object_pool.api;

import wasys.lib.generic_object_pool.Object_Pool;

/**
 * Adapter class for the {@link Object_Pool} which is a {@link AutoCloseable}
 * too. Any inherited type could be used by {@code try-with-resources} block,
 * for easier back-to-pool process, {@code close()} method does that actually.
 *
 * @author https://github.com/911992
 */
public abstract class Poolable_Object_Adapter implements Poolable_Object, AutoCloseable {

    /**
     * Field to hold the associated pool instance(which is provided by
     * {@code set_pool()} method.
     */
    protected Object_Pool pool;

    @Override
    public void post_create() {
    }

    @Override
    public void pre_destroy() {
    }

    /**
     * Works as setter method for {@code pool} field.
     *
     * @param arg_pool the {@link Object_Pool} instance that performs the
     * pool(house) for this object.
     */
    @Override
    public void set_pool(Object_Pool arg_pool) {
        this.pool = arg_pool;
    }

    /**
     * Implemented from {@link AutoCloseable}, simple asks the associated
     * {@code pool} instance to release this instance to the pool. This method
     * would be called automatically by JVM, when this object has acquired using
     * a {@code try-with-resources}
     *
     * @throws Exception any generic/common exception about releasing the object
     */
    @Override
    final public void close() throws Exception {
        if (pool == null) {
            return;
        }
        pool.release_an_instance(this);
    }

}
