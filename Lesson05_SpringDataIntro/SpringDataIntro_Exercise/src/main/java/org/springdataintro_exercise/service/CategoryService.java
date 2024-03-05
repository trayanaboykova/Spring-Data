package org.springdataintro_exercise.service;

import org.springdataintro_exercise.data.entities.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;

    Set<Category> getRandomCategories();
}
