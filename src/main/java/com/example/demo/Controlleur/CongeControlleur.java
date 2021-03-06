package com.example.demo.Controlleur;

import java.util.Date;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.Exception.ResourceNotFoundException;
import com.example.demo.dao.CongeRepository;
import com.example.demo.dao.SalarieRepository;
import com.example.demo.entities.Conge;
import com.example.demo.entities.Salarie;
import com.example.demo.entities.TypeConge;
import com.example.demo.message.response.ResponseMessage;
import com.google.common.net.HttpHeaders;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CongeControlleur {
	@Autowired
	private CongeRepository congeRepository;
	@Autowired
	private SalarieRepository salarieRepository;
	
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
	@GetMapping("/statAccepter")
	  public  Long getStatistiqueNbConAcc() {
		Long nbConAcc =congeRepository.getStatistiqueConAcc();
			return nbConAcc;
		}
	@GetMapping("/statRefuser")
	  public  Long getStatistiqueNbConRefu() {
		Long nbConRefu =congeRepository.getStatistiqueConRefu();
			return nbConRefu;
		}
	
	@GetMapping("/statEnAttente")
	  public  Long getStatistiqueNbConEnAttente() {
		Long nbConEnAtt =congeRepository.getStatistiqueConEnAttente();
			return nbConEnAtt;
		}
	@GetMapping("/conStatut")
	  public List<Conge> getAllCongesByStatut() {
	    System.out.println("Get all Conges...");
		  List<Conge> conges = congeRepository.getListConByStatut("accepter");
	 	    return conges;
	  }
	
	  @GetMapping("/conid/{id}")
	  public List<Conge> getCongeByIdSal(@PathVariable(value = "id") Long id)
				throws ResourceNotFoundException {
		  List<Conge> conges = congeRepository.getCongeByIdSal(id);
		  return conges;
			}
	  
	  @GetMapping("/conAccepid1/{id}/{date_debut}")
	  public Conge getCongeAccepterByIdSal12(@PathVariable(value = "id") Long id,@PathVariable(value = "date_debut") String date_debut)
			  throws ParseException {
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		  Date date = formatter.parse(date_debut);
		  String formattedDate = formatter.format(date);
		  Conge conges =congeRepository.getCongeAccepterByIdSal1(id,formattedDate);
				return conges;
			}
	  
	  @GetMapping("/conAccepid/{id}")
	  public List<Conge> getCongeAccepterByIdSal(@PathVariable(value = "id") Long id)
				throws ResourceNotFoundException {
		  List<Conge> conges = congeRepository.getCongeAccepterByIdSal(id);
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
	
	
	
	@GetMapping("/existe/{id_type}")
	  public  Long Existe(@PathVariable(value = "id_type") Long id_type)
	{
		Long nb =congeRepository.Existe(id_type);
			return nb;
		}
	
	@PostMapping("/con/{id}")
	public  ResponseEntity<Object> createConge(@Valid @RequestBody Conge conge,@PathVariable(value = "id") Long id)  {
		Salarie salarie = salarieRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException());
			    conge.setSalarie(salarie);	
			    Long existe= Existe(conge.getTypeconge().getId_type());
			
		        
			    if(conge.getTypeconge().getId_type()==9) {	
			  		double a = salarie.getSolde_conge();
			  	if(conge.getDuree() <= a)
			  	{ 
			  		return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
			    }
			  	else {
			  		 Map<String, Object> body = new LinkedHashMap<>();
				        body.put("message", "Solde insuffisant !");
			  	      return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
			  	    }
			  	  }  else if (conge.getTypeconge().getId_type()==19) {
			  		
			  		List<Conge> conges= congeRepository.getCongeAccepterByIdSal(id); 
			  		if(conges.size() == 0)
			  		{
			  			return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
			  		}
			  		for(Conge conge1 : conges){
					  	  Date dateConge1 = conge1.getDate_debut();
				  	    String strDateFormatConge1 = "yyyy/MM";
				  	    DateFormat dateFormatConge1 = new SimpleDateFormat(strDateFormatConge1);
				  	    String formattedDateConge1= dateFormatConge1.format(dateConge1);
				  	    
				  	    Date dateConge = conge.getDate_debut();
				  	    String strDateFormatConge = "yyyy/MM";
				  	    DateFormat dateFormatConge = new SimpleDateFormat(strDateFormatConge);
				  	    String formattedDateConge= dateFormatConge.format(dateConge);
				  	    
				  	    if(formattedDateConge1.equals(formattedDateConge)) {
				  	    	 Map<String, Object> body = new LinkedHashMap<>();
						        body.put("message", "impossible! vous avez deja pris vos 2h ce mois-ci ");
					  	      return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
//						        continue;
				  	    } else 
				  	    {
				  	    	return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
				  	    }
				  	   }
			  		 Map<String, Object> body = new LinkedHashMap<>();
				        body.put("message", "impossible! vous avez deja pris vos 2h ce mois-ci ");
			  		return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
			  	  }
 
//			  		Date date = new Date();
//			  	    String strDateFormat = "yyyy/MM";
//			  	    DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
//			  	    String formattedDate= dateFormat.format(date);
//			  	    
//			  	    Date dateConge = conge.getDate_debut();
//			  	    String strDateFormatConge = "yyyy/MM";
//			  	    DateFormat dateFormatConge = new SimpleDateFormat(strDateFormatConge);
//			  	    String formattedDateConge= dateFormatConge.format(dateConge);
//			  	    if(formattedDate.equals(formattedDateConge))
//			  	    {
//			  	    	 Map<String, Object> body = new LinkedHashMap<>();
//					        body.put("message", "impossible! vous avez deja pris vos 2h ce mois-ci ");
//				  	      return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
//			  	    } else 
//			  	    {
//			  	    	return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
//			  	    }
			  		
			  	   
			  	  else if (conge.getTypeconge().getId_type()==13) {
			  		if(conge.getDuree() <= 3)
				  	{ 
				  		return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
				    }
				  	else {
				  		 Map<String, Object> body = new LinkedHashMap<>();
					        body.put("message", "Vous avez droit a 3 jours de congé seulement !");
				  	      return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
				  	    }
			  		  
			  	  }else if (conge.getTypeconge().getId_type()==14) {
				  		if(conge.getDuree() <= 2)
					  	{ 
					  		return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
					    }
					  	else {
					  		 Map<String, Object> body = new LinkedHashMap<>();
						        body.put("message", "Vous avez droit a 2 jours de congé seulement !");
					  	      return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
					  	    }
			  	  }else if (conge.getTypeconge().getId_type()==15) {
				  		if(conge.getDuree() <= 1)
					  	{ 
					  		return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
					    }
					  	else {
					  		 Map<String, Object> body = new LinkedHashMap<>();
						        body.put("message", "Vous avez droit a 1 jours de congé seulement !");
					  	      return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
					  	    }
			  	 }else if (conge.getTypeconge().getId_type()==14) {
				  		if(conge.getDuree() <= 2)
					  	{ 
					  		return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
					    }
					  	else {
					  		 Map<String, Object> body = new LinkedHashMap<>();
						        body.put("message", "Vous avez droit a 2 jours de congé seulement !");
					  	      return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
					  	    }
			  	  }else if (conge.getTypeconge().getId_type()==16) {
				  		if(conge.getDuree() <= 1)
					  	{ 
					  		return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
					    }
					  	else {
					  		 Map<String, Object> body = new LinkedHashMap<>();
						        body.put("message", "Vous avez droit a 1 jours de congé seulement !");
					  	      return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
					  	    }
			  	  }else if (conge.getTypeconge().getId_type()==17) {
				  		if(conge.getDuree() <= 3)
					  	{ 
					  		return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
					    }
					  	else {
					  		 Map<String, Object> body = new LinkedHashMap<>();
						        body.put("message", "Vous avez droit a 3 jours de congé seulement !");
					  	      return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
					  	    }
			  	  }else if (conge.getTypeconge().getId_type()==18) {
				  		if(conge.getDuree() <= 2)
					  	{ 
					  		return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
					    }
					  	else {
					  		 Map<String, Object> body = new LinkedHashMap<>();
						        body.put("message", "Vous avez droit a 2 jours de congé seulement !");
					  	      return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
					  	    }
			  	  }
			    
			  	else{
			    return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
			    }
				


	}
	
	  @PutMapping("/con/{num}/{id}")
	  public ResponseEntity<Object> updateConge(@PathVariable("num") long num, @RequestBody Conge conge1,@PathVariable(value = "id") Long id) {
	    System.out.println("Update Conge with ID = " + num + "...");
	    Salarie salarie = salarieRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException());
	    conge1.setSalarie(salarie);	
	    Optional<Conge> CarteInfo = congeRepository.findById(num);
		 
	    if (CarteInfo.isPresent()) {
	    	  if(conge1.getTypeconge().getId_type()==9) {	
			  		double a = salarie.getSolde_conge();
			  	if(conge1.getDuree() < a)
			  	{ Conge conge = CarteInfo.get();
		    	conge.setDate_debut(conge1.getDate_debut());
		    	conge.setDate_fin(conge1.getDate_fin());
		    	conge.setDuree(conge1.getDuree());
		    	conge.setStatut(conge1.getStatut());
		    	conge.setSalarie(conge1.getSalarie());
		    	conge.setTypeconge(conge1.getTypeconge());
			  		return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
			    }
			  	else {
			  		 Map<String, Object> body = new LinkedHashMap<>();
				        body.put("message", "Solde insuffisant !");
			  	      return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
			  	    }
			  	  }
	    	  
	    	  else if (conge1.getTypeconge().getId_type()==19) {
			  		
			  		List<Conge> conges= congeRepository.getCongeAccepterByIdSal(id); 
			  		if(conges.size() == 0)
			  		{
			  			return new ResponseEntity<>(congeRepository.save(conge1), HttpStatus.OK);
			  		}
			  		for(Conge conge2 : conges){
					  	  Date dateConge2 = conge2.getDate_debut();
				  	    String strDateFormatConge2 = "yyyy/MM";
				  	    DateFormat dateFormatConge2 = new SimpleDateFormat(strDateFormatConge2);
				  	    String formattedDateConge2= dateFormatConge2.format(dateConge2);
				  	    
				  	    Date dateConge = conge1.getDate_debut();
				  	    String strDateFormatConge1 = "yyyy/MM";
				  	    DateFormat dateFormatConge1 = new SimpleDateFormat(strDateFormatConge1);
				  	    String formattedDateConge1= dateFormatConge1.format(dateConge);
				  	    
				  	    if(formattedDateConge2.equals(formattedDateConge1)) {
				  	    	 Map<String, Object> body = new LinkedHashMap<>();
						        body.put("message", "impossible! vous avez deja pris vos 2h ce mois-ci ");
					  	      return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
//						        continue;
				  	    } else 
				  	    {
				  	    	 Conge conge = CarteInfo.get();
						    	conge.setDate_debut(conge1.getDate_debut());
						    	conge.setDate_fin(conge1.getDate_fin());
						    	conge.setDuree(conge1.getDuree());
						    	conge.setStatut(conge1.getStatut());
						    	conge.setSalarie(conge1.getSalarie());
						    	conge.setTypeconge(conge1.getTypeconge());
				  	    	return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
				  	    	
				  	    }
				  	   }
			  		 Map<String, Object> body = new LinkedHashMap<>();
				        body.put("message", "impossible! vous avez deja pris vos 2h ce mois-ci ");
			  		return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
			  	  }

//	    	  else if (conge1.getTypeconge().getId_type()==19) {
//				  		Date date = new Date();
//				  	    String strDateFormat = "yyyy/MM";
//				  	    DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
//				  	    String formattedDate= dateFormat.format(date);
//				  	    
//				  	    Date dateConge = conge1.getDate_debut();
//				  	    String strDateFormatConge = "yyyy/MM";
//				  	    DateFormat dateFormatConge = new SimpleDateFormat(strDateFormatConge);
//				  	    String formattedDateConge= dateFormat.format(dateConge);
//				  	    if(formattedDate.equals(formattedDateConge))
//				  	    {
//				  	    	 Map<String, Object> body = new LinkedHashMap<>();
//						        body.put("message", "impossible! vous avez deja pris vos 2h ce mois-ci ");
//					  	      return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
//				  	    } else 
//				  	    {
//				  	    	 Conge conge = CarteInfo.get();
//						    	conge.setDate_debut(conge1.getDate_debut());
//						    	conge.setDate_fin(conge1.getDate_fin());
//						    	conge.setDuree(conge1.getDuree());
//						    	conge.setStatut(conge1.getStatut());
//						    	conge.setSalarie(conge1.getSalarie());
//						    	conge.setTypeconge(conge1.getTypeconge());
//				  	    	return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
//				  	    }
//				  	   }
			  	  else if (conge1.getTypeconge().getId_type()==13) {
					  		if(conge1.getDuree() <= 3)
						  	{ 
					  			Conge conge = CarteInfo.get();
						    	conge.setDate_debut(conge1.getDate_debut());
						    	conge.setDate_fin(conge1.getDate_fin());
						    	conge.setDuree(conge1.getDuree());
						    	conge.setStatut(conge1.getStatut());
						    	conge.setSalarie(conge1.getSalarie());
						    	conge.setTypeconge(conge1.getTypeconge());
						  		return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
						    }
						  	else {
						  		 Map<String, Object> body = new LinkedHashMap<>();
							        body.put("message", "Vous avez droit a 3 jours de congé seulement !");
						  	      return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
						  	    }
					  		  
					  	  }else if (conge1.getTypeconge().getId_type()==14) {
						  		if(conge1.getDuree() <= 2)
							  	{ 
						  			Conge conge = CarteInfo.get();
							    	conge.setDate_debut(conge1.getDate_debut());
							    	conge.setDate_fin(conge1.getDate_fin());
							    	conge.setDuree(conge1.getDuree());
							    	conge.setStatut(conge1.getStatut());
							    	conge.setSalarie(conge1.getSalarie());
							    	conge.setTypeconge(conge1.getTypeconge());
							  		return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
							    }
							  	else {
							  		 Map<String, Object> body = new LinkedHashMap<>();
								        body.put("message", "Vous avez droit a 2 jours de congé seulement !");
							  	      return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
							  	    }
					  	  }else if (conge1.getTypeconge().getId_type()==15) {
						  		if(conge1.getDuree() <= 1)
							  	{   
						  			Conge conge = CarteInfo.get();
							    	conge.setDate_debut(conge1.getDate_debut());
							    	conge.setDate_fin(conge1.getDate_fin());
							    	conge.setDuree(conge1.getDuree());
							    	conge.setStatut(conge1.getStatut());
							    	conge.setSalarie(conge1.getSalarie());
							    	conge.setTypeconge(conge1.getTypeconge());
							  		return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
							    }
							  	else {
							  		 Map<String, Object> body = new LinkedHashMap<>();
								        body.put("message", "Vous avez droit a 1 jours de congé seulement !");
							  	      return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
							  	    }
					  	 }else if (conge1.getTypeconge().getId_type()==16) {
						  		if(conge1.getDuree() <= 1)
							  	{   
						  			Conge conge = CarteInfo.get();
							    	conge.setDate_debut(conge1.getDate_debut());
							    	conge.setDate_fin(conge1.getDate_fin());
							    	conge.setDuree(conge1.getDuree());
							    	conge.setStatut(conge1.getStatut());
							    	conge.setSalarie(conge1.getSalarie());
							    	conge.setTypeconge(conge1.getTypeconge());
							  		return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
							    }
							  	else {
							  		 Map<String, Object> body = new LinkedHashMap<>();
								        body.put("message", "Vous avez droit a 1 jours de congé seulement !");
							  	      return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
							  	    }
					  	  }else if (conge1.getTypeconge().getId_type()==17) {
						  		if(conge1.getDuree() <= 3)
							  	{ 
						  			Conge conge = CarteInfo.get();
							    	conge.setDate_debut(conge1.getDate_debut());
							    	conge.setDate_fin(conge1.getDate_fin());
							    	conge.setDuree(conge1.getDuree());
							    	conge.setStatut(conge1.getStatut());
							    	conge.setSalarie(conge1.getSalarie());
							    	conge.setTypeconge(conge1.getTypeconge());
							  		return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
							    }
							  	else {
							  		 Map<String, Object> body = new LinkedHashMap<>();
								        body.put("message", "Vous avez droit a 3 jours de congé seulement !");
							  	      return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
							  	    }
					  	  }else if (conge1.getTypeconge().getId_type()==18) {
						  		if(conge1.getDuree() <= 2)
							  	{ 
						  			Conge conge = CarteInfo.get();
							    	conge.setDate_debut(conge1.getDate_debut());
							    	conge.setDate_fin(conge1.getDate_fin());
							    	conge.setDuree(conge1.getDuree());
							    	conge.setStatut(conge1.getStatut());
							    	conge.setSalarie(conge1.getSalarie());
							    	conge.setTypeconge(conge1.getTypeconge());
							  		return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
							    }
							  	else {
							  		 Map<String, Object> body = new LinkedHashMap<>();
								        body.put("message", "Vous avez droit a 2 jours de congé seulement !");
							  	      return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
							  	    }
					  	  }
	    	  
	    	else {
	    	Conge conge = CarteInfo.get();
	    	conge.setDate_debut(conge1.getDate_debut());
	    	conge.setDate_fin(conge1.getDate_fin());
	    	conge.setDuree(conge1.getDuree());
	    	conge.setStatut(conge1.getStatut());
	    	conge.setSalarie(conge1.getSalarie());
	    	conge.setTypeconge(conge1.getTypeconge());
	               
	      return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
			  	  }
	    	  }
	    else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	
//	@PostMapping("/con1")
//	public ResponseEntity<Conge> createConge1(@Valid @RequestBody Conge conge) {
//		System.out.print(conge);
//		if(conge.getTypeconge().getId_type()==10) {	
//		double a = conge.getSalarie().getSolde_conge();
//	if(conge.getDuree() < a)
//	{ 
//		return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
//    } 
//	else {
//	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	    }
//	  }  
//	else{
//	System.out.print(conge);
//	return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
//}
//		} 
		


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
	 
	

//	  @PutMapping("/con/{num}")
//	  public ResponseEntity<Conge> updateConge(@PathVariable("num") long num, @RequestBody Conge conge1) {
//	    System.out.println("Update Conge with ID = " + num + "...");
//	 
//	    Optional<Conge> CarteInfo = congeRepository.findById(num);
//		 
//	    if (CarteInfo.isPresent()) {
//	    	Conge conge = CarteInfo.get();
//	    	conge.setDate_debut(conge1.getDate_debut());
//	    	conge.setDate_fin(conge1.getDate_fin());
//	    	conge.setDuree(conge1.getDuree());
//	    	conge.setStatut(conge1.getStatut());
//	    	conge.setSalarie(conge1.getSalarie());
//	    	conge.setTypeconge(conge1.getTypeconge());
//	               
//	      return new ResponseEntity<>(congeRepository.save(conge), HttpStatus.OK);
//	    } else {
//	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	    }
//	  }
	  
	  
	  @PutMapping("/conAccep/{num}")
	  public ResponseEntity<Conge> accepterDemande(@PathVariable("num") long num,@RequestBody Conge conge1) {
	    System.out.println("Update Conge with ID = " + num + "...");
	    Optional<Conge> c = congeRepository.findById(num);
	   
//	    double nbjourenfctsoldeconge=((conge1.getDuree()*1.7)/1.5);
//		double soldeCongeSalarie = conge1.getSalarie().getSolde_conge();
//	    if (CarteInfo.isPresent()) {
//	    	Conge conge = CarteInfo.get();
//	    	if(conge1.getTypeconge().getId_type()==10) {
//	    	if ((nbjourenfctsoldeconge < soldeCongeSalarie)) {
//	    	double nvSoldeConge =conge1.getSalarie().getSolde_conge()-nbjourenfctsoldeconge;
//	    	conge.getSalarie().setSolde_conge(nvSoldeConge);
//	    	conge.setStatut("accepter");
//	    	}else {
//	    		conge.setStatut("refuser - solde insuffisant");
//	    	}
//	    	}else {
//	    	conge.setStatut("accepter");}
		 
	    if (c.isPresent()) {
	    	Conge conge = c.get();
	    	 if(conge1.getTypeconge().getId_type()==10) {
		    	 double nbjour=conge1.getDuree();
		    	double soldeCongeSalarie = conge1.getSalarie().getSolde_conge();
		    	double nvSoldeConge =soldeCongeSalarie-nbjour;
		    	conge.getSalarie().setSolde_conge(nvSoldeConge);
		    	conge.setStatut("accepter");
		    }
	    	conge.setStatut("accepter");
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
	  
	
	   
	  
	  @GetMapping("/SumCongePris/{username}")
	  public ResponseEntity<Object>  getSumCongePris(@PathVariable(value = "username") String username)
				throws ResourceNotFoundException {
		  List <Object> conges = congeRepository.getSumCongePris(username);
		
		
			  Map<String, Object> body = new LinkedHashMap<>();
		        body.put("resultat", conges);
		  
		  return new ResponseEntity<Object>(body,HttpStatus.OK);

	        }
			

	  
//	  @GetMapping("/SumCongeRestant/{username}")
//	  public List<Object[]> getSumCongeRestant(@PathVariable(value = "username") String username)
//				throws ResourceNotFoundException {
//		  List<Object[]> conges = congeRepository.getSumCongeRestant(username);
//		  return conges;
//			}
	  
}
