package com.idis.gestion.service.impl;

import com.idis.gestion.dao.RoleRepository;
import com.idis.gestion.entities.Role;
import com.idis.gestion.service.RoleService;
import com.idis.gestion.service.pagination.PageRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public PageRole listRoles(String roleName, int enable, Pageable pageable) {
        Page<Role> roles = roleRepository.listRoles(roleName, enable, pageable);
        PageRole pRoles = new PageRole();
        pRoles.setRoles(roles.getContent());
        pRoles.setPage(roles.getNumber());
        pRoles.setNombreRoles(roles.getNumberOfElements());
        pRoles.setTotalRoles((int)roles.getTotalElements());
        pRoles.setTotalPages(roles.getTotalPages());
        return pRoles;
    }

    @Override
    public List<Role> findAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public Role findRoleById(Long id) {
        return roleRepository.getRoleById(id);
    }

    @Override
    public Role findRoleByRoleName(String roleName) {
        return roleRepository.findRoleByRoleName(roleName);
    }

    @Override
    public Role saveRole(Role role) {
        role.setCreateAt(new Date());
        role.setUpdateAt(new Date());
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Role role) {
        Role r = roleRepository.getRoleById(role.getId());
        if(r == null) throw new RuntimeException("Ce role n'existe pas");
        r.setEnable(role.getEnable());
        r.setRoleName(role.getRoleName());
        r.setUpdateAt(new Date());
        return roleRepository.save(r);
    }

    @Override
    public void disableRole(Long id, Date date) {
        Role r = roleRepository.getRoleById(id);
        if(r == null) throw new RuntimeException("Ce role n'existe pas");
        roleRepository.disableRole(id, date);
    }

    @Override
    public void enableRole(Long id, Date date) {
        Role r = roleRepository.getRoleById(id);
        if(r == null) throw new RuntimeException("Ce role n'existe pas");
        roleRepository.enableRole(id, date);
    }

    @Override
    public void removeRole(Long id) {
        Role r = roleRepository.getRoleById(id);
        if(r == null) throw new RuntimeException("Ce role n'existe pas");
        roleRepository.removeRoleById(id);
    }
}
