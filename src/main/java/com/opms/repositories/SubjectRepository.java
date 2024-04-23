package com.opms.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.opms.db.entities.Subject;
import com.opms.enums.CourseLevel;

public interface SubjectRepository extends JpaRepository<Subject, Long>{
	
	@Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE "
			+ " ELSE FALSE "
			+ " END FROM Subject s JOIN Course c ON s.course.id = c.id WHERE s.code = :code AND c.teacher.id = :userId ")
	Boolean ifSubjectExist(String code , Long userId);
	
	@Query( value = "SELECT s FROM Subject s WHERE s.teacher.id = :userId")
	List<Subject> findAllByUser(Long userId);
	
	@Query( value = "SELECT s.* FROM subjects s WHERE s.teacher_id = :userId "
			+ " ORDER BY s.created_on DESC LIMIT :size OFFSET :page " , nativeQuery = true)
	List<Subject> findAllByUserWithPaging(Long userId , int page , int size);
	
	@Query( value = "SELECT s.* FROM subjects s JOIN courses c ON s.course_id = c.id "
			+ " ORDER BY s.created_on DESC LIMIT :size OFFSET :page " , nativeQuery = true)
	List<Subject> findAll( int page , int size );
	
	@Query("SELECT COUNT(s) FROM Subject s JOIN Course c ON s.course.id = c.id WHERE c.teacher.id = :userId ")
	Integer totalSize(Long userId);
	
	@Query(value="SELECT s.* FROM subjects s JOIN courses c ON s.course_id = c.id WHERE ( c.teacher_id = :userId ) "
					+ " AND ( LOWER(s.code) LIKE %:keyword% ) "
					+ " AND (:createdOn IS NULL OR DATE(s.created_on) = :createdOn ) "
					+ "	AND (:semester IS NULL OR s.semester = :semester) "
					+ " AND (:courseLevel IS NULL OR s.course_level = :courseLevel) "
					+ " ORDER BY s.created_on DESC LIMIT :size OFFSET :page ",
			nativeQuery = true)
	List<Subject> searchAllByUser(Long userId , String createdOn , String keyword , String courseLevel , String semester , int page , int size);
	
	@Query(value="SELECT s.* FROM subjects s JOIN courses c ON s.course_id = c.id WHERE "
			+ " ( LOWER(s.code) LIKE %:keyword% ) "
			+ " AND (:createdOn IS NULL OR DATE(s.created_on) = :createdOn ) "
			+ "	AND (:semester IS NULL OR s.semester = :semester) "
			+ " AND (:courseLevel IS NULL OR s.course_level = :courseLevel) "
			+ " ORDER BY s.created_on DESC LIMIT :size OFFSET :page ",
	nativeQuery = true)
List<Subject> searchAll(String createdOn , String keyword , String courseLevel , String semester , int page , int size);

	@Query("SELECT s FROM Subject s WHERE s.course.id = :courseId")
	List<Subject> searchAllByStudents(Long courseId);
}
