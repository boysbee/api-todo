package com.exam.todo.services;

import com.exam.todo.Application;
import com.exam.todo.dao.jpa.TaskRepository;
import com.exam.todo.domain.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by boysbee on 9/5/2016 AD.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@WebIntegrationTest
@Transactional
@Profile("test")
public class TaskServiceTest {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @Before
    public void setUp() {
        Task task1 = taskRepository.save(new Task("task1", "done"));
        Task task2 = taskRepository.save(new Task("task2", "done"));
    }

    @After
    public void tearDown() {
        taskRepository = null;
        taskService = null;
    }

    @Test
    public void getAllTasks() {
        assertNotNull(taskService.getAllTask(1, 10));
    }

    @Test
    public void getSingleTask() {
        Task actual = taskService.getTask(2l);
        assertEquals(2, actual.getId());
    }

    @Test
    public void deleteTask() {
        Task delete = taskRepository.save(new Task("taskDelete", "pending"));
        taskService.deleteTask(delete.getId());
        Task actual = taskRepository.findOne(delete.getId());
        assertNull(actual);
    }

    @Test
    public void createTask() {
        assertNotNull(taskService.createTask(new Task("taskCreate", "done")));
    }


    @Test
    public void updateTask() {
        Task taskUpdate = taskService.createTask(new Task("update_task", "pending"));
        Task updatedTask = new Task(taskUpdate.getId(),"updated_task","done");
        taskService.updateTask(updatedTask);
        Task actual = taskService.getTask(taskUpdate.getId());

        assertEquals("done",actual.getStatus());
    }
}