package org.xmlprocessing_lab;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.xmlprocessing_lab.models.AddressDto;
import org.xmlprocessing_lab.models.PersonDto;
import org.xmlprocessing_lab.models.PhoneBook;
import org.xmlprocessing_lab.models.PhoneNumber;

import java.util.List;

@Component
public class Main implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        JAXBContext personContext = JAXBContext.newInstance(PersonDto.class);
        Marshaller personMarshaller = personContext.createMarshaller();
        personMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        AddressDto addressDto = new AddressDto("USA", "New York");
        PersonDto person = new PersonDto("Taylor", "Swift", 13, addressDto);
        personMarshaller.marshal(person, System.out);

        JAXBContext bookContext = JAXBContext.newInstance(PhoneBook.class);
        Marshaller bookMarshaller = bookContext.createMarshaller();
        bookMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        PhoneNumber firstNumber = new PhoneNumber("Dorothea", "130811");
        PhoneNumber secondNumber = new PhoneNumber("James", "130813");
        PhoneNumber thirdNumber = new PhoneNumber("Betty", "130711");
        PhoneBook book = new PhoneBook(
                "Augustine",
                List.of("First", "Second", "Third"),
                List.of(firstNumber, secondNumber, thirdNumber));

        bookMarshaller.marshal(book, System.out);
    }
}

