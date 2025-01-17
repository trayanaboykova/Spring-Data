package org.mvc_project.service.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.modelmapper.ModelMapper;
import org.mvc_project.data.entities.Employee;
import org.mvc_project.data.entities.Project;
import org.mvc_project.data.repositories.EmployeeRepository;
import org.mvc_project.data.repositories.ProjectRepository;
import org.mvc_project.service.EmployeeService;
import org.mvc_project.service.model.imports.EmployeeImportModel;
import org.mvc_project.service.model.imports.EmployeeRootImportModel;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final String FILE_PATH = "src/main/resources/files/xmls/employees.xml";

    private final ModelMapper modelMapper;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(ModelMapper modelMapper, ProjectRepository projectRepository, EmployeeRepository employeeRepository) {
        this.modelMapper = modelMapper;
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public boolean isImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public void seedData() throws JAXBException, IOException {
        XmlMapper xmlMapper = new XmlMapper();

        EmployeeRootImportModel employeeRootImportModel =
                xmlMapper.readValue(readFile(), EmployeeRootImportModel.class);

        employeeRootImportModel.getEmployees().forEach(this::saveToDB);
    }

    private String saveToDB(EmployeeImportModel employeeImportModel) {
        Employee forDB = this.modelMapper.map(employeeImportModel, Employee.class);

        Optional<Project> project =
                this.projectRepository.findByName(employeeImportModel.getProject().getName());

        if (project.isEmpty()) {
            return "Invalid Employee. Project not found";
        }

        forDB.setProject(project.get());
        this.employeeRepository.save(forDB);

        return "Successfully inserted Employee";
    }

    @Override
    public String readFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String getEmployeesAbove25() {
        List<Employee> byAgeGreaterThan = this.employeeRepository.findByAgeGreaterThan(25);

        return byAgeGreaterThan
                .stream()
                .map(Objects::toString)
                .collect(Collectors.joining("\n"));
    }

}
