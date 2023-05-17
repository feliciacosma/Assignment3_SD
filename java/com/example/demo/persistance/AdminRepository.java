package com.example.demo.persistance;

import com.example.demo.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer>, CrudRepository<Admin, Integer> {
    Optional<Admin> findByUsername(String username);
}
