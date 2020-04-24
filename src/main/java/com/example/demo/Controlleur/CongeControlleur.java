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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Exception.ResourceNotFoundException;
import com.example.demo.dao.CongeRepository;
import com.example.demo.entities.Conge;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CongeControlleur {
	@Autowired
	private CongeRepository congeRepository;
	@GetMapping("/con")
	  public List<Conge> getAllConges() {
	    System.out.println("Get all Conges...");
	 
	    List<Conge> Conges = new ArrayList<>();
	    congeRepository.findAll().forEach(Conges::add);
	 
	    return Conges;
	  }
	
	@GetMapping("/con/{num}")
	public ResponseEntity<Conge> getCongeById(@PathVariable(value = "num") Long num)
			throws ResourceNotFoundException {
		Conge Conge = congeRepository.findById(num)
				.orElseThrow(() -> new ResourceNotFoundException("Conge not found for this num :: " + num));
		return ResponseEntity.ok().body(Conge);
	}
	
	@PostMapping("/con")
	public Conge createConge(@Valid @RequestBody Conge Conge) {
		return congeRepository.save(Conge);
	}
	

	@DeleteMapping("/con/{num}")
	public Map<String, Boolean> deleteConge(@PathVariable(value = "num") Long num)
			throws ResourceNotFoundException {
		Conge Conge = congeRepository.findById(num)
				.orElseThrow(() -> new ResourceNotFoundException("Conge not found  id :: " + num));

		congeRepository.delete(Conge);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	  
	 
	  @DeleteMapping("/con/delete")
	  public ResponseEntity<String> deleteAllConges() {
	    System.out.println("Delete All Conges...");
	 
	    congeRepository.deleteAll();
	 
	    return new ResponseEntity<>("All Conges have been deleted!", HttpStatus.OK);
	  }
	 
	

	  @PutMapping("/con/{num}")
	  public ResponseEntity<Conge> updateConge(@PathVariable("num") long num, @RequestBody Conge Conge) {
	    System.out.println("Update Conge with ID = " + num + "...");
	 
	    Optional<Conge> CarteInfo = congeRepository.findById(num);
		 
	    if (CarteInfo.isPresent()) {
	    	Conge conge = CarteInfo.get();
	    	conge.setDate_debut(conge.getDate_debut());
	    	conge.setDate_fin(conge.getDate_fin());
	    	conge.setStatut(conge.getStatut());
	    	conge.setSalarie(conge.getSalarie());
	    	conge.setTypeconge(conge.getTypeconge());
	          
	      
	           
	      return new ResponseEntity<>(congeRepository.save(Conge), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
}
