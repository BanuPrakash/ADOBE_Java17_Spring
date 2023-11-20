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


File -> New Project --> Intellij with Java; Name :moduleexample

module name as reverse-domain ==> artifactID + groupId

```
module moduleexample {
    exports com.adobe.prj.service; // what packages are exported
}
not exporting com.adobe.prj.util

module sample {
    requires moduleexample; // which module is used 
}
```

Resume @ 11:25






