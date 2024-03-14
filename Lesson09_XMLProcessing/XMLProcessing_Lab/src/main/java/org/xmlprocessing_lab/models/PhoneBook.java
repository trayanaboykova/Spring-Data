package org.xmlprocessing_lab.models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PhoneBook {
    private String owner;

    public PhoneBook() {
    }

    public PhoneBook(String owner) {
        this.owner = owner;
    }
}
