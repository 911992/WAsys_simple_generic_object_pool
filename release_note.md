# WAsys_simple_generic_object_pool Release Note

repo: https://github.com/911992/WAsys_simple_generic_object_pool  
Author: [911992](https://github.com/911992)  
*(NOTE: following list carries mentionable(not all) changes. For detailed changes, check source code(s))*  

**0.5.1** (Aug 23, 2020)  

0. Intergration with [WAsys_Java_type_util](https://github.com/911992/WAsys_Java_type_util) repo
    * Utilizing `Object_Factory<>` type, instead of inhouse `Object_Factory` for less confusion, and better maintaining in future.
1. `Source_Code::Object_Factory` (from `wasys.lib.generic_object_pool.api`)
    * Removed
2. `Source_Code::<many>`
    * Using `wasys.lib.java_type_util.reflect.type_sig.Object_Factory` instead of <s>`wasys.lib.generic_object_pool.api.Object_Factory`</s>
    * Documentation fixes and update
3. `Source_Code::Generic_Object_Pool`
    * Fixed `create_object(:Class)` calls over `wasys.lib.java_type_util.reflect.type_sig.Object_Factory`, passing `null` as argument
    * Implemented `Object_Factory` functions, as `Object_Pool` extends from `Object_Factory` too
4. `Source_Code::Generic_Object_Pool_Safe_Guard`
    * Implemented `Object_Factory` functions, as `Object_Pool` extends from `Object_Factory` too
5. `Source_Code::Object_Pool`
    * Extending from `Object_Factory<Poolable_Object>` interafce
6. `Source_Code::Object_Pool_Type_Wrapper`
    * Changed `arg_obj_factory` type from `Object_Factory` to `Object_Factory<Fillable_Object>` in constructor
    * Added a new constructor to instantiate a `Generic_Object_Pool` based on given `Poolable_Object` type
7. Diagrams  
    * Updated the class diagram (versioning file [here](./_diagrams/class_diagram_version_history.md))  
    * Updated the composite structure diagram (versioning file [here](./_diagrams/composite_struc_diagram_version_history.md))  
8. Repo
    * Updated `pom.xml`
        * updated artifact to version `0.5.1`
        * Added dependency to `WAsys_Java_type_util` artifact
    * Updated `README.md`
        * Typo fixes(lots of, added new typos for later fixes `(╯ ◡‿◡ )╯︵ ┻━┻` )
        * Added [WAsys_Java_type_util](https://github.com/911992/WAsys_Java_type_util) as a requirement
        * Edited *Object Creation* section, based on API/lib changes
        * More detailed info for *Utilizing The Artifact* section
        * Added dedicated *Installation* section for maven and ant builds
        * Removed redundant/completed tasks in *TODOs* section

<hr/>

**0.4.7.1** (Aug 16, 2020)  

0. Repo
    * Updated `README.md`
        * Added *Meta* section. To link related links/stuff to the repo.

<hr/>

**0.4.7** (Jun 4, 2020)  

0. `Source_Code::Generic_Object_Pool`
    * Added `mutex` field
    * Locking object now, is `mutex` field, instead of current(`this`) object
        * Thread signaling will now, will use a private internal `mutex` field, instead of shared `this` object.
1. Maven artifact to version `0.4.7`
2. Diagrams  
    * Updated the class diagram (versioning file [here](./_diagrams/class_diagram_version_history.md))  
3. Repo
    * Remove `maven-publish.yml` github action file, as unexpected run op by github by each push
    * updated `pom.xml` maven conf file
        * Removed the duplicate sonatype plugin tag usage
    * Updated `README.md`
        * Changed maven repo version to `0.4.7`
        * Added [mvn-repository](https://mvnrepository.com/artifact/com.github.911992/WAsys_simple_generic_object_pool) link

<hr/>

**0.4.6** (Jun 2, 2020)  

0. **Bug fix:** `Object_Pool.shutdown()` method now should notify/unlock any blocked thread(due to wait for a pool instance) now.
1. `Source_Code::Generic_Object_Pool`
    * Using `ArrayList`(as a non-`synchronized` context) instaed of `Vector`, since thread-safety should not be considered at this level.
    * **Bug fix:** `shutdown_pool()` method now `notifyAll()` the current instance(when required), if any blocked-thread(s) are waiting for an object.
    * Renamed `null_run` to `NULL_RUN`
    * Field `null_run`(`NULL_RUN`) is `static` now
    * Added more documentation
2. `Source_Code::Pool_Context`
    * Using `ArrayList`, instead of `Vector` for local `ctx` field
3. `Source_Code::all`
    * Updated and tweaked documentation for almost al lsource codes, except `Full_Pool_Object_Creation_Policy`, `Poolable_Object`, and `Poolable_Object_Adapter`
4. Repo
    * updated `pom.xml` maven conf file
        * Removed redundant conf for signing plugin
        * Changed the auto release by close to `false` for sonatype plugin
    * Updated `README.md`
        * Changed maven repo version to `0.4.6`
5. Diagrams  
    * Updated the class diagram (versioning file [here](./_diagrams/class_diagram_version_history.md))

<hr/>

**0.4.5** (Jun 1, 2020)  

0. Dropping github action(sorry github), to perform the deploy in-house using maven, now it works **( perfect! (⌐■_■) )**
1. `Source_Code:All`
    * Fixed issues related to documentation that failed `javadoc` op
    * Reformated the documentation
2. Repo
    * Added maven output `target` folder to `.gitignore`
    * Updated and deployed the `v0.4.5` artifcat to central repository (hooray!, thanks oss, and apache, wonderful)
    * Updated `README.md` file
        * Removed the badge of github about releases
        * Updated the maven dependency version to `0.4.5` from `0.4.4`
    * Updated the `pom.xml` file
        * It follows the recommended layout/conf as OSS recommended (may not be compatible with github action)
    * Commented out the `maven-publish` file, to disable the github action

<hr/>

**0.4.4** (Jun 1, 2020)  

0. Dropping github maven repo, instead going with apache central maven repo (great!, please work)
1. `File::pom.xml` changed the dist mgr from github to maven central/oss
2. Repo
    * Moved source files to `src/main/java` from `src` to make it compatible with maven project layout
    * Added `LICENSE.txt` and `README.txt` file for maven project

<hr/>

**0.4.3** (Jun 1, 2020)  

0. Still trying to make maven using github package happened
1. Repo
    * Updated `README.md` file
        * Added maven build badge on "Maven Config" section
    * Updated `pom.xml` file(please work, the central maven conf still looks confusing, or better I'm stupid `-_-`)
        * Changed he artifact groupid to `com.github.911992` from `wasys.lib` (hope it works now)
        * Changed the event on new releases, ratehr than any `push`


<hr/>

**0.4.2** (Jun 1, 2020)  

0. Trying to make maven using github package happened
1. Repo
    * Added `pom.xml` file(please work)
    * Added `maven-publish.yml` file(please work too)

<hr/>

**0.4.1** (May 24, 2020)  

0. `Source_Code::Generic_Object_Pool_Policy`  
    * Marking the `DEF_MAX_OBJ_COUNT`, `DEF_MIN_OBJ_COUNT`, `DEF_OBJ_CREATION_POLICY`, and `DEF_INS` as `final`  
1. Diagrams  
    * Updated the class diagram (versioning file [here](./_diagrams/class_diagram_version_history.md))  


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