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
import org.springframework.web.bind.annotation.RestController;

import com.Exception.ResourceNotFoundException;
import com.example.demo.dao.TypeCongeRepository;
import com.example.demo.entities.TypeConge;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class TypeCongeControlleur {
	@Autowired
	private TypeCongeRepository typecongeRepository;
	@GetMapping("/typecon")
	  public List<TypeConge> getAllConges() {
	    System.out.println("Get all Type Conge...");
	 
	    List<TypeConge> TypeConges = new ArrayList<>();
	    typecongeRepository.findAll().forEach(TypeConges::add);
	 
	    return TypeConges;
	  }
	
	@GetMapping("/typecon/{id_type}")
	public ResponseEntity<TypeConge> getTypeCongeById(@PathVariable(value = "id_type") Long id_type)
			throws ResourceNotFoundException {
		TypeConge TypeConge = typecongeRepository.findById(id_type)
				.orElseThrow(() -> new ResourceNotFoundException("Type Conge not found for this id_type :: " + id_type));
		return ResponseEntity.ok().body(TypeConge);
	}
	
    @PreAuthorize(" hasRole('RH')")
	@PostMapping("/typecon")
	public TypeConge createConge(@Valid @RequestBody TypeConge TypeConge) {
		return typecongeRepository.save(TypeConge);
	}
	
  @PreAuthorize(" hasRole('RH')")

	@DeleteMapping("/typecon/{id_type}")
	public Map<String, Boolean> deleteConge(@PathVariable(value = "id_type") Long id_type)
			throws ResourceNotFoundException {
		TypeConge TypeConge = typecongeRepository.findById(id_type)
				.orElseThrow(() -> new ResourceNotFoundException("Type Conge not found  id :: " + id_type));

		typecongeRepository.delete(TypeConge);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	  
  @PreAuthorize(" hasRole('RH')")

	  @DeleteMapping("/typecon/delete")
	  public ResponseEntity<String> deleteAllConges() {
	    System.out.println("Delete All Type Conge...");
	 
	    typecongeRepository.deleteAll();
	 
	    return new ResponseEntity<>("All Conges have been deleted!", HttpStatus.OK);
	  }
	 
	
  @PreAuthorize(" hasRole('RH')")

	  @PutMapping("/typecon/{id_type}")
	  public ResponseEntity<TypeConge> updateConge(@PathVariable("id_type") long id_type, @RequestBody TypeConge typeConge1) {
	    System.out.println("Update Conge with ID = " + id_type + "...");
	 
	    Optional<TypeConge> CarteInfo = typecongeRepository.findById(id_type);
		 
	    if (CarteInfo.isPresent()) {
	    	TypeConge typeconge = CarteInfo.get();
	    	typeconge.setType_conge(typeConge1.getType_conge());
	    	typeconge.setMax_permis(typeConge1.getMax_permis());
//	    	typeconge.setCong_deja_pris(typeConge1.getCong_deja_pris());
//	    	typeconge.setCong_restant(typeConge1.getCong_restant());
	    	typeconge.setConges(typeConge1.getConges());
	    	
	          
	      
	           
	      return new ResponseEntity<>(typecongeRepository.save(typeconge), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
}
