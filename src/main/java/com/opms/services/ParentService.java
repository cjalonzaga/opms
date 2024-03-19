package com.opms.services;

import java.util.List;

import com.opms.db.dtos.ParentDto;

public interface ParentService {
	ParentDto create(ParentDto dto , List<Long> studentIds);
	ParentDto findByUsername(String username);
}
