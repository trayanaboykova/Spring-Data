package softuni.exam.util.parsers;

import javax.xml.bind.JAXBException;

public interface XmlParser {

    <T> T fromFile(String filePath, Class<T> tClass) throws JAXBException;
}
