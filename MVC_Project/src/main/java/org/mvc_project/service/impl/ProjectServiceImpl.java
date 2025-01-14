package org.mvc_project.service.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.modelmapper.ModelMapper;
import org.mvc_project.data.repositories.CompanyRepository;
import org.mvc_project.data.repositories.ProjectRepository;
import org.mvc_project.service.ProjectService;
import org.mvc_project.service.model.imports.ProjectRootImportModel;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ProjectServiceImpl implements ProjectService {

    private static final String FILE_PATH = "src/main/resources/files/xmls/projects.xml";

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean isImported() {
        return this.projectRepository.count() > 0;
    }

    //TODO DO IT WITH JACKSON, some errors are appearing
    //Already tested with JAXB
    @Override
    public void seedData() throws JAXBException, IOException {
        XmlMapper xmlMapper = new XmlMapper();
        ProjectRootImportModel projectRootImportModel = xmlMapper.readValue(readFile(), ProjectRootImportModel.class);
        System.out.println();
    }

    @Override
    public String readFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }
}
