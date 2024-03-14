package org.jsonprocessing_exercise.service;

import org.jsonprocessing_exercise.service.dtos.CategoryByProductsDto;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    void seedCategories() throws IOException;

    List<CategoryByProductsDto> getAllCategoriesByProducts();
    void printAllCategoriesByProducts();
}
