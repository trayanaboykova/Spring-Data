package softuni.exam.models.dto.xmls;

import softuni.exam.models.entity.CarType;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarSeedDto implements Serializable {
    @XmlElement
    @Size(min = 2, max = 30)
    @NotNull
    private String carMake;
    @XmlElement
    @Size(min = 2, max = 30)
    @NotNull
    private String carModel;
    @XmlElement
    @NotNull
    @Positive
    private int year;
    @XmlElement
    @Size(min = 2, max = 30)
    @NotNull
    private String plateNumber;
    @XmlElement
    @NotNull
    @Positive
    private int kilometers;
    @XmlElement
    @DecimalMin(value = "1.00")
    @NotNull
    private double engine;
    @XmlElement
    @NotNull
    private CarType carType;

    public CarSeedDto() {
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    public double getEngine() {
        return engine;
    }

    public void setEngine(double engine) {
        this.engine = engine;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }
}
