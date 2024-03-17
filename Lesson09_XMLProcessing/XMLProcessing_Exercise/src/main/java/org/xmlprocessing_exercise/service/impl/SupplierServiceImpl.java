package org.xmlprocessing_exercise.service.impl;

import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.xmlprocessing_exercise.data.entities.Supplier;
import org.xmlprocessing_exercise.data.repositories.SupplierRepository;
import org.xmlprocessing_exercise.service.SupplierService;
import org.xmlprocessing_exercise.service.dto.imports.SupplierSeedDto;
import org.xmlprocessing_exercise.service.dto.imports.SupplierSeedRootDto;
import org.xmlprocessing_exercise.util.validations.ValidationUtil;
import org.xmlprocessing_exercise.util.parser.XmlParser;


@Service
public class SupplierServiceImpl implements SupplierService {
    private static final String FILE_IMPORT_PATH = "src/main/resources/xml/imports/suppliers.xml";
    private final SupplierRepository supplierRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedSupplier() throws JAXBException {
        if (this.supplierRepository.count() == 0) {
            SupplierSeedRootDto supplierSeedRootDto = xmlParser.parse(SupplierSeedRootDto.class, FILE_IMPORT_PATH);
            for (SupplierSeedDto supplierSeedDto : supplierSeedRootDto.getSupplierSeedDtoList()) {
                if (!this.validationUtil.isValid(supplierSeedDto)) {
                    System.out.println("Invalid supplier data");
                    continue;
                }
                Supplier supplier = this.modelMapper.map(supplierSeedDto, Supplier.class);
                this.supplierRepository.saveAndFlush(supplier);
            }
        }
    }
}
