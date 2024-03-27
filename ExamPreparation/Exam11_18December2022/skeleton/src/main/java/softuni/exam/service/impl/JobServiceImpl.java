package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmls.JobRootDto;
import softuni.exam.models.dto.xmls.JobSeedDto;
import softuni.exam.models.entity.Company;
import softuni.exam.models.entity.Job;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.repository.JobRepository;
import softuni.exam.service.JobService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    private static final String FILE_PATH = "src/main/resources/files/xml/jobs.xml";
    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final ResourceLoader resourceLoader;

    public JobServiceImpl(JobRepository jobRepository, CompanyRepository companyRepository, ValidationUtil validationUtil, ModelMapper modelMapper, ResourceLoader resourceLoader) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public boolean areImported() {
        return this.jobRepository.count() > 0;
    }

    @Override
    public String readJobsFileContent() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:files/xml/jobs.xml");
        InputStream is = resource.getInputStream();
        if (is == null) {
            throw new IllegalStateException("Can't find file jobs.xml in classpath");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new UncheckedIOException("Error occurred while reading file jobs.xml", e);
        }

//        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importJobs() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(JobRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        InputStream is = resourceLoader.getResource("classpath:files/xml/jobs.xml").getInputStream();
        JobRootDto jobRootDto = (JobRootDto) unmarshaller.unmarshal(new InputStreamReader(is));

        List<JobSeedDto> jobSeedDtos = jobRootDto.getJobs();

        for (JobSeedDto jobSeedDto : jobSeedDtos) {
            String jobTitle = jobSeedDto.getJobTitle();

            Optional<Job> existingJob = this.jobRepository.findByTitle(jobTitle);

            if (!this.validationUtil.isValid(jobSeedDto) || existingJob.isPresent()) {
                sb.append("Invalid job\n");
                continue;
            }

            Job job = this.modelMapper.map(jobSeedDto, Job.class);

            Optional<Company> existingCompany = companyRepository.findAllById(jobSeedDto.getCompanyId());

            if (existingCompany.isPresent()) {
                job.setCompany(existingCompany.get());

                this.jobRepository.saveAndFlush(job);
                sb.append(String.format("Successfully imported job %s\n",
                        job.getTitle()));
            }
        }


        return sb.toString();
    }

    @Override
    public String getBestJobs() {
        StringBuilder jobsDetails = new StringBuilder();

        List<Job> bestJobs = jobRepository.findBestJobs();

        for(Job job : bestJobs){
            String jobDetail = "Job title " + job.getTitle() + "\n" +
                    "   -Salary: " + String.format("%.2f", job.getSalary()) + "$\n" +
                    "   --Hours a week: " + String.format("%.2f", job.getHoursAWeek()) + "h.\n";

            jobsDetails.append(jobDetail);
        }

        return jobsDetails.toString();
    }
}
