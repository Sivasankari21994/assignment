package com.example.bookinventory.service;

import com.example.bookinventory.model.Books;

import java.util.List;
import java.util.Optional;

public interface BooksService {

    List<Books> getAllBooks();
    boolean add(Books book) ;
    Optional<Books> getBookById(int id);
   boolean update(Books book, int id);
   boolean deleteBook(int id);

}
