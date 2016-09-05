package com.exam.todo.dao.jpa;

import com.exam.todo.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by boysbee on 9/5/2016 AD.
 */
public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
    Page findAll(Pageable pageable);
}
