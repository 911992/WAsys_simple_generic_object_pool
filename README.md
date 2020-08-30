# WAsys_generic_object_pool
A very simple and generic Object Pooling pattern implementation.

## Revision History
Latest: v0.5.7 (Aug 29, 2020)  
Please refer to [release_note.md](./release_note.md) file  

## Requirements
0. Java 1.7 or later
1. [WAsys_Java_type_util](https://github.com/911992/WAsys_Java_type_util) 

## Class Diagram
![Class Diagram](./_diagrams/class_diagram_partial.svg)

## Composition Structure
![Composition Structure Diagram](./_diagrams/composite_struc_diagram_partial.svg)

## Overview
Implementation of a basic/generic Object Pooling(Interning) pattern in Java. Considering following characteristics of the implementation.

### Object Creation
Object instancing is done when pool decides(based on its policy and state) to create an instance of `Poolable_Object`, and return it back to the caller. Since version `0.5.1`, this repo/lib is dependent to [`WAsys_Java_type_util`](https://github.com/911992/WAsys_Java_type_util) repo for object instancing.  
User may either provide its implemented `Object_Factory<Poolable_Object>` type(recommended way), or simply use the `Generic_Object_Factory<Poolable_Object>` where instancing the type is not complex.

### Poolable Object Type
Any poolable entity/bean must implements `Poolable_Object` or extend `Poolable_Object_Adapter` to be considered as a poolable instances.  
**NOTE:** Object state resetting is a user must be done stuff should be considered, since there is no any clue(or automated script/run) to reset a bean before gets acquired and used from an object pool.(considering `Poolable_Object.reset_state(void):void` method)

### Releasing An Object
This is up to user to either release a pooled object back to the related pool, or not(considering inconsistency). Either ways method `release_an_instance(:Poolable_Object):void` of related `Object_Pool` must be called with a usable non-`null` object, or `null` to inform pool update its counting state.  
**HINT:** This is recommended to implement `java::lang::AutoClosable` or extend `Poolable_Object_Adapter`(which is a `Closable`), and use the required object inside a `try-with_resource`(`try(){}`) to release the `Poolable_Object` when it's no more required.

## Utilizing The Artifact
Considering following steps need to be done in order to utilize this Object Pool.  

**0) Defining The `Poolable_Object`**

The type need to be pooled(e.g. `Foo_Bar_Entity_Class`) should either extend class `Poolable_Object_Adapter` or implement `Poolable_Object`.  
**NOTE:** Make sure method `reset_state(void):void` is correctly implemnted.  
```java
public class Foo_Bar_Entity_Class extends Poolable_Object_Adapter{
  private int sample_field;
  /*called when instance need to be reset*/
  @Override	public void reset_state() {
    /*resetting the object state (ready to be used for next call)*/
    sample_field = 0;
  }
}
```
*Code snippet 0: defining a `Poolable_Object`*  

**HINT:** use [WAsys_pojo_http_data_entity_tool](https://github.com/911992/WAsys_pojo_http_data_entity_tool) which is a simple tool, generate `Poolable_Object` skeletons in far easier way.

**1) Defining The `Object_Factory<Poolable_Object>`**

Provide an `Object_Factory<Poolable_Object>` (from `WAsys_Java_type_util`). Could be done using following solutions  

0. (recommended)Implement interface `Object_Factory<Poolable_Object>` and its `create_object(:Class):Poolable_Object`. Instance a non-`null` object, and return.  
**NOTE:** Object pool may not count any `null` instance is provided by `Object_Factory`.  
```java
class Foo_Bar_Entity_Class_Factory implements Object_Factory<Foo_Bar_Entity_Class>{
    @Override public Poolable_Object create_object() {
    /*create an instance of Foo_Bar_Entity_Class*/
    Foo_Bar_Entity_Class _ins = new Foo_Bar_Entity_Class();
    /*initialize the instance and return it*/
    return _ins;
  }
}
```  
*Code snippet 1: defining a custom `Object_Factory<Poolable_Object>`*  

1. If the type instancing is done using default public constructor, and is not complex, you also could create a fast `Object_Factory` by instancing `Generic_Object_Factory` then.  
```java
Generic_Object_Factory<Foo_Bar_Entity_Class> = new Generic_Object_Factory<Foo_Bar_Entity_Class>(Foo_Bar_Entity_Class.class,...);
```  
*Code snippet 2: defining a `Object_Factory<Poolable_Object>` using `Generic_Object_Factory`*  

**2) Defining The Pool Policy**  

(optional, but recommended)Prepare an insatnce of `Generic_Object_Pool_Policy` that fits your requirements about object pooling behavior and state.

**3) Creating An `Object_Pool` Instance**  
`Generic_Object_Pool<E>` is the default impl of the `Object_Pool<E:Poolable_Object>` which is provided by the lib. It has two public static functions(as listed below) to provide concreted `Object_Pool<E>`

* `new_pool_instance(:Object_Factory<C>,:Generic_Object_Pool_Policy,arg_thread_safe:bool):Object_Pool<C>` *(returns a concreted `Object_Pool<C>`)*
* `new_pool_instance(:Class<C>,:Generic_Object_Pool_Policy,arg_thread_safe:bool):Object_Pool<C>` *(creates an `Object_Factory<C>` from given `Class<C>` and calls teh above func)*

