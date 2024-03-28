package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsons.PartSeedDto;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartsRepository;
import softuni.exam.service.PartsService;
import softuni.exam.util.ValidationUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartsServiceImpl implements PartsService {
    private static final String PARTS_FILE_PATH = "src/main/resources/files/json/parts.json";
    private final PartsRepository partRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public PartsServiceImpl(PartsRepository partRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.partRepository = partRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.partRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        InputStream is = getClass().getResourceAsStream("/files/json/parts.json");
        if (is == null) {
            throw new IllegalStateException("Can't find file parts.json in classpath");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new UncheckedIOException("Error occurred while reading file parts.json", e);
        }
//        return Files.readString(Path.of(PARTS_FILE_PATH));
    }

    @Override
    public String importParts() throws IOException {
        StringBuilder sb = new StringBuilder();

        PartSeedDto[] partSeedDtos = this.gson.fromJson(readPartsFileContent(), PartSeedDto[].class);

        for (PartSeedDto partSeedDto : partSeedDtos) {

            String partName = partSeedDto.getPartName();
            Optional<Part> existingPart = this.partRepository.findPartByPartName(partName);

            if (!this.validationUtil.isValid(partSeedDto) || existingPart.isPresent()) {
                sb.append("Invalid part\n");
                continue;
            }

            Part part = this.modelMapper.map(partSeedDto, Part.class);

            this.partRepository.saveAndFlush(part);

            sb.append(String.format("Successfully imported part %s - %s\n",
                    part.getPartName(), part.getPrice()));
        }
        return sb.toString();
    }
}
