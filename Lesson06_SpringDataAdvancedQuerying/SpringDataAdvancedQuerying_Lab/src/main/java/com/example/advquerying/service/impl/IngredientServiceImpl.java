package com.example.advquerying.service.impl;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.repositories.IngredientRepository;
import com.example.advquerying.service.IngredientService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<String> getAllIngredientsWithStartingName(String symbol) {
        Set<Ingredient> allByNameStartingWith = this.ingredientRepository.findAllByNameStartingWith(symbol);
        return allByNameStartingWith
                .stream()
                .map(Ingredient::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllIngredientsByName(List<String> strings) {
        Set<Ingredient> allByNameIn = this.ingredientRepository.findAllByNameIn(strings);
        return allByNameIn.stream()
                .sorted(Comparator.comparing(Ingredient::getPrice))
                .map(Ingredient::getName)
                .collect(Collectors.toList());
    }
}
