# TODO API
This is simple RESTful API service design and implement a deployable RESTful API backend that stores the resource for a simple `todo` tasklist.

### Build and Run

```
mvn clean package -DskipTests spring-boot:run
```
or

```
mvn clean package -DskipTests

java -jar target/api-todo-<version>.war
```

### Build WAR for deployment

Build `war` file to deploy web application.

```
mvn clean package -DskipTests
```

### API Document
You can explore RESTful api document by open this url by browser.
```
http://localhost:8090/swagger-ui.html
```
or JSON document
```
http://localhost:8090/v2/api-docs
```
