package com.example;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.controllers.BookController;
import com.example.dtos.BookResponseDto;
import com.example.exceptions.EntityNotFoundException;
import com.example.handlers.LibraryExceptionHandler;
import com.example.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
public class BookControllerTest {
    @Mock
    private BookService service;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setControllerAdvice(new LibraryExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void getAll() throws Exception {
        List<BookResponseDto> carResponseDtos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            carResponseDtos.add(new BookResponseDto("someUUIDsomeUUID", "title", 10, LocalDate.now().minusDays(1)));
        }

        String responseBooks = objectMapper.writeValueAsString(carResponseDtos);
        when(service.getAll())
                .thenReturn(carResponseDtos);

        mockMvc.perform(get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(responseBooks))
                .andExpect(status().isOk());
    }

    @Test
    void getById_ValidId() throws Exception {
        BookResponseDto bookResponseDto = new BookResponseDto("someUUIDsomeUUID", "Name", 10, LocalDate.now().minusDays(1));
        String responseBookDTOJson = objectMapper.writeValueAsString(bookResponseDto);

        when(service.getById("someUUIDsomeUUID"))
                .thenReturn(bookResponseDto);

        mockMvc.perform(get("/api/books/someUUIDsomeUUID")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(responseBookDTOJson))
                .andExpect(status().isOk());
    }

    @Test
    void getById_InvalidId() throws Exception {
        when(service.getById("someUUIDsomeUUID"))
                .thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/api/books/someUUIDsomeUUID")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException));
    }

    @Test
    void delete_InvalidId() throws Exception {
        when(service.delete("someUUIDsomeUUID"))
                .thenThrow(EntityNotFoundException.class);

        mockMvc.perform(delete("/api/books/someUUIDsomeUUID"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException));
    }

    @Test
    void postBook_InvalidBook() throws Exception {
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void patchBook_InvalidBook() throws Exception {
        mockMvc.perform(patch("/api/books/someUUIDsomeUUID")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
