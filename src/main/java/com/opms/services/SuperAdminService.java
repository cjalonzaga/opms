package com.opms.services;

import com.opms.db.dtos.SuperAdminDto;

public interface SuperAdminService {
	SuperAdminDto findByUsername(String username);
}
