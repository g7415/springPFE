package com.example.demo.Controlleur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Exception.ResourceNotFoundException;
import com.example.demo.dao.RoleRepository;
import com.example.demo.dao.SalarieRepository;
//import com.example.demo.dao.UserRepository;
import com.example.demo.entities.Role;
import com.example.demo.entities.RoleName;
import com.example.demo.entities.Salarie;
import com.example.demo.entities.TypeConge;
import com.example.demo.message.request.SignUpForm;
import com.example.demo.message.response.ResponseMessage;
import com.example.demo.security.jwt.JwtProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class SalarieControlleur {
	  @Autowired
	  AuthenticationManager authenticationManager;
 
	  @Autowired
	  RoleRepository roleRepository;
	 
	  @Autowired
	  PasswordEncoder encoder;
	 
	  @Autowired
	  JwtProvider jwtProvider;
	  @Autowired
	  SalarieRepository salarieRepository;
	  
//		@GetMapping("/manager")
//		  public  List<Salarie> getAllManager() {
//		    System.out.println("Get all manager...");
//		    List<Salarie>salaries= salarieRepository.findAll();
//			for(Salarie salarie : salaries)
//			{
//				Optional<Role> managerRole=roleRepository.findByName(RoleName.ROLE_MANAGER);
//		    if(salarie.getRoles().equals(managerRole)) 
//		    	 System.out.println("Get all manager..."+ salarie.getUsername());
//		    }
//		    return salaries;
//		}
		
	  @GetMapping("/manager")
		public List<Salarie> getAllManager() {
			
			List<Salarie> salaries =salarieRepository.getManagerList(RoleName.ROLE_MANAGER);
			return salaries;
		}
	  
	  
	@GetMapping("/sal")
	  public List<Salarie> getAllSalaries() {
	    System.out.println("Get all Salaries...");
	    List<Salarie> Salaries = new ArrayList<>();
	    salarieRepository.findAll().forEach(Salaries::add);
	    return Salaries;
	  }
	
	@GetMapping("/roles")
	  public List<Role> getAllRoles() {
	    System.out.println("Get all Salaries...");
	    List<Role> Roles = new ArrayList<>();
	    roleRepository.findAll().forEach(Roles::add);
	    return Roles;
	  }
	
	

	@GetMapping("/sal/{id}")
	public ResponseEntity<Salarie> getSalarieById(@PathVariable(value = "id") Long SalarieId)
			throws ResourceNotFoundException {
		Salarie Salarie = salarieRepository.findById(SalarieId)
		.orElseThrow(() -> new ResourceNotFoundException("Salarie not found for this id :: " + SalarieId));
		return ResponseEntity.ok().body(Salarie);
	}
	
	@GetMapping("/salarie/{username}")
	public ResponseEntity<Salarie> getSalarieByUsername(@PathVariable(value = "username") String username)
			throws ResourceNotFoundException {
		Salarie Salarie = salarieRepository.findByUsername(username)
		.orElseThrow(() -> new ResourceNotFoundException("Salarie not found for this id :: " + username));
		return ResponseEntity.ok().body(Salarie);
	}

//	 @PreAuthorize(" hasRole('RH')")
//	@PostMapping("/sal")
//	public Salarie createSalarie(@Valid @RequestBody Salarie Salarie) {
//		return salarieRepository.save(Salarie);
//	}
	

	 @PostMapping("/sal")
	 public ResponseEntity<Salarie> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
		 
	
	   if (salarieRepository.existsByUsername(signUpRequest.getUsername())) {
			throw new RuntimeException("Fail -> Username is already taken!");
	   }

	   if (salarieRepository.existsByMail(signUpRequest.getMail())) {
		   throw new RuntimeException("Fail -> Email is already in use!");  
	   }

	   // Creating user's account
	   Salarie salarie = new Salarie(signUpRequest.getNom(),signUpRequest.getPrenom(),signUpRequest.getSolde_conge(),  signUpRequest.getDate_entree(),
	   signUpRequest.getGrade(),  signUpRequest.getMail(),  signUpRequest.getNum_tel(),  signUpRequest.getNom_responsable(),  signUpRequest.getGroupe(),
	   signUpRequest.getUsername(),encoder.encode(signUpRequest.getPassword()));
	  // System.out.println(signUpRequest.getRole());

	   Set<String> strRoles = signUpRequest.getRoles();
	   Set<Role> roles = new HashSet<>();

	   strRoles.forEach(role -> {
	     switch (role) {
	     case "ROLE_MANAGER":
	       Role managerRole = roleRepository.findByName(RoleName.ROLE_MANAGER)
	           .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
	       roles.add(managerRole);

	       break;
	     case "ROLE_RH":
	       Role rhRole = roleRepository.findByName(RoleName.ROLE_RH)
	           .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
	       roles.add(rhRole);

	       break;
	     default:
	       Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
	           .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
	       roles.add(userRole);
	     }
	   });

	   salarie.setRoles(roles);
	   
	  Optional<Salarie> usrData = salarieRepository.findByUsername(signUpRequest.getManager());
      Role managerRole = roleRepository.findByName(RoleName.ROLE_MANAGER)	                
   			.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));

       if(usrData.isPresent() ) {

       	if(usrData.get().getRoles().contains(managerRole) ) {
    			salarie.setManager(usrData.get());
       	}
       else {
       	 ResponseEntity.badRequest().body("Thanks to set a correct username for manager!");
       } 
       }
	   
