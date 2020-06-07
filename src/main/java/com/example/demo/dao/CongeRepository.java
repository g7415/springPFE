package com.example.demo.dao;



import java.lang.reflect.Array;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Exception.ResourceNotFoundException;
import com.example.demo.entities.Conge;
import com.example.demo.entities.RoleName;
import com.example.demo.entities.Salarie;
import com.example.demo.entities.TypeConge;

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
		
		
		@Query("SELECT  tc.type_conge,tc.max_permis , sum(c.duree) AS congespris, tc.max_permis-sum(c.duree)as congerestant FROM Conge c JOIN c.salarie s JOIN c.typeconge tc"
				+ " WHERE c.statut = 'accepter' and s.username = :username"
				+ " group by s,tc")
	    List<Object> getSumCongePris(@Param("username") String username);
		
		
}
