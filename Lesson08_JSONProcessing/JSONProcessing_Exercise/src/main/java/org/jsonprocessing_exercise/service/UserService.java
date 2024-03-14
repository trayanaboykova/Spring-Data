package org.jsonprocessing_exercise.service;

import org.jsonprocessing_exercise.service.dtos.UserSoldProductsDto;

import java.io.FileNotFoundException;
import java.util.List;

public interface UserService {
    void seedUsers() throws FileNotFoundException;
    List<UserSoldProductsDto> getAllUsersAndSoldItems();
    void printAllUsersAndSoldItems();
}
