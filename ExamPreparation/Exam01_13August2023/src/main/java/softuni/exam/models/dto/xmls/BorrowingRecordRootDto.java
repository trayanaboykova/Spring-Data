package softuni.exam.models.dto.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@XmlRootElement(name = "borrowing_records")
@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowingRecordRootDto implements Serializable {
    @XmlElement(name = "borrowing_record")
    private List<BorrowingRecordSeedDto> borrowingRecords;

    public BorrowingRecordRootDto() {
    }

    public List<BorrowingRecordSeedDto> getBorrowingRecords() {
        return borrowingRecords;
    }

    public void setBorrowingRecords(List<BorrowingRecordSeedDto> borrowingRecords) {
        this.borrowingRecords = borrowingRecords;
    }
}
