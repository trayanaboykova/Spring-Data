package org.automappingobjects_exercise.service;

import org.automappingobjects_exercise.service.dto.UserRegisterDTO;

public interface UserService {
    String registerUser(UserRegisterDTO userRegisterDTO);
}
