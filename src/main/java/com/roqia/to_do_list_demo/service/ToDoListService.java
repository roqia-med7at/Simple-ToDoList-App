package com.roqia.to_do_list_demo.service;

import com.roqia.to_do_list_demo.dto.TaskResponseDto;
import com.roqia.to_do_list_demo.dto.ToDoListDto;
import com.roqia.to_do_list_demo.exceptionHandlers.RecordNotFoundException;
import com.roqia.to_do_list_demo.mapper.TaskResponseMapper;
import com.roqia.to_do_list_demo.mapper.ToDoListMapper;
import com.roqia.to_do_list_demo.model.Task;
import com.roqia.to_do_list_demo.model.ToDoList;
import com.roqia.to_do_list_demo.repo.ToDoListRepo;
import com.roqia.to_do_list_demo.security.model.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ToDoListService {
    @Autowired
    private ToDoListRepo toDoListRepo;
    @Autowired
    private ToDoListMapper toDoListMapper;
    @Autowired
    private TaskResponseMapper taskResponseMapper;
    @Autowired
    private UserService userService;

    public void create_dto(int user_id,ToDoListDto toDoListDto){
        User user = userService.get_user(user_id);
        ToDoList toDoList = toDoListMapper.mapToToDoList(toDoListDto);
        toDoList.setUserId(user_id);
        toDoListRepo.save(toDoList);

    }
    public ToDoList get_toDoList(int toDo_id,int user_id){
        User user = userService.get_user(user_id);
        ToDoList toDoList = toDoListRepo.findByIdAndUserId(toDo_id,user.getId());
        if(toDoList==null){
            throw new RecordNotFoundException("No such toDoList with id : "+ toDo_id);

        }
        else {
            return toDoList;
        }

    }
    public ToDoListDto get_ToDoListDto(int toDoId,int user_id){
        ToDoList toDoList = get_toDoList(toDoId,user_id);
        return toDoListMapper.mapToDto(toDoList);
    }
    public void update_ToDoList(int toDo_id,int user_id,ToDoListDto toDoListDto){
        ToDoList toDoList = get_toDoList(toDo_id,user_id);
        ToDoList updated_toDoList = toDoListMapper.mapToToDoList(toDoListDto);
        toDoList.setTitle(updated_toDoList.getTitle());
        toDoList.setDescription(updated_toDoList.getDescription());
        toDoListRepo.save(toDoList);
    }
    public void delete_toDoList(int toDo_id,int user_id){
        ToDoList toDoList = get_toDoList(toDo_id,user_id);
        toDoListRepo.delete(toDoList);
    }
    public List<ToDoListDto> getAllUserToDos(int user_id){
        List<ToDoList> toDoLists = toDoListRepo.findAllByUserId(user_id);
        return toDoListMapper.mapToDto(toDoLists);
    }
    public List<ToDoListDto> getAllDtos(){
        List<ToDoList> list = toDoListRepo.findAll();
        return toDoListMapper.mapToDto(list);
    }


}
