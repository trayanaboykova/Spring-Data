package org.automappingobjects_exercise.service;

import org.automappingobjects_exercise.service.dto.GameAddDTO;

import java.util.Map;

public interface GameService {
    String addGame(GameAddDTO gameAddDTO);

    String editGame(long id, Map<String, String> map);

    String deleteGame(long id);
}
