package com.opms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.opms.db.entities.Folder;
import com.opms.db.entities.Student;

@Repository
public interface FolderRepository extends JpaRepository<Folder , Long>{
	
	@Query("SELECT COUNT(a) FROM Folder a")
	Integer totalSize();
	
	@Query(value="SELECT s.* FROM folders s WHERE "
			+ " ( LOWER(s.name) LIKE %:keyword% ) "
			+ " AND (:createdOn IS NULL OR DATE(s.created_on) = :createdOn ) AND s.is_valid = TRUE "
			+ " ORDER BY s.created_on DESC LIMIT :size OFFSET :page " , nativeQuery = true)
	List<Folder> findAllWithPaging(String keyword , String createdOn , int page , int size);
	
	@Query(value="SELECT s.* FROM folders s WHERE s.student_id = :studentId AND s.is_valid = TRUE "
			+ " ORDER BY s.created_on DESC LIMIT :size OFFSET :page " , nativeQuery = true)
	List<Folder> findAllPageable(int page , int size , Long studentId);
}
