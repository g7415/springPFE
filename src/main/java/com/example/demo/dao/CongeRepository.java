package com.example.demo.dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Conge;
import com.example.demo.entities.RoleName;
import com.example.demo.entities.Salarie;

@EnableJpaRepositories ("com.example.demo.dao")
public interface CongeRepository extends JpaRepository<Conge,Long> {
//	 @Query("SELECT c FROM Conge c where c.salarie.id = :id")
//	    public List<Conge> findByCateg(@Param("id") Long id);
		@Query("SELECT c FROM Conge c JOIN c.salarie s WHERE s.id = :id")
	    List<Conge> getCongeByIdSal(@Param("id") Long id);
		
		@Query("SELECT c FROM Conge c JOIN c.salarie s WHERE s.username = :username")
	    List<Conge> getCongeByUsernameSal(@Param("username") String username);
		
		@Query("SELECT c FROM Conge c JOIN c.salarie.manager s WHERE s.id = :id")
	    List<Conge> getCongeListByManager(@Param("id") Long id);
		
		@Query("SELECT c FROM Conge c  WHERE c.statut = :statut")
	    List<Conge> getListConByStatut(@Param("statut") String statut);
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
