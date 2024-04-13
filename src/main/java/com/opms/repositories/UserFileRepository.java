package com.opms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.opms.db.entities.UserFile;

public interface UserFileRepository extends JpaRepository<UserFile , Long>{
	@Query("SELECT COUNT(a) FROM UserFile a")
	Integer totalSize();
	
	@Query(value="SELECT s.* FROM user_files s WHERE s.folder_id = :folderId ORDER BY s.created_on DESC LIMIT :size OFFSET :page " , nativeQuery = true)
	List<UserFile> findAllPageable(int page , int size , Long folderId);
}
