package com.example.advquerying;

import com.example.advquerying.service.IngredientService;
import com.example.advquerying.service.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final ShampooService shampooService;
    private final IngredientService ingredientService;

    public CommandLineRunnerImpl(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Enter input:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // SELECT SHAMPOO BY SIZE
        //   this.shampooService.getAllShampoosByGivenSize(reader.readLine())
        //           .forEach(System.out::println);

        // SELECT SHAMPOO BY SIZE OR LABEL
        // this.shampooService.getAllShampoosByGivenSizeOrLabel("medium", 10)
        //         .forEach(System.out::println);

        // SELECT SHAMPOOS BY PRICE
        // this.shampooService.getAllShampoosWithPriceHigherThan(BigDecimal.valueOf(5))
        //        .forEach(System.out::println);

        // SELECT INGREDIENTS BY NAME
        // this.ingredientService.getAllIngredientsWithStartingName("M")
        //        .forEach(System.out::println);

        // SELECT INGREDIENTS BY NAMES
        //  List<String> ingredientNames = reader.lines()
        //          .takeWhile(line -> !line.isEmpty())
        //          .collect(Collectors.toList());
        //  this.ingredientService.getAllIngredientsByName(ingredientNames)
        //          .forEach(System.out::println);

        // COUNT SHAMPOOS BY PRICE

        // SELECT SHAMPOOS BY INGREDIENTS
        // this.shampooService.getAllShampoosContainingIngredient(List.of(reader.readLine().split(" ")))
        //         .forEach(System.out::println);


    }
}
