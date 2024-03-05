package org.springdataintro_exercise.service.impl;

import org.springdataintro_exercise.data.entities.Author;
import org.springdataintro_exercise.data.entities.Category;
import org.springdataintro_exercise.data.repositories.CategoryRepository;
import org.springdataintro_exercise.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final String FILE_PATH = "src/main/resources/files/categories.txt";
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }



    @Override
    public void seedCategories() throws IOException {
        Files.readAllLines(Path.of(FILE_PATH))
                .stream()
                .filter(row -> !row.isEmpty())
                .forEach(row -> this.categoryRepository.saveAndFlush(new Category(row)));
    }
}
