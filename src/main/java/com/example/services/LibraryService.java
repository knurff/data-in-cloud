package com.example.services;

import java.util.List;

import com.example.dtos.LibraryRequestDto;
import com.example.dtos.LibraryResponseDto;

public interface LibraryService {

    List<LibraryResponseDto> getAll();

    LibraryResponseDto create(LibraryRequestDto libraryRequestDto);

    LibraryResponseDto getById(int id);

    boolean delete(int id);

    LibraryResponseDto update(LibraryRequestDto libraryRequestDto, int id);
}
