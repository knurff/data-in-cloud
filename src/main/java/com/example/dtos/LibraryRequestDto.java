package com.example.dtos;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

public class LibraryRequestDto {

    @NotBlank(message = "Library name is required")
    public String name;

    @PastOrPresent(message = "Library date of open is required")
    public LocalDate openDate;

    @NotNull(message = "Library working status is required")
    public Boolean working;

}
