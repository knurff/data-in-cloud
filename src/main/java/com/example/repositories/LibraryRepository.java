package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.Library;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Integer> {
}
