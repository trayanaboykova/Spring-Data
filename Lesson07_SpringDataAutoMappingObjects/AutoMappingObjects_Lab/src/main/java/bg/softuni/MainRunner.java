package bg.softuni;

import bg.softuni.models.Address;
import bg.softuni.models.Employee;
import bg.softuni.models.dto.EmployeeDto;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MainRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        Address address = new Address("USA", "New York", "Cornelia Str");
        Employee employee = new Employee("Taylor", "Swift", BigDecimal.TEN, address);
        ModelMapper modelMapper = new ModelMapper();
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
    }
}
