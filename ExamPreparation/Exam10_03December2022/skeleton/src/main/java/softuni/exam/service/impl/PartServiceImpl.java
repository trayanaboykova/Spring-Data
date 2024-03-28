package softuni.exam.service.impl;

import org.springframework.stereotype.Service;
import softuni.exam.repository.PartRepository;
import softuni.exam.service.PartService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PartServiceImpl implements PartService {
    private static final String PARTS_FILE_PATH = "src/main/resources/files/json/parts.json";
    private final PartRepository partRepository;

    public PartServiceImpl(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    @Override
    public boolean areImported() {
        return this.partRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return Files.readString(Path.of(PARTS_FILE_PATH));
    }

    @Override
    public String importParts() throws IOException {
        return null;
    }
}
