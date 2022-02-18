package com.example.servletappspring.service;

import com.example.servletappspring.entity.Personnel;
import com.example.servletappspring.repository.PersonnelRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.example.servletappspring.utility.AccessLevelCheck.checkInList;

@Service
public class PersonnelService {

    private final PersonnelRepository personnelRepository;

    public PersonnelService(PersonnelRepository personnelRepository) {
        this.personnelRepository = personnelRepository;
    }

    public Personnel registerPersonnel(Personnel personnel, HttpServletResponse response) throws Exception {
        PrintWriter out = response.getWriter();
        List<Personnel> list = findAllPersonnel();
        int status = 0;
        int result = 1;
        if (result == 0) {
            for (Personnel personnel1 : list) {
                if (personnel1.getName().equals(personnel.getName())) {
                    out.println("This personnel already exists!");
                    result = 0;
                }
            }
            if (!personnel.getAccessLevel().equals(Personnel.AccessLevel.ADMIN)
                    || !personnel.getAccessLevel().equals(Personnel.AccessLevel.MODERATOR)
                    || !personnel.getAccessLevel().equals(Personnel.AccessLevel.USER)){
                result = 0;
            }
           throw new Exception("Cannot register personnell!");
        }
        return personnelRepository.save(personnel);
    }

    public Personnel findPersonnelbyId(Long id){
        return personnelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Personnel not found with id = " + id));
    }

    public List<Personnel> findAllPersonnel(){
        return personnelRepository.findAll();
    }

    public Personnel updatePersonnel(Personnel personnel, Long id){
        return personnelRepository.findById(id)
                .map(entity -> {
                    entity.setName(personnel.getName());
                    entity.setPassword(personnel.getPassword());
                    entity.setAccessLevel(personnel.getAccessLevel());
                    return personnelRepository.save(entity);
                }).orElseThrow(() -> new EntityNotFoundException("Personnel not found with id = " + id));
    }

    public void deletePersonnelById(Long id){
        Personnel personnel = personnelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Personnel not found with id = " + id));
        personnelRepository.delete(personnel);
    }

    public void deleteAllPersonnel(){
        personnelRepository.deleteAll();
    }

    public void loginPersonnel(Personnel personnel, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(checkInList(personnel)) {
            HttpSession session = request.getSession();
            session.setAttribute("User", personnel.getName());
            session.setMaxInactiveInterval(30 * 60);
            Cookie userName = new Cookie("user", personnel.getName());
            userName.setMaxAge(30 * 60);
            response.addCookie(userName);
            PrintWriter out = response.getWriter();
            out.println("Welcome back to the team" + personnel.getName());
        } else {
            PrintWriter out = response.getWriter();
            out.println("Either user name or password is wrong!");
        }
    }

    public void logoutPersonnel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    System.out.println("JSESSIONID=" + cookie.getValue());
                    break;
                }
            }
        }
        HttpSession session = request.getSession(false);
        System.out.println("User=" + session.getAttribute("user"));
        if (session != null) {
            session.invalidate();
        }

        PrintWriter out = response.getWriter();
        out.println("Logged out successfully!");
    }

    public List<Personnel> getAllUsers(){
        return personnelRepository.findAll();
    }

    public Personnel findPersonnelByName(String name){
        Personnel personnel = personnelRepository.findPersonnelByName(name);
        if (name == null)
            throw new EntityNotFoundException("Personnel not found with name = " + name);
        return personnel;
    }

    public Personnel.AccessLevel getPersonnelAccessLevel(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        return findPersonnelByName(session.getAttribute("user").toString()).getAccessLevel();
    }
}
