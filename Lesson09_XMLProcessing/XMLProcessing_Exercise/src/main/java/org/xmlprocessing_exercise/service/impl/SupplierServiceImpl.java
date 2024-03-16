package org.xmlprocessing_exercise.service.impl;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Service;
import org.xmlprocessing_exercise.data.repositories.SupplierRepository;
import org.xmlprocessing_exercise.service.SupplierService;
import org.xmlprocessing_exercise.service.dto.imports.SupplierSeedDto;
import org.xmlprocessing_exercise.service.dto.imports.SupplierSeedRootDto;
import org.xmlprocessing_exercise.util.XmlParser;

import java.io.File;

@Service
public class SupplierServiceImpl implements SupplierService {
    private static final String FILE_IMPORT_PATH = "src/main/resources/xml/suppliers.xml";
    private final SupplierRepository supplierRepository;
    private final XmlParser xmlParser;

    public SupplierServiceImpl(SupplierRepository supplierRepository, XmlParser xmlParser) {
        this.supplierRepository = supplierRepository;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedSupplier() throws JAXBException {
        if (this.supplierRepository.count() == 0) {
            SupplierSeedRootDto supplierSeedRootDto = xmlParser.parse(SupplierSeedRootDto.class, FILE_IMPORT_PATH);
            for (SupplierSeedDto supplierSeedDto : supplierSeedRootDto.getSupplierSeedDtoList()) {

            }
        }
    }
}
