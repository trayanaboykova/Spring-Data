package softuni.exam.models.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "borrowing_records")
public class BorrowingRecord extends BaseEntity {
    @Column(name = "borrow_date")
    private LocalDate borrowDate;
    @Column(name = "return_date")
    private LocalDate returnDate;
    @Column
    private String remarks;
    @ManyToOne
    @JoinColumn(name = "books_id", referencedColumnName = "id")
    private Book books;
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private LibraryMember libraryMember;

}
