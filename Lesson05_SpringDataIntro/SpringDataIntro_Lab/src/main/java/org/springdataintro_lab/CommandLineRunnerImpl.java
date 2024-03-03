package org.springdataintro_lab;

import org.springdataintro_lab.data.entitites.User;
import org.springdataintro_lab.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    @Autowired
    private final UserRepository userRepository;

    public CommandLineRunnerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User("Taylor", 30);
        this.userRepository.saveAndFlush(user);
        System.out.println();
    }
}
