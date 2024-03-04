package org.springdataintro_lab;

import org.springdataintro_lab.data.entitites.User;
import org.springdataintro_lab.data.repositories.UserRepository;
import org.springdataintro_lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    @Autowired
    private final UserService userService;

    public CommandLineRunnerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User("Taylor", 30);
        this.userService.register(user);
        System.out.println();
    }
}
