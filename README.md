# NAB Assessment

## Table of Contents:
- [1. Problem Statement](#1-problem-statement)
- [2. Entity Relationship Diagram](#2-entity-relationship-diagram)
- [3. Architecture Design](#3-architecture-design)
- [4. Software Development Principles](#4-software-development-principles)
- [5. Java Libraries and Frameworks](#5-java-libraries-and-frameworks)
- [6. Services Detail](#6-services-detail)
- [7. Code Folder Structure](#7-code-folder-structure)
- [8. Unit Test](#8-unit-test)
- [9. Setup To Run On Local Computer](#9-setup-to-run-on-local-computer)
- [10. CURL Commands](#10-curl-commands)


## 1. Problem Statement
A small start-up named "iCommerce" wants to build a very simple online shopping
application to sell their products. In order to get to the market quickly, they just want to
build an MVP version with a very limited set of functionalities:
1. The application is simply a simple web page that shows all products on which
customers can filter, sort, and search for products based on different criteria such as
name, price, brand, color, ...
2. All product prices are subject to change at any time and the company wants to keep
track of it.
3. A product always shows the latest price when showing on the website
4. If the customer finds a product that they like, they can add it to their shopping cart
and proceed to place an order.
5. For audit support, all customers' activities such as searching, filtering, and viewing
product details need to be stored in the database.
However, failure to store customer activity is completely transparent to customers
and should have no impact on the activity itself.
6. Customers can log in simply by clicking the button “Login with Facebook”. No further
account registration is required.
7. No online payment is supported yet. The customer is required to pay by cash when
the product got delivered.

## 2. Entity Relationship Diagram
![ERD](https://github.com/taivtse/nab-icommerce-assessment/blob/master/docs/ERD.png)

## 3. Architecture Design
![ArchitectureDesign](https://github.com/taivtse/nab-icommerce-assessment/blob/master/docs/ArchitectureDesign.png)

## 4. Software Development Principles
There are software development principles that I applied in the project: <br />

**1. SOC (Separation of concerns):** <br />
Because the project was built according micro-services architecture, so the first thing I need to do was separate a large overall picture into specific concerns. 
Listed entities and figure out which of them related to a bussiness domain then indicated it as bounded context.
By doing this, I have already separated the project requirement to micro-services. 
Each service only serves for a certain funtionalities of a concerns and can scale independently.

**2. CQRS (Command Query Responsibility Segregation):** <br />
In the Product domain, I divided it into 2 services. One for reading purpose (`product-query-service`) and another one for writing purpose (`product-command-service`).
In the real-world, any ecommerce platform have a lot of products. Beside that, reading and writing data ratio are difference. If we combine reading and writing data in one service, it can be affected by each other.
By divide it into 2 parts, we can choose technologies for each part to achieve the best performance and scale independently way, for example 1 instance for writing service and 3 instances for reading service if reading ratio is larger than writing ratio.

**3. DRY (Don't Repeat Yourself):** <br />
There are 2 common modules: common-shared and protobuf-shared. They are provide boilerplate code and can be shared for all other micro-services to avoid repeating code.

**4. YAGNI (You aren't gonna need it):** <br />
In the project, I only implemented functionalities for currenty requirements. I do not add more methods, or class for furture purposes, but the system is designed with flexible expansion capacity.

**5. Law of Demeter:** <br />
To avoid services uncontrollably interdependent and risk leading to cyclic dependencies, I have applied the following rules: 
* There are 2 layers: low level services (core services) and high level services (api-gateway, background workers)
* All services in the same layer must not be able communicate directly with each other. They only serve apis for higher level services.
* High level services can communicate with low level services.
* If low level services need some data from another service, it should claim the missing data from higher level services.

**6. SOLID:** <br />
I also applied SOLID pricipal in the project.
* Each class is designed according to the Single Responsibility Principle.
* All implementation class of an interface do not alter the correctness of the program by follow the Liskov Substitution Principle.
* Interfaces are broken down and provide a certain amount of work, avoid a large interface by follow Interface Segregation Principle.
* High level modules and low level modules must depend on abstractions, not on concretions by follow Dependency Inversion Principle. Using DI supported by Spring Framework to doing this.

## 5. Java Libraries and Frameworks
* **Spring Boot:** main framework for all micro-services.
* **LogNet Spring Boot GRPC:** implement Grpc services.
* **Spring Cloud Netflix Eureka:** implement discovery service, services communicate via service name.
* **Spring Data JPA, Spring Data Redis, Spring Data Elasticsearch:** connect to data stores.
* **Spring Kafka:** asynchronous communication of inter-services.
* **Spring AOP:** using in api-gateway to tracking customer ativities.
* **Spring Security + JJWT:** implement security by using JWT. 
* **Junit:** writing unit tests.

## 6. Services Detail
![ServicesDetail](https://github.com/taivtse/nab-icommerce-assessment/blob/master/docs/ServicesDetail.png)

## 7. Code Folder Structure
1. Low level services package struture:
* **Helper:** utilities classes.
* **Config:** components and beans configuration.
* **Domain:** contains classes mapping with database, these class reflect database structure.
* **Mapper:** convert Domain to Proto and vice versase.
* **Repository:** this is the layer communicate directly to database, read/write operations to database.
* **Service:** implements bussiness logics. Higher level than Repository package and use it as dependencies.
* **Classes annotated with @GRpcService:** implements Grpc interface, use Service package as it's dependencies.
* **Classes annotated with @SpringBootApplication:** Main class to run Spring Boot application.

2. High level services package struture:
* **Config:** components and beans configuration.
* **Dto:** request and response data transfer objects.
* **Client:** wrapper class used to communicate with orther service.
* **Mapper:** convert Dto to Proto and vice versase.
* **Aspect:** implement Aspect Oriented Programming.
* **Kafka:** Kafka consumers handler.
* **Controller:** expose public Rest Api and handle bussiness logic.
* **Security:** setup authentication/authorization.
* **Classes annotated with @SpringBootApplication:** Main class to run Spring Boot application.

## 8. Unit Test
There are a number of unit testing in `order-service`. 
The unit testing applied for helper, mapper, service impl package, they consist of 21 test cases.

## 9. Setup To Run On Local Computer
**NOTE:** There are a number of backing services that I have already setup and deployed on AWS to save your time. Let's focus on the services I have built in depth. They include:
* MySQL
* Redis
* Elasticsearch
* Kafka
* Debezium (CDC): Synchronize data from MySQL, namely the table `product` in the `nab_product` database to Elasticsearch.

**Setup local services:**
Run with IntelliJ IDEA:
1. Clone the source code and open with InteliJ IDEA
2. Open the run configuration tab, I've set them up and arranged them according to their dependencies.
3. Run Maven Compile one by one in the following order:
  <img src="https://github.com/taivtse/nab-icommerce-assessment/blob/master/docs/MavenCompileOrdering.png" alt="MavenCompileOrdering" height="300"/>

4. Run Spring Boot Application one by one in the following order:
  <img src="https://github.com/taivtse/nab-icommerce-assessment/blob/master/docs/RunApplicationOrdering.png" alt="RunApplicationOrdering" height="300"/>

Done. All services already started, you can use the below cURL to test the application.

## 10. CURL Commands
There is the Postman collection, you can import it to your Postman and use it for a more visual view: <br />
https://www.getpostman.com/collections/c07a126b0c4d21ad9a3a

### There are some rule for api response data:
1. All api return data with the following structure: <br />
```json
{
  "code": 0,
  "message": "",
  "data": {}
}
```
2. `code`: SUCCESS: code >= 0, FAIL code < 0 
3. `message`: Error message when FAIL
4. `data`: Response data, it can contain any of data types
5. When calling api to create a new object, if successful, the field data in the response returns the Id of the created object.

### Customer Login:
This api simulate Login With Facebook feature.
```shell
curl -L -X POST 'http://localhost:8080/api/customers/login' \
-H 'Content-Type: application/json' \
--data-raw '{
    "name": "Võ Thành Tài",
    "email": "taivtse@gmail.com",
    "provider": "FACEBOOK",
    "providerId": "A16E518CAF8CD1A99917F3841928D",
    "photoUrl": "image",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTYxOTAxOTgxMywiaWF0IjoxNjE5MDE5ODEzfQ.fnmHbry8qVQsSYJexgNuDVX4tsciEfDmi_c_v-kNI9Q",
    "clientId": "98123841002394711283",
    "clientSecret": "3DS9v9KWpt3TeCGWcISuOZOrMtunHn4c"

}'
```
Payload:
``name``: customer name.
``email``: customer email.
``provider``: login provider, "FACEBOOK" for example.
``providerId``: the user ID provided by Facebook.
``photoUrl``: customer photo url.
``token``: customer social token issued by Facebook.
``clientId``: Facebook application id.
``clientSecret``: Facebook application client secret key.

Sample response:

### Get Customer Infomation:
```shell
curl -L -X GET 'http://localhost:8080/api/customers/1' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA'
```

Sample response:


### Get Customer Activities:
```shell
curl -L -X GET 'http://localhost:8080/api/customers/1/activities' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA'
```

Sample response:


### Search Products:
```shell
curl -L -X POST 'http://localhost:8080/api/products/search' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA' \
-H 'Content-Type: application/json' \
--data-raw '{
    "name": "",
    "brand": "",
    "priceFrom": 0,
    "priceTo": 0,
    "color": "",
    "unit": "",
    "paging": {
        "index": 0,
        "size": 100
    },
    "sorting": {
        "property": "id",
        "direction": "ASC"
    }
}'
```
That is the default search criteria with paging and sorting operations, you can change the search data to get the information you are looking for.

Sample response:

### Get Product Detail:
```shell
curl -L -X GET 'http://localhost:8080/api/products/2' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA'
```

Sample response:

### Get Product Price Histories:
```shell
curl -L -X GET 'http://localhost:8080/api/products/2/price/histories' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA'
```

Sample response:

### Add Items To Cart:
```shell
curl -L -X POST 'http://localhost:8080/api/carts/customer' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA' \
-H 'Content-Type: application/json' \
--data-raw '{
    "customerId": "1",
    "items": [
        {
            "productId": 1,
            "quantity": 3
        },
        {
            "productId": 2,
            "quantity": 1
        }
    ]
}'
```

Sample response:


### Get Customer Cart:
```shell
curl -L -X GET 'http://localhost:8080/api/carts/customer/1' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA'
```

Sample response:

### Delete Customer Cart:
```shell
curl -L -X DELETE 'http://localhost:8080/api/carts/customer/1' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA'
```

Sample response:

### Place Order:
```shell
curl -L -X POST 'http://localhost:8080/api/orders/customer/1' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA'
```

Sample response:


### Get Order Information:
```shell
curl -L -X GET 'http://localhost:8080/api/orders/1' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA'
```

### Get Customer Ordders:
```shell
curl -L -X GET 'http://localhost:8080/api/orders/customer/1' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA'
```

Sample response:


### APIs For Admin:
Here are some APIs I made for Admin, using simply by logging in as customer, but not implementing the user authorization feature yet.

#### Create product:
```shell
curl -L -X POST 'http://localhost:8080/api/products' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA' \
-H 'Content-Type: application/json' \
--data-raw '{
    "name": "Iphone XS MAX",
    "brand": "Samsung",
    "price": 29000000,
    "color": "gold",
    "image": "",
    "unit": "piece"
}'
```

#### Update product:
```shell
curl -L -X PUT 'http://localhost:8080/api/products' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA' \
-H 'Content-Type: application/json' \
--data-raw '{
    "id": 2,
    "name": "Macbook air 2020",
    "brand": "Samsung",
    "price": 29000000,
    "color": "gold",
    "image": "",
    "unit": "piece"
}'
```

#### Update order status:
```shell
curl -L -X PUT 'http://localhost:8080/api/orders/status' \
-H 'Content-Type: application/json' \
--data-raw '{
    "orderId": 12,
    "status": "COMPLETED"
}'
```
Order status flow: INIT -> PROCESSING -> DELIVERING -> COMPLETED or DROP_OFF_FAIL

# Thank you very much and hope to see you at NAB
