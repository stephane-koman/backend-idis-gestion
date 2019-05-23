package com.idis.gestion.dao;

import com.idis.gestion.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
public interface RoleRepository extends PagingAndSortingRepository<Role,Long> {

    @Query("select r from Role r where (LOWER(r.roleName) like LOWER(CONCAT('%',:roleName,'%'))) and (r.enable = :enable or :enable = 2) order by r.id desc ")
    public Page<Role> listRoles(@Param("roleName")String roleName, @Param("enable")int enable, Pageable pageable);

    @Query("select r from Role r")
    public List<Role> findAll();

    public Role findRoleByRoleName(String roleName);

    public Role getRoleById(Long id);

    @Modifying
    @Query("update Role r set r.enable = 0, r.updateAt = ?2 where r.id = ?1")
    public void disableRole(Long id, Date date);

    @Modifying
    @Query("update Role r set r.enable = 1, r.updateAt = ?2 where r.id = ?1")
    public void enableRole(Long id, Date date);

    public void removeRoleById(Long id);
}
