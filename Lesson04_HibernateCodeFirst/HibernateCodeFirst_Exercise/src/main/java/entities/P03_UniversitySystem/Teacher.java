package entities.P03_UniversitySystem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "teacher")
public class Teacher extends Information {
    @Column(nullable = false)
    private String email;
    @Column(name = "salary_per_hour", nullable = false)
    private double salaryPerHour;
    @OneToMany(mappedBy = "teacher")
    private Set<Course> courses;
}
