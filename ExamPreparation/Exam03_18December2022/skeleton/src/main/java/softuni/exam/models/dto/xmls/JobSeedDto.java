package softuni.exam.models.dto.xmls;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement(name = "job")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobSeedDto implements Serializable {
    @XmlElement
    @Size(min = 2, max = 40)
    @NotNull
    private String jobTitle;
    @XmlElement
    @Min(10)
    @NotNull
    private BigDecimal hoursAWeek;
    @XmlElement
    @Min(300)
    @NotNull
    private BigDecimal salary;
    @XmlElement
    @Size(min = 5)
    @NotNull
    private String description;
    @XmlElement(name = "companyId")
    private Long companyId;

    public JobSeedDto() {
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public BigDecimal getHoursAWeek() {
        return hoursAWeek;
    }

    public void setHoursAWeek(BigDecimal hoursAWeek) {
        this.hoursAWeek = hoursAWeek;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
