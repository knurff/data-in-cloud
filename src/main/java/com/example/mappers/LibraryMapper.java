package com.example.mappers;

import org.mapstruct.Mapper;

import com.example.dtos.LibraryRequestDto;
import com.example.dtos.LibraryResponseDto;
import com.example.models.Library;

@Mapper(componentModel = "spring")
public interface LibraryMapper {

    Library toModel(LibraryRequestDto libraryRequestDto);

    LibraryResponseDto toResponseDto(Library library);
}
