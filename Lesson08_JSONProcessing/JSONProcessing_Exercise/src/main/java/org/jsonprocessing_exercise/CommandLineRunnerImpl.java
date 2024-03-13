package org.jsonprocessing_exercise;

import org.jsonprocessing_exercise.service.CategoryService;
import org.jsonprocessing_exercise.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final CategoryService categoryService;
    private final UserService userService;

    public CommandLineRunnerImpl(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.categoryService.seedCategories();
        this.userService.seedUsers();
    }
}
