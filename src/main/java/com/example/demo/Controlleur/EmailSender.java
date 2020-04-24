//package com.example.demo.Controlleur;
//
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//import javax.mail.internet.MimeMessage;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.thymeleaf.context.Context;
//import org.thymeleaf.spring4.SpringTemplateEngine;
//
//import com.example.demo.entities.Salarie;
//
//import javassist.tools.Dump;
//
//
//@Service
//public class EmailSender {
////	 static char[] generatePassword(int length) {
////			System.out.println("your new password:");
////		String letters="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
////		String numbers="0123456789";
////		String splChar="!@#$%^&*";
////		
////		String pwd= letters +numbers+splChar;
////		Random r = new Random();
////		char[] newPass=new char[length];
////		for (int i=0;i<length;i++) {
////			newPass[i]=pwd.charAt(r.nextInt(pwd.length()));
////		}
////		return newPass;	
////		}
////char[] password=generatePassword(8);
//
// @Autowired
// SpringTemplateEngine templateEngine;
//
// @Autowired
// private JavaMailSender sender;
//
//
// public  Boolean sendMail(Salarie salarie) throws Exception {
//	
//     MimeMessage message = sender.createMimeMessage();
//     
//  
//     MimeMessageHelper helper = new MimeMessageHelper(message,
//             MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//             StandardCharsets.UTF_8.name());
//
//     Map<String, Object> model = new HashMap<String, Object>();
//     model.put("username",salarie.getUsername());
//     model.put("password",salarie.getPassword());
//
//     //model.put("password",password);
//
//    // System.out.println(generatePassword(8));
//     Context context = new Context();
//     context.setVariables(model);
//     String html = templateEngine.process("email-template", context);
//
//     try {
//         helper.setTo(salarie.getMail());
//         helper.setText(html,true);
//         helper.setSubject("Test Mail");
//     } catch (javax.mail.MessagingException e) {
//         e.printStackTrace();
//         return false;
//     }
//     sender.send(message);
//
//     return true;
//
// }
//
//
//}
package com.example.demo.Controlleur;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.example.demo.entities.Salarie;


@RequestMapping("/testapp")
@Controller
public class EmailSender {
//	 static char[] generatePassword(int length) {
//			System.out.println("your new password:");
//		String letters="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
//		String numbers="0123456789";
//		String splChar="!@#$%^&*";
//		
//		String pwd= letters +numbers+splChar;
//		Random r = new Random();
//		char[] newPass=new char[length];
//		for (int i=0;i<length;i++) {
//			newPass[i]=pwd.charAt(r.nextInt(pwd.length()));
//		}
//		return newPass;	
//		}
//char[] password=generatePassword(8);

 @Autowired
 SpringTemplateEngine templateEngine;

 @Autowired
 private JavaMailSender sender;

 @RequestMapping("/getdetails")
 public @ResponseBody Salarie sendMail(@RequestBody Salarie salarie) throws Exception {

     MimeMessage message = sender.createMimeMessage();
     MimeMessageHelper helper = new MimeMessageHelper(message,
             MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
             StandardCharsets.UTF_8.name());

     Map<String, Object> model = new HashMap<String, Object>();
     model.put("username",salarie.getUsername());
     model.put("password",salarie.getPassword());
//     model.put("password",password);

//     System.out.println(generatePassword(8));
     Context context = new Context();
     context.setVariables(model);
     String html = templateEngine.process("email-template", context);

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
