package org.springdataintro_lab.service.impl;

import org.springdataintro_lab.data.entitites.User;
import org.springdataintro_lab.data.repositories.UserRepository;
import org.springdataintro_lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(User user) {
        this.userRepository.saveAndFlush(user);
    }
}
