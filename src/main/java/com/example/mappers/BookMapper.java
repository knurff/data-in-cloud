package com.example.mappers;

import org.mapstruct.Mapper;

import com.example.dtos.BookRequestDto;
import com.example.dtos.BookResponseDto;
import com.example.models.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toModel(BookRequestDto bookRequestDto);

    BookResponseDto toResponseDto(Book book);
}

