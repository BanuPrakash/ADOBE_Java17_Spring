# ADOBE_Java17_Spring

Banuprakash C

Full Stack Architect, Corporate Trainer

Co-founder & CTO: Lucida Technologies Pvt Ltd., 

Email: banuprakashc@yahoo.co.in


https://www.linkedin.com/in/banu-prakash-50416019/

https://github.com/BanuPrakash/ADOBE_Java17_Spring

===================================

Softwares Required:
1)  openJDK 17
https://jdk.java.net/java-se-ri/17

2) IntelliJ Ultimate edition 
https://www.jetbrains.com/idea/download/?section=mac

OR

Eclipse for JEE  
	https://www.eclipse.org/downloads/packages/release/2022-09/r/eclipse-ide-enterprise-java-and-web-developers

3) MySQL  [ Prefer on Docker]

Install Docker Desktop

Docker steps:

```
a) docker pull mysql

b) docker run --name local-mysql –p 3306:3306 -e MYSQL_ROOT_PASSWORD=Welcome123 -d mysql

container name given here is "local-mysql"

For Mac:
docker run -p 3306:3306 -d --name local-mysql -e MYSQL_ROOT_PASSWORD=Welcome123 mysql


c) CONNECT TO A MYSQL RUNNING CONTAINER:

$ docker exec -t -i local-mysql bash

d) Run MySQL client:

bash terminal> mysql -u "root" -p

mysql> exit

```

1) Java 9 to 17 LTS version --> new features
2) Spring Boot RESTful and Async operations like webflux, async,.. along with JPA [entityGraph, Specification,..]
3) Spring Boot Security
4) Microservices


JPMS : Java 9 feature
Java Platform Module System / Jigsaw Project ==> modules

mylib.jar
```
	package pkg1;
	public class SampleService {
		// code uses SampleRepo
		@Secure("ADMIN")
		transaction(){
			repo.update();
			repo.insert()
		}
	}

	package pkg2;
	public class SampleRepo {
		// code
		add()
		delete()
		update()
	}
```

Other projects uses mylib.jar ==> Maven / Gradle or add jar to classpath
Other projects has access to SampleService and SampleRepo because both are "public"
I can't restrict usage of SampleService only

To Solve this we had OSGi [ Open Service Gateway Intiative] ==> is a framework providing containers for loading modules. It came up with different classloaders for different modules. Dynamically load / remove modules

Running WordApplication ==> we can add/remove SpellCheck module.
MANIFEST.MF
EXPORT-packages: pkg1
IMPORT-packages: com.adobe.aem, com.adobe.util

The Java Platform Module System (JPMS) was developed to make it easier for developers to organize large applications and libraries. 
JPMS also aims to improve the structure and security of the platform and JDK.

JPMS's goals include: 
Improving the structure and security of the platform and JDK
Improving app performance
Better handling decomposition of the platform for smaller devices
Addressing problems like ClassPath/JAR Hell and Massive Monolithic JDK

----------
AWS EC2 / EKS --> Node Size 

Dockerfile
FROM:openjdk8:alphine
COPY target/app.jar app.jar
CMD java -jar app.jar

Problem:
"rt.jar" and "jce.jar"

rt.jar --> java.util, java.text, java.sql, java.xml,....

public class Sample {
	public static void main(String[] args) {

	}
}

JPMS --> instead of using "rt.jar" --> we use modules; java.base; java.sql
we can build a image with application code + required modules --> self contained application

Dockerfile
COPY target/app.jar app.jar
CMD java -jar app.jar


============================

Module Types:
1) System modules --> included with Java SE
```
java --list-modules 
has 70 modules

java --describe-module java.sql
java.sql@17.0.5
exports java.sql
exports javax.sql
requires java.transaction.xa transitive
requires java.logging transitive
requires java.base mandated
requires java.xml transitive
uses java.sql.Driver
```

2) Named Modules / Application Modules --> this is what we build. this is what build.
this contains module-info.java ==> module-info.class

a) Single project can also contain multiple modules

javac --module-source-path src -m main.app,core.app  -d out
java --module-path out -m main.app/example.app.AppMain

-m is module
--module-path instead of -cp [classpath]
-p is short cut of --module-path

File -> New Project --> Intellij with Java; Name :moduleexample

module name as reverse-domain ==> artifactID + groupId


3) Automatic Modules
We can add unofficial existing "jar" files [without module-info.jar] to module path.
many libraries as of now are not JPMS based including "spring libraries" --> not named module

javac --module-path "spring.core.jar" 
spring becomes automatic modules; normaly name of the jar becomes the module name or if the jar contains MANIFEST.MF file --> where module name can be mentioned

module myapp {
	requires spring.beans;
	requires spring.core;
}

4) unnamed module:
jar file is added to classpath, but not to module path.
--> for downward comptibility
jar if it contains module-info.java --> it's ignored. everything in jar which is public is accessble



```
module moduleexample {
    exports com.adobe.prj.service; // what packages are exported
}
not exporting com.adobe.prj.util

module sample {
    requires moduleexample; // which module is used 
}
```

moduleexample --> Named Module
with 2 packages
module moduleexample {
    exports com.adobe.prj.service; // what packages are exported
}

sample --> Named module
is using moduleexample
module sample {
    requires moduleexample; // which module is used 
}

Intellij we need to Module dependencies [sample needs moduleexample]
--> Add module-path and not classpath


ServiceLoader upto Java 8:
in client
META-INF/services/com.example.CodecFactory	
com.example.impl.StandardCodecs # Standard codecs


mvn package

creates "jar" files

Windows:
java -p client/target/client-1.0.0.jar;api/target/api-1.0.0.jar;impl/target/impl-1.0.0.jar -m client/client.Main

