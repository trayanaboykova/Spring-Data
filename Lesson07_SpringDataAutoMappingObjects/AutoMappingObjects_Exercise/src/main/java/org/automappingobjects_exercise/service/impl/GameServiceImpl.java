package org.automappingobjects_exercise.service.impl;

import jakarta.validation.ConstraintViolation;
import org.automappingobjects_exercise.data.entities.Game;
import org.automappingobjects_exercise.data.repositories.GameRepository;
import org.automappingobjects_exercise.service.GameService;
import org.automappingobjects_exercise.service.UserService;
import org.automappingobjects_exercise.service.dto.AllGamesDTO;
import org.automappingobjects_exercise.service.dto.GameAddDTO;
import org.automappingobjects_exercise.util.ValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final ValidationService validationService;
    private UserService userService;
    private final ModelMapper modelMapper;

    public GameServiceImpl(GameRepository gameRepository, ValidationService validationService, ModelMapper modelMapper) {
        this.gameRepository = gameRepository;
        this.validationService = validationService;
        this.modelMapper = modelMapper;
    }

    @Override
    public String addGame(GameAddDTO gameAddDTO) {
        if (this.userService.getLoggedIn() != null && this.userService.getLoggedIn().isAdmin()) {
            if (!this.validationService.isValid(gameAddDTO)) {
                return this.validationService.validate(gameAddDTO)
                        .stream().map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining("\n"));
            }

            Game game = this.modelMapper.map(gameAddDTO, Game.class);
            this.gameRepository.saveAndFlush(game);

            return String.format("Added %s", game.getTitle());
        }
        return "Logged in User is not admin";
    }

    @Override
    public String editGame(long id, Map<String, String> map) {
        Optional<Game> optionalGame = this.gameRepository.findById(id);
        if (optionalGame.isEmpty()) {
            return "No such game exists with given id";
        }

        Game game = optionalGame.get();
        String output = String.format("Edited %s", game.getTitle());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            switch (entry.getKey()) {
                case "title":
                    game.setTitle(entry.getValue());
                    break;
                case "price":
                    game.setPrice(Double.parseDouble(entry.getValue()));
                    break;
                case "size":
                    game.setSize(Double.parseDouble(entry.getValue()));
                    break;
            }
        }
        this.gameRepository.saveAndFlush(game);

        return output;
    }

    @Override
    public String deleteGame(long id) {
        Optional<Game> optionalGame = this.gameRepository.findById(id);
        if (optionalGame.isEmpty()) {
            return "No such game with given id";
        }
        String output = String.format("Deleted %s", optionalGame.get().getTitle());
        this.gameRepository.delete(optionalGame.get());
        return output;
    }

    @Override
    public Set<AllGamesDTO> getAllGames() {
        return this.gameRepository.findAll()
                .stream()
                .map(g -> this.modelMapper.map(g, AllGamesDTO.class))
                .collect(Collectors.toSet());
    }

    @Override
    public String allGamesReadyForPrint() {
        return this.getAllGames().stream()
                .map(AllGamesDTO::toString)
                .collect(Collectors.joining("\n"));
    }
}
