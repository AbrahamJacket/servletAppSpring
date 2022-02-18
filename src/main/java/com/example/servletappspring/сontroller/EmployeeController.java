package com.example.servletappspring.сontroller;

import com.example.servletappspring.entity.Employee;
import com.example.servletappspring.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employee")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@RequestBody Employee employee, HttpServletRequest request) throws Exception {
        return employeeService.saveEmployee(employee, request);
    }

    @GetMapping("/employee/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getEmployeeById(@PathVariable Long id, HttpServletRequest request) throws Exception {
        return employeeService.getEmployeeById(id, request);
    }

    @GetMapping("/employee")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllEmployee(HttpServletRequest request) throws Exception {
        return employeeService.getAllEmployee(request);
    }

    @PutMapping("/employee/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee updateEmployee(@RequestBody Employee employee, @PathVariable Long id, HttpServletRequest request) throws Exception {
        return employeeService.updateEmployee(employee, id, request);
    }

    @DeleteMapping("/employee/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployeeById(@PathVariable Long id, HttpServletRequest request) throws Exception {
        employeeService.deleteEmployeeById(id, request);
    }

    @DeleteMapping("/employee")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllEmployee(HttpServletRequest request) throws Exception {
        employeeService.deleteAllEmployee(request);
    }
}
