# Eshop-services-rest
## Spring Boot REST based Shopping Cart Services

## About

This is a demo project for practicing SpringBoot + REST + Session with jdbc store type + LOMBOK.
 The idea is to build basic REST based shopping cart services like 
 - product browsing
 - shopping cart operations like add, remove products and view cart details
 - checkout - capturing payment method, order submission, place order and generate order id
 - view order details

It is based on 
    **Spring Boot**, 
    **Spring Security**, 
    **Spring Data JPA**,
    **Spring Data REST**
    **Spring JDBC sesson**
    **and Docker**. 
Database is in memory **H2**.

There is a login and registration functionality included and the session is stored using JDBC store type.

Users can shop for products. Each user has his own shopping cart (session functionality).
Checkout is transactional.


### REST API details
1. login : http://localhost:8070/api/rest/login
   set base authentication details (username and password)
   testadmin/password
2. Get products url : http://localhost:8070/api/rest/product/browse

3. Add products url : http://localhost:8070/rest/api/shoppingcart/addProduct/4

4. Remove products url : http://localhost:8070/rest/api/shoppingcart/removeProduct/1

5. View shopping cart details url: http://localhost:8070/rest/api/shoppingcart/

6. Submit cart passing payment type : http://localhost:8070/rest/api/order/submit/{CREDITCARD}

7.  View order (confirmation) url : http://localhost:8070/rest/api/order/1

## Configuration

### Configuration Files

Folder **src/resources/** contains config files for **eshop-services-rest* Spring Boot application.

* **src/resources/application.properties** - main configuration file. Here it is possible to change admin username/password,
as well as change the server port number.

## How to run

There are several ways to run the application. You can run it from the command line with included Maven Wrapper, Maven or Docker. 

Once the app starts, go to the web browser and visit `http://localhost:8070/home`

User username: **testadmin**

User password: **password**

### Maven Wrapper

#### Using the Maven Plugin

Go to the root folder of the application and type:
```bash
$ chmod +x scripts/mvnw
$ scripts/mvnw spring-boot:run
```

#### Using Executable Jar

Or you can build the JAR file with 
```bash
$ scripts/mvnw clean package
``` 

Then you can run the JAR file:
```bash
$ java -jar target/eshop-services-rest-0.0.1-SNAPSHOT.jar
```

### Maven

Open a terminal and run the following commands to ensure that you have valid versions of Java and Maven installed:

```bash
$ java -version
java version "1.8.0_102"
Java(TM) SE Runtime Environment (build 1.8.0_102-b14)
Java HotSpot(TM) 64-Bit Server VM (build 25.102-b14, mixed mode)
```

```bash
$ mvn -v
Apache Maven 3.3.9 (bb52d8502b132ec0a5a3f4c09453c07478323dc5; 2015-11-10T16:41:47+00:00)
Maven home: /usr/local/Cellar/maven/3.3.9/libexec
Java version: 1.8.0_102, vendor: Oracle Corporation
```

#### Using the Maven Plugin

The Spring Boot Maven plugin includes a run goal that can be used to quickly compile and run your application. 
Applications run in an exploded form, as they do in your IDE. 
The following example shows a typical Maven command to run a Spring Boot application:
 
```bash
$ mvn spring-boot:run
``` 

#### Using Executable Jar

To create an executable jar run:

```bash
$ mvn clean package
``` 

To run that application, use the java -jar command, as follows:

```bash
$ java -jar target/eshop-services-rest-0.0.1-SNAPSHOT.jar
```

To exit the application, press **ctrl-c**.

### Docker

It is possible to run **Eshop-services-rest** using Docker:

Build Docker image:
```bash
$ mvn clean package
$ docker build -t eshop-services-rest:dev -f docker/Dockerfile .
```

Run Docker container:
```bash
$ docker run --rm -i -p 8070:8070 \
      --name shopping-cart \
      shopping-cart:dev
```

##### Helper script

It is possible to run all of the above with helper script:

```bash
$ chmod +x scripts/run_docker.sh
$ scripts/run_docker.sh
```

## Docker 

Folder **docker** contains:

* **docker/shopping-cart/Dockerfile** - Docker build file for executing shopping-cart Docker image. 
Instructions to build artifacts, copy build artifacts to docker image and then run app on proper port with proper configuration file.

## Util Scripts

* **scripts/run_docker.sh.sh** - util script for running shopping-cart Docker container using **docker/Dockerfile**

## Tests

Tests can be run by executing following command from the root of the project:

```bash
$ mvn test
```

## Helper Tools

### HAL REST Browser

Go to the web browser and visit `http://localhost:8070/`

You will need to be authenticated to be able to see this page.

### H2 Database web interface

Go to the web browser and visit `http://localhost:8070/h2-console`

In field **JDBC URL** put 
```
jdbc:h2:mem:shopping_cart_db
```

In `/src/main/resources/application.properties` file it is possible to change both
web interface url path, as well as the datasource url.
