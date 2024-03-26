package softuni.exam.service.impl;

import org.springframework.stereotype.Service;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.repository.PersonRepository;
import softuni.exam.service.PersonService;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PersonServiceImpl implements PersonService {
    private static final String FILE_PATH = "src/main/resources/files/json/people.json";
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public boolean areImported() {
        return this.personRepository.count() > 0;
    }

    @Override
    public String readPeopleFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importPeople() throws IOException, JAXBException {


        return null;
    }
}
