package com.exam.todo.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by boysbee on 9/5/2016 AD.
 */
@Entity
@Table(name = "task")
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private final String description;

    private final String status;

    public Task() {
        this.description = "";
        this.status = "pending";
    }


    public Task(final String description, final String status) {
        this.description = description;
        this.status = status;
    }
    public Task(final long id,final String description, final String status) {
        this.id = id;
        this.description = description;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
}
