package org.springdataintro_lab.service;

import org.springdataintro_lab.data.entitites.User;

public interface UserService {
    void register(User user);

    User findUserById(int id);

    User findByName(String name);
}
