package bg.softuni;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        ModelMapper modelMapper = new ModelMapper();

        Address address = new Address("Bulgaria", "Plovdiv");
        Person person = new Person("First", "Last", LocalDate.now(), address);

        PropertyMap<Person, PersonInfoDTO> personToInfoDTO = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setBirthdate(source.getBirthday().toString());
            }
        };

        modelMapper.addMappings(personToInfoDTO);

        PersonInfoDTO dto = modelMapper.map(person, PersonInfoDTO.class);

        TypeMap<PersonInfoDTO, Person> dtoToPersonMap =
                modelMapper.createTypeMap(PersonInfoDTO.class, Person.class);
        dtoToPersonMap.addMappings(mapping -> mapping.map(
                PersonInfoDTO::getBirthdate,
                Person::setBirthday));

        Person fromMap = dtoToPersonMap.map(dto);

        Person personFromDTO = modelMapper.map(dto, Person.class);
    }
}

class PersonInfoDTO {
    private String name;
    private String birthdate;
    private String addressCity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }
}

class Person {
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Address address;

    public Person() {
    }

    public Person(String firstName, String lastName, LocalDate birthday, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}

class Address {
    private String country;
    private String city;

    public Address() {
    }

    public Address(String country, String city) {
        this.country = country;
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}


