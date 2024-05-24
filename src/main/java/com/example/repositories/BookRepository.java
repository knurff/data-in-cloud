package com.example.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.models.Book;

public interface BookRepository  extends MongoRepository<Book, String> {
}
