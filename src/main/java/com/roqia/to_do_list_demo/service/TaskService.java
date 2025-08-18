package com.roqia.to_do_list_demo.service;

import com.roqia.to_do_list_demo.dto.SortDto;
import com.roqia.to_do_list_demo.dto.TaskRequestDto;
import com.roqia.to_do_list_demo.dto.TaskResponseDto;
import com.roqia.to_do_list_demo.exceptionHandlers.RecordNotFoundException;
import com.roqia.to_do_list_demo.mapper.TaskRequestMapper;
import com.roqia.to_do_list_demo.mapper.TaskResponseMapper;
import com.roqia.to_do_list_demo.model.Task;
import com.roqia.to_do_list_demo.model.TaskSpecification;
import com.roqia.to_do_list_demo.model.ToDoList;
import com.roqia.to_do_list_demo.repo.TaskRepo;
import com.roqia.to_do_list_demo.security.model.User;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private TaskRequestMapper taskRequestMapper;
    @Autowired
    private TaskResponseMapper taskResponseMapper;
    @Autowired
    private ToDoListService toDoListService;
    @Autowired
    private UserService userService;

    public void add_task( int toDo_id,int user_id,TaskRequestDto taskRequestDto){
        ToDoList toDoList = toDoListService.get_toDoList(toDo_id,user_id);
        Task task = taskRequestMapper.mapToTask(taskRequestDto);
        task.setToDoList(toDoList);
        taskRepo.save(task);
        List<Task> tasks = toDoList.getTasks();
        if(toDoList.getTasks()==null){
            tasks=new ArrayList<>();
            tasks.add(task);
        }
        else {
            tasks.add(task);
        }
    }

    public  Task get_task(int task_id,int user_id,int toDo_id){
        ToDoList toDoList = toDoListService.get_toDoList(toDo_id,user_id);
        Task task = taskRepo.findByIdAndToDoList(task_id,toDoList.getId());
        if(task==null){
            throw new RecordNotFoundException("No such task with id"+task_id);
        }
        else {
            return task;
        }
    }
    public TaskResponseDto get_task_dto(int task_id,int user_id,int toDo_id){
        Task task = get_task(task_id,user_id,toDo_id);
        return taskResponseMapper.mapToDto(task);
    }
    public List<TaskResponseDto> get_ToDo_tasks(int toDo_id,int user_id){
        ToDoList toDoList = toDoListService.get_toDoList(toDo_id,user_id);
        return taskResponseMapper.mapToDto(toDoList.getTasks());
    }
    public void markTaskAsCompletedOrUnCompleted(int task_id,int user_id,int toDo_id,boolean completed){
        Task task = get_task(task_id,user_id,toDo_id);
        if (completed) {
            task.setStatus("completed");
        }else {
            task.setStatus("uncompleted");
        }
        taskRepo.save(task);
    }
    public void delete_task(int task_id,int user_id,int toDo_id){
        Task task = get_task(task_id,user_id,toDo_id);
        taskRepo.delete(task);
    }
    public void update_task(int task_id,int user_id,int toDo_id,TaskRequestDto taskRequestDto){
        Task task = get_task(task_id,user_id,toDo_id);
        Task updated_task = taskRequestMapper.mapToTask(taskRequestDto);
        task.setTitle(updated_task.getTitle());
        task.setDescription(updated_task.getDescription());
        task.setPriority(updated_task.getPriority());
        task.setStatus(updated_task.getStatus());
        task.setCreatedAt(updated_task.getCreatedAt());
        task.setDueDate(updated_task.getDueDate());
        taskRepo.save(task);
    }
//    public List<TaskResponseDto> sortTasksByDueDate(int toDo_id, int user_id){
//        ToDoList toDoList = toDoListService.get_toDoList(toDo_id,user_id);
//       List<Task> ls = taskRepo.sortTasksByDueDate(toDoList.getId());
//       return taskResponseMapper.mapToDto(ls);
//    }
//    public List<TaskResponseDto> sortTasksByPriority(int toDo_id, int user_id){
//        ToDoList toDoList = toDoListService.get_toDoList(toDo_id,user_id);
//        List<Task> ls = taskRepo.sortTasksByPriority(toDoList.getId());
//        return taskResponseMapper.mapToDto(ls);
//
//    }
//    public List<TaskResponseDto> sortTasksByCreatedAt(int toDoId,int userId){
//        ToDoList toDoList = toDoListService.get_toDoList(toDoId,userId);
//        List<Task> ls = taskRepo.sortTasksByCreatedAt(toDoList.getId());
//        return taskResponseMapper.mapToDto(ls);
//    }
    public List<TaskResponseDto> sortTasks(SortDto dto,int toDo_id,int user_id){
        ToDoList toDoList = toDoListService.get_toDoList(toDo_id, user_id);
        List<Task> tasks = taskRepo.findAll(Sort.by(Sort.Direction.fromString(dto.getOrderDirection()),dto.getField()));
        return taskResponseMapper.mapToDto(tasks);
    }

    public List<TaskResponseDto> filterByCompletionStatus(boolean completed,int toDo_id,int user_id) {
        ToDoList toDoList = toDoListService.get_toDoList(toDo_id, user_id);
        List<Task> tasks = toDoList.getTasks();
        List<Task> completed_tasks = new ArrayList<>();
        List<Task> uncompleted_tasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getStatus().equals("completed")) {
                completed_tasks.add(task);
            } else {
                uncompleted_tasks.add(task);
            }
        }
        if (completed) {
            return taskResponseMapper.mapToDto(completed_tasks);
        } else {
            return taskResponseMapper.mapToDto(uncompleted_tasks);
        }
    }
//    public List<TaskResponseDto> searchTaskByTitle(String title,int toDo_id,int user_id){
//        ToDoList toDoList = toDoListService.get_toDoList(toDo_id, user_id);
//       List<Task> tasks = taskRepo.findByTitleContainingIgnoreCaseAndToDoList(title,toDoList.getId());
//     return  taskResponseMapper.mapToDto(tasks);
//    }
      public List<TaskResponseDto> searchTasks(String field,String value, int toDo_id, int user_id) {
        ToDoList toDoList = toDoListService.get_toDoList(toDo_id, user_id);
          Specification<Task> specification = TaskSpecification.hasFieldLike(field,value);
          List<Task> tasks = taskRepo.findAll(specification);
          return taskResponseMapper.mapToDto(tasks);
     }
    @Transactional
    public void markTasksAsOverDue(){
        taskRepo.markTaskStatusAsOverDue(LocalDateTime.now());

    }
    public List<TaskResponseDto>get_overDueTasks(int user_id,int toDo_id){
      List<Task> overDueTasks = taskRepo.findAllByStatus("overdue");
      return  taskResponseMapper.mapToDto(overDueTasks);
    }
}
