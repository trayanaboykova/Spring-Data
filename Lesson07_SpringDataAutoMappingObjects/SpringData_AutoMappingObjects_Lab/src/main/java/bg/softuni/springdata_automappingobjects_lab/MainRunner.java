package bg.softuni.springdata_automappingobjects_lab;

import org.springframework.boot.CommandLineRunner;

public class MainRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
    }
}
