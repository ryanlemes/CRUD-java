# CRUD Java
Compiling and running

1. Running with maven locally:

- `$ mvn clear package` Obs: this command run the unit tests.
- `$ cd /target`
- `$ java -jar crud-java.jar`

2. Running with Docker: 

- `$ docker build -t crud-java/java .`
- `$ docker run -p 8080:8080 crud-java/java` Obs: Check if the port 8080 is aready in use, otherwise change the first port after `-p` flag to another one.

Will run locally on http://localhost8080
 
---

link for swagger documentation:
- http://localhost:8080/swagger-ui.html#/
