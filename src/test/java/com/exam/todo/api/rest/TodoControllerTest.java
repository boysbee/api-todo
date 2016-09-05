package com.exam.todo.api.rest;

import com.exam.todo.Application;
import com.exam.todo.dao.jpa.TaskRepository;
import com.exam.todo.domain.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.regex.Pattern;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by boysbee on 9/5/2016 AD.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
@Profile("test")
public class TodoControllerTest {


    private static final String RESOURCE_LOCATION_PATTERN = "http://localhost/todo/api/tasks/[0-9]+";


    @InjectMocks
    TodoController controller;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void initTests() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        taskRepository.save(new Task("task1", "done"));
    }

    @After
    public void tearDown() {
        mvc = null;
        taskRepository = null;
        controller = null;
    }

    @Test
    public void getAllTasks() throws Exception {
        mvc.perform(get("/todo/api/tasks")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].description", is("task1")))
                .andExpect(jsonPath("$.content[0].status", is("done")));

    }

    @Test
    public void getOneTask() throws Exception {
        mvc.perform(get("/todo/api/tasks/" + 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("task1")))
                .andExpect(jsonPath("$.status", is("done")));

    }

    @Test
    public void deleteTask() throws Exception {
        taskRepository.save(new Task("task1", "done"));
        mvc.perform(delete("/todo/api/tasks/" + 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    public void createTask() throws Exception {
        Task task = mockTask("sample_", "done");
        byte[] json = toJson(task);
        MvcResult result = mvc.perform(post("/todo/api/tasks")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
                .andReturn();
        long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

        mvc.perform(get("/todo/api/tasks/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) id)))
                .andExpect(jsonPath("$.description", containsString("sample_")));
    }

    @Test
    public void updateTask() throws Exception {
        Task task = mockTask("create_", "pending");
        byte[] json = toJson(task);
        MvcResult result = mvc.perform(post("/todo/api/tasks")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
                .andReturn();
        long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

        Task updateData = new Task(id, "updated", "done");
        byte[] jsonUpdateData = toJson(updateData);
        mvc.perform(put("/todo/api/tasks/" + id)
                .content(jsonUpdateData)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mvc.perform(get("/todo/api/tasks/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) id)))
                .andExpect(jsonPath("$.description", containsString("update")))
                .andExpect(jsonPath("$.status", is("done")));
    }


    @Test
    public void notFound() throws Exception {
        mvc.perform(get("todo/api/tasks/" + 9))
                .andExpect(status().isNotFound());

    }

    private long getResourceIdFromUrl(String locationUrl) {
        String[] parts = locationUrl.split("/");
        return Long.valueOf(parts[parts.length - 1]);
    }


    private Task mockTask(String prefix, final String status) {
        Task task = new Task(prefix.concat("task"), "done");

        return task;
    }

    private byte[] toJson(Object r) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(r).getBytes();
    }

    // match redirect header URL (aka Location header)
    private static ResultMatcher redirectedUrlPattern(final String expectedUrlPattern) {
        return new ResultMatcher() {
            public void match(MvcResult result) {
                Pattern pattern = Pattern.compile("\\A" + expectedUrlPattern + "\\z");
                assertTrue(pattern.matcher(result.getResponse().getRedirectedUrl()).find());
            }
        };
    }

}