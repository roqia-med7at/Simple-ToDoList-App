package com.roqia.to_do_list_demo.repo;

import com.roqia.to_do_list_demo.model.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToDoListRepo extends JpaRepository<ToDoList,Integer> {
@Query("SELECT ls FROM ToDoList ls WHERE ls.id=?1 AND ls.userId=?2")
    ToDoList findByIdAndUserId(int toDoId,int user_id);
   List<ToDoList> findAllByUserId(int user_id);
}
