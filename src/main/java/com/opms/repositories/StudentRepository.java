package com.opms.repositories;

import java.util.List;

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
	
	@Query("SELECT COUNT(a) FROM Student a")
	Integer totalSize();
	
	@Query(value="SELECT s.* FROM students s WHERE "
			+ " ( LOWER(s.first_name) LIKE %:keyword% OR LOWER(s.last_name) LIKE %:keyword% ) "
			+ " AND (:createdOn IS NULL OR DATE(s.created_on) = :createdOn ) "
			+ " AND s.status = :status"
			+ " ORDER BY s.created_on DESC LIMIT :size OFFSET :page " , nativeQuery = true)
	List<Student> findAllWithPaging(String keyword , String createdOn , String status , int page , int size);
	
	@Query(value="SELECT s.* FROM students s ORDER BY s.created_on DESC LIMIT :size OFFSET :page " , nativeQuery = true)
	List<Student> findAllPageable(int page , int size);
}
