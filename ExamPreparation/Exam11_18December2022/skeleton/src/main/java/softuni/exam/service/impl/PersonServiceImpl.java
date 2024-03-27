package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsons.PersonSeedDto;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Person;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.PersonRepository;
import softuni.exam.service.PersonService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {
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
            throw new IllegalStateException("Can't find file countries.json in classpath");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new UncheckedIOException("Error occurred while reading file countries.json", e);
        }
    }

    @Override
    public String importPeople() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        List<PersonSeedDto> peopleSeedDTOs = Arrays.stream(gson.fromJson(readPeopleFromFile(), PersonSeedDto[].class))
                .toList();

        for (PersonSeedDto personSeedDto : peopleSeedDTOs) {
            Optional<Person> existingPerson = findPersonByFirstNameEmailAndPhone(personSeedDto.getFirstName(),
                    personSeedDto.getEmail(),
                    personSeedDto.getPhone());
            Optional<Country> existingCountry = countryRepository.findById(Long.valueOf(personSeedDto.getCountry()));

            if (existingPerson.isPresent()) {
                sb.append("Invalid person\n");
            } else if (existingCountry.isEmpty()) {
                sb.append("Invalid person\n");
            } else if (!validationUtil.isValid(personSeedDto)) {
                sb.append("Invalid person\n");
            } else {
                Person personToSave = modelMapper.map(personSeedDto, Person.class);
                personToSave.setCountry(existingCountry.get());
                this.personRepository.saveAndFlush(personToSave);
                sb.append(String.format("Successfully imported person %s %s%n", personToSave.getFirstName(), personToSave.getLastName()));
            }
        }
        return sb.toString();
    }

    private Optional<Person> findPersonByFirstNameEmailAndPhone(String firstName, String email, String phone) {
        return this.personRepository.findPersonByFirstNameOrEmailOrPhone(firstName, email, phone);
    }

}
