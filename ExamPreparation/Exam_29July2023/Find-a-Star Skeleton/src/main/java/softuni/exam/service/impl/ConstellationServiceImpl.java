package softuni.exam.service.impl;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ConstellationServiceImpl implements ConstellationService {
    private static final String FILE_PATH = "src/main/resources/files/json/constellations.json";
    private final ConstellationRepository constellationRepository;

    public ConstellationServiceImpl(ConstellationRepository constellationRepository) {
        this.constellationRepository = constellationRepository;
    }


    @Override
    public boolean areImported() {
        return this.constellationRepository.count() > 0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importConstellations() throws IOException {
        return null;
    }
}
