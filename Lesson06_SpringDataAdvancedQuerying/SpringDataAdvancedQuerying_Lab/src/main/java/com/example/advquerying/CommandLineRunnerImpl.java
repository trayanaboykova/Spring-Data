package com.example.advquerying;

import com.example.advquerying.service.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final ShampooService shampooService;

    public CommandLineRunnerImpl(ShampooService shampooService) {
        this.shampooService = shampooService;
    }

    @Override
    public void run(String... args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //   this.shampooService.getAllShampoosByGivenSize(reader.readLine())
        //           .forEach(System.out::println);
        // this.shampooService.getAllShampoosContainingIngredient(List.of(reader.readLine().split(" ")))
        //         .forEach(System.out::println);
        // this.shampooService.getAllShampoosByGivenSizeOrLabel("medium", 10)
        //         .forEach(System.out::println);
        this.shampooService.getAllShampoosWithPriceHigherThan(BigDecimal.valueOf(5))
                .forEach(System.out::println);
    }
}
