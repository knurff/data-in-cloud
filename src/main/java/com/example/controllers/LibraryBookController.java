package com.example.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dtos.BookResponseDto;
import com.example.services.LibraryBookService;

@RestController()
@RequestMapping(value = "/api/libraries", produces = MediaType.APPLICATION_JSON_VALUE)
public class LibraryBookController {

    private final LibraryBookService libraryBookService;

    public LibraryBookController(LibraryBookService libraryBookService) {
        this.libraryBookService = libraryBookService;
    }

    @GetMapping("/{libraryId}/books")
    public LibraryBookResponse getBooksInLibrary(@PathVariable int libraryId) {
        return new LibraryBookResponse(libraryId, libraryBookService.getBooksInLibrary(libraryId));
    }

    @PostMapping("/{libraryId}/books/{bookId}")
    public LibraryBookResponse addCarToShop(@PathVariable int libraryId,
            @PathVariable String bookId) {
        return new LibraryBookResponse(libraryId, libraryBookService.addBookToLibrary(bookId, libraryId));
    }

    @DeleteMapping("/{libraryId}/books/{bookId}")
    public LibraryBookResponse removeCarFromShop(@PathVariable int libraryId,
            @PathVariable String bookId) {
        return new LibraryBookResponse(libraryId, libraryBookService.removeBookFromLibrary(bookId, libraryId));
    }

    public record LibraryBookResponse(Integer libraryId, List<BookResponseDto> books) {
    }
}