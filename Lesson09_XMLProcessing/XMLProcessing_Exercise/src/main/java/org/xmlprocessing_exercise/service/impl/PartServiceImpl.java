package org.xmlprocessing_exercise.service.impl;

import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.xmlprocessing_exercise.data.entities.Part;
import org.xmlprocessing_exercise.data.entities.Supplier;
import org.xmlprocessing_exercise.data.repositories.PartRepository;
import org.xmlprocessing_exercise.data.repositories.SupplierRepository;
import org.xmlprocessing_exercise.service.PartService;
import org.xmlprocessing_exercise.service.dto.imports.PartSeedDto;
import org.xmlprocessing_exercise.service.dto.imports.PartSeedRootDto;
import org.xmlprocessing_exercise.service.dto.imports.SupplierSeedDto;
import org.xmlprocessing_exercise.service.dto.imports.SupplierSeedRootDto;
import org.xmlprocessing_exercise.util.ValidationUtil;
import org.xmlprocessing_exercise.util.XmlParser;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class PartServiceImpl implements PartService {
    private static final String FILE_IMPORT_PATH = "src/main/resources/xml/parts.xml";

    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public PartServiceImpl(PartRepository partRepository, SupplierRepository supplierRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedParts() throws JAXBException {
        if (this.partRepository.count() == 0) {
            PartSeedRootDto partSeedRootDto = this.xmlParser.parse(PartSeedRootDto.class, FILE_IMPORT_PATH);
            for (PartSeedDto partSeedDto : partSeedRootDto.getPartSeedDtoList()) {
                if (!this.validationUtil.isValid(partSeedDto)) {
                    System.out.println("Invalid part data");
                    continue;
            }
                Part part = this.modelMapper.map(partSeedDto, Part.class);
                part.setSupplier(getRandomSupplier());

                this.partRepository.saveAndFlush(part);
            }
        }
    }

    private Supplier getRandomSupplier() {
        return this.supplierRepository
                .findById(
                        ThreadLocalRandom.current().nextLong(1, this.supplierRepository.count() + 1)
                ).get();
    }
}
