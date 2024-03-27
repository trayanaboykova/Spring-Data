package softuni.exam.models.dto.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "companies")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyRootDto implements Serializable {
    @XmlElement(name = "company")
    private List<CompanySeedDto> companies = new ArrayList<>();

    public CompanyRootDto() {
    }

    public List<CompanySeedDto> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanySeedDto> companies) {
        this.companies = companies;
    }
}
