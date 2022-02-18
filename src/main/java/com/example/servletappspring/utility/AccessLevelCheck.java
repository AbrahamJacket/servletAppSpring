package com.example.servletappspring.utility;

import com.example.servletappspring.entity.Personnel;
import com.example.servletappspring.service.PersonnelService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AccessLevelCheck {

    private static PersonnelService personnelService;

    public static int checkAccessLevel(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        int status = 3;
        if (session == null) {
            status = 0;
        } else if (session != null && personnelService.getPersonnelAccessLevel(request) == Personnel.AccessLevel.USER) {
            status = 1;
        } else if (session != null && personnelService.getPersonnelAccessLevel(request) == Personnel.AccessLevel.MODERATOR) {
            status = 2;
        }
        return status;
    }

    public static boolean checkInList(Personnel personnel) {
        List<Personnel> list = personnelService.findAllPersonnel();
        for (Personnel personnel1 : list) {
            if (personnel1.getName().equals(personnel.getName()) && personnel1.getPassword().equals(personnel.getPassword())) {
                return true;
            }
        }
        return false;
    }
}
