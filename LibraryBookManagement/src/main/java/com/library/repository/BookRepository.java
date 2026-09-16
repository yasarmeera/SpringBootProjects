package com.library.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.library.model.Book;

// BookRepository — where books are stored
@Repository
public class BookRepository {

	private List<Book> books = new ArrayList<>();

	// save() — adds a new book to the list
	public Book save(Book book) {
		books.add(book);
		return book;
	}

	// findAll() — returns every book
	public List<Book> findAll() {
		return books;
	}

	// findById() — finds one book matching an ID
	public Book findById(Integer id) {
		for (Book book : books) {
			if (book.getBookId().equals(id)) {
				return book;
			}
		}
		return null;
	}

	// deleteById() — removes a book matching an ID
	public void deleteById(Integer id) {
		books.removeIf(book -> book.getBookId().equals(id));
	}
}