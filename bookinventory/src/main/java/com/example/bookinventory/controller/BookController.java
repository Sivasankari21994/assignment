package com.example.bookinventory.controller;

import com.example.bookinventory.dto.ResponsePojo;
import com.example.bookinventory.model.Books;
import com.example.bookinventory.service.BooksService;
import com.example.bookinventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BookController {

    @Autowired
    BooksService booksService;

    @RequestMapping(method = RequestMethod.GET, value = "/getBooks")
    public ResponseEntity<Object[]>  getAllBooks() {
        ResponsePojo responsePojo = null;
        List<Books> booksList = booksService.getAllBooks();
        if(booksList != null) {
            return new ResponseEntity<Object[]>(booksList, HttpStatus.OK);
       }/* else {
            responsePojo = generateResponseMessage("success", "Books are not available");
        }
        return new ResponseEntity<Object[]>(( booksList, HttpStatus.OK);*/
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addBook",   produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addBook(@RequestBody Books book) {
        ResponsePojo responsePojo = null;
        if(booksService.add(book)) {
            responsePojo = generateResponseMessage("success", "book added successfully");
        }else {
            responsePojo = generateResponseMessage("failure", "Error while adding the book");
        }
        return new ResponseEntity<Object>(responsePojo, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get/{bookId}")
    public ResponseEntity<Object> getBook(@PathVariable int bookId) {
        ResponsePojo responsePojo = null;
        Optional<Books> book = booksService.getBookById(bookId);
        if(book.isPresent()) {
            return new ResponseEntity<Object>(book, HttpStatus.OK);
        }else {
            responsePojo = generateResponseMessage("failure", "Book is not found for this id");
            return new ResponseEntity<Object>(responsePojo, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/update/{bookId}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@RequestBody Books book,@PathVariable int id) {
        ResponsePojo responsePojo = null;
        if(booksService.update(book, id)) {
            responsePojo = generateResponseMessage("success", "Updated successfully");
        }else {
            responsePojo = generateResponseMessage("failure", "Error while updating the book");
        }
        return new ResponseEntity<Object>(responsePojo, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}")
    public ResponseEntity<Object>  deleteBook(@PathVariable int id) {
        ResponsePojo responsePojo = null;
        if(booksService.deleteBook(id)) {
            responsePojo = generateResponseMessage("success", "Deleted successfully");
        }else {
            responsePojo = generateResponseMessage("failure", "Error while deleting the book");
        }
        return new ResponseEntity<Object>(responsePojo, HttpStatus.OK);
    }

    private ResponsePojo generateResponseMessage(String status, String message) {
        ResponsePojo responsePojo = new ResponsePojo();
        responsePojo.setStatus(status);
        responsePojo.setMessage(message);
        return responsePojo;
    }

}
