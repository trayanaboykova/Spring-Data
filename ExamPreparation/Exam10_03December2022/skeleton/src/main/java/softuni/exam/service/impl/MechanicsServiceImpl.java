package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsons.MechanicsSeedDto;
import softuni.exam.models.dto.jsons.PartSeedDto;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.service.MechanicsService;
import softuni.exam.util.ValidationUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MechanicsServiceImpl implements MechanicsService {
    private static final String MECHANICS_FILE_PATH = "src/main/resources/files/json/mechanics.json";
    private final MechanicsRepository mechanicRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public MechanicsServiceImpl(MechanicsRepository mechanicRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.mechanicRepository = mechanicRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.mechanicRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        InputStream is = getClass().getResourceAsStream("/files/json/mechanics.json");
        if (is == null) {
            throw new IllegalStateException("Can't find file mechanics.json in classpath");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new UncheckedIOException("Error occurred while reading file mechanics.json", e);
        }
//        return Files.readString(Path.of(MECHANICS_FILE_PATH));
    }

    @Override
    public String importMechanics() throws IOException {
        StringBuilder sb = new StringBuilder();

        MechanicsSeedDto[] mechanicSeedDtos = this.gson.fromJson(readMechanicsFromFile(), MechanicsSeedDto[].class);

        for (MechanicsSeedDto mechanicSeedDto : mechanicSeedDtos) {

            String mechanicName = mechanicSeedDto.getFirstName();
            String email = mechanicSeedDto.getEmail();

            Optional<Mechanic> existingMechanic = this.mechanicRepository.findByFirstName(mechanicName);
            Optional<Mechanic> existingEmail = this.mechanicRepository.findByEmail(email);

            if (!this.validationUtil.isValid(mechanicSeedDto) || existingMechanic.isPresent() || existingEmail.isPresent()) {
                sb.append("Invalid mechanic\n");
                continue;
            }

            Mechanic mechanic = this.modelMapper.map(mechanicSeedDto, Mechanic.class);

            this.mechanicRepository.saveAndFlush(mechanic);

            sb.append(String.format("Successfully imported mechanic %s %s\n", mechanic.getFirstName(), mechanic.getLastName()));
        }
        return sb.toString();
    }
}
