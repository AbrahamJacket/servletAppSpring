package com.example.servletappspring.сontroller;

import com.example.servletappspring.entity.Personnel;
import com.example.servletappspring.service.PersonnelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonnelController {

    private final PersonnelService personnelService;

    public PersonnelController(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Personnel registerPersonnel(@RequestBody Personnel personnel, HttpServletResponse response) throws Exception {
        return personnelService.registerPersonnel(personnel, response);
    }

    @GetMapping("/personnel/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Personnel findPersonnelById(@PathVariable Long id){
        return personnelService.findPersonnelbyId(id);
    }

    @GetMapping("/personnel")
    @ResponseStatus(HttpStatus.OK)
    public List<Personnel> findAllPersonnel(){
        return personnelService.findAllPersonnel();
    }

    @PutMapping("/personnel/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Personnel updatePersonnel(@RequestBody Personnel personnel, @PathVariable Long id){
        return personnelService.updatePersonnel(personnel, id);
    }

    @DeleteMapping("/personnel/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePersonnelById(@PathVariable Long id){
        personnelService.deletePersonnelById(id);
    }

    @DeleteMapping("/personnel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllPersonnel(){
        personnelService.deleteAllPersonnel();
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void loginUser(@RequestBody Personnel personnel, HttpServletRequest request, HttpServletResponse response) throws IOException {
        personnelService.loginPersonnel(personnel, request, response);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logoutUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        personnelService.logoutPersonnel(request, response);
    }
}
