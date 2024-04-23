package com.opms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.opms.db.entities.Modules;

@Repository
public interface ModuleRepository extends JpaRepository<Modules, Long> {
	
}
