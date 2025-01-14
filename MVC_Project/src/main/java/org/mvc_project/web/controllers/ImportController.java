package org.mvc_project.web.controllers;

import org.mvc_project.service.CompanyService;
import org.mvc_project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Controller
@RequestMapping("/import")
public class ImportController {

    private final CompanyService companyService;
    private final ProjectService projectService;

    @Autowired
    public ImportController(CompanyService companyService, ProjectService projectService) {
        this.companyService = companyService;
        this.projectService = projectService;
    }

    @GetMapping("/xml")
    public ModelAndView xml() {
        ModelAndView modelAndView = new ModelAndView("xml/import-xml");
        modelAndView.addObject("areImported",new boolean[]{ this.companyService.isImported(),
                this.projectService.isImported(),
                false});
        return modelAndView;
    }

    @GetMapping("/companies")
    public ModelAndView companies() throws IOException {
        ModelAndView modelAndView = new ModelAndView("xml/import-companies");
        modelAndView.addObject("companies",this.companyService.readFile());
        return modelAndView;
    }

    @PostMapping("/companies")
    public ModelAndView companiesPost() throws JAXBException, IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:xml");
        this.companyService.seedData();
        return modelAndView;
    }



    @GetMapping("/projects")
    public ModelAndView projects() throws IOException {
        ModelAndView modelAndView = new ModelAndView("xml/import-projects");
        modelAndView.addObject("projects",this.projectService.readFile());
        return modelAndView;
    }

    @PostMapping("/projects")
    public ModelAndView projectsPost() throws JAXBException, IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:xml");
        this.projectService.seedData();
        return modelAndView;
    }


    @GetMapping("/employees")
    public ModelAndView employees() {
        ModelAndView modelAndView = new ModelAndView("xml/import-employees");
        modelAndView.addObject("employees","");
        return modelAndView;
    }
}