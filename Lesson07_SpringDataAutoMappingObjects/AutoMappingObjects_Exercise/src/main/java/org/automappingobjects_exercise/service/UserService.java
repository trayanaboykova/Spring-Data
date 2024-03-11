package org.automappingobjects_exercise.service;

import org.automappingobjects_exercise.data.entities.User;
import org.automappingobjects_exercise.service.dto.UserLoginDTO;
import org.automappingobjects_exercise.service.dto.UserRegisterDTO;

public interface UserService {
    String registerUser(UserRegisterDTO userRegisterDTO);

    String loginUser(UserLoginDTO userLoginDTO);

    String logoutUser();

    User getLoggedIn();
}

