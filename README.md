# NAB Assessment

## Table of Contents:
- [1. Problem Statement](#1-problem-statement)
- [2. Entity Relationship Diagram](#2-entity-relationship-diagram)
- [3. System Architecture](#3-system-architecture)
- [4. Sequence Diagram](#4-sequence-diagram)
- [5. Software Development Principles](#5-software-development-principles)
- [6. Java Libraries and Frameworks](#6-java-libraries-and-frameworks)
- [7. Services Detail](#7-services-detail)
- [8. Code Folder Structure](#8-code-folder-structure)
- [9. Unit Test](#9-unit-test)
- [10. Setup To Run On Local Computer](#10-setup-to-run-on-local-computer)
- [11. CURL Commands](#11-curl-commands)


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

## 3. System Architecture
![ArchitectureDesign](https://github.com/taivtse/nab-icommerce-assessment/blob/master/docs/ArchitectureDesign.png)

## 4. Sequence Diagram
### Tracking customer activity:
![TrackingCustomerActivitySD](https://github.com/taivtse/nab-icommerce-assessment/blob/master/docs/TrackingCustomerActivitySD.png)

### Tracking product price changed:
![TrackingProductPriceChangedSD](https://github.com/taivtse/nab-icommerce-assessment/blob/master/docs/TrackingProductPriceChangedSD.png)

## 5. Software Development Principles
There are software development principles that I applied in the project: <br />

**1. SOC (Separation of concerns):** <br />
Since the project is built on a microservice architecture, the first thing I need to do is separate a broad big picture into specific concerns.
Entities are listed and find them related to the business domain then indicate it as the bound context.
By doing this, I split the project requirement into microservices.
Each service only caters to certain tasks of concern and is able to scale independently.

**2. CQRS (Command Query Responsibility Segregation):** <br />
In the Products domain, I divide it into 2 services. One is for read purposes (`product-query-service`) and the other for writing purposes (`product-command-service`). 
In the real world, any ecommerce platform has a plethora of products. Besides, the read and write rate is the difference. If we combine read and write data in a service, it can be affected by each other. 
By dividing it into 2 parts, we can choose the technology for each part to achieve best performance and scale independently, e.g. 1 instance for write service and 3 instance for read service if the read rate is greater than the write rate.

**3. DRY (Don't Repeat Yourself):** <br />
There are 2 common modules: common-shared and protobuf-shared. They are provide boilerplate code and can be shared for all other microservices to avoid repeating code.

**4. YAGNI (You aren't gonna need it):** <br />
In the project, I only implemented functionalities for currenty requirements. I do not add more methods, or class for furture purposes, but the system is designed with flexible expansion capacity.

**5. Law of Demeter:** <br />
To avoid services uncontrollably interdependent and risk leading to cyclic dependencies, I have applied the following rules: 
* There are 2 layers: low level services (core services) and high level services (api-gateway, background workers)
* All services in the same layer must not be able communicate directly with each other. They only serve apis for higher level services.
* High level services can communicate with low level services.
* If low level services need some data from another service, it should request the missing data from higher level services.

**6. SOLID:** <br />
I also applied SOLID pricipal in the project.
* Each class is designed according to the Single Responsibility Principle.
* All implementation class of an interface do not alter the correctness of the program by follow the Liskov Substitution Principle.
* Interfaces are broken down and provide a certain amount of work, avoid a large interface by follow Interface Segregation Principle.
* High level modules and low level modules must depend on abstractions, not on concretions by follow Dependency Inversion Principle. Using DI supported by Spring Framework to doing this.

## 6. Java Libraries and Frameworks
* **Spring Boot:** main framework for all microservices.
* **LogNet Spring Boot GRPC:** implement Grpc services.
* **Spring Cloud Netflix Eureka:** implement discovery service, services communicate via service name.
* **Spring Data JPA, Spring Data Redis, Spring Data Elasticsearch:** connect to data stores.
* **Spring Kafka:** asynchronous communication of inter-services.
* **Spring AOP:** using in api-gateway to tracking customer ativities.
* **Spring Security + JJWT:** implement security by using JWT. 
* **Junit:** writing unit tests.

## 7. Services Detail
Services Technical Stack:
![ServicesDetail](https://github.com/taivtse/nab-icommerce-assessment/blob/master/docs/ServicesDetail.png)

Services Port:
![ServicesPortInfo](https://github.com/taivtse/nab-icommerce-assessment/blob/master/docs/ServicesPortInfo.png)
**NOTE:** Make sure that all of the above ports are available on your computer when you get them up and running.

## 8. Code Folder Structure
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

## 9. Unit Test
There are a number of unit testing in `order-service`. 
The unit testing applied for helper, mapper, service impl package, they consist of 21 test cases.

## 10. Setup To Run On Local Computer
**NOTE:** There are a number of backing services that I have already setup and deployed on AWS to save your time. Let's focus on the services I have built in depth. They include:
* MySQL
* Redis
* Elasticsearch
* Kafka
* Debezium (CDC): Synchronize data from MySQL, namely the table `product` in the `nab_product` database to Elasticsearch.

**Setup local services:**
Run with IntelliJ IDEA:
1. Clone the source code and open with InteliJ IDEA
2. Choose Add as Maven Project and wait for it's dependencies loading.
  <img src="https://github.com/taivtse/nab-icommerce-assessment/blob/master/docs/AddAsMavenProject.png" alt="AddAsMavenProject"/>
  
3. Open Project Structure, select Java 8 SDK
  <img src="https://github.com/taivtse/nab-icommerce-assessment/blob/master/docs/ProjectStructureChooseSDK.png" alt="ProjectStructureChooseSDK"/>

4. Open the run configuration tab, I've set them up and arranged them according to their dependencies.
  <img src="https://github.com/taivtse/nab-icommerce-assessment/blob/master/docs/RunConfiguration.png" alt="RunConfiguration"/>
  
5. Run Maven Compile one by one in the following order:
  <img src="https://github.com/taivtse/nab-icommerce-assessment/blob/master/docs/MavenCompileOrdering.png" alt="MavenCompileOrdering" width="350"/>

6. If you have Multirun plugin, just click on `Low Level Services Multirun` and waiting for it to run all low level services, then click on `High Level Services Multirun` and run it. <br />
If you do not have Multirun plugin, click one by one and run in the following order:
  <img src="https://github.com/taivtse/nab-icommerce-assessment/blob/master/docs/SpringApplicationOrdering.png" alt="SpringApplicationOrdering" width="350"/>
Note: Always run Discovery Server first.

7. Open your browser then navigate to http://localhost:8761/, scroll to `Instances currently registered with Eureka` section, verify that all service already started.
  <img src="https://github.com/taivtse/nab-icommerce-assessment/blob/master/docs/EurekaUI.png" alt="EurekaUI"/>

Done. All services already started, you can use the below cURL to test the application.
**NOTE:** If you have any problem when set up project, please feel free to contact to me via: <br />
Facebook: https://www.facebook.com/taivtse/ <br />
Zalo: 0932938178

## 11. CURL Commands
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
2. `code`: SUCCESS: code >= 0, FAIL: code < 0 
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
- `name`: customer name.
- `email`: customer email.
- `provider`: login provider, "FACEBOOK" for example.
- `providerId`: the user ID provided by Facebook.
- `photoUrl`: customer photo url.
- `token`: customer social token issued by Facebook.
- `clientId`: Facebook application id.
- `clientSecret`: Facebook application client secret key.
- Note: A customer will be identified by a provider + providerId.

Sample response:
```json
{
  "code": 0,
  "message": "Success",
  "data": {
    "customerId": 1,
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MzYzODM4LCJleHAiOjE2MjAyMjc4Mzh9.8qxg81jxSCLpEQB-JjZFZlIz_KoT-bnUCYR8nA9bZolPS_rmiZZm73KwDoDD4TalefDTtr8JsQlbUJ7zWywBlA"
  }
}
```
- `customerId`: customer id in the system.
- `accessToken`: the access token will be attached to the header to check the authentication.


### Get Customer Infomation:
```shell
curl -L -X GET 'http://localhost:8080/api/customers/1' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA'
```

Sample response:
```json
{
  "code": 0,
  "message": "Success",
  "data": {
    "id": 1,
    "name": "Võ Thành Tài",
    "email": "taivtse@gmail.com",
    "provider": "FACEBOOK",
    "providerId": "A16E518CAF8CD1A99917F3841928D",
    "photoUrl": "image"
  }
}
```


### Get Customer Activities:
```shell
curl -L -X GET 'http://localhost:8080/api/customers/1/activities' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA'
```

Sample response:
```json
{
  "code": 0,
  "message": "Success",
  "data": [
    {
      "customerId": 1,
      "action": "GET_CUSTOMER_INFO",
      "method": "GET",
      "requestURI": "/api/customers/1",
      "queryString": "",
      "dateTime": 1619364445931,
      "body": "",
      "response": "{\"code\":0,\"message\":\"Success\",\"data\":{\"id\":1,\"name\":\"Võ Thành Tài\",\"email\":\"taivtse@gmail.com\",\"provider\":\"FACEBOOK\",\"providerId\":\"A16E518CAF8CD1A99917F3841928D\",\"photoUrl\":\"image\"}}"
    },
    {
      "customerId": 1,
      "action": "LOGIN",
      "method": "POST",
      "requestURI": "/api/customers/login",
      "queryString": "",
      "dateTime": 1619363838953,
      "body": "{\"name\":\"Võ Thành Tài\",\"email\":\"taivtse@gmail.com\",\"provider\":\"FACEBOOK\",\"providerId\":\"A16E518CAF8CD1A99917F3841928D\",\"photoUrl\":\"image\",\"token\":\"eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTYxOTAxOTgxMywiaWF0IjoxNjE5MDE5ODEzfQ.fnmHbry8qVQsSYJexgNuDVX4tsciEfDmi_c_v-kNI9Q\",\"clientId\":\"98123841002394711283\",\"clientSecret\":\"3DS9v9KWpt3TeCGWcISuOZOrMtunHn4c\"}",
      "response": "{\"code\":0,\"message\":\"Success\",\"data\":{\"customerId\":1,\"accessToken\":\"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MzYzODM4LCJleHAiOjE2MjAyMjc4Mzh9.8qxg81jxSCLpEQB-JjZFZlIz_KoT-bnUCYR8nA9bZolPS_rmiZZm73KwDoDD4TalefDTtr8JsQlbUJ7zWywBlA\"}}"
    }
  ]
}
```

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
```json
{
    "code": 0,
    "message": "Success",
    "data": [
        {
            "id": 1,
            "name": "New Apple iPhone 12 Pro Max (128GB, Graphite) [Locked] + Carrier Subscription",
            "brand": "Apple",
            "price": 22000000,
            "color": "gold",
            "image": "https://m.media-amazon.com/images/I/71XXJC7V8tL._FMwebp__.jpg",
            "unit": "piece"
        },
        {
            "id": 2,
            "name": "2020 Apple MacBook Air with Apple M1 Chip (13-inch, 8GB RAM, 256GB SSD Storage) - Space Gray",
            "brand": "Apple",
            "price": 30000000,
            "color": "gray",
            "image": "https://images-na.ssl-images-amazon.com/images/I/71jG%2Be7roXL._AC_SX679_.jpg",
            "unit": "piece"
        },
        {
            "id": 3,
            "name": "NVIDIA Jetson Xavier NX Developer Kit (812674024318)",
            "brand": "NVIDIA",
            "price": 9000000,
            "color": "black",
            "image": "https://images-na.ssl-images-amazon.com/images/I/71TiOw6fSuL._AC_SX679_.jpg",
            "unit": "kit"
        }
    ]
}
```

### Get Product Detail:
```shell
curl -L -X GET 'http://localhost:8080/api/products/2' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA'
```

Sample response:
```json
{
    "code": 0,
    "message": "Success",
    "data": {
        "id": 2,
        "name": "2020 Apple MacBook Air with Apple M1 Chip (13-inch, 8GB RAM, 256GB SSD Storage) - Space Gray",
        "brand": "Apple",
        "price": 30000000,
        "color": "gray",
        "image": "https://images-na.ssl-images-amazon.com/images/I/71jG%2Be7roXL._AC_SX679_.jpg",
        "unit": "piece"
    }
}
```

### Get Product Price Histories:
```shell
curl -L -X GET 'http://localhost:8080/api/products/2/price/histories' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA'
```

Sample response:
```json
{
    "code": 0,
    "message": "Success",
    "data": [
        {
            "productId": 2,
            "oldPrice": 45000000,
            "newPrice": 42000000,
            "dateTime": 1619378298735
        },
        {
            "productId": 2,
            "oldPrice": 49000000,
            "newPrice": 45000000,
            "dateTime": 1619378294734
        },
        {
            "productId": 2,
            "oldPrice": 45000000,
            "newPrice": 49000000,
            "dateTime": 1619378289234
        },
        {
            "productId": 2,
            "oldPrice": 30000000,
            "newPrice": 45000000,
            "dateTime": 1619378261236
        }
    ]
}
```

### Add Cart Items:
```shell
curl -L -X POST 'http://localhost:8080/api/carts/items/customer' \
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
```json
{
    "code": 0,
    "message": "Success",
    "data": null
}
```

### Get Customer Cart:
```shell
curl -L -X GET 'http://localhost:8080/api/carts/customer/1' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA'
```

Sample response:
```json
{
    "code": 0,
    "message": "Success",
    "data": {
        "customerId": 1,
        "items": [
            {
                "productId": 1,
                "quantity": 3,
                "dateTime": 1619378404352
            },
            {
                "productId": 2,
                "quantity": 1,
                "dateTime": 1619378404352
            }
        ]
    }
}
```

### Remove Cart Items:
```shell
curl -L -X DELETE 'http://localhost:8080/api/carts/items/customer' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA' \
-H 'Content-Type: application/json' \
--data-raw '{
    "customerId": "1",
    "productIds": [
        1
    ]
}'
```

Sample response:
```json
{
    "code": 0,
    "message": "Success",
    "data": null
}
```

### Clear Customer Cart:
```shell
curl -L -X DELETE 'http://localhost:8080/api/carts/customer/1' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA'
```

Sample response:
```json
{
    "code": 0,
    "message": "Success",
    "data": null
}
```

### Place Order:
```shell
curl -L -X POST 'http://localhost:8080/api/orders/customer/1' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA'
```

Sample response:
```json
{
  "code": 0,
  "message": "Success",
  "data": 1
}
```
- `data`: order id if place order success.

### Get Order Information:
```shell
curl -L -X GET 'http://localhost:8080/api/orders/1' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA'
```

Sample response:
```json
{
  "code": 0,
  "message": "Success",
  "data": {
    "id": 1,
    "customerId": 1,
    "customerName": "Võ Thành Tài",
    "totalAmount": 132000000,
    "status": "INIT",
    "createdDate": 0,
    "items": [
      {
        "id": 1,
        "orderId": 0,
        "productId": 1,
        "price": 30000000,
        "quantity": 3
      },
      {
        "id": 2,
        "orderId": 0,
        "productId": 2,
        "price": 42000000,
        "quantity": 1
      }
    ]
  }
}
```

### Get Customer Ordders:
```shell
curl -L -X GET 'http://localhost:8080/api/orders/customer/1' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA'
```

Sample response:
```json
{
    "code": 0,
    "message": "Success",
    "data": [
        {
            "id": 1,
            "customerId": 1,
            "customerName": "Võ Thành Tài",
            "totalAmount": 132000000,
            "status": "INIT",
            "createdDate": 0,
            "items": [
                {
                    "id": 1,
                    "orderId": 0,
                    "productId": 1,
                    "price": 30000000,
                    "quantity": 3
                },
                {
                    "id": 2,
                    "orderId": 0,
                    "productId": 2,
                    "price": 42000000,
                    "quantity": 1
                }
            ]
        }
    ]
}
```

### APIs For Admin:
Here are some APIs I made for Admin, using simply by log in as customer, but not implementing the user authorization feature yet.

#### Create product:
```shell
curl -L -X POST 'http://localhost:8080/api/products' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA' \
-H 'Content-Type: application/json' \
--data-raw '{
    "name": "Samsung Electronics Samsung Galaxy S21 5G | Factory Unlocked Android Cell Phone",
    "brand": "Samsung",
    "price": 15000000,
    "color": "pink",
    "image": "",
    "unit": "piece"
}'
```

Sample response:
```json
{
    "code": 0,
    "message": "Success",
    "data": 4
}
```
- `data`: product id if create success.

#### Update product:
```shell
curl -L -X PUT 'http://localhost:8080/api/products' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA' \
-H 'Content-Type: application/json' \
--data-raw '{
    "id": 4,
    "name": "Samsung Electronics Samsung Galaxy S21 5G | Factory Unlocked Android Cell Phone",
    "brand": "Samsung",
    "price": 17000000,
    "color": "pink",
    "image": "",
    "unit": "piece"
}'
```

Sample response:
```json
{
    "code": 0,
    "message": "Success",
    "data": null
}
```

#### Update order status:
Order status flow: INIT -> PROCESSING -> DELIVERING -> COMPLETED or DROP_OFF_FAIL
```shell
curl -L -X PUT 'http://localhost:8080/api/orders/status' \
-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE5MTA0NTQ5LCJleHAiOjE2MTk5Njg1NDl9.-iiS-oxLZF6RVx5fQFm66ZfaJ0YvVijxptdsu-kjAad0NUQrmeQ2vf2X_RoB5gWpR59QA3dSD4SFAYx6aRkjXA' \
-H 'Content-Type: application/json' \
--data-raw '{
    "orderId": 1,
    "status": "PROCESSING"
}'
```

Sample response:
```json
{
    "code": 0,
    "message": "Success",
    "data": null
}
```

# Thank you very much and hope to see you at NAB
