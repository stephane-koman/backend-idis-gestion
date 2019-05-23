package com.idis.gestion.web;

import com.idis.gestion.entities.Role;
import com.idis.gestion.service.RoleService;
import com.idis.gestion.service.pagination.PageRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class RoleRestController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/add-role")
    public Role addRole(@RequestBody Role role){
        Role r = roleService.findRoleByRoleName(role.getRoleName());
        if(r != null) throw new RuntimeException("Ce role existe déjà");
        return roleService.saveRole(role);
    }

    @GetMapping(value = "/search-roles")
    public PageRole searchRoles(
            @RequestParam(name = "roleName", defaultValue = "") String roleName,
            @RequestParam(name = "enable", defaultValue = "1") int enable,
            Pageable pageable
    ){
        return roleService.listRoles(roleName, enable, pageable);
    }

    @GetMapping(value = "/all-roles")
    public List<Role> allRoles(){
        return roleService.findAllRole();
    }

    @GetMapping(value = "/take-role")
    public Role getRole(
            @RequestParam(name = "id", defaultValue = "") Long id
    ){
        return roleService.findRoleById(id);
    }

    @PostMapping(value = "/update-role")
    public Role updateRole(
            @RequestBody Role role
    ){
        return roleService.updateRole(role);
    }

    @PostMapping(value = "/disable-role")
    public boolean disableRole(
            @RequestBody Role role
    ){
        roleService.disableRole(role.getId(), new Date());
        return true;
    }

    @PostMapping(value = "/enable-role")
    public boolean enableRole(
            @RequestBody Role role
    ){
        roleService.enableRole(role.getId(), new Date());
        return true;
    }

    @PostMapping(value = "/remove-role")
    public boolean removeRole(
            @RequestBody Role role
    ){
        roleService.removeRole(role.getId());
        return true;
    }
}
