# Shop manager

### Reference Documentation
Simple application that exposes REST endpoints to perform the basic CRUD on generic products in a shop.
Although it is a simple application, it can be used as a reference for structuring your codebase according to the *hexagonal architecture* (also called *port and adapters*).
This code is meant to demonstrate how we can model a business domain/subdomain as a *domain model* (from DDD terminology), where high level policies are independent of the infrastructure, and the aggregates consistency is protected with transactional boundaries.
The sample includes an *outbox pattern* implementation, which is a common pattern used to ensure that messages are sent to the message broker only after the transaction is committed.

### Running the application and infrastructure locally
In order for the application to run on your computer, you need to execute the following steps:
1. build the application docker image by running the following command:
    ```
    docker build -t shop-manager .
    ```
2. run all required containers by executing the docker-compose file:
    ```
    docker-compose -f docker-compose.yaml up -d
    ```
