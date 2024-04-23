package com.opms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.opms.db.entities.Activity;
import com.opms.db.entities.ModuleFile;
import com.opms.db.entities.Modules;

@Repository
public interface ModuleFileRepository extends JpaRepository<ModuleFile , Long>{
	
}
