package softuni.exam.models.dto.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Set;

@XmlRootElement(name = "borrowing_records")
@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowingRecordRootDto implements Serializable {
    @XmlElement(name = "borrowing_record")
    private Set<BorrowingRecordSeedDto> borrowingRecords;

    public BorrowingRecordRootDto() {
    }

    public Set<BorrowingRecordSeedDto> getBorrowingRecords() {
        return borrowingRecords;
    }

    public void setBorrowingRecords(Set<BorrowingRecordSeedDto> borrowingRecords) {
        this.borrowingRecords = borrowingRecords;
    }
}
