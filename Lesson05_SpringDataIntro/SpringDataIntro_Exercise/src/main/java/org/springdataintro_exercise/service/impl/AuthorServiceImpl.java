package org.springdataintro_exercise.service.impl;

import org.springdataintro_exercise.data.entities.Author;
import org.springdataintro_exercise.data.repositories.AuthorRepository;
import org.springdataintro_exercise.service.AuthorService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private static final String FILE_PATH = "src/main/resources/files/authors.txt";

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRepository.count() == 0) {
            Files.readAllLines(Path.of(FILE_PATH))
                    .stream()
                    .filter(row -> !row.isEmpty())
                    .forEach(row -> {
                        String[] tokens = row.split("\\s+");
                        this.authorRepository.saveAndFlush(new Author(tokens[0], tokens[1]));
                    });
        }

    }

    @Override
    public Author getRandomAuthor() {
        int randomId = ThreadLocalRandom
                .current()
                .nextInt(1, (int) this.authorRepository.count() + 1);
        return this.authorRepository.findById(randomId).get();
    }
}
