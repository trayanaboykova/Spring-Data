package org.mvc_project.service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface EmployeeService {
    boolean isImported();

    void seedData() throws JAXBException, IOException;

    String readFile() throws IOException;

    String getEmployeesAbove25();
}
