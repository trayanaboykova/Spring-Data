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
        printGoldenBooksWithLessThan5000Copies();

        // BOOKS BY PRICE


        // NOT RELEASED BOOKS


        // BOOKS RELEASED BEFORE DATE


        // AUTHORS SEARCH


        // BOOK TITLES SEARCH


        // COUNT BOOKS


        // TOTAL BOOK COPIES


        // REDUCED BOOK


        // OTHER
        // printAllBooksAfterYear(2000);
        // printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
        // printAllAuthorsAndNumberOfTheirBooks();
        // printALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");

    }

    private void printGoldenBooksWithLessThan5000Copies() {
        List<String> titles = bookService.findAllByEditionAndCopies(EditionType.GOLD, 5000);

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

    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
