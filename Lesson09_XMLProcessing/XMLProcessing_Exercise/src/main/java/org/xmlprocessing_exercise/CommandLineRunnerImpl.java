package org.xmlprocessing_exercise;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.xmlprocessing_exercise.service.CarService;
import org.xmlprocessing_exercise.service.CustomerService;
import org.xmlprocessing_exercise.service.PartService;
import org.xmlprocessing_exercise.service.SupplierService;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;

    public CommandLineRunnerImpl(SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.supplierService.seedSupplier();
        this.partService.seedParts();
        this.carService.seedCars();
        this.customerService.seedCustomers();

    }
}
