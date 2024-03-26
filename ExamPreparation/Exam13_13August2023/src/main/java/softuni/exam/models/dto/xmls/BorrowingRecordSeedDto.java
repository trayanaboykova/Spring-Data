package softuni.exam.models.dto.xmls;


import softuni.exam.util.LocalDateAdapter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;

@XmlRootElement(name = "borrowing_record")
@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowingRecordSeedDto implements Serializable {
    @NotNull
    @XmlElement(name = "borrow_date")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate borrowDate;
    @NotNull
    @XmlElement(name = "return_date")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate returnDate;
    @XmlElement(name = "book")
    @NotNull
    private BookDto books;
    @XmlElement(name = "member")
    @NotNull
    private LibraryMemberDto libraryMembers;
    @XmlElement(name = "remarks")
    @Size(min = 3, max = 100)
    private String remarks;

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

    public BookDto getBook() {
        return books;
    }

    public void setBook(BookDto book) {
        this.books = book;
    }

    public LibraryMemberDto getLibraryMembers() {
        return libraryMembers;
    }

    public void setLibraryMembers(LibraryMemberDto libraryMembers) {
        this.libraryMembers = libraryMembers;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
