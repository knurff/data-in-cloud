package com.example.services;

import org.springframework.stereotype.Service;

import com.example.models.LibraryBookAction;
import com.example.repositories.LibraryBookActionRepository;

@Service
public class LibraryBookActionServiceImpl implements LibraryBookActionService {

    private final LibraryBookActionRepository repository;

    public LibraryBookActionServiceImpl(LibraryBookActionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(LibraryBookAction libraryBookAction) {
        repository.save(libraryBookAction);
    }
}
