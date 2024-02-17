package com.opms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.opms.db.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student , Long>{
	Student findByUsername(String username);
	
	@Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE "
			+ " ELSE FALSE "
			+ " END FROM Student s WHERE s.username = :username AND s.firstName = :firstName AND s.lastName = :lastName ")
	Boolean ifUserExist(String username,String firstName, String lastName);
	
	@Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE "
			+ " ELSE FALSE "
			+ " END FROM Student s WHERE s.username = :username ")
	Boolean ifUserExist(String username);
}
