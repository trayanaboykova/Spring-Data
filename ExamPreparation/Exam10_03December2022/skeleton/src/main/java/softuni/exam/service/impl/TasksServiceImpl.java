package softuni.exam.service.impl;

import org.springframework.stereotype.Service;
import softuni.exam.repository.TasksRepository;
import softuni.exam.service.TasksService;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TasksServiceImpl implements TasksService {
    private static final String TASKS_FILE_PATH = "src/main/resources/files/xml/tasks.xml";
    private final TasksRepository taskRepository;

    public TasksServiceImpl(TasksRepository taskRepository) {
        this.taskRepository = taskRepository;
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
        return null;
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
        return null;
    }
}
