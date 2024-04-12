package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmls.CompanyRootDto;
import softuni.exam.models.dto.xmls.CompanySeedDto;
import softuni.exam.models.entity.Company;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CompanyService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {
    private static final String FILE_PATH = "src/main/resources/files/xml/companies.xml";
    private final CompanyRepository companyRepository;
    private final CountryRepository countryRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final ResourceLoader resourceLoader;

    public CompanyServiceImpl(CompanyRepository companyRepository, CountryRepository countryRepository, ValidationUtil validationUtil, ModelMapper modelMapper, ResourceLoader resourceLoader) {
        this.companyRepository = companyRepository;
        this.countryRepository = countryRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesFromFile() throws IOException {
//        Resource resource = resourceLoader.getResource("classpath:files/xml/companies.xml");
//        InputStream is = resource.getInputStream();
//        if (is == null) {
//            throw new IllegalStateException("Can't find file companies.xml in classpath");
//        }
//
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
//            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
//        } catch (IOException e) {
//            throw new UncheckedIOException("Error occurred while reading file companies.xml", e);
//        }
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importCompanies() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(CompanyRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        InputStream is = resourceLoader.getResource("classpath:files/xml/companies.xml").getInputStream();
        CompanyRootDto companyRootDto = (CompanyRootDto) unmarshaller.unmarshal(new InputStreamReader(is));

        List<CompanySeedDto> companySeedDtos = companyRootDto.getCompanies();

        for (CompanySeedDto companySeedDto : companySeedDtos) {
            String companyName = companySeedDto.getName();

            Optional<Company> existingCompany = this.companyRepository
                    .findByName(companyName);

            if (!this.validationUtil.isValid(companySeedDto) || existingCompany.isPresent()) {
                sb.append("Invalid company\n");
                continue;
            }

            Company company = this.modelMapper.map(companySeedDto, Company.class);

            long countryIdFromDto = companySeedDto.getCountryId();

            Optional<Country> existingCountry = countryRepository.findById(countryIdFromDto);

            if (existingCountry.isPresent()) {
                company.setCountry(existingCountry.get());
                this.companyRepository.saveAndFlush(company);

                sb.append(String.format("Successfully imported company %s %d\n",
                        company.getName(),
                        company.getCountry().getId()));
            }
        }

        return sb.toString();
    }

}



