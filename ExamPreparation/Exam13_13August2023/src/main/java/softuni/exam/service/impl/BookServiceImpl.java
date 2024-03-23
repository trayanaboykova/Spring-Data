package softuni.exam.service.impl;

import softuni.exam.service.BookService;

import java.io.IOException;

public class BookServiceImpl implements BookService {


    @Override
    public boolean areImported() {
        return false;
    }

    @Override
    public String readBooksFromFile() throws IOException {
        return null;
    }

    @Override
    public String importBooks() throws IOException {
        return null;
    }
}
