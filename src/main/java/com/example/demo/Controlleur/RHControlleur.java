//package com.example.demo.Controlleur;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.demo.dao.RHRepository;
//import com.example.demo.entities.RH;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
//@Api(description=" RH")
//@RestController
//@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:4200")
//
//public class RHControlleur {
//	@Autowired
//	private RHRepository rhdao;
//	@ApiOperation("Consulter tous les RH")
//	
//	@GetMapping(value="/rh")
//	   public List<RH> rh(){
//		return rhdao.findAll();
//	    }
//
//
//
//@GetMapping(value="rh/{Id_RH}")
//   public ResponseEntity<RH> rh(@PathVariable Long Id_RH) throws Exception{
//   final RH rh = rhdao.findById(Id_RH).orElseThrow(()->new Exception("Le responsable RH n'existe pas"));
//	  return ResponseEntity.ok().body(rh);
//	}
//
//
//
//@PostMapping(value="/rh")
//	    
//	    public RH addrh(@Valid @RequestBody RH rh){
//	     return rhdao.save(rh);}
//	
//
//
//	@DeleteMapping(value="rh/{Id_RH}")
//		    
//	      public Map<String,Boolean> deletesalare(@PathVariable Long Id_RH) throws Exception {
//		     RH cat = rhdao.findById(Id_RH).orElseThrow(()->new Exception("Le responsable RH n'existe pas"));
//		     rhdao.delete(cat);
//		     Map<String,Boolean> response = new HashMap<>();
//		     response.put("Le responsable RH est supprimé!",Boolean.TRUE);
//		     return response;
//		   }
//
//	@PutMapping(value="rh/{Id_RH}")
//    public ResponseEntity<RH> updateRH(@PathVariable Long Id_RH,@Valid @RequestBody RH catDetails) throws Exception{
//	RH c = rhdao.findById(Id_RH).orElseThrow(()->new Exception("le responsable RH n'existe pas"));
//	  c.setNom(catDetails.getNom());
//      c.setPrenom(catDetails.getPrenom());
//      c.setDate_entree(catDetails.getDate_entree());
//      c.setGrade(catDetails.getGrade());
//      c.setMail(catDetails.getMail());
//      c.setGroupe(catDetails.getGroupe());
//      c.setNom_responsable(catDetails.getNom_responsable());
//      c.setNum_tel(catDetails.getNum_tel());
//      c.setSolde_conge(catDetails.getSolde_conge());
//        RH updateRH = rhdao.save(c);
//        return ResponseEntity.ok(updateRH);
//    }
//
//
//	
//}