```java
//Implement a factory for the type needs to be pooled
class Foo_Bar_Entity_Class_Factory implements Object_Factory<Foo_Bar_Entity_Class_Factory>{
...
}
//creating a thread-safe Object_Pool
Object_Pool<Foo_Bar_Entity_Class_Factory> _pool = Generic_Object_Pool.new_pool_instance(new Foo_Bar_Entity_Class_Factory(),Generic_Object_Pool_Policy.DEF_INS,true/*true(by default) to ask for a thread-safe Object_Pool*/);
```  
*Code snippet 3: Creating a thread-safe `Object_Pool<Foo_Bar_Entity_Class_Factory>` with an existing/concreted `Object_Factory<Foo_Bar_Entity_Class_Factory>`*  

**HINT:** since version `0.5.7`, the internal pool context(`Pool_Context`) was removed.

```java
Object_Pool<Foo_Bar_Entity_Class> _pool = new Generic_Object_Pool.new_pool_instance<Foo_Bar_Entity_Class>(Foo_Bar_Entity_Class.class,...);
```  
*Code snippet 4: creating an `Object_Pool<Foo_Bar_Entity_Class_Factory>` without an existing `Object_Factory`*  

**NOTE:** Since version `0.5.7`, internal context(`Pool_Context`) was removed, same for `Object_Pool_Type_Wrapper<E>` as the `Object_Pool<:Poolable_Object>` is generic now.

## Installation
Repository is available in maven central repository(and its dependencies). Or you may perform a local build by clone and build it using eitehr maven(recommended) or ant.

### Maven Dependency
Simply add the the following `depedency` to your `pom.xml` maven file.  

```xml
<dependency>
  <groupId>com.github.911992</groupId>
  <artifactId>WAsys_simple_generic_object_pool</artifactId>
  <version>0.5.7</version>
</dependency>
```
*Code snippet 5: maven dependency*  
*you may also grab artifact(s) from the [central maven repo](https://search.maven.org/search?q=g:com.github.911992%20AND%20a:WAsys_simple_generic_object_pool) too.*  

### Build Using Maven
Clone the repo using `git clone https://github.com/911992/WAsys_simple_generic_object_pool.git`  
And build it using `mvn` as following
```
mvn clean dependency:copy-dependencies package
```
Generated `jar`(s)  will be available under `target` folder.

### Build Using Ant
Clone the repo using `git clone https://github.com/911992/WAsys_simple_generic_object_pool.git`  
And build it using `ant` as following
```
ant clean jar
```
Generated `jar` (without dependent artifacts)  will be available under `dist` folder.
*Not a maven project? this project is `ant` compatible too*  



## `Generic_Object_Pool` Activity | State Diagram
![Composition Structure Diagram](./_diagrams/object_pool_state_partial.svg)

### Sample Usage
You may check [this repo](https://github.com/911992/WAsys_simple_generic_object_pool_sample_usage)

## FAQ
**Q0: What if user forgets to release an `Poolable_Object`?**  
A: Please don't. The Default `Generic_Object_Pool` implementation may not keep instances of working instances, so for any forgotten releasing objects, there will be no any mem-leak from pool, but inconsistency.

**Q1: What if user forgets to implement the `reset_state(void):void` method on target `Poolable_Object` type?**  
A: Please don't. There will be no explicitly-related issue to pool context/object, but this may bring some inconsistency for user business part.

**Q2: What if calling for a new objects from an already shutdown `Object_Pool`?**  
A: `Object_Pool` will call the associated factory for object creation, but will not count new creation calls when a pool is in shutdown state.

## Troubleshooting
**T0: `Object_Pool::get_an_instance(void):Poolable_Object` instance, returns a `null` object**  
Possible situations:  
* This is related to related/given `Object_Factory`, make sure the implemented type works well.
* Pool is full, and user has initialized/set the `Full_Pool_Object_Creation_Policy` with related `Generic_Object_Pool_Policy` as **`Return_Null`**.


**T1: Thread endlessly wait on `Object_Pool::get_an_instance(void):Poolable_Object` call**  
Only debugging could say, but considerring following possible scenarios.
Reason 0: This is probably related to related/given `Generic_Object_Pool_Policy`, as `full_pool_instancing_policy := Wait_Till_One_Free`. It means current thread have to waint until anotehr thread release one object, so it could be recycled/reuse for current trhead/call.
Reason 1: Related/given `Object_Factory` causes the thread lock, make sure it's implemented right.

## Meta
Also considerign following libs/tools may related to this repo
0. [WAsys_Poolable_Object_generator_tool](https://github.com/911992/WAsys_Poolable_Object_generator_tool) tool to generate `Poolable_Object` entities
1. [WAsys_pojo_http_data_entity_tool](https://github.com/911992/WAsys_pojo_http_data_entity_tool) tool to generate `Fillable_Object` (and `Poolable_Object`) entities

## TODOs
- [ ] more control for pooled object at `Object_Pool` level (not sure yet!)