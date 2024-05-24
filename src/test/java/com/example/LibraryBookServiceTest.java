package com.example;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.controllers.LibraryBookController;
import com.example.dtos.BookResponseDto;
import com.example.handlers.LibraryExceptionHandler;
import com.example.services.LibraryBookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
public class LibraryBookServiceTest {

    @Mock
    private LibraryBookService service;

    @InjectMocks
    private LibraryBookController controller;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new LibraryExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void getAllCarsFromShop() throws Exception {
        List<BookResponseDto> books = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            books.add(new BookResponseDto("someUUIDsomeUUID", "Name", 10, LocalDate.now().minusDays(1)));
        }
        String booksDTOList = objectMapper.writeValueAsString(books);
        when(service.getBooksInLibrary(1))
                .thenReturn(books);



        mockMvc.perform(get("/api/libraries/1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(booksDTOList))
                .andExpect(status().isOk());
    }

    @Test
    void addDocumentToStudent() throws Exception {
        List<BookResponseDto> books = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            books.add(new BookResponseDto("someUUIDsomeUUID", "Name", 10, LocalDate.now().minusDays(1)));
        }

        String booksDTOList = objectMapper.writeValueAsString(books);
        when(service.addBookToLibrary("63a7d215d52900a17925625", 1))
                .thenReturn(books);



        mockMvc.perform(post("/api/libraries/1/books/63a7d215d52900a17925625")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(booksDTOList))
                .andExpect(status().isOk());
    }

    @Test
    void deleteDocumentFromStudent() throws Exception {
        List<BookResponseDto> books = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            books.add(new BookResponseDto("someUUIDsomeUUID", "Name", 10, LocalDate.now().minusDays(1)));
        }


        String booksDTOList = objectMapper.writeValueAsString(books);
        when(service.removeBookFromLibrary("63a7d215d52900a17925625", 1))
                .thenReturn(books);



        mockMvc.perform(delete("/api/libraries/1/books/63a7d215d52900a17925625")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(booksDTOList))
                .andExpect(status().isOk());

    }
}
