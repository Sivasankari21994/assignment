package com.example.bookinventory.dao;

import com.example.bookinventory.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksInventoyDAO extends JpaRepository<Books, Integer> {

}
