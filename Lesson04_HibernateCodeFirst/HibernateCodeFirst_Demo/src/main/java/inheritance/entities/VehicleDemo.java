package inheritance.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "vehicles_demo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "our_type")
public abstract class VehicleDemo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    private String type;

    protected VehicleDemo() {}
    protected VehicleDemo(String type) {
        this.type = type;
    }
}
