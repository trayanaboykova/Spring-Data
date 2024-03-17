package org.xmlprocessing_exercise.service.impl;

import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.xmlprocessing_exercise.data.entities.Supplier;
import org.xmlprocessing_exercise.data.repositories.SupplierRepository;
import org.xmlprocessing_exercise.service.SupplierService;
import org.xmlprocessing_exercise.service.dto.exports.SupplierLocalDto;
import org.xmlprocessing_exercise.service.dto.exports.SupplierLocalRootDto;
import org.xmlprocessing_exercise.service.dto.imports.SupplierSeedDto;
import org.xmlprocessing_exercise.service.dto.imports.SupplierSeedRootDto;
import org.xmlprocessing_exercise.util.validations.ValidationUtil;
import org.xmlprocessing_exercise.util.parser.XmlParser;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class SupplierServiceImpl implements SupplierService {
    private static final String FILE_IMPORT_PATH = "src/main/resources/xml/imports/suppliers.xml";
    private static final String FILE_EXPORT_LOCAL_PATH = "src/main/resources/xml/exports/local-suppliers.xml";
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

    @Override
    public void exportLocalSuppliers() throws JAXBException {
        List<SupplierLocalDto> supplierLocalDtos = this.supplierRepository.findAllByIsImporter(false)
                .stream()
                .map(s -> {
                    SupplierLocalDto dto = this.modelMapper.map(s, SupplierLocalDto.class);
                    dto.setPartsCount(s.getParts().size());
                    return dto;
                })
                .collect(Collectors.toList());

        SupplierLocalRootDto supplierLocalRootDto = new SupplierLocalRootDto();
        supplierLocalRootDto.setSupplierLocalDto(supplierLocalDtos);

        this.xmlParser.exportToFile(SupplierLocalRootDto.class, supplierLocalRootDto, FILE_EXPORT_LOCAL_PATH);
    }
}
