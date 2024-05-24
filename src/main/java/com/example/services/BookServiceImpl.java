package com.example.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.dtos.BookRequestDto;
import com.example.dtos.BookResponseDto;
import com.example.exceptions.EntityNotFoundException;
import com.example.mappers.BookMapper;
import com.example.models.Book;
import com.example.repositories.BookRepository;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<BookResponseDto> getAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDto create(BookRequestDto bookRequestDto) {
        Book book = bookMapper.toModel(bookRequestDto);

        return bookMapper.toResponseDto(bookRepository.save(book));
    }

    @Override
    public BookResponseDto getById(String id) {
        return bookMapper.toResponseDto(this.findBookBy(id));
    }

    @Override
    public boolean delete(String id) {
        bookRepository.delete(this.findBookBy(id));
        return true;
    }

    @Override
    public BookResponseDto update(BookRequestDto bookRequestDto, String id) {
        return bookMapper.toResponseDto(
                bookRepository.findById(id)
                        .map(entity -> {
                            entity.setTitle(bookRequestDto.title);
                            entity.setPages(bookRequestDto.pages);
                            entity.setPublishDate(bookRequestDto.publishDate);
                            return bookRepository.save(entity);
                        })
                        .orElseThrow(() -> new EntityNotFoundException("StudentDocument with id " + id + " not found"))
        );
    }

    private Book findBookBy(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car shop with id " + id + " not found"));
    }
}