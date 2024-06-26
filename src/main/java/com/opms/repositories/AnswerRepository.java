package com.opms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.opms.db.entities.Answer;
import com.opms.enums.AnswerStatus;

@Repository
public interface AnswerRepository extends JpaRepository<Answer , Long>{
	
	@Query("SELECT a FROM Answer a WHERE a.student.id = :userId and a.activity.id =:activityId")
	Answer findByUser(Long userId , Long activityId);
	
	@Query("SELECT a FROM Answer a WHERE a.activity.id = :activityId ")
	List<Answer> findAllByActivity(Long activityId);
	
	@Query("SELECT a FROM Answer a WHERE a.activity.id = :activityId and a.section.id = :sectionId")
	List<Answer> findAllBySection(Long activityId ,Long sectionId);
	
	@Query("SELECT COUNT(a) FROM Answer a WHERE a.activity.id = :activityId AND a.status = :status")
	Integer totalSize(Long activityId , AnswerStatus status);
}
