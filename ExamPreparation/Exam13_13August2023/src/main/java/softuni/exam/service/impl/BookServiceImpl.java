package softuni.exam.service.impl;

import org.springframework.stereotype.Service;
import softuni.exam.repository.BookRepository;
import softuni.exam.service.BookService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class BookServiceImpl implements BookService {
    private static final String FILE_PATH = "src/main/resources/files/json/books.json";
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean areImported() {
        return this.bookRepository.count() > 0;
    }

    @Override
    public String readBooksFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importBooks() throws IOException {
        return null;
    }
}