//	   EmailSender emailSend = new EmailSender();
//	   try {
//		emailSend.sendMail(salarie);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.print("erreur");
//		}
	
	   return new ResponseEntity<>(salarieRepository.save(salarie), HttpStatus.OK);
	 }
	
	@PreAuthorize("hasRole('RH')")
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
	  
	  @PreAuthorize("hasRole('RH')")
	  @DeleteMapping("/sal/delete")
	  public ResponseEntity<String> deleteAllSalaries() {
	    System.out.println("Delete All Salaries...");
	    salarieRepository.deleteAll();
	 
	    return new ResponseEntity<>("All Salaries have been deleted!", HttpStatus.OK);
	  }
	 
//	 @PutMapping("/salarie/{username}")
//	 public ResponseEntity<Salarie> changePassword(@PathVariable("username") String username, @RequestBody Salarie salarie1){
//		 Optional<Salarie> s = salarieRepository.findByUsername(username);		 
//		    if (s.isPresent()) {
//		    	Salarie salarie=new Salarie();
//		    	salarie.setPassword(encoder.encode(salarie1.getPassword()));
//		      return new ResponseEntity<>(salarieRepository.save(salarie), HttpStatus.OK);
//		    } else {
//		      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		    }
//	 }
	 
//	 @PreAuthorize(" hasRole('RH')")
	  @PutMapping("/sal/{id}")
	  public ResponseEntity<Salarie> updateSalarie(@PathVariable("id") long id, @RequestBody SignUpForm salarie1) {
	    System.out.println("Update Salarie with ID = " + id + "...");
	 
	    Optional<Salarie> CarteInfo = salarieRepository.findById(id);
		 
	    if (CarteInfo.isPresent()) {
	    	Salarie salarie = CarteInfo.get();
//	    	Salarie salarie=new Salarie();
//	    	salarie.setId(id);
	    	salarie.setNom(salarie1.getNom());
	    	salarie.setPrenom(salarie1.getPrenom());
	    	salarie.setGroupe(salarie1.getGroupe());
	    	salarie.setDate_entree(salarie1.getDate_entree());
	    	salarie.setGrade(salarie1.getGrade());
	    	salarie.setMail(salarie1.getMail());
	    	salarie.setNum_tel(salarie1.getNum_tel());
	    	salarie.setNom_responsable(salarie1.getNom_responsable());
	    	salarie.setUsername(salarie1.getUsername());
//	    	String encoded = new BCryptPasswordEncoder().encode(salarie1.getPassword());
//	    	salarie.setPassword(encoded);
	    	salarie.setPassword(encoder.encode(salarie1.getPassword()));
	    	salarie.setSolde_conge(salarie1.getSolde_conge());
	    
	    	
	    	 Optional<Salarie> usrData = salarieRepository.findByUsername(salarie1.getManager());
	    	   	Role managerRole1 = roleRepository.findByName(RoleName.ROLE_MANAGER)	                
	    	   			.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));

	    	       if(usrData.isPresent() ) {

	    	       	if(usrData.get().getRoles().contains(managerRole1) ) {
	    	    			salarie.setManager(usrData.get());
	    	       	}
	    	       }else {
	    	       	 ResponseEntity.badRequest().body("Thanks to set a correct username for manager!");
	    	       }
	    	
	    	 Set<String> strRoles = salarie1.getRoles();
	  	   Set<Role> roles = new HashSet<>();

	  	   strRoles.forEach(role -> {
	  	     switch (role) {
	  	     case "ROLE_MANAGER":
	  	       Role managerRole = roleRepository.findByName(RoleName.ROLE_MANAGER)
	  	           .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
	  	       roles.add(managerRole);

	  	       break;
	  	     case "ROLE_RH":
	  	       Role rhRole = roleRepository.findByName(RoleName.ROLE_RH)
	  	           .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
	  	       roles.add(rhRole);

	  	       break;
	  	     default:
	  	       Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
	  	           .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
	  	       roles.add(userRole);
	  	     }
	  	   });

	  	   salarie.setRoles(roles);
	    	
