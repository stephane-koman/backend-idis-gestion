package com.idis.gestion.service;

import com.idis.gestion.entities.Personne;
import com.idis.gestion.entities.Role;
import com.idis.gestion.entities.Utilisateur;
import com.idis.gestion.service.pagination.PageRole;
import com.idis.gestion.service.pagination.PageUtilisateur;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface UtilisateurService {

    //------------------------------- UTILISATEUR ------------------------------------------------

    public PageUtilisateur listUsers(String username, String role, int enable, Pageable pageable);
    public Utilisateur saveUser(Utilisateur user);
    public Utilisateur updateUser(Utilisateur user);
    public Utilisateur findUserByUsername(String username);
    public Utilisateur findUserByPersonne(Long id);
    public Utilisateur findUserById(Long id);
    public void disableUser(Long id, Date date);
    public void enableUser(Long id, Date date);
    public void removeUser(Long id);

    //------------------------------- ROLE & UTILISATEUR ------------------------------------------------

    public void addRoleToUser(String username, String roleName);
    public void removeRoleToUser(Utilisateur utilisateur, Role role);

}
