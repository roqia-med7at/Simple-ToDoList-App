package com.roqia.to_do_list_demo.repo;

import com.roqia.to_do_list_demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task,Integer>, JpaSpecificationExecutor<Task> {
//    @Query("SELECT task FROM Task task WHERE toDoList = ?1 ORDER BY task.dueDate ASC")
//    List<Task> sortTasksByDueDate(int toDoId);
//    @Query("SELECT task FROM Task task WHERE toDoList = ?1 ORDER BY task.createdAt DESC")
//    List<Task> sortTasksByCreatedAt(int toDoId);
//    @Query("SELECT * FROM task t WHERE t.todo_list_id = ?1 ORDER BY CASE t.priority WHEN 'HIGH' THEN 1 WHEN 'MEDIUM' THEN 2 WHEN 'LOW' THEN 3 END ASC")
//    List<Task> sortTasksByPriority(int toDoId);
    @Query(
            value = "SELECT * FROM task t WHERE t.id = ?1 AND t.todo_list_id = ?2",
            nativeQuery = true)
    Task findByIdAndToDoList(int id,int toDoId);
    @Modifying
    @Query(value = "UPDATE task  SET status='overdue' WHERE due_date < ?1  ",nativeQuery = true)
    void markTaskStatusAsOverDue(LocalDateTime now);
    List<Task>findAllByStatus(String status);
//    List<Task> findByTitleContainingIgnoreCaseAndToDoList(String title,int todoId);
}
