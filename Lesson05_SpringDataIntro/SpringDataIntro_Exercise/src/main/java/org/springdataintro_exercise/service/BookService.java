package org.springdataintro_exercise.service;

import org.springdataintro_exercise.data.entities.Book;

import java.io.IOException;
import java.util.List;


public interface BookService {
    void seedBooks() throws IOException;

    List<String> findAllBooksAfterYear2000();

    List<String> findAllByBooksByGeorgePowellOrdered();
}
