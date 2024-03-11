package org.automappingobjects_exercise.service.impl;

import jakarta.validation.ConstraintViolation;
import org.automappingobjects_exercise.data.entities.User;
import org.automappingobjects_exercise.data.repositories.UserRepository;
import org.automappingobjects_exercise.service.UserService;
import org.automappingobjects_exercise.service.dto.UserLoginDTO;
import org.automappingobjects_exercise.service.dto.UserRegisterDTO;
import org.automappingobjects_exercise.util.ValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private User loggedIn;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ValidationService validationService;

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, ValidationService validatorService) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.validationService = validatorService;
    }


    @Override
    public String registerUser(UserRegisterDTO userRegisterDTO) {
        if (!this.validationService.isValid(userRegisterDTO)) {
            Set<ConstraintViolation<UserRegisterDTO>> set = this.validationService.validate(userRegisterDTO);
            return set.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("\n"));
        }

        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            return "Passwords don't match";
        }

        Optional<User> optional = this.userRepository.findUserByEmail(userRegisterDTO.getEmail());
        if (optional.isPresent()) {
            return "User with that email already exists.";
        }

        User user = this.modelMapper.map(userRegisterDTO, User.class);
        if (this.userRepository.count() == 0) {
            user.setAdmin(true);
        }
        this.userRepository.saveAndFlush(user);
        return String.format("%s was registered.", user.getFullName());
    }

    @Override
    public String loginUser(UserLoginDTO userLoginDTO) {
        Optional<User> optional = this.userRepository.findByEmailAndPassword(userLoginDTO.getEmail(), userLoginDTO.getPassword());
        if (optional.isEmpty()) {
            return "Email or password is incorrect";
        }

        setLoggedIn(optional.get());

        return String.format("Successfully logged in %s", optional.get().getFullName());
    }

    @Override
    public String logoutUser() {
        if (this.loggedIn == null) {
            return "Cannot log out. No user was logged in";
        }
        String output = String.format("User %s was successfully logged out", this.loggedIn.getFullName());
        setLoggedIn(null);
        return output;
    }

    public void setLoggedIn(User loggedIn) {
        this.loggedIn = loggedIn;
    }
}
