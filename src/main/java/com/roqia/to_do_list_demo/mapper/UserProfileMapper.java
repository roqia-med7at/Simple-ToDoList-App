package com.roqia.to_do_list_demo.mapper;

import com.roqia.to_do_list_demo.dto.UserProfileDto;
import com.roqia.to_do_list_demo.security.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfileMapper instance = Mappers.getMapper(UserProfileMapper.class);

    public UserProfileDto mapToDto(User user);
    public User mapToUser(UserProfileDto dto);

}
