package com.opms.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.opms.db.entities.Image;

@Repository
public interface ImageRepository extends CrudRepository<Image , Long>{
	@Query("SELECT CASE WHEN COUNT(img) > 0 THEN TRUE "
			+ " ELSE FALSE "
			+ " END FROM Image img WHERE img.originalFileName = :filename ")
	Boolean checkImageExist(String filename);
}
