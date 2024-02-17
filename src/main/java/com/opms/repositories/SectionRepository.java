package com.opms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.opms.db.entities.Section;

public interface SectionRepository extends JpaRepository<Section , Long>{
	@Query("SELECT s FROM Section s WHERE s.teacher.id = :userId")
	List<Section> findAllByTeacher(Long userId);
}
