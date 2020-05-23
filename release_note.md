# WAsys_simple_generic_object_pool Release Note

repo: https://github.com/911992/WAsys_simple_generic_object_pool  
Author: [911992](https://github.com/911992)  
*(NOTE: following list carries mentionable(not all) changes. For detailed changes, check source code(s))*  

**0.4** (May 22, 2020)  

0. Added some documentation for API-level types (it did really hurt, documenting is hard -_- )
1. `Poolable_Object` restarting event(`reset_state(void):void`) now should be called when an instance is asked for releasing(rather than when it's acquired) regardless if the object will be added to the pool(for recycling), or it should be destroyed(e.g. pool is full)
2. `Source_Code::Generic_Object_Pool`  
    * Restarting(`reset_state(void):void`) a `Poolabe_Object` now is happened at the time it's release to avoid any possible memory leak(unexpected live reference), before checking if it could be added to the pool, or not.
3. `Source_Code::Generic_Object_Pool_Policy`
    * Chnaged the default minimum object count (`DEF_MIN_OBJ_COUNT`) to `0`
    * Constructor now check if `max_object_count`, and/or `min_object_count` are negative, so there will be a `IndexOutOfBoundsException`
    * Reorder(swap) the `max_object_count`, and `min_object_count` fields(and related setter, constructor members)
    * Marking the `min_object_count`, and `full_pool_instancing_policy` as `final`
4. `Source_Code::all`
    * Removed the `last edit: mmm dd, yyyy` from header parts(since hard to update)
5. Diagrams
    * Updated object pool state diagram
    * Updated class diagram
6. Repo
    * Added class diagram dedicated versioning [file](./_diagrams/class_diagram_version_history.md)
    * Added object pool state diagram dedicated versioning [file](./_diagrams/object_pool_state_version_history.md)
    * Removed `lib` folder(gitignore)
    * Updated the `[README.md](./README.md)` file
        * Added missed explination about T0 (troubleshootings)
        * Updated the TODOs section, checked the documenting task
        * Some code formatting fixes

**0.3.2** (May 15, 2020)  

0. Repo  
    * Moved the changes from main README file, in this dedicated release note one
    * Added social media preview file
1. Diagrams  
    * Updated class diagram (fixed interface-interface inheritance relations/links)

**0.3.1** (May 12, 2020)  

0. Diagrams
    * Updated class diagram (fixed some ambiguous access specifiers)

**0.3** (May 9, 2020)  

0. `Source_Code::Generic_Object_Pool_Safe_Guard`
    * (important) Locking the `pool` var, instead of current instance(`this`), since different `Generic_Object_Pool_Safe_Guard` instances would have one *shared* pool.  
1. Diagrams
    * Updated class diagram

**0.2** (May 8, 2020)  

0. `Source_Code::all`
    * Update all files info headers into non javadoc(simple multiline comment) format.
1. `Source_Code::Generic_Object_Pool`
    * Added shutdown guard check
    * Calling for unregister the pool instance by shutdown call
2. `Source_Code::Generic_Object_Pool_Policy`
    * Implemented/override the `equals(:Object):bool` method (automated by netbeans, thanks)
    * Added `IndexOutOfBoundsException` throwable exception for constructor method signature
3. `Source_Code::Generic_Object_Pool_Safe_Guard`
    * Mark the `close(void):void` method synchronized
4. `Source_Code::Object_Pool`
    * Added `is_registered(void):bool` and `get_policy(void):Generic_Object_Pool_Policy` methods
5. `Source_Code::Object_Pool_Type_Wrapper`
    * Removed constructor `Object_Pool_Type_Wrapper(:Object_Factory,:bool)`
6. `Source_Code::Pool_Context`
    * Updated policy to find an already working pool in context, using both factory and policy vars
    * Added `unregister_pool(:Object_Pool,:bool):void` method to allow unregister a pool from context
    * Removed `register_pool(...):Object_Pool` methods, no more needed
    * Added `get_pool`, `get_pool_registered_synced`, and `get_pool_unregistered_synced` method(s)
    * Using default `Generic_Object_Pool_Policy` when null ptr is given
    * Type check on unregister pool to make sure given arg is not a out-of-context instance
7. `Source_Code::Pool_Context`
    * Check for associated pool null-check before call the release method
8. Repo
    * Updated FAQ section in this file

9. Diagrams
    * Updated class diagram
    * Updated activity|state diagram

**Initial Release 0.1** (May 6, 2020)