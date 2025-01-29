package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsons.StarSeedDto;
import softuni.exam.models.entity.Star;
import softuni.exam.models.entity.StarType;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidationUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StarServiceImpl implements StarService {
    private static final String FILE_PATH = "files/json/stars.json"; // Adjusted for ClassLoader
    private final StarRepository starRepository;
    private final ConstellationRepository constellationRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public StarServiceImpl(StarRepository starRepository, ConstellationRepository constellationRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.starRepository = starRepository;
        this.constellationRepository = constellationRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.starRepository.count() > 0;
    }

    @Override
    public String readStarsFileContent() throws IOException {
        return loadFileFromClasspath(FILE_PATH);
    }

    @Override
    public String importStars() throws IOException {
        StringBuilder sb = new StringBuilder();

        // Load JSON file from classpath
        String fileContent = loadFileFromClasspath(FILE_PATH);
        StarSeedDto[] starSeedDtos = this.gson.fromJson(fileContent, StarSeedDto[].class);

        for (StarSeedDto starSeedDto : starSeedDtos) {
            Optional<Star> optionalStar = this.starRepository.findByName(starSeedDto.getName());

            if (!this.validationUtil.isValid(starSeedDto) || optionalStar.isPresent()) {
                sb.append("Invalid star\n");
                continue;
            }

            Star star = this.modelMapper.map(starSeedDto, Star.class);
            star.setStarType(StarType.valueOf(starSeedDto.getStarType()));

            // Check if constellation exists before setting it
            this.constellationRepository.findById(starSeedDto.getConstellation())
                    .ifPresent(star::setConstellation);

            this.starRepository.saveAndFlush(star);
            sb.append(String.format("Successfully imported star %s - %.2f light years\n",
                    star.getName(), star.getLightYears()));
        }

        return sb.toString();
    }

    @Override
    public String exportStars() {
        return this.starRepository
                .findAllByStarTypeOrderByLightYears()
                .stream()
                .map(s -> String.format("Star: %s\n" +
                                "   *Distance: %.2f light years\n" +
                                "   **Description: %s\n" +
                                "   ***Constellation: %s\n",
                        s.getName(),
                        s.getLightYears(),
                        s.getDescription(),
                        s.getConstellation() != null ? s.getConstellation().getName() : "N/A"))
                .collect(Collectors.joining());
    }

    // 🔥 **Helper Method to Load File from Classpath**
    private String loadFileFromClasspath(String filePath) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);

        if (inputStream == null) {
            throw new FileNotFoundException("File not found in classpath: " + filePath);
        }

        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }
}
