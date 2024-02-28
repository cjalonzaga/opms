package com.opms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.opms.db.entities.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity , Long>{
	
	@Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE "
			+ "ELSE FALSE "
			+ "END FROM Activity s WHERE s.title = :title")
	Boolean ifActivityExist(String title);
	
	@Query(
	value= "SELECT a.* FROM activities a WHERE a.teacher_id = :userId ORDER BY a.created_on DESC LIMIT :size OFFSET :page ", nativeQuery = true)
	List<Activity> findAllActivitiesByUserPaging(Long userId , int page , int size);
	
	@Query("SELECT COUNT(a) FROM Activity a WHERE a.teacher.id = :userId")
	Integer totalSize(Long userId);
	
	@Query( value = "SELECT a.* FROM activities a JOIN activity_section ass ON a.id = ass.activity_id JOIN student_section ss ON "
			+ " ass.section_id = ss.section_id WHERE ss.student_id = :studentId", nativeQuery = true)
	List<Activity> findAllByStudent(Long studentId);
}
