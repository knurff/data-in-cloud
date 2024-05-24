package com.example.dtos;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

public class BookRequestDto {

    @NotBlank(message = "Book title is required")
    public String title;

    @NotNull(message = "Pages count is required or 0")
    @Min(value = 0, message = "Pages count can`t have negative amount")
    public Integer pages;

    @PastOrPresent
    public LocalDate publishDate;

}
