package org.mvc_project.service;

import org.mvc_project.web.models.UserLoginModel;
import org.mvc_project.web.models.UserRegisterModel;

public interface UserService {
    boolean validateRegisterModel(UserRegisterModel userRegisterModel);

    void registerUser(UserRegisterModel userRegisterModel);

    boolean loggedIn(UserLoginModel userLoginModel);
}
