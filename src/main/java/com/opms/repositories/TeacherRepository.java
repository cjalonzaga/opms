package com.opms.repositories;

import java.util.List;

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
	
	@Query("SELECT COUNT(a) FROM Teacher a")
	Integer totalSize();
	
	@Query(value="SELECT s.* FROM teachers s WHERE "
			+ " ( LOWER(s.first_name) LIKE %:keyword% OR LOWER(s.last_name) LIKE %:keyword% ) "
			+ " AND (:createdOn IS NULL OR DATE(s.created_on) = :createdOn ) "
			+ " AND s.status = :status AND s.is_valid = TRUE "
			+ " ORDER BY s.created_on DESC LIMIT :size OFFSET :page " , nativeQuery = true)
	List<Teacher> findAllWithPaging(String keyword , String createdOn , String status , int page , int size);
	
	@Query(value="SELECT s.* FROM teachers s WHERE s.is_valid = TRUE ORDER BY s.created_on DESC LIMIT :size OFFSET :page " , nativeQuery = true)
	List<Teacher> findAllPageable(int page , int size);
}
