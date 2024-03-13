package org.jsonprocessing_exercise.service.impl;

import com.google.gson.Gson;
import org.jsonprocessing_exercise.data.entities.User;
import org.jsonprocessing_exercise.data.repositories.UserRepository;
import org.jsonprocessing_exercise.service.UserService;
import org.jsonprocessing_exercise.service.dtos.UserSeedDto;
import org.jsonprocessing_exercise.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;

@Service
public class UserServiceImpl implements UserService {
    // constants
    private static final String FILE_PATH = "src/main/resources/json/users.json";

    // beans
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public void seedUsers() throws FileNotFoundException {
        if (this.userRepository.count() == 0) {
            UserSeedDto[] userSeedDtos = this.gson.fromJson(new FileReader(FILE_PATH), UserSeedDto[].class);

            for (UserSeedDto userSeedDto : userSeedDtos) {
                if (!this.validationUtil.isValid(userSeedDto)) {
                    this.validationUtil.getViolations(userSeedDto)
                            .forEach(v -> System.out.println(v.getMessage()));
                    continue;
                }
                User user = this.modelMapper.map(userSeedDto, User.class);
                this.userRepository.saveAndFlush(user);
            }
        }
    }
}
