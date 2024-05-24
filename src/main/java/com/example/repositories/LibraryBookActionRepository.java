package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.LibraryBookAction;

public interface LibraryBookActionRepository extends JpaRepository<LibraryBookAction, Integer> {
}
