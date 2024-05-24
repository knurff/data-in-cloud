package com.example.controllers;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dtos.BookResponseDto;
import com.example.models.LibraryBookAction;
import com.example.services.LibraryBookService;

@RestController()
@RequestMapping(value = "/api/libraries", produces = MediaType.APPLICATION_JSON_VALUE)
public class LibraryBookController {

    static final String exchangeName = "LibraryExchange";
    private final RabbitTemplate rabbitTemplate;
    private final LibraryBookService libraryBookService;

    public LibraryBookController(RabbitTemplate rabbitTemplate, LibraryBookService libraryBookService) {
        this.rabbitTemplate = rabbitTemplate;
        this.libraryBookService = libraryBookService;
    }

    @GetMapping("/{libraryId}/books")
    public LibraryBookResponse getBooksInLibrary(@PathVariable int libraryId) {
        return new LibraryBookResponse(libraryId, libraryBookService.getBooksInLibrary(libraryId));
    }

    @PostMapping("/{libraryId}/books/{bookId}")
    public LibraryBookResponse addCarToShop(@PathVariable int libraryId,
            @PathVariable String bookId) {
        LibraryBookResponse libraryBookResponse = new LibraryBookResponse(libraryId,
                libraryBookService.addBookToLibrary(bookId, libraryId));
        LibraryBookAction libraryBookAction = new LibraryBookAction(libraryId, bookId, "SAVE");
        rabbitTemplate.convertAndSend(exchangeName, "first.key", libraryBookAction);
        return libraryBookResponse;
    }

    @DeleteMapping("/{libraryId}/books/{bookId}")
    public LibraryBookResponse removeCarFromShop(@PathVariable int libraryId,
            @PathVariable String bookId) {
        LibraryBookResponse libraryBookResponse = new LibraryBookResponse(libraryId,
                libraryBookService.removeBookFromLibrary(bookId, libraryId));
        LibraryBookAction libraryBookAction = new LibraryBookAction(libraryId, bookId, "DELETE");
        rabbitTemplate.convertAndSend(exchangeName, "first.key", libraryBookAction);
        return libraryBookResponse;
    }

    public record LibraryBookResponse(Integer libraryId, List<BookResponseDto> books) {
    }
}