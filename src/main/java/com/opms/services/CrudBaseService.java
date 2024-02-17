package com.opms.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudBaseService<D,E> {
	D get(Long id);
	List<D> getAll();
	D create(D d);
	void delete(Long id);
}
