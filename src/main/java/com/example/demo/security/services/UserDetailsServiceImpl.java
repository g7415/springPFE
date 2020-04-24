package com.example.demo.security.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.SalarieRepository;
//import com.example.demo.dao.UserRepository;
import com.example.demo.entities.Salarie;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

 @Autowired
 SalarieRepository userRepository;

 @Override
 @Transactional
 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	  Salarie salarie = userRepository.findByUsername(username).orElseThrow(
       () -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));

   return UserPrinciple.build(salarie);
 }
}