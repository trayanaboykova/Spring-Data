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

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Book getBooks() {
        return books;
    }

    public void setBooks(Book books) {
        this.books = books;
    }

    public LibraryMember getLibraryMember() {
        return libraryMember;
    }

    public void setLibraryMember(LibraryMember libraryMember) {
        this.libraryMember = libraryMember;
    }
}
