package org.xmlprocessing_lab.models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "address")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddressDto {
    private String country;
    private String city;

    public AddressDto() {}

    public AddressDto(String country, String city) {
        this.country = country;
        this.city = city;
    }
}
