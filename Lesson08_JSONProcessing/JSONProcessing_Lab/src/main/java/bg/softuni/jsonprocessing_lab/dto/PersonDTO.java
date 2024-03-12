package bg.softuni.jsonprocessing_lab.dto;

import java.util.List;

public class PersonDTO {
    private String firstName;
    private String lastName;
    private int age;
    private boolean isMarried;
    private List<Integer> lotteryNumbers;
    private AddressDTO address;

    public PersonDTO(String firstName, String lastName, int age, boolean isMarried, List<Integer> lotteryNumbers, AddressDTO address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.isMarried = isMarried;
        this.lotteryNumbers = lotteryNumbers;
        this.address = address;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", isMarried=" + isMarried +
                ", lotteryNumbers=" + lotteryNumbers +
                ", address=" + address +
                '}';
    }
}
