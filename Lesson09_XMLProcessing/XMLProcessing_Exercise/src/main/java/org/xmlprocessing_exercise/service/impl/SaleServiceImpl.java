package org.xmlprocessing_exercise.service.impl;

import org.springframework.stereotype.Service;
import org.xmlprocessing_exercise.data.entities.Car;
import org.xmlprocessing_exercise.data.entities.Customer;
import org.xmlprocessing_exercise.data.entities.Sale;
import org.xmlprocessing_exercise.data.repositories.CarRepository;
import org.xmlprocessing_exercise.data.repositories.CustomerRepository;
import org.xmlprocessing_exercise.data.repositories.SaleRepository;
import org.xmlprocessing_exercise.service.SaleService;
import org.xmlprocessing_exercise.util.parser.XmlParser;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SaleServiceImpl implements SaleService {
    private final List<Double> discounts = List.of(1.0, 0.95, 0.9, 0.85, 0.8, 0.7, 0.6, 0.5);
    private final SaleRepository saleRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private XmlParser xmlParser;

    public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository, CustomerRepository customerRepository, XmlParser xmlParser) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedSales() {
        if (this.saleRepository.count() == 0) {
            for (int i = 0; i < 50; i++) {
                Sale sale = new Sale();
                sale.setCar(getRandomCar());
                sale.setCustomer(getRandomCustomer());
                sale.setDiscount(getRandomDiscount());
                this.saleRepository.saveAndFlush(sale);
            }
        }
    }

    private double getRandomDiscount() {
        return discounts.get(ThreadLocalRandom.current().nextInt(1, discounts.size()));
    }

    private Customer getRandomCustomer() {
        return this.customerRepository.findById(
                ThreadLocalRandom.current().nextLong(1, this.customerRepository.count() + 1)).get();
    }

    private Car getRandomCar() {
        return this.carRepository.findById(
                ThreadLocalRandom.current().nextLong(1, this.carRepository.count() + 1)).get();
    }
}
