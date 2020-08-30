/*
 * Copyright (c) 2020, https://github.com/911992 All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

 /*
WAsys_simple_generic_object_pool
File: Generic_Object_Pool_Safe_Guard.java
Created on: May 7, 2020 5:38:31 PM
    @author https://github.com/911992
  
History:
    0.5.7(20200829)
        • Type is generic <A:Poolable_Object> (as it's super interface is)
        • Removed is_registered method
        • Marked the constructor with default package access-spec
        • Updated documentation

    0.5.1(20200823)
        • Implemented Object_Factory functions, as Object_Pool extends from Object_Factory too
        • Updated documentation

    0.4.6(20200602)
        • Updated documentation

    0.4.5(20200601)
        • Fixed some issues related to javadoc

    0.4(20200522)
        • Updated the header(this comment) part
        • Added some doc

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
import wasys.lib.java_type_util.reflect.type_sig.Object_Factory;

/**
 * Proxy type for a concreted {@link Object_Pool}, makes the associated real
 * {@link Object_Pool} thread-safe.
 * <p>This class implements the
 * {@link Object_Pool}, but it has <b>no</b> real object pooling functionality,
 * instead it simple forwards all related signals to the associated real
 * {@link Object_Pool}</p>
 * <p>The reason for this class is to make a concreted non
 * thread-safe {@link Object_Pool}, thread-safe.</p>
 * <p>By initializing, the associated
 * the {@link Object_Pool} should be provided.</p>
 * <p><b>Important Note:</b> thread-safety is done by {@code synchronize}ing the
 * associated {@link Object_Pool}, rather than {@code synchronized} methods,
 * sine a shared {@link Object_Pool} could be used for different instances of
 * this type.</p>
 * @author https://github.com/911992
 * @see Generic_Object_Pool#new_pool_instance(java.lang.Class, wasys.lib.generic_object_pool.Generic_Object_Pool_Policy, boolean) 
 * @see Generic_Object_Pool#new_pool_instance(wasys.lib.java_type_util.reflect.type_sig.Object_Factory, wasys.lib.generic_object_pool.Generic_Object_Pool_Policy, boolean) 
 */
public class Generic_Object_Pool_Safe_Guard<A extends Poolable_Object> implements Object_Pool<A> {

    /**
     * Pointer to the real, non-{@code null} object pool.
     */
    final private Object_Pool<A> pool;

    /**
     * @param pool the implemented pool instance need to be threaded thread-safe
     */
    Generic_Object_Pool_Safe_Guard(Object_Pool<A> pool) {
        this.pool = pool;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public A get_an_instance() {
        synchronized (pool) {
            return pool.get_an_instance();
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void release_an_instance(A arg_instance) {
        synchronized (pool) {
            pool.release_an_instance(arg_instance);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int idle_objects_count() {
        synchronized (pool) {
            return pool.idle_objects_count();
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int available_objects_count() {
        synchronized (pool) {
            return pool.available_objects_count();
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int working_object_count() {
        synchronized (pool) {
            return pool.working_object_count();
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void shutdown_pool() {
        synchronized (pool) {
            pool.shutdown_pool();
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean pool_is_working() {
        synchronized (pool) {
            return pool.pool_is_working();
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Generic_Object_Pool_Policy get_policy() {
        return pool.get_policy();
    }

    /**
     * Calls the associated pool {@code close()} method
     * @throws Exception forwarded from associated pool.
     */
    @Override
    public void close() throws Exception {
        synchronized (pool) {
            pool.close();
        }
    }

    /**
     * Get an instance of underlying(real) {@link Object_Factory}, and return it.
     * {@inheritDoc}
     */
    @Override
    public A create_object(Class type) {
        return get_an_instance();
    }

}
