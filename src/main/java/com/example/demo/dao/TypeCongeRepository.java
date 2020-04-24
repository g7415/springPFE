package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.demo.entities.TypeConge;


@EnableJpaRepositories ("com.example.demo.dao")

public interface TypeCongeRepository extends JpaRepository<TypeConge,Long> {

}
