package com.exam.todo.api.rest;

import com.exam.todo.Application;
import com.exam.todo.dao.jpa.TaskRepository;
import com.exam.todo.domain.Task;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public void tearDown(){
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

}