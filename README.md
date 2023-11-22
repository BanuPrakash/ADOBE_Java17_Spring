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

* JPMS
* Pattern Matching Switch statement
* sealed
* CDS
* ADS
* jshell
* var --> java 11 feature
*
variables can be declared with "var" --> statically typed

HashMap<String,List<String>> myData = new HashMap<String,List<String>>();
becomes
var myData = new HashMap<String,List<String>>();

for(var data : myData.entrySet()) {

}

Where "Var" can't be used:
1) var a; // cannot infer to a type
2) var nothing = null;
3) var lambda = () -> System.out.println("AAA");

var usage in lambda parameters

```
 public static void main(String[] args) {
        Predicate<Integer> p1 = (@NotNull Integer t) -> true; // regular way of using annotation
       // Predicate<Integer> p2 = (@NotNull  t) -> true; // invalid
        Predicate<Integer> p3 = (@NotNull var t) -> true; // regular way of using annotation

    }
```

* Garbage Collection
--> Concurrent Mark Sweep GC is depreicated --> Java 11
EDEN Area / New Generation --> newly created objects reside
Surviour Stage 1 and Surviour Stage 2 area was to used to move object which survied Short term GC [ collect unused objects in New Generation] into Old Generation
Old Generation: any objects which survived 3 cycles of Short term GC was moved to Old Generation

--> New GC 
a) Epsilon GC: No-op GC
allows memory allocation, no removing of unused memory
 FinancialApp --> Start the app @ 9:00 and shutdown by 5:00

 java MemoryPolluter 

java -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC MemoryPolluter
Terminating due to java.lang.OutOfMemoryError: Java heap space

b) G1GC
--> default

---------------------

* enahancements in Collection API
takeWhile(), takeUtil(). teeing Collector

----------------------

Spring Boot 3 --> needs Java 17 version

=========================================================

Spring / Spring Boot and JPA

Spring Framework : provided a Inversion of Control container for building enterprise application


Bean --> any object managed by spring container is a bean

Java 1.2 --> bean --> any re-usable software component


Dependency Injection is done using IoC

Spring Container is created using meta-data [XML or Annotation]

using XML as metadata:
```

interface EmployeeDao {
	addEmployee(Employee e);
}

public class EmployeeDaoMongoImpl implements EmployeeDao {
	addEmployee(...)
}

public class EmployeeDaoSQLImpl implements EmployeeDao {
	addemployee(...)
}

public class AppService {
	EmployeeDao empDao; // loosly coupled

	public void setDao(EmployeeDao edao) {
		this.empDao = edao;
	}

	insert(Employee e) {
		this.empDao.addEmployee(e);
	}
}

beans.xml
<beans>
	<bean id="mongo" class="pkg.EmployeeDaoMongoImpl" />
	<bean id="sql" class="pkg.EmployeeDaoSQLImpl" />
	<bean id="service" class="pkg.AppService">
		<property name="dao" ref="sql" />
	</bean>
</beans>

ApplicationContext ctx = new ApplicationContext("beans.xml");
```

Using Annotation: 
Advantage --> Java Compiler can check/validate
XML --> SAXParser is required, IDE doesn't pick issues 

```

@Repository
public class EmployeeDaoMongoImpl implements EmployeeDao {
	addEmployee(...)
}

public class EmployeeDaoSQLImpl implements EmployeeDao {
	addemployee(...)
}

@Service
public class AppService {
	@Autowired
	EmployeeDao empDao; // loosly coupled
	insert(Employee e) {
		this.empDao.addEmployee(e);
	}
}

AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
ctx.scan("com.adobe"); // scan com.adobe and it's sub packages for the below annotations at class-level
ctx.refresh();


1) @Component ==> utility 
2) @Repository ==> CRUD
3) @Service ==> Service Facade
4) @Configuration ==> usually for reading configurations and factory methods
5) @Controller ==> MVC
6) @RestController ==> RESTful
7) @ControllerAdvice ==> GlobalExceptionHandler for Web application

and creates instances of the class

a) @Service
public class AppService {

creates instance as "appService"

b) @Repository
public class EmployeeDaoMongoImpl implements EmployeeDao {

creates instance as "employeeDaoMongoImpl"
```

Wiring can be done using @Autowired, @Inject or constructor DI

Spring uses "Byte Buddy" , "Java Assist", "CG Lib" for byte-code instrumentation and creating proxies
@Autowired
EmployeeDao empDao;

can get changed to
EmployeeDao empDao = new EmployeeDaoJdbcImpl();
or
EmployeeDao empDao = EmployeeDao.factoryEmployeeDao();

----------

Spring Boot?
* Framework on top of Spring Framweork
Spring boot 2.x is a framework on top of Spring Framework 5.x
Spring boot 3.x is a framework on top of Spring Framemork 6.x

Why Spring boot?
1) highly opiniated: lot's of configuration comes out of the box
a) If we want to develop web application --> provides embedded Tomcat container
b) If we use RDBMS --> provides DataSource [ pool of database connection] using HikariCP library
c) if we use JPA/ORM --> provided Hibernate as JPA Vendor
2) Not much difference between Standalone and Web based application ==> both are started using main()
3) creation of Spring Container is already configured


https://start.spring.io/

1) @SpringBootApplication
is made of 3 annoations
a) @ComponentScan
--> scans for above mentioned annoations in "com.adobe.springdemo" and it's sub-packages
and creates instances of them
com.example.springdemo <-- won't scan

b) @EnableAutoConfiguration
--> creates built-in configured objects --> highly opiniated
create DataSource, JPAVendor

create on ConditionalMissingBean, ConditionalOnProperty,,,

c) @Configuration 

