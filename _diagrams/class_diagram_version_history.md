# Class Diagram Version History
repo: https://github.com/911992/WAsys_simple_generic_object_pool  
file: [class_diagram](./_diagrams/class_diagram.svg)  
Author: [911992](https://github.com/911992)  


**v0.5.7** (Aug 29, 2020)

* Removed `wasys::lib::generic_object_pool::Object_Pool_Type_Wrapper` class
* Removed `wasys::lib::generic_object_pool::Pool_Context` class
* Changed the `DEF_MAX_OBJ_COUNT` of `Generic_Object_Pool_Policy` class to `8` (previously as `64`)
* Interface `Object_Pool`, and class `Generic_Object_Pool` are now generic/types as `<A:Poolable_Object>`
* Changed signature of `Object_Pool` methods to accept type-var `A` instead of `Poolable_Object`
* Removed `is_registered(void):bool` method from `Object_Pool`
* Added `new_pool_instance(:Object_Factory<C>,:Generic_Object_Pool_Policy,arg_thread_safe:bool:=true):Object_Pool<C>` and `new_pool_instance(:Class<C>,:Generic_Object_Pool_Policy,arg_thread_safe:bool:=true):Object_Pool<C>` `static` methods to `Generic_Object_Pool`
* Marked essential members of `Generic_Object_Pool` with `protected` access-spec
* Fixed association links between `Generic_Object_Pool`, `Generic_Object_Pool_Safe_Guard`, and `Generic_Object_Factory` as removal of `Pool_Context`, and `Object_Pool_Type_Wrapper` classes.

<hr/>

**v0.5.1** (Aug 23, 2020)

* Removed `wasys::lib::generic_object_pool::api::Object_Factory` interface
* Added `WAsys_Java_type_util` componenet/artifact depedency
    * Dependency to `Object_Factory<A>` and `Generic_Object_Factory<A>` (replaced `wasys::lib::generic_object_pool::api::Object_Factory`)
* `Object_Pool` extends from `Object_Factory<Poolable_Object>` now
* `Object_Pool_Type_Wrapper` creates `Generic_Object_Factory<A:Poolable_Object>` now

<hr/>

**v0.4.7** (Jun 4, 2020)

* Added `mutex:Object` field to class `Generic_Object_Pool`

<hr/>

**v0.4.6** (Jun 2, 2020)

* Changed the `Generic_Object_Pool.pool` type from `Vector` to `ArrayList`
* Renamed `Generic_Object_Pool.null_run` field to `Generic_Object_Pool.NULL_RUN`
* Field `null_run`(`NULL_RUN`) is not `static`
* Changed the `Pool_Context.ctx` type from `Vector` to `ArrayList`

<hr/>

**v0.4.1** (May 22, 2020)

* Marking the `DEF_MAX_OBJ_COUNT`, `DEF_MIN_OBJ_COUNT`, `DEF_OBJ_CREATION_POLICY`, and `DEF_INS` as `{final}` in class `Generic_Object_Pool_Policy`

<hr/>

**v0.4** (May 22, 2020)

* Created this file(dedicated versioning file for [class diagram](./_diagrams/class_diagram.uxf))
* Reordered the max(`max_object_count`) ,ad min(`min_object_count`) fields in `Generic_Object_Pool_Policy` type(also affected members)
* Mark the `min_object_count`, and `full_pool_instancing_policy` fields as `final` in `Generic_Object_Pool_Policy`
* Changed the `DEF_MIN_OBJ_COUNT` value to `0` (from `8`) in `Generic_Object_Pool_Policy`
* Fixed the wrong associated between `Poolable_Object_Adapter` and `AutoClosable`
* Fixed the missed/wrong association(s) between `Runnable` and `Generic_Object_Pool`

<hr/>  


**v0.3.2** (May 15, 2020)

* Fixed interface-interface inheritance notations(links)  

<hr/>  

**v0.3.1** (May 12, 2020)

* Fixed ambiguous some fields access specifiers 

<hr/>  

**v0.3** (May 9, 2020)

* Updated the note related to `Generic_Object_Pool_Safe_Guard`

<hr/>  

**v0.2** (May 8, 2020)

* Fixed some small notations
* Add missed singleton associate to `Pool_Context` class
* Added `Pool_Context.unregister_pool(:Object_Pool,:bool):void`
* Mark `register_pool(...):Object_Pool` methods `private`, now use `get_pool()` method instead
* Removed `register_pool(...):Object_Pool` method(s)
* Updated `Object_Pool_Type_Wrapper` constructor, to tell if pool object is needed to be registered or not
* Remove the confusing `Object_Pool_Type_Wrapper` constructor due to lack of doc in src code for now
* Added registered flag field in `Generic_Object_Pool`
* Fixed the misnamed `Runnable` as `AutoClosable` for `Generic_Object_Pool`
* Added `is_registered(void):bool` and `get_policy(void):Generic_Object_Pool_Policy` methods to `Object_Pool`

<hr/>  

**v0.1** (May 6, 2020)

* Initial release