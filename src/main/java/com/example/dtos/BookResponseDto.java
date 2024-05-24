package com.example.dtos;

import java.time.LocalDate;

public class BookResponseDto {

    private String id;

    private String title;

    private Integer pages;

    private LocalDate publishDate;

    public BookResponseDto(String id, String title, Integer pages, LocalDate publishDate) {
        this.id = id;
        this.title = title;
        this.pages = pages;
        this.publishDate = publishDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }
}
