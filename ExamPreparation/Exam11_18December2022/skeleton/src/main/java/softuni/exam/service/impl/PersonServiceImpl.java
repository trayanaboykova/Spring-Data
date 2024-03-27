package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsons.PersonSeedDto;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Person;
import softuni.exam.models.entity.StatusType;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.PersonRepository;
import softuni.exam.service.PersonService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {
    private static final String FILE_PATH = "src/main/resources/files/json/people.json";
    private final PersonRepository personRepository;
    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public PersonServiceImpl(PersonRepository personRepository, CountryRepository countryRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.personRepository = personRepository;
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.personRepository.count() > 0;
    }

    @Override
    public String readPeopleFromFile() throws IOException {
        InputStream is = getClass().getResourceAsStream("/files/json/people.json");
        if (is == null) {
            throw new IllegalStateException("Can't find file people.json in classpath");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new UncheckedIOException("Error occurred while reading file people.json", e);
        }
    }

    @Override
    public String importPeople() throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();

        PersonSeedDto[] personSeedDtos = gson.fromJson(readPeopleFromFile(), PersonSeedDto[].class);

        for (PersonSeedDto personSeedDto : personSeedDtos) {

            boolean doesPersonExist = personRepository.existsByFirstNameOrEmailOrPhone(
                    personSeedDto.getFirstName(), personSeedDto.getEmail(), personSeedDto.getPhone());

            if (!validationUtil.isValid(personSeedDto) || doesPersonExist) {
                sb.append("Invalid person\n");
                continue;
            }
            Person person = modelMapper.map(personSeedDto, Person.class);
            person.setStatusType(StatusType.valueOf(personSeedDto.getStatusType()));
            Optional<Country> country = countryRepository.findById(Long.valueOf(personSeedDto.getCountry()));
            if (country.isPresent()) {
                person.setCountry(country.get());

                personRepository.saveAndFlush(person);

                sb.append(String.format("Successfully imported person %s - %s\n",
                        personSeedDto.getFirstName(),
                        personSeedDto.getLastName()));
            }
        }

        return sb.toString();
    }
}
