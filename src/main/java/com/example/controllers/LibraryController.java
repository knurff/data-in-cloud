package com.example.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dtos.LibraryRequestDto;
import com.example.dtos.LibraryResponseDto;
import com.example.services.LibraryService;

@RestController()
@RequestMapping(value = "/api/libraries", produces = MediaType.APPLICATION_JSON_VALUE)
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping()
    public List<LibraryResponseDto> getAll() {
        return libraryService.getAll();
    }

    @GetMapping("/{id}")
    public LibraryResponseDto getById(@PathVariable String id) {
        return libraryService.getById(Integer.parseInt(id));
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable int id) {
        return libraryService.delete(id);
    }

    @PostMapping()
    public LibraryResponseDto postLibrary(@Valid @RequestBody LibraryRequestDto libraryRequestDto) {
        return libraryService.create(libraryRequestDto);
    }

    @PatchMapping("/{id}")
    public LibraryResponseDto delete(@Valid @RequestBody LibraryRequestDto libraryRequestDto, @PathVariable int id) {
        return libraryService.update(libraryRequestDto, id);
    }

}