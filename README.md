# Spring Boot Microservice Architecture with Docker and Docker Compose

## Brief Description
This project is a simple microservice architecture that has inventory, notification (not implemented, just showing that message is received), order and
product services. The services are communicating with each other using synchronous REST calls, Resilience4j is used for fault tolerance and asynchronous
communication is done using Kafka. The services are deployed in Docker containers and are communicating with each other using Docker Compose.
jin used to deploy docker containers to docker hub. These are the services that are used in this project:

## Architecture
![Architecture](
    screenshots/3x-dark.png
)

* **Inventory Service**: This service is responsible for managing the inventory of the products. It has a REST API that can be used to get the inventory
* **Notification Service**: This service is responsible for sending notifications to the users. It has a REST API that can be used to send notifications
* **Order Service**: This service is responsible for managing the orders. It has a REST API that can be used to get the orders
* **Product Service**: This service is responsible for managing the products. It has a REST API that can be used to get the products
* **Gateway Service**: This service is responsible for routing the requests to the appropriate services. It has a REST API that can be used to get the products
* **Config Service**: This service is responsible for managing the configuration of the services. It has a REST API that can be used to get the products
* **Discovery Service**: This service is responsible for managing the services. It has a REST API that can be used to get the products
* **Zipkin Service**: This service is responsible for managing the traces of the services. It has a REST API that can be used to get the products
* **Prometheus Service**: This service is responsible for managing the metrics of the services. It has a REST API that can be used to get the products
* **Grafana Service**: This service is responsible for managing the dashboards of the services. It has a REST API that can be used to get the products
* **Kafka Service**: This service is responsible for managing the messages of the services. It has a REST API that can be used to get the products
* **Zookeeper Service**: This service is responsible for managing the messages of the services. It has a REST API that can be used to get the products
* **Postgres Service**: This service is responsible for managing the messages of the services. It has a REST API that can be used to get the products


## Packages
* spring-boot-starter-oauth2-resource-server: This package is used to secure the services using OAuth2.
* spring-cloud-starter-netflix-eureka-client: This package is used to register the services to the discovery service.
* spring-cloud-starter-netflix-eureka-server: This package is used to register the services to the discovery service.
* spring-cloud-starter-gateway: This package is used to route the requests to the appropriate services.
* spring-boot-starter-security: This package is used to secure the services.
* micrometer-registry-prometheus: This package is used to monitor the services.
* zipkin-reporter-brave: This package is used to trace the services.
* spring-boot-actuator: This package is used to monitor the services and get the metrics of the services.
* micrometer-registry-prometheus: This package is used to monitor the services and get the metrics of the services.
* spring-kafka: This package is used to communicate with Kafka.
* lombok: This package is used to reduce the boilerplate code.
* spring-boot-starter-data-jpa: This package is used to communicate with the database.
* spring-boot-starter-webflux: This package is used to create fully asynchronous and non-blocking application built on event-loop execution model.
* spring-boot-starter-circuitbreaker-resilience4j: This package is used to make the services fault-tolerant by synchronizing the calls.
* spring-cloud-starter-loadbalancer: This package is used to load balance the requests beside the gateway service.
* spring-boot-starter-data-mongodb: This package is used to communicate with the mongodb with boilerplate annotations and methods.
* jakarta.validation-api: This package is used to validate the requests.

## Tech Stack
* Java 17
* Spring Boot 3.0.5
* Spring Cloud 2022.0.1
* KeyCloak
* Zipkin
* Prometheus
* Grafana
* Kafka
* Zookeeper
* Postgres
* MongoDB
* MySQL
* Docker
* Docker Compose
* Maven
* Git

## Swagger
Swagger is used to document the REST APIs. After running the services by using Docker, you can access the swagger documentation from the following URL:
* http://localhost:8181/swagger-ui.html

![Swagger](
    screenshots/swagger-ui.png
)

All API's can be selected from the top of the page.

## Prerequisites
* Java 17
* Maven 3.3.9
* Docker 1.12.1
* Docker Compose 1.8.0
* Git 

Before run, be careful about ports below are not used by other applications:
* 2181 -> Zookeeper Service
* 3000 -> Grafana Service
* 3306 -> MySQL Service for KeyCloak
* 8080 -> KeyCloak Service
* 9411 -> Zipkin Service
* 9090 -> Prometheus Service
* 9092 -> Kafka Service
* 5431 -> Postgres for Order Service
* 5432 -> Postgres for Inventory Service
* 8761 -> Discovery Service
* 8181 -> API Gateway - Swagger

## How to run using Docker Compose
You can change the ports from the docker-compose.yml file. To run the project, follow the steps below:
1. Clone the project
1. Run `docker-compose up -d` to start the services.
1. Run `docker-compose ps` to check the status of the services or `docker-compose logs -f` to see the logs.

## How to run without Docker Compose
1. Clone the project
2. Run `mvn clean install` to build the project for every service.
3. Run `mvn spring-boot:run` to run the services one by one.
4. This must be the running order:
   1. Discovery Service
   2. Api Gateway
   3. Order of the other services does not matter.

## After running the services
1. Run `docker-compose ps` to check the status of the services or `docker-compose logs -f` to see the logs.
2. Go to `http://localhost:8080/admin/master/console/` to login to KeyCloak. The username is `admin` and the password is `admin` as default.
3. Create a new realm with name `spring-boot-microservice-realm-`.
4. Go to Clients and create a new client with id `spring-cloud-client`.
5. Make sure that Client Authentication switch is off, then go next.
6. No need to enter Root URL, Valid redirect URIs etc. and save.
7. Go to Realm roles and create a new role with name `ROLE_USER`.
8. Then go to Users and create a new user with username `user`.
9. Go to Credentials tab and enter a password, turn Temporary off and save.
10. Go to Role Mappings tab, click Assign Role button, select `ROLE_USER` and save.
11. Go to Clients and select `spring-cloud-client`.
12. Go to Service Account Roles tab, click Add Roles button, select `ROLE_USER` and save.
13. Go to Credentials tab and copy Client Secret.
14. Go to Postman or any other REST client and send a POST request to `http://localhost:8080/realms/spring-boot-microservice-realm/protocol/openid-connect/token` with the following body:
```json
{
    "grant_type": "password",
    "client_id": "spring-cloud-client",
    "username": "user",
    "password": "**password**"
}
```
15. Copy the access_token from the response.
16. Make sure that the Authorization header is set to `Bearer **access_token**` for every request.
17. Now, you can access the services with that access token.

## Docker Hub
You can find the images of the services from the following link:
[Docker Hub](
https://hub.docker.com/u/melihcanclk
)