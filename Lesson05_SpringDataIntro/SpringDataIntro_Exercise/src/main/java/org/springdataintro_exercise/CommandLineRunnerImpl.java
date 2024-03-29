package org.springdataintro_exercise;

import org.springdataintro_exercise.service.AuthorService;
import org.springdataintro_exercise.service.BookService;
import org.springdataintro_exercise.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
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
        getAllBooksAfterYear2000();
        getAuthorsFirstAndLastNameBeforeBooks1990();
        getAllAuthorsByBooksDesc();
        printBooksByGeorgePowell();
    }

    private void printBooksByGeorgePowell() {
        this.bookService.findAllByBooksByGeorgePowellOrdered()
                .forEach(System.out::println);
    }

    private void getAllAuthorsByBooksDesc() {
        this.authorService.getAllAuthorsDescBooks()
                .forEach(System.out::println);
    }

    private void getAuthorsFirstAndLastNameBeforeBooks1990() {
        this.authorService.getAllAuthorsFirstAndLastNameForBooksBeforeYear1990()
                .forEach(System.out::println);
    }

    private void getAllBooksAfterYear2000() {
        this.bookService.findAllBooksAfterYear2000()
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        this.categoryService.seedCategories();
        this.authorService.seedAuthors();
        this.bookService.seedBooks();
    }
}
