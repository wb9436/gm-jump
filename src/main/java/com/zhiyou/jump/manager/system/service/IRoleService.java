package com.zhiyou.jump.manager.system.service;

import java.util.List;

import com.zhiyou.jump.manager.dto.system.RoleDto;

public interface IRoleService {

	List<RoleDto> list();

	RoleDto get(Integer id);

	int save(RoleDto role);

	int update(RoleDto role);

	int remove(Integer id);

	int batchremove(Integer[] ids);
	
	List<RoleDto> getByUserId(Integer userId);

}
