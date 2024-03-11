package org.automappingobjects_exercise;

import org.automappingobjects_exercise.service.UserService;
import org.automappingobjects_exercise.service.dto.UserLoginDTO;
import org.automappingobjects_exercise.service.dto.UserRegisterDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final UserService userService;

    public CommandLineRunnerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String input = reader.readLine();
        while (!input.equals("END")) {
            String[] tokens = input.split("\\|");

            String command = "";
            switch (tokens[0]) {
                case "RegisterUser":
                    command = this.userService.registerUser(new UserRegisterDTO
                            (tokens[1], tokens[2], tokens[3], tokens[4]));
                    break;
                case "LoginUser":
                    command = this.userService.loginUser(new UserLoginDTO
                            (tokens[1], tokens[2]));
                    break;
                case "Logout":
                    command = this.userService.logoutUser();
                    break;
            }

            System.out.println(command);
            input = reader.readLine();
        }
    }
}
