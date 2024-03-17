package org.xmlprocessing_exercise.service.impl;

import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.xmlprocessing_exercise.data.entities.Customer;
import org.xmlprocessing_exercise.data.repositories.CustomerRepository;
import org.xmlprocessing_exercise.service.CustomerService;
import org.xmlprocessing_exercise.service.dto.exports.CustomerOrderedDto;
import org.xmlprocessing_exercise.service.dto.exports.CustomerOrderedRootDto;
import org.xmlprocessing_exercise.service.dto.exports.CustomerTotalSalesDto;
import org.xmlprocessing_exercise.service.dto.exports.CustomerTotalSalesRootDto;
import org.xmlprocessing_exercise.service.dto.imports.CustomerSeedRootDto;
import org.xmlprocessing_exercise.util.parser.XmlParser;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final String FILE_IMPORT_PATH = "src/main/resources/xml/imports/customers.xml";
    private static final String FILE_EXPORT_CUSTOMERS_PATH = "src/main/resources/xml/exports/ordered-customers.xml";
    private static final String FILE_EXPORT_CUSTOMERS_BOUGHT_PATH = "src/main/resources/xml/exports/customers-total-sales.xml";
    private final CustomerRepository customerRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, XmlParser xmlParser, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCustomers() throws JAXBException {
        if (this.customerRepository.count() == 0) {
            CustomerSeedRootDto customerSeedRootDto = xmlParser.parse(CustomerSeedRootDto.class, FILE_IMPORT_PATH);
            customerSeedRootDto.getCustomerSeedDtoList().forEach(
                    c -> this.customerRepository.saveAndFlush(this.modelMapper.map(c, Customer.class)));
        }
    }

    @Override
    public void exportOrderedCustomers() throws JAXBException {
        List<CustomerOrderedDto> customerOrderedDtos = this.customerRepository.findAllByOrderByBirthDateAscIsYoungDriverAsc()
                .stream()
                .map(c -> this.modelMapper.map(c, CustomerOrderedDto.class))
                .collect(Collectors.toList());

        CustomerOrderedRootDto customerOrderedRootDto = new CustomerOrderedRootDto();
        customerOrderedRootDto.setCustomerOrderedDtoList(customerOrderedDtos);

        this.xmlParser.exportToFile(CustomerOrderedRootDto.class, customerOrderedRootDto, FILE_EXPORT_CUSTOMERS_PATH);
    }

    @Override
    public void exportCustomersWithBoughtCars() throws JAXBException {
        List<CustomerTotalSalesDto> collect = this.customerRepository.findAllWithBoughtCars()
                .stream()
                .map(c -> {
                    CustomerTotalSalesDto customerTotalSalesDto = new CustomerTotalSalesDto();
                    customerTotalSalesDto.setFullName(c.getName());
                    customerTotalSalesDto.setBoughtCars(c.getSales().size());
                    double spentMoney = c.getSales()
                            .stream()
                            .mapToDouble(s -> s.getCar().getParts().stream().mapToDouble(p -> p.getPrice().doubleValue()).sum() * s.getDiscount())
                            .sum();
                    customerTotalSalesDto.setSpentMoney(BigDecimal.valueOf(spentMoney));

                    return customerTotalSalesDto;
                })
                .sorted(Comparator.comparing(CustomerTotalSalesDto::getBoughtCars).reversed()
                        .thenComparing(Comparator.comparing(CustomerTotalSalesDto::getSpentMoney).reversed()))
                .collect(Collectors.toList());

        CustomerTotalSalesRootDto customerTotalSalesRootDto = new CustomerTotalSalesRootDto();
        customerTotalSalesRootDto.setCustomerTotalSalesDtos(collect);

        this.xmlParser.exportToFile(CustomerTotalSalesRootDto.class, customerTotalSalesRootDto, FILE_EXPORT_CUSTOMERS_BOUGHT_PATH);
    }
}

