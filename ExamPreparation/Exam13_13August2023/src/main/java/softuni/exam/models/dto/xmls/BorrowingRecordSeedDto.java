package softuni.exam.models.dto.xmls;


import softuni.exam.util.LocalDateAdapter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
    private BookDto book;
    @XmlElement(name = "member")
    @NotNull
    private LibraryMemberDto member;
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
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }

    public LibraryMemberDto getMember() {
        return member;
    }

    public void setMember(LibraryMemberDto member) {
        this.member = member;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
