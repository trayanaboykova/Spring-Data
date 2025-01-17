package org.mvc_project.web.controllers;

import org.mvc_project.service.EmployeeService;
import org.mvc_project.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/export")
public class ExportController {

    private final ProjectService projectService;
    private final EmployeeService employeeService;

    public ExportController(ProjectService projectService, EmployeeService employeeService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/project-if-finished")
    public ModelAndView finishedProjects() {
        String finishedProjects = this.projectService.getFinishedProjects();

        ModelAndView modelAndView = new ModelAndView("export/export-project-if-finished");
        modelAndView.addObject("projectsIfFinished", finishedProjects);

        return modelAndView;
    }

    @GetMapping("/employees-above")
    public ModelAndView employeesAbove() {
        String employeesInfo = this.employeeService.getEmployeesAbove25();

        ModelAndView modelAndView =
                new ModelAndView("export/export-employees-with-age");
        modelAndView.addObject("employeesAbove", employeesInfo);

        return modelAndView;
    }
}
