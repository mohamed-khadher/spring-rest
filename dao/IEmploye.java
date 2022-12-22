package tn.enig.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.enig.model.Employe;


@Repository
public interface IEmploye extends JpaRepository<Employe, Integer>{
	@Query("SELECT e FROM Employe e WHERE e.departement.id = ?1")
	List<Employe> getEmpByDep(Integer id);
}
