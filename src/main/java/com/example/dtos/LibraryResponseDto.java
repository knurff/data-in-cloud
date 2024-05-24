package com.example.dtos;

import java.time.LocalDate;

public class LibraryResponseDto {

    private Integer id;

    private String name;

    private LocalDate openDate;

    private Boolean working;

    public LibraryResponseDto(Integer id, String name, LocalDate openDate, Boolean working) {
        this.id = id;
        this.name = name;
        this.openDate = openDate;
        this.working = working;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    public Boolean getIsWorking() {
        return working;
    }

    public void setIsWorking(Boolean working) {
        this.working = working;
    }
}
