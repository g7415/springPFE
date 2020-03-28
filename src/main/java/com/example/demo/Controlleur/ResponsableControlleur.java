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
//import com.example.demo.dao.ResponsableRepository;
//import com.example.demo.entities.Responsable;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
//@Api(description=" Responsable")
//@RestController
//@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:4200")
//
//public class ResponsableControlleur {
//	@Autowired
//	private ResponsableRepository responsabledao;
//	@ApiOperation("Consulter tous les Responsables")
//	
//	@GetMapping(value="/responsable")
//	   public List<Responsable> responsable(){
//		return responsabledao.findAll();
//	    }
//
//
//
//@GetMapping(value="responsable/{Id_Responsable}")
//   public ResponseEntity<Responsable> rh(@PathVariable Long Id_Responsable) throws Exception{
//   final Responsable responsable = responsabledao.findById(Id_Responsable).orElseThrow(()->new Exception("Le responsable  n'existe pas"));
//	  return ResponseEntity.ok().body(responsable);
//	}
//
//
//
//@PostMapping(value="/responsable")
//	    
//	    public Responsable addresponsable(@Valid @RequestBody Responsable responsable){
//	     return responsabledao.save(responsable);}
//	
//
//
//	@DeleteMapping(value="responsable/{Id_Responsable}")
//		    
//	      public Map<String,Boolean> deleteresponsable(@PathVariable Long Id_Responsable) throws Exception {
//		     Responsable cat = responsabledao.findById(Id_Responsable).orElseThrow(()->new Exception("Le responsable  n'existe pas"));
//		     responsabledao.delete(cat);
//		     Map<String,Boolean> response = new HashMap<>();
//		     response.put("Le responsable  est supprimé!",Boolean.TRUE);
//		     return response;
//		   }
//
//	@PutMapping(value="responsable/{Id_Responsable}")
//    public ResponseEntity<Responsable> updateRH(@PathVariable Long Id_Responsable,@Valid @RequestBody Responsable catDetails) throws Exception{
//	Responsable c = responsabledao.findById(Id_Responsable).orElseThrow(()->new Exception("le responsable  n'existe pas"));
//	  c.setNom(catDetails.getNom());
//      c.setPrenom(catDetails.getPrenom());
//      c.setDate_entree(catDetails.getDate_entree());
//      c.setGrade(catDetails.getGrade());
//      c.setMail(catDetails.getMail());
//      c.setGroupe(catDetails.getGroupe());
//      c.setNom_responsable(catDetails.getNom_responsable());
//      c.setNum_tel(catDetails.getNum_tel());
//      c.setSolde_conge(catDetails.getSolde_conge());
//        Responsable updateResponsable = responsabledao.save(c);
//        return ResponseEntity.ok(updateResponsable);
//    }
//
//
//
//}
