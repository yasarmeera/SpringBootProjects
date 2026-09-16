package com.library.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Book — the blueprint of a book (defines what info every book has)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

	private Integer bookId;

	@NotBlank(message = "Book name is required")
	private String bookName;

	@NotBlank(message = "Author name is required")
	private String author;

	@Positive(message = "Price must be positive")
	private double price;

	private boolean available;
}