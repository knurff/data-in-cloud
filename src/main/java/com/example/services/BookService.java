package com.example.services;

import java.util.List;

import com.example.dtos.BookRequestDto;
import com.example.dtos.BookResponseDto;

public interface BookService {

    List<BookResponseDto> getAll();

    BookResponseDto create(BookRequestDto bookRequestDto);

    BookResponseDto getById(String id);

    boolean delete(String id);

    BookResponseDto update(BookRequestDto bookRequestDto, String id);
}
