package org.xmlprocessing_lab;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.xmlprocessing_lab.models.AddressDto;
import org.xmlprocessing_lab.models.PersonDto;
import org.xmlprocessing_lab.models.PhoneBook;

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
        PhoneBook book = new PhoneBook("Augustine");
        bookMarshaller.marshal(book, System.out);
    }
}

