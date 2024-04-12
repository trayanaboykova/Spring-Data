package softuni.exam.models.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "companies")
public class Company extends BaseEntity {
    @Column(name = "date_established", nullable = false)
    private LocalDate dateEstablished;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String website;
    @ManyToOne
    @JoinColumn(name = "country_id",
            referencedColumnName = "id",
            nullable = false)
    private Country country;
    @OneToMany(mappedBy = "company")
    private List<Job> jobs;

    @ManyToMany(mappedBy = "companyList")
    private List<Job> jobList;

    public Company() {
    }

    public LocalDate getDateEstablished() {
        return dateEstablished;
    }

    public void setDateEstablished(LocalDate dateEstablished) {
        this.dateEstablished = dateEstablished;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public List<Job> getJobList() {
        return jobList;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }
}
