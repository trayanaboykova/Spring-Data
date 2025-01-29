package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsons.ConstellationSeedDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.util.ValidationUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class ConstellationServiceImpl implements ConstellationService {
    private static final String FILE_PATH = "files/json/constellations.json"; // Adjusted for classpath loading
    private final ConstellationRepository constellationRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public ConstellationServiceImpl(ConstellationRepository constellationRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.constellationRepository = constellationRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.constellationRepository.count() > 0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_PATH);

        if (inputStream == null) {
            throw new FileNotFoundException("File not found in classpath: " + FILE_PATH);
        }

        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

    @Override
    public String importConstellations() throws IOException {
        StringBuilder sb = new StringBuilder();

        // Load JSON using ClassLoader
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_PATH);

        if (inputStream == null) {
            throw new FileNotFoundException("File not found in classpath: " + FILE_PATH);
        }

        // Read JSON file using InputStreamReader
        ConstellationSeedDto[] constellationSeedDtos = gson.fromJson(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8),
                ConstellationSeedDto[].class
        );

        for (ConstellationSeedDto constellationSeedDto : constellationSeedDtos) {
            Optional<Constellation> optional = this.constellationRepository.findByName(constellationSeedDto.getName());

            if (!this.validationUtil.isValid(constellationSeedDto) || optional.isPresent()) {
                sb.append("Invalid constellation\n");
                continue;
            }

            Constellation constellation = this.modelMapper.map(constellationSeedDto, Constellation.class);
            this.constellationRepository.saveAndFlush(constellation);
            sb.append(String.format("Successfully imported constellation %s - %s\n",
                    constellation.getName(), constellation.getDescription()));
        }
        return sb.toString();
    }
}