Mac:
java -p client/target/client-1.0.0.jar:api/target/api-1.0.0.jar:impl/target/impl-1.0.0.jar -m client/client.Main

--
Create Image:

```
The jlink tool links a set of modules, along with their transitive dependencies, to create a custom runtime image.

jlink --module-path client/target/client-1.0.0.jar:api/target/api-1.0.0.jar:impl/target/impl-1.0.0.jar --add-modules client,api,impl --output myimage --launcher MYAPP=client/client.Main

myimage/bin> ./MYAPP
```

Day 2

Recap:
Java 9 feature --> JPMS
module <module-name> {
	exports package
	requires module
	provides Interface with Impl
	uses Interface
}

* system-modules --> jdk 9+ ==> ~70 modules are there
java.base mandated --> no need for requires java.base;
requires java.sql; --> explicitly added

* Named module --> contains module-info.java

* Automatic modules --> any jar added to module-path [not classpath] 
* unnamed module --> no module-info.jar and we use classpath

--> JLink to create a Image --> image size will be mianture one compared to full JDK [rt.jar and jce.jar] used

--> Maven Module and JPMS module

--------------------------------

sealed: Java 15
-> sealed, final

1) A sealed type has a fixed set of direct subtypes
2) direct subtype must be listed in "permits" clause.
3) If a single source file contains subtypes then no need for "permits"
4) direct subtypes of a sealed type must be final, sealed or non-sealed
5) Helps in java 19 pattern matching

```
sealed interface JSONValue permits JSONObject,JSONArray, JSONPrimitive  {
}

final class JSONObject implements JSONValue {

}

final class JSONArray implements JSONValue {

}

sealed class JSONPrimitive implements JSONValue permits JSONString, JSONBoolean, JSONNumber{
}

final class JSONString extends JSONPrimitive {

}

------

sealed class Node permits Element, CDATASection, Text, Comment {

}

final class Text extends Node {

}

// Elements has many HTML Elements like HTMLInputElement, HTMLButtonElement, HTMLDivElement
non-sealed class Element extends Node {

}

we can have:

class HTMLInputElement extends Element {

}

```

Pattern matching: preview feature in 17.

TrafficLight.java
```
public sealed interface TrafficLight { // no need for permits if subtypes are in single source file
}

final class RedLight implements  TrafficLight {}
final class GreenLight implements  TrafficLight {}
final class YellowLight implements  TrafficLight {}

```
Main.java
```
// no need for default because we have fixed sub-types
public class Main {
    public static void main(String[] args) {
        TrafficLight light = new RedLight();
        return switch (light) {
            case RedLight r -> System.out.println("wait");
            case YellowLight r -> System.out.println("ready");
            case GreenLight r -> System.out.println("Go...");
        }
    }
}
```
javac --source 17 --enable-preview -Xlint:preview *.java
 java --enable-preview Main 

--------
java 9: jshell to execute statements, functions without a class and main()
% jshell
jshell> 


java 12 introduced arrow operator,typecasted variable in statement

```
// upto java 11 feature
Object obj = "Hello World";
if(obj instanceof String) {
	String s = (String) obj;
	int len = s.length();
	System.out.println(len);
}

// from java 12+
Object obj = "Hello World";
if(obj instanceof String s) {
	int len = s.length();
	System.out.println(len);
}
```

Arrow in switch:

```
// java 13 introduced yield
int getData(String mode) {
	int result = switch(mode) {
		case "a", "b" -> 1; // case "a", "b": yield 1;
		case "c" -> 2;
		default -> -1;
	};
	return result;
}

case "a","b": 
	// do something
	yield "done";

```

Record --> java 14
Records --> to create immutable objects --> DTOs

```
public record PersonDTO(String name, int age){}
// code has constructor, getters, equals, hashCode and toString()

Person p = new PersonDTO("Sam", 35);
p.getName(); // or p.name()

looks like a @Value  of lombok

Can override constructor
public record PersonDTO(String name, int age) {
	public PersonDTO {
		if(age < 0) {
			throw new IllegalArgumentException("name is not valid");
		}
	}

	@Overide
	public String name() {
		return name.toUpperCase();
	}
}
```

Class Data Sharing [10] ==> built-in classes ==> classlist

Classloader --> 
	findLoadedClass(),loadClass(), findSystemClass(), verifyClass(), defineClass() --> time consuming process

sudo java -Xshare:dump

uses /Library/Java/JavaVirtualMachines/jdk-17.0.5.jdk/Contents/Home/lib/classlist and creates a memory mapped bytecode after doing load class, verify, define class:

/Library/Java/JavaVirtualMachines/jdk-17.0.5.jdk/Contents/Home/lib/server/classes.jca

Java 9 feature: execute "source code:
java -Xshare:on -Xlog:class+load Sample.java >> a.txt
java -Xshare:off -Xlog:class+load Sample.java >> a.txt

 java 12: Application Data Sharing
 can add our classes also into "memory mapped share"

java -jar AppCDS.jar
0.919 seconds

java -XX:ArchiveClassesAtExit=appCDS.jsa -jar AppCDS.jar

java -XX:SharedArchiveFile=appCDS.jsa -jar AppCDS.jar
0.68 seconds 

java -XX:SharedArchiveFile=a.jsa -XX:DumpLoadedClassList=test.lst Sample  

Node instance --> appCDS.jsa

pod1 
java -XX:SharedArchiveFile=appCDS.jsa -jar AppCDS.jar
pod2 
java -XX:SharedArchiveFile=appCDS.jsa -jar AppCDS.jar

JVM --> Metaspace, Stack, Heap

======

Resume @ 11:40

