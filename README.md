# Running the application
##### Prerequisites
* Install [Docker](https://docs.docker.com/get-docker/)

#### The application should be built first
```
./gradlew clean build
```
#### Spin up the docker containers
```
./start.sh
```

#### Stop the containers
```
./stop.sh
```

# Architecture
### <b>RESTful service</b> - using SpringBoot. There are multiple benefits that come with using Spring Boot for developing RESTful services:
* Helps to autoconfigure all components for a production-grade Spring app
* Facilitates the creation and testing of Java-based applications by providing a default setup for unit and integration tests
* Helps to avoid all the manual work of writing boilerplate code, annotations, and complex XML configurations
* Comes with embedded HTTP servers like Jetty and Tomcat to test web applications
* The integration of Spring Boot with the Spring ecosystem which includes Spring Data and Spring Security is easy.

Our Spring Boot application follows a layered architecture in which each layer communicates with the layer directly below or above (hierarchical structure) it.
There are four layers in Spring Boot are as follows:
* Presentation Layer
* Business Layer
* Persistence Layer
* Database Layer

### MongoDB
* MongoDB is a document database used to build highly available and scalable internet applications.
* It allows a flexible schema aproach (e.g. in our case, it allows us to have a list of ingredients)

# Technology
* Java 17
* Spring Boot 2.6.4 (with Spring Web MVC, Spring Data MongoDB, Spring Security)
* MongoDB
* Gradle

# APIs
| Methods     | URL             | Actions     |
| :---        |    :----:       | ---: |
| POST        | /v1/recipes     | Create a new recipe |
| GET         | /v1/recipes     | List all recipes |
| PUT         | /v1/recipes     | Update a recipe |
| DELETE      | /v1/recipes/{id}| Delete a recipe by id |