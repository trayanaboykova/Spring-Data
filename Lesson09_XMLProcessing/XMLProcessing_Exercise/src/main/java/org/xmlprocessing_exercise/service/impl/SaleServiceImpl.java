package org.xmlprocessing_exercise.service.impl;

import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.xmlprocessing_exercise.data.entities.Car;
import org.xmlprocessing_exercise.data.entities.Customer;
import org.xmlprocessing_exercise.data.entities.Part;
import org.xmlprocessing_exercise.data.entities.Sale;
import org.xmlprocessing_exercise.data.repositories.CarRepository;
import org.xmlprocessing_exercise.data.repositories.CustomerRepository;
import org.xmlprocessing_exercise.data.repositories.SaleRepository;
import org.xmlprocessing_exercise.service.SaleService;
import org.xmlprocessing_exercise.service.dto.exports.CarDto;
import org.xmlprocessing_exercise.service.dto.exports.SaleDiscountDto;
import org.xmlprocessing_exercise.service.dto.exports.SaleDiscountsRootDto;
import org.xmlprocessing_exercise.util.parser.XmlParser;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {
    private static final String FILE_EXPORT_SALES_PATH = "src/main/resources/xml/exports/sales-discounts.xml";
    private final List<Double> discounts = List.of(1.0, 0.95, 0.9, 0.85, 0.8, 0.7, 0.6, 0.5);
    private final SaleRepository saleRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private XmlParser xmlParser;
    private ModelMapper modelMapper;

    public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository, CustomerRepository customerRepository, XmlParser xmlParser, ModelMapper modelMapper) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
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

    @Override
    public void exportSales() throws JAXBException {
        List<SaleDiscountDto> saleDiscountDtos = this.saleRepository
                .findAll()
                .stream()
                .map(s -> {
                    SaleDiscountDto saleDiscountDto = this.modelMapper.map(s, SaleDiscountDto.class);
                    CarDto car = this.modelMapper.map(s.getCar(), CarDto.class);

                    saleDiscountDto.setCarDto(car);
                    saleDiscountDto.setCustomerName(s.getCustomer().getName());
                    saleDiscountDto.setPrice(s.getCar().getParts().stream().map(Part::getPrice).reduce(BigDecimal::add).get());
                    saleDiscountDto.setPriceWithDiscount(saleDiscountDto.getPrice().multiply(BigDecimal.valueOf(s.getDiscount())));
                    return saleDiscountDto;
                })
                .collect(Collectors.toList());

        SaleDiscountsRootDto saleDiscountsRootDto = new SaleDiscountsRootDto();
        saleDiscountsRootDto.setSaleDiscountDtos(saleDiscountDtos);

        this.xmlParser.exportToFile(SaleDiscountsRootDto.class, saleDiscountsRootDto, FILE_EXPORT_SALES_PATH);
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
