package com.opms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.opms.db.entities.Folder;
import com.opms.db.entities.UserFile;

@Repository
public interface UserFileRepository extends JpaRepository<UserFile , Long>{
	@Query("SELECT COUNT(a) FROM UserFile a WHERE a.isValid = TRUE ")
	Integer totalSize();
	
	@Query(value="SELECT s.* FROM user_files s WHERE s.folder_id = :folderId AND s.is_valid = TRUE"
			+ " ORDER BY s.created_on DESC LIMIT :size OFFSET :page " , nativeQuery = true)
	List<UserFile> findAllPageable(int page , int size , Long folderId);
	
	@Query(value="SELECT s.* FROM user_files s WHERE s.student_id = :studentId AND s.is_valid = TRUE"
			+ " ORDER BY s.created_on DESC LIMIT :size OFFSET :page " , nativeQuery = true)
	List<UserFile> findAllPageableByUser(int page , int size , Long studentId);
	
	@Query("SELECT  uf FROM UserFile uf WHERE uf.folder.id = :folderId AND uf.isValid = TRUE ")
	List<UserFile> findAllByFolder(Long folderId);
}