2) SpringApplication.run(SpringDemoApplication.class, args);
this creates a Spring Container

ApplicationContext ctx = SpringApplication.run(SpringDemoApplication.class, args);

http://server/api/products

@RequestMapping("api/products") searchs only in classes which are marked as @controller or @RestController

@Repository
Unified way of generating Exception for RDBMS
https://github.com/spring-projects/spring-framework/blob/main/spring-jdbc/src/main/resources/org/springframework/jdbc/support/sql-error-codes.xml

try {

} catch(SQLException ex) {
	if(ex.getErrorCode() === 2011) {
		throw new DuplicateKeyException(ex.getMessage())
	}
}

====

Day 3:

Recap: sealed, pattern matching, record, G1GC, EpsilonGC, var, jshell, ..
Spring boot: @SpringBootApplication --> @ComponentScan, @EnableAutoConfiguration and @Configuration, @Autowired

BeanFactory --> use it for only DependecyInjection
ApplicationContext --> DI, multiple context [ integrate with other technologies, ...]

More than one bean is qualified to be @Autowired
```
Solution 1: @Qualifier
 @Autowired
 @Qualifier("employeeDaoMongoImpl")
 private EmployeeDao employeeDao; // loosely coupled wire by type

Solution 2: @Primary

Remove Qualifer and
@Repository
@Primary
public class EmployeeDaoMongoImpl implements  EmployeeDao {

Solution 3: @Profile
@Repository
@Profile("prod")
public class EmployeeDaoMongoImpl implements  EmployeeDao {

@Repository
@Profile("dev")
public class EmployeeDaoSqlImpl implements  EmployeeDao {

a) application.properties / application.yml
spring.profiles.active=prod

b) Program Arguments
Run > Edit Configurations > Active profile: dev

Program arguments has high priority over application.properties

System Properties > Program arguments > configuration file [application.properties / application.yml]

Solution 4:
@Repository
@ConditionalOnMissingBean("employeeDaoMongoImpl")
public class EmployeeDaoSqlImpl implements  EmployeeDao {
}

Solution 5:
@ConditionalOnProperty(name = "dao", value = "MONGO")
public class EmployeeDaoMongoImpl implements  EmployeeDao {

application.properties
dao=MONGO

```

Spring Factory methods:
methods which return an object; returned object is managed by spring container
* 3rd party classes doesn't contain @Component, @Repository, @Service ,,,
* Customize way of creating object

Building RESTful WS with JPA

ORM --> Object Relational Mapping
Java Object <----> Relational Database table
fields <---> columns of database table

ORM generates SQL for CRUD operations and provides apis to do the same

ORM Frameworks: JDO, Hibernate, TopLink, KODO, OpenJPA, EclipseLink,....

JavaPersistence API is a specification for ORMs

Application --> JPA --> ORM Frameworks --> JDBC --> RDBMS

ORM has default first level cache; can add second level cache

PersistanceContext is a container to manage entities

EntityManagerFactory

====

```
@Configuration
public class AppConfig {

	// factory method
	@Bean(name="dataSource")
	public DataSource getDataSource() {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver            
		cpds.setJdbcUrl( "jdbc:postgresql://localhost/testdb" );
		cpds.setUser("swaldman");                                  
		cpds.setPassword("test-password");                                  
		cpds.setMinPoolSize(5);                                     
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		return cpds;
	}

	@Bean
	public LocalContainerEntityManagerFactory emf(DataSource ds) {
		LocalContainerEntityManagerFactory emf = new LocalContainerEntityManagerFactory();
		emf.setDataSource(ds);
		emf.setJpaVendor(new HibernateJpaVendor());
		..
		return emf;
	}
}


@Service
public class AppService {
	@Autowired
	DataSource ds;

	@PersistenceContext
	EntityManager em;

	public void addProduct(Product p) {
		em.save(p);
	}
}

```

Spring Data Jpa --> is a layer on JPA simples CRUD operation
a) creates DataSource using entiries present in application.properties
b) creates EntityManagerFactory using entiries present in application.properties
c) provides implementations classes for interfaces of type JpaRepository extends CrudRepository

```
public interface ProductDao extends JpaRepository<Product, Integer> {

}
```


Spring Data JPA creates implmentation class for the "ProductDao" interface which has pre-defined methods for CRUD. [ means no @Repository class]

@Autowired
ProductDao productDao; // object of  generated implementiton class is wired


Spring Initilizer project :

mysql, jpa [ spring-data-jpa], lombok [code generation]

By including Spring Data JPA we get:
HikariCP, Hibernate as JPA Vendor

https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html

hbm-ddl operations: create, update, verify
create: DDL on start of application; delete tables on application exit --> good for testing env
update: create table if not exists, use tables if exist, alter table if required
verify: We already have tables--> use them if mapping is appropriate. Don't allow alter

spring.jpa.hibernate.ddl-auto=update

```
products
id  | name | price | qty | ver
2	  A
3 	  B

customers
email 			| first_name
sam@adobe.com		Samantha
rita@adobe.com		Rita

orders
oid | order_date 			| customer_fk 		| total
120	  20-1-2023 4:30:12		  rita@adobe.com		98493
121	  21-1-2023 5:12:10		  rita@adobe.com		9000


items
item_id | quantity | amount | product_fk | order_fk
89			1			8989	3			120
90			2			8222	2			120


@JoinColumn with @ManyToOne FK is added to owning side
@JoinColumn with @OneToMany FK is added to child side

```

CascadeType.ALL

order has 5 items

em.save(order); // this saves 5 items also

em.delete(order); // this deletes order and it's entites


Without cascade:
em.save(order);
em.save(item1);
em.save(item2);
em.save(item3);
em.save(item4);
em.save(item5);

=====================





