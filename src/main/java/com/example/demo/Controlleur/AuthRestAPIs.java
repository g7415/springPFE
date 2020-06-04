package com.example.demo.Controlleur;

import java.nio.charset.StandardCharsets;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.example.demo.dao.RoleRepository;
import com.example.demo.dao.SalarieRepository;
//import com.example.demo.dao.UserRepository;
import com.example.demo.entities.Role;
import com.example.demo.entities.RoleName;
import com.example.demo.entities.Salarie;
import com.example.demo.message.request.LoginForm;
import com.example.demo.message.request.SignUpForm;
import com.example.demo.message.response.JwtResponse;
import com.example.demo.message.response.ResponseMessage;
import com.example.demo.security.jwt.JwtProvider;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

 @Autowired
 AuthenticationManager authenticationManager;

 @Autowired
 SalarieRepository salarieRepository;

 @Autowired
 RoleRepository roleRepository;

 @Autowired
 PasswordEncoder encoder;

 @Autowired
 JwtProvider jwtProvider;
 
 @Autowired
 SpringTemplateEngine templateEngine;

 @Autowired
 private JavaMailSender sender;

 @PostMapping("/signin")
 public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

   Authentication authentication = authenticationManager.authenticate(
       new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

   SecurityContextHolder.getContext().setAuthentication(authentication);

   String jwt = jwtProvider.generateJwtToken(authentication);
   UserDetails userDetails = (UserDetails) authentication.getPrincipal();

   return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
 }

 @PostMapping("/signup")
 public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
   if (salarieRepository.existsByUsername(signUpRequest.getUsername())) {
     return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
         HttpStatus.BAD_REQUEST);
   }

   if (salarieRepository.existsByMail(signUpRequest.getMail())) {
     return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
         HttpStatus.BAD_REQUEST);
   }

  // Creating user's account
   Salarie salarie = new Salarie(signUpRequest.getNom(),signUpRequest.getPrenom(),signUpRequest.getSolde_conge(),  signUpRequest.getDate_entree(),
   		signUpRequest.getGrade(),  signUpRequest.getMail(),  signUpRequest.getNum_tel(),  signUpRequest.getNom_responsable(),  signUpRequest.getGroupe(),
			 signUpRequest.getUsername(),encoder.encode(signUpRequest.getPassword()));

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
   salarieRepository.save(salarie);
   return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
 }


 @PutMapping("/resetPassword/{username}")
 public ResponseEntity<Salarie> reinitialiseMdp(@PathVariable("username") String username,@Valid @RequestBody Salarie signUpRequest) {
   System.out.println("Update Conge with ID = " + username + "...");

   Optional<Salarie> CarteInfo = salarieRepository.findByUsername(username);
	 
   if (CarteInfo.isPresent()) {
   	Salarie typeconge = CarteInfo.get();
   	typeconge.setPassword(encoder.encode(signUpRequest.getPassword()));
  
     return new ResponseEntity<>(salarieRepository.save(typeconge), HttpStatus.OK);
   } else {
     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }
 }
 
 
 
 public static String alphaNumericString(int len) {
	    String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    Random rnd = new Random();

	    StringBuilder sb = new StringBuilder(len);
	    for (int i = 0; i < len; i++) {
	        sb.append(AB.charAt(rnd.nextInt(AB.length())));
	    }
	    return sb.toString();
	}
 @RequestMapping("/resetPassword")
 public @ResponseBody Salarie sendMailresetPassword(@RequestBody Salarie salarie) throws Exception {
	 Salarie user = salarieRepository.findByMail(salarie.getMail());
	    if (user == null) {
	        throw new UserPrincipalNotFoundException(null);
	    }
 MimeMessage message = sender.createMimeMessage();
 MimeMessageHelper helper = new MimeMessageHelper(message,
       MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
       StandardCharsets.UTF_8.name());

 Map<String, Object> model = new HashMap<String, Object>();
 //model.put("username",salarie.getUsername());
// String randomPass = alphaNumericString(10);
// model.put("password",randomPass);

// 	user.setPassword(encoder.encode(randomPass));
 	salarieRepository.save(user);
 
// 	 model.put("url","http://localhost:4200/auth/login");
 	 model.put("url","http://localhost:4200/reinitialiseMdp");
  	

// System.out.println(randomPass);
 Context context = new Context();
 context.setVariables(model);
 String html = templateEngine.process("email-resetPassword", context);

 try {
   helper.setTo(salarie.getMail());
   helper.setText(html,true);
   helper.setSubject("Test Mail");
 } catch (javax.mail.MessagingException e) {
   e.printStackTrace();
 }
 sender.send(message);
 return salarie;

 }


}