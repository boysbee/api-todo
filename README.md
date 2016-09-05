# TODO API
This is simple RESTful API service design and implement a deployable RESTful API backend that stores the resource for a simple `todo` tasklist.

### Preparation
- JDK 1.8
- Apache Maven 3.3.0+

### Build and Run
You can use profile to run `production`, `dev`, `test` with paramter `-Dspring.profiles.active=<profile>`
```
mvn clean package -DskipTests spring-boot:run -Dspring.profiles.active=dev
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

### Using

You can see what urls are available using curl:

```sh
$ curl localhost:8090
```
- view all items in the list.
```sh
$ curl curl -X GET -H "Content-Type:application/json" localhost:8090/todo/api/tasks
```
- view a single task in the list.
```sh
$ curl -X GET -H "Content-Type:application/json" localhost:8090/todo/api/tasks/1
```
- add a task to the list.
```sh
$ curl -X POST -H "Content-Type:application/json" -d '{ "description" : "Task1", "status" : "pending" }' localhost:8090/todo/api/tasks
```
- edit existing task.
```sh
$ curl -X PUT -H "Content-Type:application/json" -d '{ "id":1, "description" : "Task1_update", "status" : "done" }' localhost:8090/todo/api/tasks/1
```
- set the task status.
```sh
$ curl -X PUT -H "Content-Type:application/json" -d 'success' localhost:8090/todo/api/tasks/2/setStatus
```
- delete a task from the list.
```sh
$ curl -X DELETE http://localhost:8090//todo/api/tasks/1
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
