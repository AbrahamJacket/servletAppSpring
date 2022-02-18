package com.example.servletappspring.service;

import com.example.servletappspring.entity.Employee;
import com.example.servletappspring.repository.EmployeeRepository;
import com.example.servletappspring.repository.PersonnelRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PersonnelRepository personnelRepository;
    private final PersonnelService personnelService;

    public EmployeeService(EmployeeRepository employeeRepository, PersonnelRepository personnelRepository, PersonnelService personnelService) {
        this.employeeRepository = employeeRepository;
        this.personnelRepository = personnelRepository;
        this.personnelService = personnelService;
    }

    public Employee saveEmployee(Employee employee, HttpServletRequest request) throws Exception {
        if (personnelService.checkAccessLevel(request) >= 2) {
            return employeeRepository.save(employee);
        } else return null;
    }

    public Employee getEmployeeById(Long id, HttpServletRequest request) throws Exception {
        if (personnelService.checkAccessLevel(request) >= 1) {
            return employeeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
        } else throw new Exception("Acces Denied!");
    }

    public List<Employee> getAllEmployee(HttpServletRequest request) throws Exception {
        if (personnelService.checkAccessLevel(request) >= 1) {
            return employeeRepository.findAll();
        } else throw new Exception("Acces Denied!");
    }

    public Employee updateEmployee(Employee employee, Long id, HttpServletRequest request) throws Exception {
        if (personnelService.checkAccessLevel(request) >= 2) {
            return employeeRepository.findById(id)
                    .map(entity -> {
                        entity.setName(employee.getName());
                        entity.setCountry(employee.getCountry());
                        entity.setEmail(employee.getEmail());
                        return employeeRepository.save(entity);
                    }).orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
        } else throw new Exception("Acces Denied!");
    }

    public void deleteEmployeeById(Long id, HttpServletRequest request) throws Exception {
        if (personnelService.checkAccessLevel(request) == 3) {
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
            employeeRepository.delete(employee);
        } else throw new Exception("Acces Denied!");
    }

    public void deleteAllEmployee(HttpServletRequest request) throws Exception {
        if (personnelService.checkAccessLevel(request) == 3) {
            employeeRepository.deleteAll();
        } else throw new Exception("Acces Denied!");
    }
}