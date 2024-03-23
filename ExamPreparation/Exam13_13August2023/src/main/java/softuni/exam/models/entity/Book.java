package softuni.exam.models.entity;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "books")
public class Book extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;
    @Column(nullable = false)
    private boolean available;
    @Enumerated(EnumType.STRING)
    @Column(name = "book_genre", nullable = false)
    private BookGenre bookGenre;
    @Column(nullable = false)
    @Positive
    private double rating;

}
