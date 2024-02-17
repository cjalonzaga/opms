package com.opms.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.opms.db.entities.UserData;

@Repository
public interface UserDataRepository extends CrudRepository<UserData , Long>{

}
