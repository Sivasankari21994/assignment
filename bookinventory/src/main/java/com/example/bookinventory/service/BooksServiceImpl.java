package com.example.bookinventory.service;

import com.example.bookinventory.dao.BooksInventoyDAO;
import com.example.bookinventory.model.Books;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BooksServiceImpl implements BooksService {

    @Autowired
    BooksInventoyDAO booksInventoyDAO;

    @Override
    public List<Books> getAllBooks() {
        try {
            return booksInventoyDAO.findAll();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean add(Books book) {
        try {
            booksInventoyDAO.save(book);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Books> getBookById(int id) {
        try {
            return booksInventoyDAO.findById(id);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Books bookInput, int id) {
        try {
            Books book = booksInventoyDAO.findById(id);
            if(book != null) {
                book.setAuthorName(bookInput.getAuthorName());
                book.setBookName(book.getAuthorName());
                booksInventoyDAO.save(book);
                return true;
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteBook(int id) {
        try {
            booksInventoyDAO.deleteById(id);
            return true;
        }catch (Exception e) {
            return false;
        }
        return false;
    }
}
