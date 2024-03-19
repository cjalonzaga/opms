package com.opms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.opms.db.entities.Parent;

@Repository
public interface ParentRepository extends JpaRepository<Parent , Long>{
	
	Parent findByUsername(String username);
	
	@Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE "
			+ " ELSE FALSE "
			+ " END FROM Parent p WHERE p.username = :username ")
	Boolean ifUserExist(String username);
}
