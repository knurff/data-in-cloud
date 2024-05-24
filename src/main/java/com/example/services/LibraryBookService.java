package com.example.services;

import java.util.List;

import com.example.dtos.BookResponseDto;

public interface LibraryBookService {
    List<BookResponseDto> getBooksInLibrary(Integer libraryId);

    List<BookResponseDto> addBookToLibrary(String bookId, int libraryId);

    List<BookResponseDto> removeBookFromLibrary(String bookId, int libraryId);
}
