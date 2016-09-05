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

### Configuration
Default port `8090` you can change value `server.port` default port in `src/main/resources/application.yml`

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
