package com.roqia.to_do_list_demo.mapper;

import com.roqia.to_do_list_demo.dto.TaskRequestDto;
import com.roqia.to_do_list_demo.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskRequestMapper {
    TaskRequestMapper instance = Mappers.getMapper(TaskRequestMapper.class);

    public TaskRequestDto mapToDto(Task task);
    public Task mapToTask(TaskRequestDto taskRequestDto);

}
