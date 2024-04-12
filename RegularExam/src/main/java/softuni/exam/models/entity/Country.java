package softuni.exam.models.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "countries")
public class Country extends BaseEntity {
    @Column
    private String capital;
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "country")
    private Set<Volcano> volcanos;

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Volcano> getVolcanos() {
        return volcanos;
    }

    public void setVolcanos(Set<Volcano> volcanos) {
        this.volcanos = volcanos;
    }
}
