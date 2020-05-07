/*
 * Copyright (c) 2020, Arash (911992) All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

/**
 * WAsys_simple_generic_object_pool
 * File: Pool_Context.java
 * Created on: May 6, 2020 10:07:03 PM | last edit: May 6, 2020
 *      @author https://github.com/911992
 * 
 * History:
 *  initial version: 0.1(20200506)
 */

package wasys.lib.generic_object_pool;

import java.util.Vector;
import wasys.lib.generic_object_pool.api.Object_Factory;

/**
 * 
 * @author https://github.com/911992
 */
public class Pool_Context {   
    
    private static final Pool_Context INSTANCE = new Pool_Context();
    
    private final Vector<Generic_Object_Pool> ctx=new Vector<Generic_Object_Pool>(1,7);
    
    private Pool_Context() {}
    
    public static final Pool_Context get_insatcne() {
        return INSTANCE;
    }
    
    public synchronized Object_Pool register_pool(Object_Factory arg_obj_factory,boolean arg_thread_safe){
        return register_pool(arg_obj_factory, Generic_Object_Pool_Policy.DEF_INS,arg_thread_safe);
    }
    
    public synchronized Object_Pool register_pool(Object_Factory arg_obj_factory,Generic_Object_Pool_Policy arg_pool_Policy,boolean arg_thread_safe){
        for(int a=0;a<ctx.size();a++){
            Generic_Object_Pool _ins = ctx.elementAt(a);
            if(arg_obj_factory == _ins.get_factory() ){
                if(arg_thread_safe){
                    return new Generic_Object_Pool_Safe_Guard(_ins);
                }
                return _ins;
            }
        }
        Generic_Object_Pool _new_pool=new Generic_Object_Pool(arg_obj_factory, arg_pool_Policy);
        ctx.addElement(_new_pool);
        if(arg_thread_safe){
            return new Generic_Object_Pool_Safe_Guard(_new_pool);
        }
        return _new_pool;
    }
}
