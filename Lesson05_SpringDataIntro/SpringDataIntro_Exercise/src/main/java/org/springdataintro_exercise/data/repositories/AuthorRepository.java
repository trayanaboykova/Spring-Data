package org.springdataintro_exercise.data.repositories;

import org.springdataintro_exercise.data.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Set<Author> findAllByBooksReleaseDateBefore(LocalDate releaseDate);

    @Query("FROM Author a ORDER BY SIZE(a.books) DESC")
    Set<Author> findAllByOrderByBooksSizeDesc();
}
