package com.opms.repositories;

import org.springframework.data.repository.CrudRepository;

import com.opms.db.entities.UserRole;


public interface UserRoleRepository extends CrudRepository<UserRole , Long>{
	
}
