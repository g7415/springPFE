//package com.example.demo.Controlleur;
//
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@CrossOrigin(origins = "http://localhost:4200")
//@RestController
//public class TestRestAPIs {
// 
// @GetMapping("/api/test/user")
// @PreAuthorize("hasRole('USER') or hasRole('MANAGER')")
// public String userAccess() {
//   return ">>> User Contents!";
// }
//
// @GetMapping("/api/test/rh")
// @PreAuthorize("hasRole('RH') or hasRole('USER')")
// public String projectManagementAccess() {
//   return ">>> Project RH Board";
// }
// 
// @GetMapping("/api/test/manager")
// @PreAuthorize("hasRole('MANAGER')")
// public String adminAccess() {
//   return ">>> Admin Contents";
// }
//}