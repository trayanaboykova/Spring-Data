package entities.P02_SalesDatabase;

import entities.BaseEntity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "store_locations")
public class StoreLocation extends BaseEntity {
    @Column(name = "location_name", nullable = false)
    String locationName;
    @OneToMany(mappedBy = "storeLocation")
    private Set<Sale> sale;
}
