package com.example.demo.Controlleur;

import java.sql.Date;
import java.text.SimpleDateFormat;
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
import com.example.demo.dao.CongeRepository;
import com.example.demo.entities.Conge;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CongeControlleur {
	@Autowired
	private CongeRepository congeRepository;

	  @GetMapping("/ConByManager/{id}")
			public List<Conge> getListConByManager(@PathVariable("id") long id) {
//				Long id = conge.getSalarie().getManager().getId();
				List<Conge> conge1 =congeRepository.getCongeListByManager(id);
				return conge1;
			}
	
	@GetMapping("/con")
	  public List<Conge> getAllConges() {
	    System.out.println("Get all Conges...");
	 
	    List<Conge> Conges = new ArrayList<>();
	    congeRepository.findAll().forEach(Conges::add);
	 
	    return Conges;
	  }

	  @GetMapping("/conid/{id}")
	  public List<Conge> getCongeByIdSal(@PathVariable(value = "id") Long id)
				throws ResourceNotFoundException {
		  List<Conge> conges = congeRepository.getCongeByIdSal(id);
		  return conges;
			}
	  @GetMapping("/conusername/{username}")
	  public List<Conge> getCongeByIdSal(@PathVariable(value = "username") String username)
				throws ResourceNotFoundException {
		  List<Conge> conges = congeRepository.getCongeByUsernameSal(username);
		  return conges;
			}
	
	@GetMapping("/con/{num}")
	public ResponseEntity<Conge> getCongeById(@PathVariable(value = "num") Long num)
			throws ResourceNotFoundException {
		Conge Conge = congeRepository.findById(num)
				.orElseThrow(() -> new ResourceNotFoundException("Conge not found for this num :: " + num));
		return ResponseEntity.ok().body(Conge);
	}
	
	@PostMapping("/con")
	public Conge createConge(@Valid @RequestBody Conge conge) {
		System.out.print(conge);
		return congeRepository.save(conge);
	}
	

//	@PostMapping("/con")
//	public ResponseEntity<Conge> createConge1(@Valid @RequestBody Conge conge) {
//		System.out.print(conge);
//		double nbjourenfctsoldeconge=((conge.getDuree()*1.7)/2.5);
//		double a = conge.getSalarie().getSolde_conge();
//	if(nbjourenfctsoldeconge < a)
//	{ 
//		return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
//    } 
//	else {
//	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	    }
//	  }  
		


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
	  public ResponseEntity<Conge> updateConge(@PathVariable("num") long num, @RequestBody Conge conge1) {
	    System.out.println("Update Conge with ID = " + num + "...");
	 
	    Optional<Conge> CarteInfo = congeRepository.findById(num);
		 
	    if (CarteInfo.isPresent()) {
	    	Conge conge = CarteInfo.get();
	    	conge.setDate_debut(conge1.getDate_debut());
	    	conge.setDate_fin(conge1.getDate_fin());
	    	conge.setDuree(conge1.getDuree());
	    	conge.setStatut(conge1.getStatut());
	    	conge.setSalarie(conge1.getSalarie());
	    	conge.setTypeconge(conge1.getTypeconge());
	               
	      return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	  
	  
	  @PutMapping("/conAccep/{num}")
	  public ResponseEntity<Conge> accepterDemande(@PathVariable("num") long num,@RequestBody Conge conge1) {
	    System.out.println("Update Conge with ID = " + num + "...");
	    Optional<Conge> CarteInfo = congeRepository.findById(num);
	    
	    double nbjourenfctsoldeconge=((conge1.getDuree()*1.7)/2.5);
		double soldeCongeSalarie = conge1.getSalarie().getSolde_conge();
	    if (CarteInfo.isPresent()) {
	    	Conge conge = CarteInfo.get();
	    	if(conge1.getTypeconge().getId_type()==5) {
	    	if ((nbjourenfctsoldeconge < soldeCongeSalarie)) {
	    	double nvSoldeConge =conge1.getSalarie().getSolde_conge()-nbjourenfctsoldeconge;
	    	conge.getSalarie().setSolde_conge(nvSoldeConge);
	    	conge.setStatut("accepter");
	    	}else {
	    		conge.setStatut("refuser - solde insuffisant");
	    	}
	    	}else {
	    	conge.setStatut("accepter");}
//		  	  EmailSender emailSend = new EmailSender();
//			   try {
//				emailSend.sendMail2(conge1);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//		  	 
//				}
	      return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
			} else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	    

	  }  
	  
	  @PutMapping("/conRefuser/{num}")
	  public ResponseEntity<Conge> refuserDemande(@PathVariable("num") long num,@RequestBody Conge conge1) {
	    System.out.println("Update Conge with ID = " + num + "...");
	 
	    Optional<Conge> CarteInfo = congeRepository.findById(num);
		 
	    if (CarteInfo.isPresent()) {
	    	Conge conge = CarteInfo.get();
	    	conge.setStatut("refuser");
	    	     
	      return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }   
	  
	  
	  
}
