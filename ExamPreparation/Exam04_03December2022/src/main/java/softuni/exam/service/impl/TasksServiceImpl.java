package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmls.CarRootDto;
import softuni.exam.models.dto.xmls.TaskRootDto;
import softuni.exam.models.dto.xmls.TaskSeedDto;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Part;
import softuni.exam.models.entity.Task;
import softuni.exam.repository.CarsRepository;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.repository.PartsRepository;
import softuni.exam.repository.TasksRepository;
import softuni.exam.service.TasksService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TasksServiceImpl implements TasksService {
    private static String TASKS_FILE_PATH = "src/main/resources/files/xml/tasks.xml";
    private final TasksRepository taskRepository;
    private final CarsRepository carsRepository;
    private final MechanicsRepository mechanicsRepository;
    private final PartsRepository partsRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public TasksServiceImpl(TasksRepository taskRepository, CarsRepository carsRepository, MechanicsRepository mechanicsRepository, PartsRepository partsRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.carsRepository = carsRepository;
        this.mechanicsRepository = mechanicsRepository;
        this.partsRepository = partsRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return this.taskRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return Files.readString(Path.of(TASKS_FILE_PATH));
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext jaxbContext = JAXBContext.newInstance(TaskRootDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        TaskRootDto taskRootDto = (TaskRootDto) unmarshaller.unmarshal(new File(TASKS_FILE_PATH));

        for (TaskSeedDto taskSeedDto : taskRootDto.getTasks()) {

            long carId = taskSeedDto.getCar().getId();
            Optional<Car> existingCar = this.carsRepository.findById(carId);

            String mechanicName = taskSeedDto.getMechanic().getFirstName();
            Optional<Mechanic> existingMechanic = this.mechanicsRepository.findByFirstName(mechanicName);

            long partId = taskSeedDto.getPart().getId();
            Optional<Part> existingPart = this.partsRepository.findById(partId);


            if (!this.validationUtil.isValid(taskSeedDto) || existingCar.isEmpty() || existingMechanic.isEmpty() || existingPart.isEmpty()) {
                sb.append("Invalid task\n");
                continue;
            }

            Task task = this.modelMapper.map(taskSeedDto, Task.class);
            task.setCars(existingCar.get());
            task.setMechanics(existingMechanic.get());
            task.setParts(existingPart.get());

            this.taskRepository.saveAndFlush(task);

            sb.append(String.format("Successfully imported task %.2f\n", task.getPrice()));
        }

        return sb.toString();
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
        return null;
    }
}
