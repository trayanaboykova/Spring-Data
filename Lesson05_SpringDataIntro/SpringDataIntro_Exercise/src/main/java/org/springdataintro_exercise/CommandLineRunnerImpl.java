package org.springdataintro_exercise;

import org.springdataintro_exercise.service.AuthorService;
import org.springdataintro_exercise.service.BookService;
import org.springdataintro_exercise.service.CategoryService;
import org.springframework.boot.CommandLineRunner;

import java.io.IOException;

public class CommandLineRunnerImpl implements CommandLineRunner {
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    public CommandLineRunnerImpl(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
    }

    private void seedData() throws IOException {
        this.categoryService.seedCategories();
        this.authorService.seedAuthors();
        this.bookService.seedBooks();
    }
}
