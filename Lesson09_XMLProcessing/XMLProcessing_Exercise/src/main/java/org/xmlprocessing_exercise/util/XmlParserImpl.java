package org.xmlprocessing_exercise.util;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Component;
import org.xmlprocessing_exercise.service.dto.imports.SupplierSeedRootDto;

import java.io.File;

@Component
public class XmlParserImpl implements XmlParser {
    @Override
    public <E> E parse(Class<E> clazz, String path) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        E object = (E) unmarshaller.unmarshal(new File(path));
        return object;
    }
}
