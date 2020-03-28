package com.example.demo.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import com.example.demo.entities.Conge;

@EnableJpaRepositories ("com.example.demo.dao")
public interface CongeRepository extends JpaRepository<Conge,Long> {
	
}
