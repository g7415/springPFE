package com.example.demo.Controlleur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Exception.ResourceNotFoundException;
import com.example.demo.dao.SalarieRepository;
import com.example.demo.entities.Salarie;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class SalarieControlleur {
	@Autowired
	private SalarieRepository salarieRepository;
	@GetMapping("/sal")
	  public List<Salarie> getAllSalaries() {
	    System.out.println("Get all Salaries...");
	 
	    List<Salarie> Salaries = new ArrayList<>();
	    salarieRepository.findAll().forEach(Salaries::add);
	 
	    return Salaries;
	  }
	
	@GetMapping("/sal/{id}")
	public ResponseEntity<Salarie> getSalarieById(@PathVariable(value = "id") Long SalarieId)
			throws ResourceNotFoundException {
		Salarie Salarie = salarieRepository.findById(SalarieId)
				.orElseThrow(() -> new ResourceNotFoundException("Salarie not found for this id :: " + SalarieId));
		return ResponseEntity.ok().body(Salarie);
	}

	@PostMapping("/sal")
	public Salarie createSalarie(@Valid @RequestBody Salarie Salarie) {
		return salarieRepository.save(Salarie);
	}
	

	@DeleteMapping("/sal/{id}")
	public Map<String, Boolean> deleteSalarie(@PathVariable(value = "id") Long SalarieId)
			throws ResourceNotFoundException {
		Salarie Salarie = salarieRepository.findById(SalarieId)
				.orElseThrow(() -> new ResourceNotFoundException("Salarie not found  id :: " + SalarieId));

		salarieRepository.delete(Salarie);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	  
	 
	  @DeleteMapping("/sal/delete")
	  public ResponseEntity<String> deleteAllSalaries() {
	    System.out.println("Delete All Salaries...");
	 
	    salarieRepository.deleteAll();
	 
	    return new ResponseEntity<>("All Salaries have been deleted!", HttpStatus.OK);
	  }
	 
	

	  @PutMapping("/sal/{id}")
	  public ResponseEntity<Salarie> updateSalarie(@PathVariable("id") long id, @RequestBody Salarie Salarie) {
	    System.out.println("Update Salarie with ID = " + id + "...");
	 
	    Optional<Salarie> CarteInfo = salarieRepository.findById(id);
		 
	    if (CarteInfo.isPresent()) {
	    	Salarie salarie = CarteInfo.get();
	    	salarie.setNom(Salarie.getNom());
	    	salarie.setPrenom(Salarie.getPrenom());
	    	salarie.setGroupe(Salarie.getGroupe());
	    	salarie.setDate_entree(Salarie.getDate_entree());
	    	salarie.setGrade(Salarie.getGrade());
	    	salarie.setMail(Salarie.getMail());
	    	salarie.setNum_tel(Salarie.getNum_tel());
	    	salarie.setNom_responsable(Salarie.getNom_responsable());
	    	salarie.setPassword(Salarie.getPassword());
	    	salarie.setUsername(Salarie.getUsername());
	    	salarie.setSolde_conge(Salarie.getSolde_conge());
	    	
	    	
	          
	      
	           
	      return new ResponseEntity<>(salarieRepository.save(Salarie), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
}
