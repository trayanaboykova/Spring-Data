package softuni.exam.service.impl;

import org.springframework.stereotype.Service;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.service.MechanicsService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class MechanicsServiceImpl implements MechanicsService {
    private static final String MECHANICS_FILE_PATH = "src/main/resources/files/json/mechanics.json";
    private final MechanicsRepository mechanicRepository;

    public MechanicsServiceImpl(MechanicsRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;
    }

    @Override
    public boolean areImported() {
        return this.mechanicRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return Files.readString(Path.of(MECHANICS_FILE_PATH));
    }

    @Override
    public String importMechanics() throws IOException {
        return null;
    }
}
