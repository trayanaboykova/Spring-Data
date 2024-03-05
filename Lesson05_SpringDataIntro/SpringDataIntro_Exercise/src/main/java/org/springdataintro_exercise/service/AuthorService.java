package org.springdataintro_exercise.service;

import org.springdataintro_exercise.data.entities.Author;

import java.io.IOException;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();

}
