package bg.softuni.jsonprocessing_lab;

import bg.softuni.jsonprocessing_lab.dto.AddressDTO;
import bg.softuni.jsonprocessing_lab.dto.PersonDTO;
import bg.softuni.jsonprocessing_lab.entities.Person;
import bg.softuni.jsonprocessing_lab.services.PersonService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Main implements CommandLineRunner {
    private Gson gson;
    private PersonService personService;

    private ModelMapper modelMapper;


    @Autowired
    public Main(@Qualifier("withoutNulls") Gson gson, PersonService personService, ModelMapper modelMapper) {
        this.gson = gson;
        this.personService = personService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        //printJson(gson);
        readJson(gson);

    }

    private void readJson(Gson gson) {
        String json = """
                {
                  "firstName": "Taylor",
                  "lastName": "Swift",
                  "age": 33,
                  "isMarried": true,
                  "lotteryNumbers": [
                    1,
                    10,
                    13,
                    23,
                    31
                  ],
                  "address": {
                    "country": "Bulgaria",
                    "city": "Plovdiv"
                  }
                }
                """;

        PersonDTO[] personDTO = gson.fromJson(json, PersonDTO[].class);
        List<Person> realPeople = new ArrayList<>();
        for (PersonDTO dto : personDTO) {
            Person person = modelMapper.map(dto, Person.class);
            realPeople.add(person);
        }
        for (Person realPerson : realPeople) {
            System.out.println(realPerson);
        }
    }

    private static void printJson(Gson gson) {
        PersonDTO personDTO = new PersonDTO(
                "Taylor",
                "Swift",
                33,
                true,
                List.of(1, 10, 13, 23, 31),
                new AddressDTO("Bulgaria", "Plovdiv"));

        String json = gson.toJson(personDTO);

        System.out.println(json);
    }
}
