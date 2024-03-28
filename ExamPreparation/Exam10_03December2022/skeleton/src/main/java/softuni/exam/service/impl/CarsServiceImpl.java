package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmls.CarRootDto;
import softuni.exam.models.dto.xmls.CarSeedDto;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarsRepository;
import softuni.exam.service.CarsService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class CarsServiceImpl implements CarsService {
    private final String CARS_FILE_PATH = "src/main/resources/files/xml/cars.xml";
    private final CarsRepository carRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CarsServiceImpl(CarsRepository carRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return Files.readString(Path.of(CARS_FILE_PATH));
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(CarRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        CarRootDto carRootDto = (CarRootDto) unmarshaller.unmarshal(new File(CARS_FILE_PATH));


        for (CarSeedDto carSeedDto : carRootDto.getCars()) {

            String existingPlateNumber = carSeedDto.getPlateNumber();

            Optional<Car> carByPlateNumber = this.carRepository.findByPlateNumber(existingPlateNumber);

            if (!this.validationUtil.isValid(carSeedDto) || carByPlateNumber.isPresent()) {
                sb.append("Invalid car\n");
                continue;
            }

            Car car = this.modelMapper.map(carSeedDto, Car.class);

            this.carRepository.saveAndFlush(car);

            sb.append(String.format("Successfully imported car %s - %s\n", car.getCarMake(), car.getCarModel()));
        }
        return sb.toString();
    }
}
