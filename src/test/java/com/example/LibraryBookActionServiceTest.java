package com.example;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.models.LibraryBookAction;
import com.example.repositories.LibraryBookActionRepository;
import com.example.services.LibraryBookActionServiceImpl;

@SpringBootTest
public class LibraryBookActionServiceTest {
    @Mock
    private LibraryBookActionRepository repository;

    @InjectMocks
    private LibraryBookActionServiceImpl service;

    @Test
    public void testSave() {
        LibraryBookAction action = new LibraryBookAction();
        service.save(action);
        verify(repository, times(1)).save(action);
    }
}
