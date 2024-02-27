package lab.composition;

import jakarta.persistence.*;
import lab.inheritance.Plane;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Basic
    private String name;

    @OneToMany(mappedBy = "owner")
    private List<Plane> planes;

    public Company() {
        this.planes = new ArrayList<>();
    }
}
