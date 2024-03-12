package bg.softuni.jsonprocessing_lab.dto;

public class PersonDTO {
    private String firstName;
    private String lastName;
    private int age;
    private boolean isMarried;

    public PersonDTO(String firstName, String lastName, int age, boolean isMarried) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.isMarried = isMarried;
    }
}
