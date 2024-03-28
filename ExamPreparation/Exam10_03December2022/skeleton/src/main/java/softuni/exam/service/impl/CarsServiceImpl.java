package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarsServiceImpl implements CarsService {
    private final String CARS_FILE_PATH = "src/main/resources/files/xml/cars.xml";
    private final CarsRepository carRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final ResourceLoader resourceLoader;

    public CarsServiceImpl(CarsRepository carRepository, ModelMapper modelMapper, ValidationUtil validationUtil, ResourceLoader resourceLoader) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.resourceLoader = resourceLoader;
    }


    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:files/xml/cars.xml");
        InputStream is = resource.getInputStream();
        if (is == null) {
            throw new IllegalStateException("Can't find file cars.xml in classpath");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new UncheckedIOException("Error occurred while reading file cars.xml", e);
        }
//        return Files.readString(Path.of(CARS_FILE_PATH));
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(CarRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        InputStream is = resourceLoader.getResource("classpath:files/xml/cars.xml").getInputStream();
        CarRootDto carRootDto = (CarRootDto) unmarshaller.unmarshal(new InputStreamReader(is));

//        CarRootDto carRootDto = (CarRootDto) unmarshaller.unmarshal(new File(CARS_FILE_PATH));

        List<CarSeedDto> carSeedDtos = carRootDto.getCars();

        for (CarSeedDto carSeedDto : carSeedDtos) {

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
