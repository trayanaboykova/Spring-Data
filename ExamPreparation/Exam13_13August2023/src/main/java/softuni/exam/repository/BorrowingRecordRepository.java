package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.Genre;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

    @Query(value = "SELECT br FROM BorrowingRecord br " +
            "JOIN br.books b " +
            "WHERE b.genre = :genre AND br.borrowDate < :date " +
            "ORDER BY br.borrowDate DESC, b.title ASC")
    List<BorrowingRecord> findAllByBooksGenreAndBorrowDateBefore(@Param("genre") Genre genre, @Param("date") LocalDate date);

}
