package com.example.advquerying.service;

import java.math.BigDecimal;
import java.util.List;

public interface IngredientService {
    List<String> getAllIngredientsWithStartingName(String symbol);
    List<String> getAllIngredientsByName(List<String> strings);
    int deleteIngredientByName(String name);
    int updatedIngredientPrices();
    int updatePricesForGivenNames();
}
