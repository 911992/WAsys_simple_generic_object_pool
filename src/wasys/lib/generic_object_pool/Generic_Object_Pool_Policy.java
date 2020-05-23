/*
 * Copyright (c) 2020, Arash All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

 /*
WAsys_simple_generic_object_pool
File: Generic_Object_Pool_Policy.jav
Created on: May 6, 2020 10:43:13 PM
    @author https://github.com/911992
  
History:
    0.4(20200522)
        • Updated the header(this comment) part
        • Added some doc
        • Chnaged the default minimum object count (`DEF_MIN_OBJ_COUNT`) to `0`
        • Constructor will throw IndexOutOfBoundsException if either given min_object_count, and/or max_object_count
        • Reorder(swap) the `max_object_count`, and `min_object_count` input args for the constructor(same for fields, setter, and DEF_INS)
        • Marking the min_object_count, and full_pool_instancing_policy as final

    0.2(20200508)
        •Implemented/override the equals(:Object):bool method (automated by netbeans, thanks)
        •Adding IndexOutOfBoundsException throwable exception for constructor method signature

    initial version: 0.1(20200506)
 */
package wasys.lib.generic_object_pool;

/**
 * Explains the state-policy a {@link Object_Pool} should follow.
 *
 * @author https://github.com/911992
 */
public class Generic_Object_Pool_Policy {

    public static int DEF_MAX_OBJ_COUNT = 64;
    public static int DEF_MIN_OBJ_COUNT = 0;
    public static Full_Pool_Object_Creation_Policy DEF_OBJ_CREATION_POLICY = Full_Pool_Object_Creation_Policy.Create_New_No_Pooling;
    public static final Generic_Object_Pool_Policy DEF_INS = new Generic_Object_Pool_Policy(DEF_MIN_OBJ_COUNT, DEF_MAX_OBJ_COUNT, DEF_OBJ_CREATION_POLICY);

    final private int min_object_count;
    private int max_object_count;
    final private Full_Pool_Object_Creation_Policy full_pool_instancing_policy;

    /**
     *
     * @param arg_max_object_count the maximum allowed objects could be created
     * by the object pool
     * @param arg_min_object_count the minimum size of objects need to created
     * by the initialization of the pool
     * @param full_pool_instancing_policy policy, when pool may not be able to
     * create more objects
     * @throws IndexOutOfBoundsException when {@code min_object_count} is
     * greater than {@code max_object_count}, or either of them is negative.
     */
    public Generic_Object_Pool_Policy(int arg_min_object_count, int arg_max_object_count, Full_Pool_Object_Creation_Policy full_pool_instancing_policy) throws IndexOutOfBoundsException {
        if (arg_min_object_count > arg_max_object_count || arg_min_object_count == -1 || arg_max_object_count == -1) {
            throw new IndexOutOfBoundsException("min object count should not be larger than max object count");
        }
        this.min_object_count = arg_min_object_count;
        this.max_object_count = arg_max_object_count;
        this.full_pool_instancing_policy = full_pool_instancing_policy;
    }

    /**
     * @return minimum amount of objects needed to be created by the pool
     * initialization
     */
    public int getMin_object_count() {
        return min_object_count;
    }

    /**
     * @return maximum allowed objects could be created by the pool
     */
    public int getMax_object_count() {
        return max_object_count;
    }

    /**
     * @return policy, when pool may not be able to create more objects
     */
    public Full_Pool_Object_Creation_Policy getFull_pool_instancing_policy() {
        return full_pool_instancing_policy;
    }

    /**
     * Internal lib use.
     *
     * @param max_object_count increasing(changing) the maximum allowed
     * instances by the pool
     */
    void setMax_object_count(int max_object_count) {
        this.max_object_count = max_object_count;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Generic_Object_Pool_Policy other = (Generic_Object_Pool_Policy) obj;
        if (this.max_object_count != other.max_object_count) {
            return false;
        }
        if (this.min_object_count != other.min_object_count) {
            return false;
        }
        if (this.full_pool_instancing_policy != other.full_pool_instancing_policy) {
            return false;
        }
        return true;
    }

}
