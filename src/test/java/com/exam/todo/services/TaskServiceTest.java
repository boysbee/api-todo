package com.exam.todo.services;

import com.exam.todo.Application;
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
public class TaskServiceTest {
    @Autowired
    private TaskService taskService;
    @Test
    public void getAllTasks(){
        assertNotNull(taskService.getAllTask(1,10));
    }

}