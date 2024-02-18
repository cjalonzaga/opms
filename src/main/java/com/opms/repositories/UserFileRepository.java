package com.opms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opms.db.entities.UserFile;

public interface UserFileRepository extends JpaRepository<UserFile , Long>{

}
