package com.exam.todo.services;

import com.exam.todo.dao.jpa.TaskRepository;
import com.exam.todo.domain.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
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
    CounterService counterService;

    @Autowired
    GaugeService gaugeService;

    @Autowired
    private TaskRepository taskRepository;

    public Page<Task> getAllTask(Integer page, Integer size) {
        Page tasks = taskRepository.findAll(new PageRequest(page, size));
        if (size > 50) {
            counterService.increment("TaskService.getAllTask.largePayload");
        }
        return tasks;
    }

    public Task getTask(final Long id) {
        return taskRepository.findOne(id);
    }

    public void deleteTask(final Long id) {
        this.taskRepository.delete(id);
    }

    public Task createTask(final Task task) {
        return taskRepository.save(task);
    }
}
