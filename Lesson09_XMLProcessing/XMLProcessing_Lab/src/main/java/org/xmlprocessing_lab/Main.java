package org.xmlprocessing_lab;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.xmlprocessing_lab.models.PersonDto;

@Component
public class Main implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        JAXBContext personContext = JAXBContext.newInstance(PersonDto.class);
        Marshaller personMarshaller = personContext.createMarshaller();

        PersonDto person = new PersonDto("Taylor", "Swift", 13);

        personMarshaller.marshal(person, System.out);
    }
}
