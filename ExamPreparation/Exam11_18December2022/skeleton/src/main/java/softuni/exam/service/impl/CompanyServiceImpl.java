package softuni.exam.service.impl;

import org.springframework.stereotype.Service;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.service.CompanyService;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CompanyServiceImpl implements CompanyService {
    private static final String FILE_PATH = "src/main/resources/files/xml/companies.xml";
    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importCompanies() throws IOException, JAXBException {
        return null;
    }
}
