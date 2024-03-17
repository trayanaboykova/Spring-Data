package org.xmlprocessing_exercise.service.dto.imports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarSeedRootDto implements Serializable {
    @XmlElement(name = "car")
    private List<CarSeedDto> carSeedDtoList;

    public List<CarSeedDto> getCarSeedDtoList() {
        return carSeedDtoList;
    }

    public void setCarSeedDtoList(List<CarSeedDto> carSeedDtoList) {
        this.carSeedDtoList = carSeedDtoList;
    }
}
