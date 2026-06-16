# Westpac Prime Summation API

This project is a Spring Boot REST API that calculates the sum of all prime numbers up to a specified limit. The default limit is **10,000,000**.
The API is easily interacted with for demonstration via OpenAPI/Swagger (see below).

## Endpoint
- **GET** `/v1/prime-summation`
- Optional query parameter `upToLimit` (default `10000000`).
- Returns a JSON response with the sum.

## Main class (where the spring context is wired)
* **core.westpac.prime.PrimeAPI**
  * Configurations are in resources/
    * application.yml (default configuration for demonstration)
    * application-test.yml (test configuration)

## Technologies
- Java 17+ (hard dependency)
- Maven (hard dependency)
- Spring Boot 4.1.0
- Swagger/OpenAPI 3 for API documentation
- Docker (built with Maven Jib plugin)
  - A dependency only if you choose to run via Docker

## Notes on Testing
Both unit level and integration testing (end-to-end API testing)
has been implemented and will be automatically invoked when the Maven **install
phase** or **test phase** runs during the build life-cycle.

## Build and Run
### Compile and run all tests via maven
```
mvn clean install
```

### Deploy to local docker repository
```
mvn compile jib:dockerBuild
```

### Run the Spring boot application via docker  
```
docker run --rm -it --network=host prime-api:latest
```
This deployment uses a pass-through network between the guest and host machine hence
port 8080 (the application API port on the guest) can be seen from the host machine. Once the 
container is closed (running in interactive mode, stopped vis CTRL-C) the container
instance will be removed (but the image remains).

* A successful startup looks like the following:
```
INFO 1 --- [main] core.westpac.prime.PrimeAPI              : Starting PrimeAPI using Java 17.0.19 with PID 1 (/app/classes started by root in /)
INFO 1 --- [main] core.westpac.prime.PrimeAPI              : No active profile set, falling back to 1 default profile: "default"
INFO 1 --- [main] o.s.boot.tomcat.TomcatWebServer          : Tomcat initialized with port 8080 (http)
INFO 1 --- [main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
INFO 1 --- [main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/11.0.22]
INFO 1 --- [main] b.w.c.s.WebApplicationContextInitializer : Root WebApplicationContext: initialization completed in 830 ms
INFO 1 --- [main] o.s.boot.tomcat.TomcatWebServer          : Tomcat started on port 8080 (http) with context path '/'
INFO 1 --- [main] core.westpac.prime.PrimeAPI              : Started PrimeAPI in 1.483 seconds (process running for 1.718)
```

## API Documentation
### The easiest way to interact with the application is via the OpenAPI/Swagger UI, the URL is:
### [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)