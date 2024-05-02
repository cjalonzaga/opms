package com.opms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.opms.db.entities.Modules;

@Repository
public interface ModuleRepository extends JpaRepository<Modules, Long> {
	@Query(
	value= "SELECT a.* FROM modules a WHERE a.teacher_id = :userId AND a.is_valid = TRUE ORDER BY a.created_on DESC LIMIT :size OFFSET :page ", nativeQuery = true)
	List<Modules> findAllModulesByUserPaging(Long userId , int page , int size);
	
	@Query("SELECT COUNT(a) FROM Modules a WHERE a.teacher.id = :userId")
	Integer totalSize(Long userId);
	
	@Query( value = "SELECT a.* FROM modules a JOIN module_section ass ON a.id = ass.module_id "
			+ " WHERE ass.section_id = :sectionId AND a.is_valid = TRUE ORDER BY a.created_on DESC LIMIT :size OFFSET :page ", nativeQuery = true)
	List<Modules> findAllByStudentSection(Long sectionId , int page , int size);
	
	@Query( value = "SELECT a.* FROM modules a "
			+ " WHERE a.teacher_id = :id ", nativeQuery = true)
	List<Modules> findAllByTeacher(Long id);

	@Query(value = "SELECT COUNT(a.id) FROM modules a JOIN module_section ms ON a.id = ms.module_id  WHERE ms.section_id = :sectionId" , nativeQuery = true)
	int totalSizeBySection(Long sectionId);
}
