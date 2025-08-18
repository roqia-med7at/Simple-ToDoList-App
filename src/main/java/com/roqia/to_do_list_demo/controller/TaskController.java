package com.roqia.to_do_list_demo.controller;

import com.roqia.to_do_list_demo.dto.SortDto;
import com.roqia.to_do_list_demo.dto.TaskRequestDto;
import com.roqia.to_do_list_demo.dto.TaskResponseDto;
import com.roqia.to_do_list_demo.dto.ToDoListDto;
import com.roqia.to_do_list_demo.model.Task;
import com.roqia.to_do_list_demo.model.ToDoList;
import com.roqia.to_do_list_demo.security.model.UserPrincipal;
import com.roqia.to_do_list_demo.service.TaskService;
import com.roqia.to_do_list_demo.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private ToDoListService toDoListService;
    @Autowired
    private TaskService taskService;

    @GetMapping("/todoId/{id}")
    public ResponseEntity<List<TaskResponseDto>> get_todo_tasks(@PathVariable int id, Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int user_id = userPrincipal.getUserId();
       List<TaskResponseDto> list = taskService.get_ToDo_tasks(id,user_id);
       return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    @PostMapping("/todo_id/{todoId}")
    public ResponseEntity<?> add_task(@PathVariable int todoId, Authentication authentication, @RequestBody TaskRequestDto taskRequestDto){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int user_id = userPrincipal.getUserId();
        taskService.add_task(todoId,user_id,taskRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("Task created successfully");
    }
    @PutMapping("/todo_id/{todoId}/task_id/{taskId}")
    public ResponseEntity<?> update_task(@PathVariable int todoId,@PathVariable int taskId, Authentication authentication,@RequestBody  TaskRequestDto taskRequestDto){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int user_id = userPrincipal.getUserId();
       taskService.update_task(taskId,user_id,todoId,taskRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("Task updated successfully");
    }
    @DeleteMapping("/todo_id/{todoId}/task_id/{taskId}")
    public ResponseEntity<?> delete_todo_task(@PathVariable int todoId,@PathVariable int taskId, Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int user_id = userPrincipal.getUserId();
        taskService.delete_task(taskId,user_id,todoId);
        return ResponseEntity.status(HttpStatus.OK).body("Task deleted successfully");
    }
    @PatchMapping("/todo_id/{todoId}/task_id/{taskId}/complete")
    public ResponseEntity<?> markTaskAsCompletedOrUnCompleted(@PathVariable int todoId, @PathVariable int taskId, Authentication authentication, @RequestBody Map<String,Boolean> flag){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int user_id = userPrincipal.getUserId();
        Boolean complete = flag.get("complete");
        taskService.markTaskAsCompletedOrUnCompleted(taskId,user_id,todoId,complete);
        return ResponseEntity.status(HttpStatus.OK).body("Task marked successfully");
    }
    @GetMapping("/todo_id/{todoId}/overdue")
    public ResponseEntity<List<TaskResponseDto>> get_overdue_tasks(@PathVariable int todoId, Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int user_id = userPrincipal.getUserId();
        List<TaskResponseDto> taskResponseDtos = taskService.get_overDueTasks(user_id,todoId);
        return ResponseEntity.status(HttpStatus.OK).body(taskResponseDtos);
    }
    @GetMapping("/todo_id/{todoId}")
    public ResponseEntity<List<TaskResponseDto>> filterByCompletion(@PathVariable int todoId, Authentication authentication,@RequestParam boolean completed){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int user_id = userPrincipal.getUserId();
       List<TaskResponseDto> taskResponseDtos = taskService.filterByCompletionStatus(completed,todoId,user_id);
        return ResponseEntity.status(HttpStatus.OK).body(taskResponseDtos);
    }
    @GetMapping("/todo_id/{todoId}/search")
    public ResponseEntity<List<TaskResponseDto>> searchTask(@PathVariable int todoId,@RequestParam String searchField,@RequestParam String searchValue,Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int user_id = userPrincipal.getUserId();
        List<TaskResponseDto> taskResponseDtos = taskService.searchTasks(searchField,searchValue,todoId,user_id);
        return ResponseEntity.status(HttpStatus.OK).body(taskResponseDtos);
    }
    @GetMapping("/todo_id/{todoId}/sort")
    public ResponseEntity<List<TaskResponseDto>> sortTasks(@PathVariable int todoId, @RequestBody SortDto sortDto, Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int user_id = userPrincipal.getUserId();
        List<TaskResponseDto> ls = taskService.sortTasks(sortDto,todoId,user_id);
//
        return ResponseEntity.status(HttpStatus.OK).body(ls);
    }

}
