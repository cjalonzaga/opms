package com.opms.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PagingAware <D , T>{
	Page<D> findAllByUserWithPaging(Pageable pageable);
	Page<D> searchAllByUser(String createdOn , String keyword , Pageable pageable);
}
