**Release Note**
repo: https://github.com/911992/WAsys_simple_generic_object_pool
*(NOTE: following list carries mentionable(not all) changes. For detailed changes, check source code(s))*

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