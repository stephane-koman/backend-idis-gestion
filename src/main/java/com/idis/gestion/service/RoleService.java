package com.idis.gestion.service;

import com.idis.gestion.entities.Role;
import com.idis.gestion.service.pagination.PageRole;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface RoleService {

    public PageRole listRoles(String roleName, int enable, Pageable pageable);
    public List<Role> findAllRole();
    public Role findRoleById(Long id);
    public Role findRoleByRoleName(String roleName);
    public Role saveRole(Role role);
    public Role updateRole(Role role);
    public void disableRole(Long id, Date date);
    public void enableRole(Long id, Date date);
    public void removeRole(Long id);
}
