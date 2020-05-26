package com.example.demo.dao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entities.Role;
import com.example.demo.entities.RoleName;
import com.example.demo.entities.Salarie;
import com.example.demo.message.request.SignUpForm;

@EnableJpaRepositories ("com.example.demo.dao")

public interface SalarieRepository extends JpaRepository<Salarie,Long> {
	 Optional<Salarie> findByUsername(String username);
	   Boolean existsByUsername(String username);
		Boolean existsByMail(String mail);
		@Query("SELECT s FROM Salarie s JOIN s.roles r WHERE r.name = :role")
	    List<Salarie> getManagerList(@Param("role") RoleName roleName);
	
		Salarie findByMail(String mail);

}
