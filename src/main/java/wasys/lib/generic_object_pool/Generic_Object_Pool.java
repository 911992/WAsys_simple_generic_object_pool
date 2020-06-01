/*
 * Copyright (c) 2020, Arash (911992) All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

 /*
WAsys_simple_generic_object_pool
File: Generic_Object_Pool.java
Created on: May 6, 2020 10:09:17 PM
    @author https://github.com/911992
  
History:
    0.4.5(20200601)
        • Fixed some issues related to javadoc

    0.4(20200522)
        • Updated the header(this comment) part
        • Added some doc
        • Poolable_Object.reset_state() method now is called when an instance is release(regardless if it could be backed to pool, or needs to be destroyed), to avoid any possible mem leak on object
        • Removed calling for Poolable_Object.reset_state() when an object is selected to return (as above covers it)

    0.2(20200508)
        •Added shutdown guard check(method shutdown_pool(void):void) before iterate over pooled object for destroy signal.
        •Added registered flag var(and it's setter)
        •Implemented Object_Pool::is_registered(void):bool method
        •Implemented Object_Pool::get_policy(void):Generic_Object_Pool_Policy method
        •shutdown_pool method calls pool_context class if it's registered for an unregister op

   initial version: 0.1(20200506)
 */
package wasys.lib.generic_object_pool;

import java.util.Vector;
import wasys.lib.generic_object_pool.api.Object_Factory;
import wasys.lib.generic_object_pool.api.Poolable_Object;

/**
 * A <b>non-thread safe</b> implementation of {@link Object_Pool}.
 * <p>It needs a
 * {@link Object_Factory} for creating new poolable objects, besides that
 * depends on {@link Full_Pool_Object_Creation_Policy} type for fulled pool
 * situation decision making.</p>
 * <p>It <b>does not</b> keep object
 * instance(references) of objects in use(gotten).</p>
 * <p><b>Note:</b> There is <b>NO</b> type check of releasing types, so make sure
 * an object is acquired and released to the correct object pool instance</p>
 * <p><b>Note:</b> Any {@code null} instance by associated {@link  Object_Factory}
 * <b>will not</b> be counted as a success object</p>
 *
 * @author https://github.com/911992
 */
public class Generic_Object_Pool implements Object_Pool {

    private final Object_Factory factory;
    private final Generic_Object_Pool_Policy policy;

    private Vector<Poolable_Object> pool;
    private int working_ins_count = 0;
    volatile private boolean pool_working = true;
    private boolean registered;

    private final Runnable notify_thread_run = new Runnable() {
        @Override
        public void run() {
            synchronized (Generic_Object_Pool.this) {
                Generic_Object_Pool.this.notify();
            }
        }
    };

    private final Runnable null_run = new Runnable() {
        @Override
        public void run() {

        }
    };

    final private Runnable release_obj_run;

    public Generic_Object_Pool(Object_Factory factory, Generic_Object_Pool_Policy policy) {
        this.factory = factory;
        this.policy = policy;
        switch (policy.getFull_pool_instancing_policy()) {
            case Wait_Till_One_Free: {
                release_obj_run = notify_thread_run;
                break;
            }
            default: {
                release_obj_run = null_run;
            }
        }
        init_pool();
    }

    private void init_pool() {
        pool = new Vector<>(policy.getMax_object_count(), 11);
        for (int a = 0; a < policy.getMin_object_count(); a++) {
            Poolable_Object _ins = factory.create_object();
            _ins.set_pool(this);
            pool.addElement(_ins);
        }
    }

    Object_Factory get_factory() {
        return factory;
    }

    @Override
    public Poolable_Object get_an_instance() {
        Poolable_Object _res;
        if (pool.size() > 0) {
            _res = pool.remove(pool.size() - 1);
            working_ins_count++;
        } else if (working_ins_count >= policy.getMax_object_count()) {
            switch (policy.getFull_pool_instancing_policy()) {
                case Create_New_Extend_Pool_Size: {
                    policy.setMax_object_count(policy.getMax_object_count() + 1);
                    working_ins_count++;
                }
                case Create_New_No_Pooling: {
                    _res = factory.create_object();
                    if (_res != null) {
                        _res.post_create();
                        _res.set_pool(this);
                    }
                    break;
                }
                case Return_Null: {
                    _res = null;
                    break;
                }
                case Wait_Till_One_Free: {
                    try {
                        while (pool.size() == 0) {
                            synchronized (this) {
                                this.wait();
                            }
                        }
                        return get_an_instance();
                    } catch (Exception e) {
                    }
                }
                default: {
                    throw new Error("Unhandled Return Policy! Fix The Code.");
                }
            }
        } else {
            _res = factory.create_object();
            if (_res != null) {
                _res.post_create();
                _res.set_pool(this);
                if (pool_working) {
                    working_ins_count++;
                }
            }
        }

        return _res;
    }

    @Override
    public void release_an_instance(Poolable_Object arg_instance) {
        if (arg_instance != null) {
            arg_instance.reset_state();
        }
        if (working_ins_count == 0 || pool_working == false) {
            if (arg_instance != null) {
                arg_instance.pre_destroy();
            }
            return;
        }
        working_ins_count--;
        if (arg_instance != null) {
            pool.add(arg_instance);
        }
        release_obj_run.run();
    }

    @Override
    public int idle_objects_count() {
        return pool.size();
    }

    @Override
    public int available_objects_count() {
        return policy.getMax_object_count() - working_ins_count;
    }

    @Override
    public int working_object_count() {
        return working_ins_count;
    }

    @Override
    public void shutdown_pool() {
        if (pool_working == false) {
            return;
        }
        pool_working = false;
        for (int a = 0; a < pool.size();) {
            pool.remove(a).pre_destroy();
        }
        if (registered) {
            Pool_Context.get_insatcne().unregister_pool(this, false);
        }
    }

    @Override
    public boolean pool_is_working() {
        return pool_working;
    }

    @Override
    public boolean is_registered() {
        return registered;
    }

    @Override
    public void close() throws Exception {
        shutdown_pool();
    }

    void set_as_registered(boolean arg_registered) {
        this.registered = arg_registered;
    }

    @Override
    public Generic_Object_Pool_Policy get_policy() {
        return policy;
    }

}
