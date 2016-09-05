package com.exam.todo.services;

import com.exam.todo.dao.jpa.TaskRepository;
import com.exam.todo.domain.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by boysbee on 9/5/2016 AD.
 */
@Service
public class TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskRepository taskRepository;

    public Page<Task> getAllTask(Integer page, Integer size) {
        Page tasks = taskRepository.findAll(new PageRequest(page, size));
        return tasks;
    }

}
