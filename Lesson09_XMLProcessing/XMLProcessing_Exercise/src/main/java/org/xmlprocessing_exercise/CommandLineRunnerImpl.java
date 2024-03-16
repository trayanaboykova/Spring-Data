package org.xmlprocessing_exercise;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.xmlprocessing_exercise.service.SupplierService;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final SupplierService supplierService;

    public CommandLineRunnerImpl(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.supplierService.seedSupplier();
    }
}
