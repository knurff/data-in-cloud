package com.example.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dtos.BookRequestDto;
import com.example.dtos.BookResponseDto;
import com.example.services.BookService;

@RestController()
@RequestMapping(value = "/api/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping()
    public List<BookResponseDto> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/{id:[a-zA-Z0-9]*}")
    public BookResponseDto getById(@PathVariable String id) {
        return bookService.getById(id);
    }

    @DeleteMapping("/{id:[a-zA-Z0-9]*}")
    public boolean delete(@PathVariable String id) {
        return bookService.delete(id);
    }

    @PostMapping()
    public BookResponseDto postBook(@Valid @RequestBody BookRequestDto bookRequestDto) {
        return bookService.create(bookRequestDto);
    }

    @PatchMapping("/{id:[a-zA-Z0-9]*}")
    public BookResponseDto delete(@Valid @RequestBody BookRequestDto bookRequestDto, @PathVariable String id) {
        return bookService.update(bookRequestDto, id);
    }
}
