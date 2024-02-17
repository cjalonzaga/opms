package com.opms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.opms.db.entities.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher , Long>{
	Teacher findByUsername(String username);
	
	@Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE "
			+ " ELSE FALSE "
			+ " END FROM Teacher s WHERE s.username = :username ")
	boolean ifUserExist(String username);
}
