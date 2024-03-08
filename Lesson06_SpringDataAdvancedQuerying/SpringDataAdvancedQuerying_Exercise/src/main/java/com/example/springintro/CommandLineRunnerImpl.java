package com.example.springintro;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
        // BOOKS TITLES BY AGE RESTRICTION
        // printBooksByAgeRestriction();

        // GOLDEN BOOKS
        // printGoldenBooksWithLessThan5000Copies();

        // BOOKS BY PRICE
        // printBooksWithPriceOutOfRange();

        // NOT RELEASED BOOKS
        // printBooksNotIssuedAt();

        // BOOKS RELEASED BEFORE DATE
        // printBookInfoForBooksReleasedBefore();

        // AUTHORS SEARCH


        // BOOK TITLES SEARCH


        // COUNT BOOKS


        // TOTAL BOOK COPIES


        // REDUCED BOOK


    }

    private void printBookInfoForBooksReleasedBefore() {
        Scanner scanner = new Scanner(System.in);
        String beforeDate = scanner.nextLine();
        LocalDate parsedDate = LocalDate.parse(beforeDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        List<Book> books = bookService.findAllReleasedBefore(parsedDate);
        books.forEach(b -> System.out.printf("%s %s $%.2f%n",
                b.getTitle(),
                b.getEditionType(),
                b.getPrice()));
    }

    private void printBooksNotIssuedAt() {
        List<String> titles = bookService.findTitlesForBooksNotPublishedIn(2000);

        titles.forEach(System.out::println);
    }

    private void printBooksWithPriceOutOfRange() {
        List<Book> books = bookService.findAllBooksWithPriceOutsideOf(5, 40);

        books.forEach(b -> System.out.printf("%s $%.2f%n", b.getTitle(), b.getPrice()));
    }

    private void printGoldenBooksWithLessThan5000Copies() {
        List<String> titles = bookService.findTitlesByEditionAndCopies(EditionType.GOLD, 5000);

        for (String t : titles) {
            System.out.println(t);
        }
    }

    private void printBooksByAgeRestriction() {
        Scanner scanner = new Scanner(System.in);
        String restriction = scanner.nextLine();
        try {
            AgeRestriction ageRestriction = AgeRestriction.valueOf(restriction.toUpperCase());
            List<String> titles = bookService.findAllByAgeRestriction(ageRestriction);
            titles.forEach(System.out::println);
        } catch (IllegalArgumentException ex) {
            System.out.println("Wrong age category");
        }
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
