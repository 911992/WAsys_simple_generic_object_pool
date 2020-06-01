/*
 * Copyright (c) 2020, Arash All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */
package wasys.lib.generic_object_pool.api;

import wasys.lib.generic_object_pool.Object_Pool;

/*
WAsys_simple_generic_object_pool
File: Poolable_Object.java
Created on: May 6, 2020 10:29:35 PM
    @author https://github.com/911992
  
History:
    0.4(20200522)
        • Updated the header(this comment) part
        • Added some doc

    initial version: 0.1(20200506)
 */
/**
 * Marks a type as a pool-able type, by implementing the required state
 * monitoring.
 *
 * @author https://github.com/911992
 */
public interface Poolable_Object {

    /**
     * Called by the {@link Object_Pool} when new instance of the object is
     * created by {@link Object_Factory}
     */
    public void post_create();

    /**
     * Called by the {@link Object_Pool} when object is no more required to be
     * part of the pool. This could be because of pool is in shutdown(close)
     * mode, or a released object is no more valid to be backed to the pool(e.g.
     * pool is full)
     */
    public void pre_destroy();

    /**
     * Called by the {@link Object_Pool} when this({@link Poolable_Object})
     * object has been asked to be released(back-to-the-pool), and could be
     * added(or reused immediately) by the associated {@link Object_Pool}
     */
    public void reset_state();

    /**
     * Called by the {@link Object_Pool} <b>probably once</b>, to introduce the
     * associated {@link Object_Pool} to this instance. This helps the
     * user/this-type to hold the given object pool, for releasing this instance
     * by associated object pool instance. Given value could be ignored, but
     * mind this object must be released to the correct/related
     * {@link Object_Pool}
     *
     * @param arg_pool the {@link Object_Pool} instance that performs the
     * pool(house) for this object.
     */
    public void set_pool(Object_Pool arg_pool);
}
