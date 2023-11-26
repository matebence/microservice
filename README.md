## Software architecture 

We start first with the customers problems, not with implementations

- Waterfall logic -Big and complex  designs
- Agile - Learn as you work

### Conways Law

Any organization that design a system will inevitable produce a design whose structure os a copy of the organization communication structure.
The solution to this is: Change the structure of organization to mach agile methods.

Architecture types:
- Monolith
- Service oriented
- Microservice

## Domain Driven design

The main domain we have to answer the question:
What?? Why??

Inspect and Adapt
- 1 Make a small change
- 2 Get feedback
- 3 Inspect and adopt
- 4 Improve

Domain driven design is organized code along certain well-defined boundaries
- Bounded Context - Natural devision within a busines

For example if we are doing a book store. The store itself is the context. The purpose of it, its to sell book to people, but to make the store working we need a warehouse. From where the books are shipped

What are the reponsibilites of the people working within that context
- Warehouse -> shipping
- Store -> saling

Then we have subdomain:
- Support subdomain
    - Review subdomain
    - Support the code domain
- Core subdomain
    - Listening subdomain  
    - What makes the money
- Generic subdomain
    - Auth/identity subdomain
    - Its just solves the problem

Each domain object is a entity object or a value object
- Entity object - has an id and its mutable
- Value object - has no id and its inmutable

Aggregate - is one or more domains (guest & host entity). For example here the aggrate rule is that we dont have more guest then the number of rooms in the house

An aggregate is a collection of entities you talk to through a single portal. Much like an object/class in object oriented world. Has one job and does one thing. Does that in a speicifc context
 
Aggregate rules
- reference other aggregates by id
- changes are committed and rolledback as a whole
- changes to an aggregate are done via the root

Modelling items:
Actor - User
Aggregate - One or more domains
Command - the method which completes the task
Domain event - The event
Hot spot - What should happen next
Policy - what should happen, some kind of rule
System - which system takes over
Read model - the model in code

