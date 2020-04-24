package com.example.demo.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import com.example.demo.entities.Conge;

@EnableJpaRepositories ("com.example.demo.dao")
public interface CongeRepository extends JpaRepository<Conge,Long> {
	
}
//package com.example.demo.dao;
//
//
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.data.repository.query.Param;
//
//import com.example.demo.entities.Conge;
//
//@EnableJpaRepositories ("com.example.demo.dao")
//public interface CongeRepository extends JpaRepository<Conge,Long> {
//	 @Query("SELECT t FROM Conge t where t.id_type = :id_type")
//	    public List<Conge> findByCateg(@Param("id_type") Long id_type);
//}
