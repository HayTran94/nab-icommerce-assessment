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
In practice, any ecommerce platform will have many products. Beside that, reading and writing data ratio are difference. If you combine reading and writing data in a service, it can be affected by each other.
By divived it in to 2 services, you can scale independently way, for example 1 instance for writing service and 3 instances for reading service if reading ratio is larger than writing ratio.

DRY
SOLID
KISS
Law of Demeter
YAGNI