//	  	  EmailSender emailSend = new EmailSender();
//		   try {
//			emailSend.sendMail(salarie);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	    	
	      return new ResponseEntity<>(salarieRepository.save(salarie), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	  
	  
	  @PutMapping("/resetPassword/{username}")
	  public ResponseEntity<Salarie> reinitialiseMdp(@PathVariable("username") String username, @RequestBody Salarie salarie) {
	    System.out.println("Update Conge with ID = " + username + "...");
	 
	    Optional<Salarie> CarteInfo = salarieRepository.findByUsername(username);
		 
	    if (CarteInfo.isPresent()) {
	    	Salarie typeconge = CarteInfo.get();
	    	typeconge.setUsername(salarie.getUsername());
	    	typeconge.setPassword(encoder.encode(salarie.getPassword()));
	      return new ResponseEntity<>(salarieRepository.save(typeconge), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	  @PutMapping("/changePassword/{username}")
	  public ResponseEntity<Salarie> updatePassword(@PathVariable("username") String username, @RequestBody Salarie salarie) {
	    System.out.println("Update Conge with ID = " + username + "...");
	 
	    Optional<Salarie> CarteInfo = salarieRepository.findByUsername(username);
		 
	    if (CarteInfo.isPresent()) {
	    	Salarie typeconge = CarteInfo.get();
	    	typeconge.setPassword(encoder.encode(salarie.getPassword()));
	      return new ResponseEntity<>(salarieRepository.save(typeconge), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	  @PutMapping("/updateProfil/{id}")
	  public ResponseEntity<Salarie> updateProfil(@PathVariable("id") Long id, @RequestBody Salarie salarie) {
	    System.out.println("Update Conge with ID = " + id + "...");
	 
	    Optional<Salarie> CarteInfo = salarieRepository.findById(id);
		 
	    if (CarteInfo.isPresent()) {
	    	Salarie typeconge = CarteInfo.get();
	    	typeconge.setNom(salarie.getNom());
	    	typeconge.setPrenom(salarie.getPrenom());
	    	typeconge.setMail(salarie.getMail());
	    	typeconge.setNum_tel(salarie.getNum_tel());

	      return new ResponseEntity<>(salarieRepository.save(typeconge), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	  
}
