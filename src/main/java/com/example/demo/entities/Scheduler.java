package com.example.demo.entities;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.dao.SalarieRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@Component
public class Scheduler {
	 private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);
	 private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	@Autowired
	SalarieRepository salarieRepository;
	@Scheduled(cron = "0 15 10 1 * ?")
//	@Scheduled(cron = "0 * * * * ?")
	public void scheduleTaskWithCronExpression() {
		double soldeActuelle=0;
		List<Salarie>salaries= salarieRepository.findAll();
		for(Salarie salarie : salaries)
		{
			  Date dateEntree = salarie.getDate_entree();
		  	    String strDateFormatDateEntree = "yyyy";
		  	    DateFormat dateFormatDateEntree = new SimpleDateFormat(strDateFormatDateEntree);
		  	    String formattedDateDateEntree= dateFormatDateEntree.format(dateEntree);
		  	  System.out.print("formattedDateDateEntree"+formattedDateDateEntree);
		  	    
		  	  Date date = new Date();
		  	    String strDateFormat = "yyyy";
		  	    DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		  	    String formattedDate= dateFormat.format(date);
		  	  System.out.print("formattedDate"+formattedDate);
		  	  
		  	  Long dateEnt = Long.parseLong(formattedDateDateEntree);
		  	Long dateAuj = Long.parseLong(formattedDate);
		  	    
		  	Long difference =(dateAuj-dateEnt);
		  	System.out.print("diff"+difference);
		 
			if(difference>=6) {
				soldeActuelle = salarie.getSolde_conge();
				salarie.setSolde_conge(soldeActuelle + 2.08);
				logger.info("le nouveau solde de congé est ", soldeActuelle + 2.08);
				salarieRepository.save(salarie);
			}else if (difference<6 && difference>3) {
				soldeActuelle = salarie.getSolde_conge();
				salarie.setSolde_conge(soldeActuelle + 1.92);
				logger.info("le nouveau solde de congé est ", soldeActuelle + 1.92);
				salarieRepository.save(salarie);
			}else if (difference<=3) {
		soldeActuelle = salarie.getSolde_conge();
		salarie.setSolde_conge(soldeActuelle + 1.7);
		logger.info("le nouveau solde de congé est ", soldeActuelle + 1.7);
		salarieRepository.save(salarie);
		}

		} 
	    logger.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
	}
	}