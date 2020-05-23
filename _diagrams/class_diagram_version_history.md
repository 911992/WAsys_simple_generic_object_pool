# Class Diagram Version History
repo: https://github.com/911992/WAsys_simple_generic_object_pool  
file: [class_diagram](./_diagrams/class_diagram.svg)  
Author: [911992](https://github.com/911992)  

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