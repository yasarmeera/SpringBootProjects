package com.library.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.library.model.Book;
import com.library.service.BookService;

import jakarta.validation.Valid;

// BookController — the front door, receives requests
@RestController
@RequestMapping("/books")
public class BookController {

	private BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	// POST /books — add a new book
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Book addBook(@Valid @RequestBody Book book) {
		return bookService.addBook(book);
	}

	// GET /books — get every book
	@GetMapping
	public List<Book> getAllBooks() {
		return bookService.getAllBooks();
	}

	// GET /books/{id} — get one book
	@GetMapping("/{id}")
	public Book getBookById(@PathVariable Integer id) {
		return bookService.getBookById(id);
	}

	// PUT /books/{id} — replace a book fully
	@PutMapping("/{id}")
	public Book updateBook(@PathVariable Integer id, @Valid @RequestBody Book book) {
		return bookService.updateBook(id, book);
	}

	// PATCH /books/{id}/availability — update just one field
	@PatchMapping("/{id}/availability")
	public Book updateAvailability(@PathVariable Integer id, @RequestParam boolean available) {
		return bookService.updateAvailability(id, available);
	}

	// GET /books/search?name=... — search books by name
	@GetMapping("/search")
	public List<Book> searchBook(@RequestParam String name) {
		return bookService.searchByName(name);
	}

	// DELETE /books/{id} — delete a book
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBook(@PathVariable Integer id) {
		bookService.deleteBook(id);
	}
}