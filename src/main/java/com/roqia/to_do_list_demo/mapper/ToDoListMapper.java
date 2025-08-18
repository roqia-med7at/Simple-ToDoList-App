package com.roqia.to_do_list_demo.mapper;

import com.roqia.to_do_list_demo.dto.ToDoListDto;
import com.roqia.to_do_list_demo.model.ToDoList;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ToDoListMapper {
    ToDoListMapper instance = Mappers.getMapper(ToDoListMapper.class);

    public ToDoListDto mapToDto(ToDoList toDoList);
    public ToDoList mapToToDoList(ToDoListDto toDoListDto);
    public List<ToDoListDto> mapToDto(List<ToDoList> toDoList);
}
