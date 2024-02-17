package com.opms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.opms.db.entities.Activity;

@Repository
public interface AssignmentRepository extends JpaRepository<Activity , Long>{
	
}
