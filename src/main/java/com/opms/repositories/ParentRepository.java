package com.opms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.opms.db.entities.Parent;
import com.opms.db.entities.Student;

@Repository
public interface ParentRepository extends JpaRepository<Parent , Long>{
	
	Parent findByUsername(String username);
	
	@Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE "
			+ " ELSE FALSE "
			+ " END FROM Parent p WHERE p.username = :username ")
	Boolean ifUserExist(String username);
	
	@Query("SELECT COUNT(a) FROM Parent a")
	Integer totalSize();
	
	@Query(value="SELECT s.* FROM parents s WHERE "
			+ " ( LOWER(s.first_name) LIKE %:keyword% OR LOWER(s.last_name) LIKE %:keyword% ) "
			+ " AND (:createdOn IS NULL OR DATE(s.created_on) = :createdOn ) "
			+ " AND s.status = :status"
			+ " ORDER BY s.created_on DESC LIMIT :size OFFSET :page " , nativeQuery = true)
	List<Parent> findAllWithPaging(String keyword , String createdOn , String status , int page , int size);
	
	@Query(value="SELECT s.* FROM parents s ORDER BY s.created_on DESC LIMIT :size OFFSET :page " , nativeQuery = true)
	List<Parent> findAllPageable(int page , int size);
}
