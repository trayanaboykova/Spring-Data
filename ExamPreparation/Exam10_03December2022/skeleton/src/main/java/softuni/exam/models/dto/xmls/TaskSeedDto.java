package softuni.exam.models.dto.xmls;

import softuni.exam.util.XmlLocalDateTimeAdapter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskSeedDto implements Serializable {
    @XmlElement
    @XmlJavaTypeAdapter(XmlLocalDateTimeAdapter.class)
    @NotNull
    private LocalDateTime date;
    @XmlElement
    @Positive
    @NotNull
    private BigDecimal price;
    @XmlElement
    private CarDto car;
    @XmlElement
    private MechanicDto mechanic;
    @XmlElement
    private PartDto part;

    public TaskSeedDto() {
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CarDto getCar() {
        return car;
    }

    public void setCar(CarDto car) {
        this.car = car;
    }

    public MechanicDto getMechanic() {
        return mechanic;
    }

    public void setMechanic(MechanicDto mechanic) {
        this.mechanic = mechanic;
    }

    public PartDto getPart() {
        return part;
    }

    public void setPart(PartDto part) {
        this.part = part;
    }
}
