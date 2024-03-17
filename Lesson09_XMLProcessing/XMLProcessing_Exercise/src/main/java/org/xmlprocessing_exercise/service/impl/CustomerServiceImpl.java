package org.xmlprocessing_exercise.service.impl;

import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.xmlprocessing_exercise.data.entities.Customer;
import org.xmlprocessing_exercise.data.repositories.CustomerRepository;
import org.xmlprocessing_exercise.service.CustomerService;
import org.xmlprocessing_exercise.service.dto.imports.CustomerSeedRootDto;
import org.xmlprocessing_exercise.util.parser.XmlParser;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final String FILE_IMPORT_PATH = "src/main/resources/xml/customers.xml";
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
}

