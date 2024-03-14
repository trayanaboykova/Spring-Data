package org.xmlprocessing_lab.models;

public class AddressDto {
    private String country;
    private String city;

    public AddressDto() {}

    public AddressDto(String country, String city) {
        this.country = country;
        this.city = city;
    }
}
