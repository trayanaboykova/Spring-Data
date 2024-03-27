package softuni.exam.models.dto.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "jobs")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobRootDto implements Serializable {
    @XmlElement(name = "job")
    private List<JobSeedDto> jobs = new ArrayList<>();

    public JobRootDto() {
    }

    public List<JobSeedDto> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobSeedDto> jobs) {
        this.jobs = jobs;
    }
}
