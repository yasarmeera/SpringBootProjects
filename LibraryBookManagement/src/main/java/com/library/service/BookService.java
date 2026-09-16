package com.library.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.library.exception.BookNotFoundException;
import com.library.exception.DuplicateBookIdException;
import com.library.model.Book;
import com.library.repository.BookRepository;

// BookService — where the business rules live
@Service
public class BookService {

	private BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	// addBook() — checks for duplicate ID, then saves the book
	public Book addBook(Book book) {
		Book existing = bookRepository.findById(book.getBookId());
		if (existing != null) {
			throw new DuplicateBookIdException("A book with ID " + book.getBookId() + " already exists");
		}
		return bookRepository.save(book);
	}

	// getAllBooks() — returns the full list
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	// getBookById() — finds one book, throws error if missing
	public Book getBookById(Integer id) {
		Book book = bookRepository.findById(id);
		if (book == null) {
			throw new BookNotFoundException("Book with ID " + id + " not found");
		}
		return book;
	}

	// updateBook() — replaces all details of an existing book
	public Book updateBook(Integer id, Book newBook) {
		Book existingBook = getBookById(id);
		existingBook.setBookName(newBook.getBookName());
		existingBook.setAuthor(newBook.getAuthor());
		existingBook.setPrice(newBook.getPrice());
		existingBook.setAvailable(newBook.isAvailable());
		return existingBook;
	}

	// updateAvailability() — changes only the available/unavailable status
	public Book updateAvailability(Integer id, boolean available) {
		Book book = getBookById(id);
		book.setAvailable(available);
		return book;
	}

	// searchByName() — finds books whose name matches the search text
	public List<Book> searchByName(String name) {
		List<Book> result = new ArrayList<>();
		for (Book book : bookRepository.findAll()) {
			if (book.getBookName().toLowerCase().contains(name.toLowerCase())) {
				result.add(book);
			}
		}
		return result;
	}

	// deleteBook() — removes a book, throws error if it doesn't exist
	public void deleteBook(Integer id) {
		getBookById(id);
		bookRepository.deleteById(id);
	}
}