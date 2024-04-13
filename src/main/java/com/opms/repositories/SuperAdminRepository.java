package com.opms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.opms.db.entities.SuperAdmin;

@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdmin , Long>{
	SuperAdmin findByUsername(String username);
	
	@Query("SELECT CASE WHEN COUNT(sa) > 0 THEN TRUE "
			+ " ELSE FALSE "
			+ " END FROM SuperAdmin sa WHERE sa.username = :username ")
	Boolean ifUserExist(String username);
}
