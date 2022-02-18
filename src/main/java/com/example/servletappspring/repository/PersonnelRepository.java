package com.example.servletappspring.repository;

import com.example.servletappspring.entity.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonnelRepository extends JpaRepository<Personnel, Long> {
    Personnel findPersonnelByName(String name);
}
