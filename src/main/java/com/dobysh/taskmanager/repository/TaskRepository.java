package com.dobysh.taskmanager.repository;

import com.dobysh.taskmanager.model.Task;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends GenericRepository<Task>{
}
