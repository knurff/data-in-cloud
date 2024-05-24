package com.example.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.dtos.LibraryRequestDto;
import com.example.dtos.LibraryResponseDto;
import com.example.exceptions.EntityNotFoundException;
import com.example.mappers.LibraryMapper;
import com.example.models.Library;
import com.example.repositories.LibraryRepository;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final LibraryRepository libraryRepository;
    private final LibraryMapper libraryMapper;

    public LibraryServiceImpl(LibraryRepository libraryRepository, LibraryMapper libraryMapper) {
        this.libraryRepository = libraryRepository;
        this.libraryMapper = libraryMapper;
    }

    @Override
    public List<LibraryResponseDto> getAll() {
        return libraryRepository.findAll().stream().map(libraryMapper::toResponseDto).collect(Collectors.toList());
    }

    @Override
    public LibraryResponseDto create(LibraryRequestDto libraryRequestDto) {
        return libraryMapper.toResponseDto(libraryRepository.save(libraryMapper.toModel(libraryRequestDto)));
    }

    @Override
    public LibraryResponseDto getById(int id) {
        return libraryMapper.toResponseDto(findLibrary(id));
    }

    @Override
    public boolean delete(int id) {
        libraryRepository.deleteById(id);
        return true;
    }

    @Override
    public LibraryResponseDto update(LibraryRequestDto libraryRequestDto, int id) {
        return libraryMapper.toResponseDto(libraryRepository.findById(id).map(entity -> {
            entity.setName(libraryRequestDto.name);
            entity.setWorking(libraryRequestDto.working);
            entity.setOpenDate(libraryRequestDto.openDate);
            return libraryRepository.save(entity);
        }).orElseThrow(() -> new EntityNotFoundException("Library with id " + id + " not found")));
    }

    private Library findLibrary(int id) {
        return libraryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Library with id " + id + " not found"));

    }
}
