package org.jsonprocessing_exercise.service;

import org.jsonprocessing_exercise.service.dtos.exports.UserAndProductDto;
import org.jsonprocessing_exercise.service.dtos.exports.UserSoldProductsDto;

import java.io.FileNotFoundException;
import java.util.List;

public interface UserService {
    void seedUsers() throws FileNotFoundException;
    List<UserSoldProductsDto> getAllUsersAndSoldItems();
    void printAllUsersAndSoldItems();
    UserAndProductDto getUserAndProductDto();
    void printGetUserAndProductDto();
}
