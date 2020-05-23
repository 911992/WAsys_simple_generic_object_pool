/*
 * Copyright (c) 2020, Arash (911992) All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

 /*
WAsys_simple_generic_object_pool
File: Pool_Context.java
Created on: May 6, 2020 10:07:03 PM
    @author https://github.com/911992
 
History:
    0.4(20200522)
        • Updated the header(this comment) part
        • Added some doc

    0.2(20200508)
        •Added check for given Generic_Object_Pool_Policy in register_pool(:Object_Factory,:bool):Object_Pool for returning an existing pool instance
        •Added unregister_pool(:Object_Pool,:bool):void to remove a registered pool from context
        •Removed register_pool(...):Object_Pool methods, now use get_pool method instead
        •Added get_pool, get_pool_registered_synced, and get_pool_unregistered_synced method(s)
        •Using default Generic_Object_Pool_Policy when null ptr is given
        •Type check on unregister pool to make sure given arg is not a out-of-context instance
 
    initial version: 0.1(20200506)
 */
package wasys.lib.generic_object_pool;

import java.util.Vector;
import wasys.lib.generic_object_pool.api.Object_Factory;

/**
 * Provides implemented({@link Generic_Object_Pool}) from either context, or
 * creates new one.
 *
 * @author https://github.com/911992
 */
public class Pool_Context {

    private static final Pool_Context INSTANCE = new Pool_Context();

    private final Vector<Generic_Object_Pool> ctx = new Vector<Generic_Object_Pool>(1, 7);

    private Pool_Context() {
    }

    public static final Pool_Context get_insatcne() {
        return INSTANCE;
    }

    /**
     * Calls
     * {@code get_pool(arg_obj_factory,arg_pool_Policy,thread_safe:=true,register:=false)}
     * method. Returns a synchronized(thread-safe) {@link Object_Pool} instance,
     * also unregistered(user have to keep the reference if wish to use
     * somewhere else). local context is checked if another pool with same
     * params were created previously or not, if no, then a new instance will be
     * created.
     *
     * @param arg_obj_factory the factory instance for creating the objects
     * @param arg_pool_Policy Policy instance the new pool should follow
     * @return a synchronized and unregistered {@link Object_Pool} instance
     */
    public synchronized Object_Pool get_pool_unregistered_synced(Object_Factory arg_obj_factory, Generic_Object_Pool_Policy arg_pool_Policy) {
        return get_pool(arg_obj_factory, arg_pool_Policy, true, false);
    }

    /**
     * Calls
     * {@code get_pool(arg_obj_factory,arg_pool_Policy,thread_safe:=true,register:=true)}
     * method. Returns a synchronized(thread-safe) {@link Object_Pool} instance,
     * also register it to the local context, so could be grabbed somewhere
     * else. local context is checked if another pool with same params were
     * created previously or not, if no, then a new instance will be created.
     *
     * @param arg_obj_factory the factory instance for creating the objects
     * @param arg_pool_Policy Policy instance the new pool should follow
     * @return a synchronized and registered {@link Object_Pool} instance
     */
    public synchronized Object_Pool get_pool_registered_synced(Object_Factory arg_obj_factory, Generic_Object_Pool_Policy arg_pool_Policy) {
        return get_pool(arg_obj_factory, arg_pool_Policy, true, true);
    }

    /**
     * Searches the current local context to find if there is any
     * available(previously created) pool with exact factory and policy, if yes,
     * then eturn the same instance, otherwise creates a new one.
     *
     * @param arg_obj_factory the factory instance for creating the objects
     * @param arg_pool_Policy Policy instance the new pool should follow
     * @param arg_thread_safe tells if the pool should work
     * thread-safe(synchronized) or not
     * @param arg_register tells if the pool should be registered at the local
     * context or not
     * @return a {@link Object_Pool} instance
     */
    public synchronized Object_Pool get_pool(Object_Factory arg_obj_factory, Generic_Object_Pool_Policy arg_pool_Policy, boolean arg_thread_safe, boolean arg_register) {
        Generic_Object_Pool _res = null;
        if (arg_pool_Policy == null) {
            arg_pool_Policy = Generic_Object_Pool_Policy.DEF_INS;
        }
        if (arg_register) {
            for (int a = 0; a < ctx.size(); a++) {
                Generic_Object_Pool _ins = ctx.elementAt(a);
                if (arg_obj_factory == _ins.get_factory() && _ins.get_policy().equals(arg_pool_Policy)) {
                    _res = _ins;
                    arg_register = false;
                    break;
                }
            }
        }
        if (_res == null) {
            _res = new Generic_Object_Pool(arg_obj_factory, arg_pool_Policy);
        }
        if (arg_register) {
            ctx.addElement(_res);
        }
        if (arg_thread_safe) {
            return new Generic_Object_Pool_Safe_Guard(_res);
        }
        return _res;
    }

    /**
     * Removes the given pool instance from the context. Also calls the
     * {@code shutdown_pool()} method of the given index if asked
     *
     * @param arg_pool_instance the non {@code null} pool instance need to be
     * removed from the local context
     * @param arg_shutdown if the pool needed to be shutdown or not
     */
    public synchronized void unregister_pool(Object_Pool arg_pool_instance, boolean arg_shutdown) {
        if (arg_pool_instance == null || ((arg_pool_instance instanceof Generic_Object_Pool) == false)) {
            return;
        }
        Generic_Object_Pool _arg = (Generic_Object_Pool) arg_pool_instance;
        if (ctx.remove(_arg) == false) {
            return;
        }
        if (arg_shutdown) {
            _arg.shutdown_pool();
        }
    }
}
