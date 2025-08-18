package com.roqia.to_do_list_demo.mapper;

import com.roqia.to_do_list_demo.dto.TaskRequestDto;
import com.roqia.to_do_list_demo.dto.TaskResponseDto;
import com.roqia.to_do_list_demo.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskResponseMapper {
    TaskResponseMapper instance = Mappers.getMapper(TaskResponseMapper.class);

    public TaskResponseDto mapToDto(Task task);
    public Task mapToTask(TaskResponseDto taskResponseDto);
    public List<TaskResponseDto> mapToDto(List<Task> task);
}
