package com.idis.gestion.service.impl;

import com.idis.gestion.dao.PersonneRepository;
import com.idis.gestion.dao.RoleRepository;
import com.idis.gestion.dao.UtilisateurRepository;
import com.idis.gestion.entities.Personne;
import com.idis.gestion.entities.Role;
import com.idis.gestion.entities.Utilisateur;
import com.idis.gestion.service.UtilisateurService;
import com.idis.gestion.service.pagination.PageUtilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PersonneRepository personneRepository;

    @Override
    public PageUtilisateur listUsers(String username, String role, int enable, Pageable pageable) {
        Page<Utilisateur> users = utilisateurRepository.listUsers(username, role, enable, pageable);
        PageUtilisateur pUsers = new PageUtilisateur();
        pUsers.setUtilisateurs(users.getContent());
        pUsers.setPage(users.getNumber());
        pUsers.setNombreUtilisateurs(users.getNumberOfElements());
        pUsers.setTotalUtilisateurs((int)users.getTotalElements());
        pUsers.setTotalPages(users.getTotalPages());
        return pUsers;
    }

    @Override
    public Utilisateur saveUser(Utilisateur user) {
        String hashPW = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashPW);
        return utilisateurRepository.save(user);
    }

    @Override
    public Utilisateur updateUser(Utilisateur user) {
        Utilisateur utilisateur = utilisateurRepository.getUtilisateurById(user.getId());
        if (utilisateur == null) throw new RuntimeException("Ce utilisateur n'existe pas");
        if(user.getPassword() != null){
            String hashPW = bCryptPasswordEncoder.encode(user.getPassword());
            utilisateur.setPassword(hashPW);
        }
        utilisateur.setUpdateAt(new Date());

        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        System.out.println(username + ":" + roleName);
        Role role = roleRepository.findRoleByRoleName(roleName);
        Utilisateur user = utilisateurRepository.findByUsername(username);
        user.getRoles().add(role);
    }

    @Override
    public void removeRoleToUser(Utilisateur utilisateur, Role role) {
        utilisateur.getRoles().remove(role);
    }

    @Override
    public Utilisateur findUserByUsername(String username) {
        return utilisateurRepository.findByUsername(username);
    }

    @Override
    public Utilisateur findUserByPersonne(Long id) {
        return utilisateurRepository.findUtilisateurByPersonne(id);
    }

    @Override
    public Utilisateur findUserById(Long id) {
        Utilisateur user = utilisateurRepository.getUtilisateurById(id);
        if(user == null) throw new RuntimeException("Ce utilisateur n'existe pas");
        return user;
    }

    @Override
    public void disableUser(Long id, Date date) {
        Utilisateur user = utilisateurRepository.getUtilisateurById(id);
        if(user == null) throw new RuntimeException("Ce utilisateur n'existe pas");
        if(user.getPersonne() == null) personneRepository.disablePersonne(user.getPersonne().getId(), new Date());
        utilisateurRepository.disableUser(id, date);
    }

    @Override
    public void enableUser(Long id, Date date) {
        Utilisateur user = utilisateurRepository.getUtilisateurById(id);
        if(user == null) throw new RuntimeException("Ce utilisateur n'existe pas");
        if(user.getPersonne() != null) personneRepository.enablePersonne(user.getPersonne().getId(), new Date());
        utilisateurRepository.enableUser(id, date);
    }

    @Override
    public void removeUser(Long id) {
        Utilisateur user = utilisateurRepository.getUtilisateurById(id);
        if(user == null) throw new RuntimeException("Ce utilisateur n'existe pas");
        utilisateurRepository.removeUtilisateurById(id);
    }
}
