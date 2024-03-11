package org.automappingobjects_exercise.service.impl;

import org.automappingobjects_exercise.data.entities.Game;
import org.automappingobjects_exercise.data.entities.User;
import org.automappingobjects_exercise.data.repositories.GameRepository;
import org.automappingobjects_exercise.data.repositories.UserRepository;
import org.automappingobjects_exercise.service.UserService;
import org.automappingobjects_exercise.service.dto.CartItemDTO;
import org.automappingobjects_exercise.util.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final UserService userService;
    private final UserRepository userRepository;
    private Set<Game> cart = new HashSet<>();
    private final GameRepository gameRepository;

    public ShoppingCartServiceImpl(UserService userService, UserRepository userRepository, GameRepository gameRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public String addItem(CartItemDTO item) {
        if (this.userService.getLoggedIn() != null) {
            Optional<Game> optional = this.gameRepository.findByTitle(item.getTitle());
            if (optional.isEmpty()) {
                return "No such game exists";
            }
            this.cart.add(optional.get());
            return String.format("%s added to cart.", optional.get().getTitle());
        }
        return "No user is logged in";

    }

    @Override
    public String deleteItem(CartItemDTO item) {
        if (this.userService.getLoggedIn() != null) {
            Optional<Game> optional = this.gameRepository.findByTitle(item.getTitle());
            if (optional.isEmpty()) {
                return "No such game exists";
            }
            this.cart.remove(optional.get());
            return String.format("%s removed from cart.", optional.get().getTitle());
        }
        return "No user is logged in";
    }

    @Override
    public String buyItem() {
        User user = this.userService.getLoggedIn();
        if (user != null) {
            user.getGames().addAll(this.cart);
            this.userRepository.saveAndFlush(user);
            String output = "Successfully bought games:\n" +
                    this.cart.stream().map(g -> " -" + g.getTitle())
                            .collect(Collectors.joining("\n"));
            this.cart = new HashSet<>();
            return output;
        }
        return "No user is logged in";
    }
}
