# NAB Assessment

### Problem Statement
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

### Entity Relationship Diagram
![ERD](https://github.com/taivtse/nab-icommerce-assessment/blob/master/docs/ERD.png)

### Architecture Design
![ArchitectureDesign](https://github.com/taivtse/nab-icommerce-assessment/blob/master/docs/ArchitectureDesign.png)

### Software Development Principles
There are software development principles which I applied in the project:
1. SOC (Separation of concerns):
Because the project was built according micro-services architecture, so the first thing I need to do was separate a large overall picture into specific concerns. 
Listed entities and figure out which of them related to a bussiness domain then indicated it as bounded context.
By doing this, I have already separated the project requirement to micro-services. 
Each service only serves for a certain funtionalities of a concerns and can scale independently.

2. CQRS (Command Query Responsibility Segregation):
In the Product domain, I divided it into 2 services. One for reading purpose (product-query-service) and another one for writing purpose (product-command-service).
In practice, any ecommerce platform will have many products. Beside that, reading and writing data ratio are difference. If we combine reading and writing data in a service, it can be affected by each other.
By divide it into 2 parts, we can choose technologies for each part to achieve the best performance and scale independently way, for example 1 instance for writing service and 3 instances for reading service if reading ratio is larger than writing ratio.

3. DRY (Don't Repeat Yourself):
There are 2 common modules: common-shared and protobuf-shared. They are provide boilerplate code and can be shared for all other micro-services to avoid repeating code.

4. YAGNI (You aren't gonna need it):
In the project, I only implemented functionalities for currenty requirements. I do not add more methods, or class for furture purposes, but the system is designed with flexible expansion capacity.

5. Law of Demeter:
To avoid services uncontrollably interdependent and risk leading to cyclic dependencies, I have applied the following rules: 
* There are 2 layers: low level services (core services) and high level services (api-gateway, background workers)
* All services in the same layer must not be able communicate directly with each other. They only serve apis for higher level services.
* High level services can communicate with low level services.
* If low level services need some data from another service, it should claim the missing data from higher level services.

6. SOLID:
I also applied SOLID pricipal in the project.
* Each class is designed according to the Single Responsibility Principle.
* All implementation class of an interface do not alter the correctness of the program by follow the Liskov Substitution Principle.
* Interfaces are broken down and provide a certain amount of work, avoid a large interface by follow Interface Segregation Principle.
* High level modules and low level modules must depend on abstractions, not on concretions by follow Dependency Inversion Principle. Using DI supported by Spring Framework to doing this.

### Java Libraries and Frameworks
* Spring boot: main framework for all micro-services.
* LogNet/grpc-spring-boot-starter: implement Grpc services.
* Spring Netflix Eureka: implement discovery service, services communicate via service name.
* Spring Data JPA, Spring Data Redis, Spring Data Elasticsearch: connect to data stores.
* Spring Kafka: asynchronous communication of inter-services.
* Spring AOP: using in api-gateway to tracking customer ativities.
* Spring Security + JJWT: implement security by using JWT. 
* Junit: writing unit tests.

### Code Folder Structure
1. Low level services package struture:
* Helper: utilities classes.
* Config: components and beans configuration.
* Domain: contains classes mapping with database, these class reflect database structure.
* Mapper: convert Domain to Proto and vice versase.
* Repository: this is the layer communicate directly to database, read/write operations to database.
* Service: implements bussiness logics. Higher level than Repository package and use it as dependencies.
* Classes annotated with @GRpcService: implements Grpc interface, use Service package as it's dependencies.
* Classes annotated with @SpringBootApplication: Main class to run Spring Boot application.

2. High level services package struture:
* Config: components and beans configuration.
* Dto: request and response data transfer objects.
* Client: wrapper class used to communicate with orther service.
* Mapper: convert Dto to Proto and vice versase.
* Aspect: implement Aspect Oriented Programming.
* Kafka: Kafka consumers handler.
* Controller: expose public Rest Api and handle bussiness logic.
* Security: setup authentication/authorization.
* Classes annotated with @SpringBootApplication: Main class to run Spring Boot application.
