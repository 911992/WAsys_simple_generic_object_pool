# Composite Structure Diagram Version History
repo: https://github.com/911992/WAsys_simple_generic_object_pool  
file: [class_diagram](./_diagrams/composite_struc_diagram.svg)  
Author: [911992](https://github.com/911992)  

**v0.5.1** (Aug 23, 2020)

* Removed `Object_Factory` and its package `wasys::lib::generic_object_pool`
* Added `wasys::lib::java_type_util` package/dependency
    * Added dependant `Object_Factory` and `Generic_Object_Factory` types from `Type Signature` module/componenet
* Added `Object_Pool_Type_Wrapper` as a dedicated `object pool implementations`
* Added link/association between `Object_Pool_Type_Wrapper` and `Generic_Object_Factory`
* Added link/association between `user componenet` and `Object_Pool_Type_Wrapper`
* Added selectable path as implement-and-pass an `Object_Factory` to `Pool_Context`, OR direct call/instancing of `Object_Pool_Type_Wrapper`
* created this dedicated version history file

<hr/>

**v0.1** (May 6, 2020)

* Initial release