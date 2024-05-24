package com.example.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.example.dtos.BookResponseDto;
import com.example.exceptions.EntityNotFoundException;

@Service
public class LibraryBookServiceImpl implements LibraryBookService {


    private final String CONNECTION_STRING = "DefaultEndpointsProtocol=https;AccountName=dataincloudlibrary;AccountKey=UHuJE6MV39eh0pqd9wkhr7fnOY322vXZsJqBdPHFeAN13PGS5u9U9NF+aGVkxTgD7FEkcwxBKy/F+AStaobVkQ==;EndpointSuffix=core.windows.net";
    private final String CONTAINER_NAME = "library";

    private final LibraryService libraryService;
    private final BookService bookService;

    public LibraryBookServiceImpl(LibraryService libraryService, BookService bookService) {
        this.libraryService = libraryService;
        this.bookService = bookService;
    }

    @Override
    public List<BookResponseDto> getBooksInLibrary(Integer libraryId) {
        libraryService.getById(libraryId);

        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(
                CONNECTION_STRING).buildClient();
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(CONTAINER_NAME);

        List<String> files = new ArrayList<>();
        containerClient.listBlobs().iterator().forEachRemaining(blobItem -> files.add(blobItem.getName()));

        return getListOfBooksFromBlobList(files, libraryId);
    }


    @Override
    public List<BookResponseDto> addBookToLibrary(String bookId, int libraryId) {
        libraryService.getById(libraryId);
        bookService.getById(bookId);

        String fileName = libraryId + "_" + bookId;

        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(
                CONNECTION_STRING).buildClient();
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(CONTAINER_NAME);
        BlobClient blobClient = containerClient.getBlobClient(fileName);

        if (!blobClient.exists()) {
            blobClient.upload(BinaryData.fromBytes(new byte[] { }));
        }

        List<String> files = new ArrayList<>();
        containerClient.listBlobs().iterator().forEachRemaining(blobItem -> files.add(blobItem.getName()));

        return getListOfBooksFromBlobList(files, libraryId);
    }

    @Override
    public List<BookResponseDto> removeBookFromLibrary(String bookId, int libraryId) {
        bookService.getById(bookId);
        libraryService.getById(libraryId);

        String fileName = libraryId + "_" + bookId;

        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(
                CONNECTION_STRING).buildClient();
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(CONTAINER_NAME);
        BlobClient blobClient = containerClient.getBlobClient(fileName);

        if (blobClient.exists()) {
            blobClient.delete();
        }

        List<String> files = new ArrayList<>();
        containerClient.listBlobs().iterator().forEachRemaining(blobItem -> files.add(blobItem.getName()));

        return getListOfBooksFromBlobList(files, libraryId);
    }

    private List<BookResponseDto> getListOfBooksFromBlobList(List<String> files, Integer libraryId) {
        return files.stream()
                .filter(e -> e.startsWith(libraryId + "_"))
                .map(e -> e.replace(libraryId + "_", ""))
                .map(id -> {
                    try {
                        return bookService.getById(id);
                    } catch (EntityNotFoundException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
