package com.exam.todo.dao.jpa;

import com.exam.todo.Application;
import com.exam.todo.domain.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created by boysbee on 9/5/2016 AD.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@WebIntegrationTest
@Transactional
@Profile("test")
public class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void it_should_create_task() {
        Task result = createTask("task2", "done");
        assertEquals("task2", result.getDescription());
    }

    private Task createTask(String description, String status) {
        return taskRepository.save(new Task(description, status));
    }

    @Test
    public void it_should_found_task(){
        Task expected = createTask("task2","done");
        Task actual = taskRepository.findOne(expected.getId());
        assertEquals(expected.getDescription(),actual.getDescription());
    }

}