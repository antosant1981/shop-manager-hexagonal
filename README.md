# Shop manager

### Reference Documentation
Simple application, based on hexagonal architecture, that exposes REST endpoints to perform the basic CRUD on generic products in a shop.
In order for it to run, we need to start a postgres database instance.

## Running your postgres server 
Run the following command to start a docker container with postgresql:
```shell
docker run --name=shop-manager -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -p 5432:5432 -d postgres
```
