package com.opms.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.opms.db.entities.Course;
import com.opms.enums.CouncilType;

public interface CourseRepository extends JpaRepository<Course, Long>{
	
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE "
			+ " ELSE FALSE "
			+ " END FROM Course c WHERE c.abbreviation = :abbr AND c.teacher.id = :userId ")
	Boolean ifCourseExist(String abbr , Long userId);
	
	@Query("SELECT c FROM Course c WHERE c.teacher.id = :userId")
	List<Course> findAllByUser(Long userId);
	
	@Query(
		value="SELECT c.* FROM courses c ORDER BY c.created_on DESC LIMIT :size OFFSET :page ", 
		nativeQuery = true)
	List<Course> findAllCourseByUserPaging(int page , int size);
	
	@Query("SELECT COUNT(a) FROM Course a")
	Integer totalSize();
	
	@Query(
			value="SELECT c.* FROM courses c WHERE "
					+ " ( LOWER(c.name) LIKE %:keyword% OR LOWER(c.abbreviation) LIKE %:keyword% ) "
					+ "  AND (:createdOn IS NULL OR DATE(c.created_on) = :createdOn ) "
					+ " ORDER BY c.created_on DESC LIMIT :size OFFSET :page ", 
			nativeQuery = true)
	List<Course> searchAllByUser( String createdOn , String keyword , int page , int size);
	
}
