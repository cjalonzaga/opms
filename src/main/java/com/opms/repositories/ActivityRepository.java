package com.opms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.opms.db.entities.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity , Long>{
	
//	@Query(
//	value=
//		"SELECT avt.* FROM activities avt JOIN subjects s ON avt.subject_id = s.id JOIN courses cx ON s.course_id = cx.id "+
//		"WHERE cx.user_id = :userId LIMIT :limit OFFSET :offSet ", nativeQuery = true)
//	List<Activity> findAllActivityByUserPaging(Long userId , int offSet , int limit);
//	
//	@Query("SELECT COUNT(a) FROM Activity a JOIN Subject ss ON a.subject.id = ss.id JOIN Course cc ON ss.course.id = cc.id "
//			+ "WHERE cc.user.id = :userId ")
//	Integer totalSize(Long userId);
	
}
