package com.exam.todo.api.rest;

import com.exam.todo.domain.Task;
import com.exam.todo.exception.DataFormatException;
import com.exam.todo.services.TaskService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by boysbee on 9/5/2016 AD.
 */
@RestController
@RequestMapping(value = "/todo/api/tasks")
@Api(basePath = "/todo/api/tasks", value = "todo", description = "TODO API")
public class TodoController extends AbstractRestHandler {
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a task resource.", notes = "Returns the URL of the new resource in the Location header.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created")})
    public void createTask(@RequestBody Task task,
                           HttpServletRequest request, HttpServletResponse response) {
        Task createTask = this.taskService.createTask(task);
        response.setHeader("Location", request.getRequestURL().append("/").append(createTask.getId()).toString());
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update a task resource.", notes = "You have to provide a valid task ID in the URL and in the payload. The ID attribute can not be updated.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content")})
    public void updateTask(@ApiParam(value = "The ID of the existing task resource.", required = true)
                           @PathVariable("id") Long id, @RequestBody Task task,
                           HttpServletRequest request, HttpServletResponse response) {
        checkResourceFound(this.taskService.getTask(id));
        if (id != task.getId()) throw new DataFormatException("ID doesn't match!");
        this.taskService.updateTask(task);
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all tasks.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok")})
    public
    @ResponseBody
    Page<Task> getAllTasks(@ApiParam(value = "The page number (zero-based)", required = true)
                           @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                           @ApiParam(value = "Tha page size", required = true)
                           @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                           HttpServletRequest request, HttpServletResponse response) {
        return this.taskService.getAllTask(page, size);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single task.", notes = "You have to provide a valid task ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")})
    public
    @ResponseBody
    Task getTask(@ApiParam(value = "The ID of the task.", required = true)
                 @PathVariable("id") Long id,
                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        Task task = this.taskService.getTask(id);
        checkResourceFound(task);
        return task;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a task resource.", notes = "You have to provide a valid task ID in the URL. Once deleted the resource can not be recovered.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content")})
    public void deleteTask(@ApiParam(value = "The ID of the existing task resource.", required = true)
                           @PathVariable("id") Long id, HttpServletRequest request,
                           HttpServletResponse response) {
        checkResourceFound(this.taskService.getTask(id));
        this.taskService.deleteTask(id);
    }
}
