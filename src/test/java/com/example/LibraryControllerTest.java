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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.controllers.LibraryController;
import com.example.dtos.LibraryResponseDto;
import com.example.exceptions.EntityNotFoundException;
import com.example.handlers.LibraryExceptionHandler;
import com.example.models.Library;
import com.example.services.LibraryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
public class LibraryControllerTest {

    @Mock
    private LibraryService libraryService;

    @InjectMocks
    private LibraryController libraryController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(libraryController)
                .setControllerAdvice(new LibraryExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void getAll() throws Exception {
        ArrayList<LibraryResponseDto> libraries = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            libraries.add(new LibraryResponseDto(i, "Library " + i, LocalDate.now().minusYears(i), true));
        }
        String responseLibraries = objectMapper.writeValueAsString(libraries);
        when(libraryService.getAll()).thenReturn(libraries);

        mockMvc.perform(get("/api/libraries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(responseLibraries))
                .andExpect(status().isOk());
    }

    @Test
    void getById_ValidId() throws Exception {
        LibraryResponseDto responseLibrary = new LibraryResponseDto(1, "Library " + 1, LocalDate.now().minusYears(1), true);
        String responseLibraryString = objectMapper.writeValueAsString(responseLibrary);

        when(libraryService.getById(1))
                .thenReturn(responseLibrary);

        mockMvc.perform(get("/api/libraries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(responseLibraryString))
                .andExpect(status().isOk());
    }

    @Test
    void getById_InvalidId() throws Exception {
        when(libraryService.getById(99999))
                .thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/api/libraries/99999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException));
    }

    @Test
    void delete_InvalidId() throws Exception {
        when(libraryService.delete(99999))
                .thenThrow(EntityNotFoundException.class);

        mockMvc.perform(delete("/api/libraries/99999"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException));
    }

    @Test
    void postLibrary_InvalidLibrary() throws Exception {
        Library responseLibrary = new Library("Library " + 1, LocalDate.now().minusYears(1), true);
        String responseLibraryString = objectMapper.writeValueAsString(responseLibrary);

        mockMvc.perform(post("/api/libraries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(responseLibraryString))
                .andExpect(status().isBadRequest());
    }

    @Test
    void patchLibrary_InvalidLibrary() throws Exception {
        Library responseLibrary = new Library("Library " + 1, LocalDate.now().minusYears(1), true);
        String responseLibraryString = objectMapper.writeValueAsString(responseLibrary);

        mockMvc.perform(patch("/api/libraries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(responseLibraryString))
                .andExpect(status().isBadRequest());
    }
}
