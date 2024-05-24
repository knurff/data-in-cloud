package com.example.models;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class LibraryBookAction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "LibraryId is required")
    @Min(value = 0, message = "LibraryId must be positive")
    private Integer libraryId;

    @NotBlank(message = "BookId is required")
    private String bookId;

    @NotBlank(message = "Action is required")
    private String action;

    @CreationTimestamp
    private LocalDate createdAt;

    public LibraryBookAction() {
    }

    public LibraryBookAction(Integer libraryId, String bookId, String action) {
        this.libraryId = libraryId;
        this.bookId = bookId;
        this.action = action;
    }

    @Override
    public String toString() {
        return "LibraryBookAction{" +
                "id=" + id +
                ", libraryId=" + libraryId +
                ", bookId='" + bookId + '\'' +
                ", action='" + action + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
