package com.roqia.to_do_list_demo.mapper;

import com.roqia.to_do_list_demo.dto.TokenDto;
import com.roqia.to_do_list_demo.security.model.RefreshToken;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    TokenMapper instance = Mappers.getMapper(TokenMapper.class);
    public TokenDto mapToDto(RefreshToken token);
    public RefreshToken mapToToken(TokenDto dto);
}
