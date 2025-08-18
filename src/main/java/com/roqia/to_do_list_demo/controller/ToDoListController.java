package com.roqia.to_do_list_demo.controller;

import com.roqia.to_do_list_demo.dto.TaskResponseDto;
import com.roqia.to_do_list_demo.dto.ToDoListDto;
import com.roqia.to_do_list_demo.security.model.UserPrincipal;
import com.roqia.to_do_list_demo.service.TaskService;
import com.roqia.to_do_list_demo.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class ToDoListController {
    @Autowired
    private ToDoListService toDoListService;
    @Autowired
    private TaskService taskService;
    @GetMapping("")
    public ResponseEntity<List<ToDoListDto>> getAllDtos(){
        List<ToDoListDto> dtos = toDoListService.getAllDtos();
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<TaskResponseDto>> get_user_todos(@PathVariable int id, Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int user_id = userPrincipal.getUserId();
        List<TaskResponseDto> list = taskService.get_ToDo_tasks(id,user_id);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    @PostMapping("")
    public ResponseEntity<?> create_todo(Authentication authentication,@RequestBody ToDoListDto toDoListDto){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int user_id = userPrincipal.getUserId();
        toDoListService.create_dto(user_id,toDoListDto);
        return ResponseEntity.status(HttpStatus.OK).body("ToDo created successfully");
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update_todo(@PathVariable int id, Authentication authentication,@RequestBody ToDoListDto toDoListDto){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int user_id = userPrincipal.getUserId();
        toDoListService.update_ToDoList(id,user_id,toDoListDto);
        return ResponseEntity.status(HttpStatus.OK).body("ToDo updated successfully");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete_todo(@PathVariable int id, Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int user_id = userPrincipal.getUserId();
        toDoListService.delete_toDoList(id,user_id);
        return ResponseEntity.status(HttpStatus.OK).body("ToDo deleted successfully");
    }

}
