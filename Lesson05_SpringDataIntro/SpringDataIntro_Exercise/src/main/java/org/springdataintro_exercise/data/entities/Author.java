package org.springdataintro_exercise.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.springdataintro_exercise.data.entities.base.BaseEntity;

import java.util.Set;

@Entity
@Table(name = "authors")
public class Author extends BaseEntity {
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @OneToMany(mappedBy = "author")
    private Set<Book> books;

    public Author() {
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
