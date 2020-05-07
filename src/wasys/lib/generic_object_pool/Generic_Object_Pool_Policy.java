/*
 * Copyright (c) 2020, Arash All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

/**
 * WAsys_simple_generic_object_pool
 * File: Generic_Object_Pool_Policy.jav
 * Created on: May 6, 2020 10:43:13 PM | last edit: May 6, 2020
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
public class Generic_Object_Pool_Policy {
    public static int DEF_MAX_OBJ_COUNT= 64;
    public static int DEF_MIN_OBJ_COUNT = 8;
    public static Full_Pool_Object_Creation_Policy DEF_OBJ_CREATION_POLICY= Full_Pool_Object_Creation_Policy.Create_New_No_Pooling;
    public static final Generic_Object_Pool_Policy DEF_INS =new Generic_Object_Pool_Policy(DEF_MAX_OBJ_COUNT,DEF_MIN_OBJ_COUNT,DEF_OBJ_CREATION_POLICY);
    private int max_object_count;
    private int min_object_count;
    private Full_Pool_Object_Creation_Policy full_pool_instancing_policy;

    public Generic_Object_Pool_Policy(int max_object_count, int min_object_count, Full_Pool_Object_Creation_Policy full_pool_instancing_policy) {
        if(min_object_count > max_object_count){
            throw new IndexOutOfBoundsException("min object count should not be larger than max object count");
        }
        this.max_object_count = max_object_count;
        this.min_object_count = min_object_count;
        this.full_pool_instancing_policy = full_pool_instancing_policy;
    }

    public int getMax_object_count() {
        return max_object_count;
    }

    public int getMin_object_count() {
        return min_object_count;
    }

    public Full_Pool_Object_Creation_Policy getFull_pool_instancing_policy() {
        return full_pool_instancing_policy;
    }

    void setMax_object_count(int max_object_count) {
        this.max_object_count = max_object_count;
    }

}