![Process modelling](https://raw.githubusercontent.com/matebence/microservice/master/docs/process_modelling.png)

## Web services

There are main Groups of Web Services:
SOAP - XML based
RESTfull - JSON based

Security in Web services:
- Basic Authentication
- API key

### RESTful APIs and HATEOAS 

API principles

Uniform Resource Identifier (URI)

Operations
- GET
- POST
- PUT
- DELETE
- PATCH

Formats
- XML
- JSON

Stateless
- Not state is stored

### WSDL - Web service Description language

Contains information like the data types being used in SOAP messages nad operation avaible via the web service

SOAP Messages
- Envelop
- Header
- Body
- Fault

### Developing APIs Using GraphQL  

A syntax descrbing how to ask for data, which is useally used to load data

Query document
- A string sent to a server to process and request data
- Are read only operation and cannot be manipualted

## Microservices - main building blockss

Microservices are result of problems with two architecture paradigms
- Monolith - The original architecture. All software components are executed in a single process
- SOA - Apps are services exposing functionlity to the outside world. Services expose metadata to declare their functionality (SOAP/WSDL)

Problems with Monolith & SOA

- Monolith
    With monolith all the components must be developed using the same development platform
    With monolith, new deployment is always for the whole app

- SOA
    First problems is Complicated and Expensive ESB
    Second is Lack of tooling - monitoring, deploying etc ...


Microservices - Martin Fowler and James Lewis publication 'Microservices' had 9 characteristics:
- Componentization via Services - Components are one part and together they compose the software. Modularity can be achieved via Libraries(import keyword Monolith) Services (Web API, RPC)
- Organized Around Business Capabilities - Traditional project have teams with horizontal responsibilites (UI, API, Logic, DB et ...). With Microservices every service is handled by a single team
- Products not Projects - With traditional project, the goal is deliver a working code. After delivering the team moves on the next project. With Microservices the goal is to deliver a working product. A product needs ongoing support and requires close relationship with the customer
- Smart Endpoints and Dump Pipes - SOA used complicated WSDL and SOAP, in  microservices we use lightweight HTTP calls
- Decantralized Goverance - In traditional project there is a standard for alsmost everythin. With microservices each team makes its own decision
- Decentralized data management - Traditional systems have a single database. With microservices each service has isw own database. This is the most controversial attribute of Microservices, because its now always possible (data duplication, distributed transactions etc ...)
- Infrastrictire Automation - Short deployment cycles are a must, automation of deployment and testing
- Design for Failure - With microservices there lot of process and a lot of network traffic. A lot can go wrong the code must assume failure can happen (Catch, Retry, Logging, Monitoring)
- Evolutationery Design - The move to microserives should be gradual.No need to break everythinh. Start small and upgrade each part seperatly.

### Problems Solved by Microservices

- Single technology platform - With monolith all the componets must be developed using the same develpoment platform
- Inflexible deployment - With monolith new deployment is always for the whole app
- Inneficent compute resources - With monolith compote resources CPU and RAM are divided across all components
- Large and complex - With monlith, the code is large and complex
- Complicated and Expensive ESB - With SOA, the ESB is of the main compoenents, which is difficult
- Lack of tooling - For SOA to be effective, short development cycles were needed. Allows for quick testing. No tooling existed

### Designing Microservices Architecture

- Business requirments -  The collection of requirments around a specific business cability
- Functionlity autonomy - THe maximum functionality that does not involve other business requirments
- Data entities - Service is designed around well specific data entities. For exmaple, orders, items (one order service and another items)
- Data autonomy - Services does not depend on data from other services to function properly

|                       |Inventory                    |Orders                            |Customers                               |Payments                     |
|-----------------------|-----------------------------|----------------------------------|----------------------------------------|-----------------------------|
|Business requirments   |Manage inventory items       |Manage orders                     |Manage customers                        |Perform payments             |
|Functional             |Add, remove, update, quentity|Add, cancel, Calculate sum        |Add, update, remove, get account details|Perform payments             |
|Date entities          |Items                        |Order, shipping address           |Customer, address, contact details      |Perform history              |
|Data autonomy          |None                         |Related to Items, Cusstomer by ID |Related to orders by Id		          |None                         |

Edge case 1 - Retrives all customers fron NYC with total numbers of orders for each customer. This is a problem because we have to use always two services
- Data duplication - duplicate data in two diff dbs
- Services query - Http Rest calls
- Aggregation service - third service which solves the issue

Edge case 2 - Retrieve list of all the orders in the system. This will be become after yers the list will be huge
        
### Communication Patterns

- one to one sync - A Service calls another service and waits for the reponse
- one to one async - A Service calls another service and continue with the work
- Pub/Sub - A service want to notify other services about something

### Testing Microservices

- Unit testing - Test individual code units
- Integration testing - Cover almost all code paths in the service
- End to end tests - Test the whole flows of the system

### When Not to Use Microservices

- Interminled Functionlity or Data - One of the most important microsercies attributes its autonomy. When there is no way to separate logic or data - microservices will not help
- Performance Sensitivity systems - Microservices systems have performance overhead. Results of the network hops
- Quick and Dirty systems - Microservices design and implementation takes time. If you need a small quick systems, and need it now - dont go with Microservices
- No planned updates -Some systems have almost no planned future updates. Microservices main strenght is in the short update cycle

### Breaking Monolith to Microservices

Strategies for breaking monlith 
- New modules as services - We create the new module as service and expose the REST API to the old monolith
- Separate existing modules to services - We take out one module and takes it out as service and we expose it as REST API
- Complete rewrite - We get all modules as (micro)services. Here we can go big refactors

### Patterns

- Database per service
- Single entry point - api gateway
- Service discorver - eureka
- ACID -saga

Event-Driven Microservices:
If two services want to communicate - request & response
What if there are multiple services - for this we use a message broker

Types of saga's:
- Choreography-Based Saga - via message broker
- Orchestration-Based Saga - one Service (is the Orchestration) which process and sends the events  

![Choreography-Based Saga](https://raw.githubusercontent.com/matebence/microservice/master/docs/Choreography-Based-Saga.png)
![Choreography-Based Saga-failed](https://raw.githubusercontent.com/matebence/microservice/master/docs/Choreography-Based-Saga-failed.png)

![Orchestration-Based Saga](https://raw.githubusercontent.com/matebence/microservice/master/docs/Orchestration-Based-Saga.png)
![Orchestration-Based Saga](https://raw.githubusercontent.com/matebence/microservice/master/docs/Orchestration-Based-Saga.png)

**(CQRS)Command Query Responsibility Segregation**

In REST:
- Commands are POST PUT DELETE PATCH
- Query is GET

Event soursing:
How does we persist data in trandtional app? We first create a new prodcut and then we over and over again update it. How does this goes in event soursing? All changes are stored

```bash
docker run -d --name axonserver -p 8024:8024 -p 8124:8124 -v //D/Projects/microservice/docker/data:/axonserver/data -v //D/Projects/microservice/docker/eventdata:/axonserver/eventdata -v //D/Projects/microservice/docker/config:/axonserver/config axoniq/axonserver:4.4.9
```

```bash
cd axon-server

mkdir config
touch axonserver.properties

vi axonserver.properties
    server.port=8024
    axoniq.axonserver.name=My Axon Server
    axoniq.axonserver.hostname=localhost
    axoniq.axonserver.devmode.enabled=true

java -jar axonserver.jar
```
