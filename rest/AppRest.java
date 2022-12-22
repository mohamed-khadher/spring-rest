package tn.enig.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.enig.dao.IDepartement;
import tn.enig.dao.IEmploye;
import tn.enig.model.Departement;
import tn.enig.model.Employe;

@RestController	
@RequestMapping("/api")
public class AppRest {
	
	@Autowired
	IDepartement depRepo;
	
	@Autowired
	IEmploye empRepo;
	
	public void setDepRepo(IDepartement depRepo) {
		this.depRepo = depRepo;
	}
	
	public void setEmpRepo(IEmploye empRepo) {
		this.empRepo = empRepo;
	}
	
	@GetMapping("/departements")
	public List<Departement> getAllDep(){
		return depRepo.findAll();
	}
	
	@GetMapping("/employes")
	public List<Employe> getAllEmp(){
		return empRepo.findAll();
	}
	
	@GetMapping("/getEmployesByDepartement")
	public List<Employe> getEmpByDep(@RequestParam(name="id") String id){
		Integer depId;
		try {
			depId = Integer.parseInt(id);
			return empRepo.getEmpByDep(depId);
		} catch (Exception e) {
			return null;
		}
	}
	
	@GetMapping("/getEmployeByID")
	public Employe getEmpByID(@RequestParam(name="id") String id) {
		Integer empId;
		try {
			empId = Integer.parseInt(id);
			return empRepo.findById(empId).get();
		} catch (Exception e) {
			return null;
		}
	}
	
	@PostMapping("/addEmploye")
	public String addEmp(HttpServletRequest req) {
		String nom = req.getParameter("nom");
		String fonction = req.getParameter("fonction");
		Employe newEmp = new Employe();
		newEmp.setNom(nom);
		newEmp.setFonction(fonction);
		
		try {
			Integer idDep = Integer.parseInt(req.getParameter("depId"));
			Departement dep = depRepo.findById(idDep).get();
			newEmp.setDepartement(dep);
			empRepo.save(newEmp);
			return String.format("Employé enregistré dans le departement %s(%d) avec succés", dep.getDescription(), dep.getId());
		} catch (Exception e) {
			empRepo.save(newEmp);
			return "Employé Ajouté";
		}
	}
	
	@PostMapping("/updateEmp")
	public String updateEmp(HttpServletRequest req) {
		Integer depId;
		Integer empId;
		Employe emp;
		Departement newDep ;
		try {
			depId = Integer.parseInt(req.getParameter("depId"));
			empId = Integer.parseInt(req.getParameter("empId"));
		} catch (Exception e) {
			return "Merci de verifier vos entrés !";
		}
		try {
			emp = empRepo.findById(empId).get();
			newDep = depRepo.findById(depId).get();
			emp.setDepartement(newDep);
			empRepo.save(emp);
			return "Employé mis à jour!";
		} catch (Exception e) {
			return "Employé ou département introuvable !";
		}
	}
	
	@GetMapping("/removeEmploye")
	public String removeEmp(@RequestParam(name="empId") String empId) {
		Integer id;
		Employe emp;
		try {
			id = Integer.parseInt(empId);
		} catch (Exception e) {
			return "Verifier l'id entré";
		}
		try {
			emp = empRepo.findById(id).get();
		} catch (Exception e) {
			return "Employé introuvable";
		}
		empRepo.delete(emp);
		return "Employé supprimé.";
	}
	
	
	
	
}
