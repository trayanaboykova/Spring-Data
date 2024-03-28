package softuni.exam.models.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity {
    @Column(name = "car_make", nullable = false)
    private String carMake;
    @Column(name = "car_model", nullable = false)
    private String carModel;
    @Enumerated(EnumType.STRING)
    @Column(name = "car_type", nullable = false)
    private String carType;
    @Column(nullable = false)
    private double engine;
    @Column(nullable = false)
    private int kilometers;
    @Column(name = "plate_number", nullable = false, unique = true)
    private String plateNumber;
    @Column(nullable = false)
    private int year;
    @OneToMany(mappedBy = "cars")
    private List<Task> tasks;

    public Car() {
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

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public double getEngine() {
        return engine;
    }

    public void setEngine(double engine) {
        this.engine = engine;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
