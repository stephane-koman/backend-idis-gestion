package com.idis.gestion.web;

import com.idis.gestion.entities.Role;
import com.idis.gestion.entities.Utilisateur;
import com.idis.gestion.service.UtilisateurService;
import com.idis.gestion.service.pagination.PageUtilisateur;
import com.idis.gestion.web.controls.HeadersControls;
import com.idis.gestion.web.forms.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class UtilisateurRestController {

    @Autowired
    private UtilisateurService utilisateurService;

    private HeadersControls headersControls = new HeadersControls();

    @PostMapping("/admin/add-user")
    public Utilisateur addUtilisateur(@RequestBody RegisterForm userForm) {

        if (!userForm.getPassword().equals(userForm.getRepassword()))
            throw new RuntimeException("Les mots de passe doivent être identiques");

        Utilisateur userApp = utilisateurService.findUserByUsername(userForm.getUsername());

        if (userApp != null)
            throw new RuntimeException("Ce utilisateur existe déjà");

        userApp = utilisateurService.findUserByPersonne(userForm.getPersonne().getId());

        if (userApp != null)
            throw new RuntimeException("Cette personne a déjà un compte");

        Utilisateur user = new Utilisateur();
        user.setUsername(userForm.getUsername());
        user.setPassword(userForm.getPassword());
        user.setPersonne(userForm.getPersonne());
        user.setCreateAt(new Date());
        user.setUpdateAt(new Date());

        utilisateurService.saveUser(user);

        System.out.println(user.getId() + ":" + user.getUsername());

        if (userForm.getRoles().size() != 0) {

            userForm.getRoles().forEach(r -> {

                utilisateurService.addRoleToUser(user.getUsername(), r.getRoleName());

            });
        }
        return user;
    }

    @GetMapping(value = "/admin/search-users")
    public PageUtilisateur searchUtilisateurs(
            @RequestParam(name = "username", defaultValue = "") String username,
            @RequestParam(name = "roles", defaultValue = "") String role,
            @RequestParam(name = "enable", defaultValue = "1") int enable,
            Pageable pageable
    ) {
        return utilisateurService.listUsers(username, role, enable, pageable);
    }

    @GetMapping(value = "/admin/take-user")
    public Utilisateur getUtilisateur(
            @RequestParam(name = "id", defaultValue = "") Long id
    ) {
        return utilisateurService.findUserById(id);
    }

    @GetMapping(value = "/profile/user")
    public Utilisateur getProfil(
            @RequestHeader(value = "Authorization") String jwt
    ) {
        String username = headersControls.getUsername(jwt);
        Utilisateur utilisateur = utilisateurService.findUserByUsername(username);
        if(utilisateur == null) throw new RuntimeException("Cet utilisateur n'existe pas");
        return utilisateur;
    }

    @PostMapping("/admin/update-user")
    public Utilisateur updateUtilisateur(
            @RequestBody RegisterForm userForm
    ) {
        if (!userForm.getPassword().equals(userForm.getRepassword()))
            throw new RuntimeException("Les mots de passe doivent être identiques");
        Utilisateur utilisateur = utilisateurService.findUserById(userForm.getId());
        utilisateur.setPassword(userForm.getPassword());

        utilisateurService.updateUser(utilisateur);

        Collection<Role> roles = new ArrayList<>(utilisateur.getRoles());
        if (roles.size() != 0) {
            System.out.println(utilisateur.getRoles().size());
            roles.forEach(r -> {
                utilisateurService.removeRoleToUser(utilisateur, r);
            });
        }

        if (userForm.getRoles().size() != 0) {
            userForm.getRoles().forEach(r -> {
                utilisateurService.addRoleToUser(userForm.getUsername(), r.getRoleName());
            });
        }
        return utilisateurService.findUserById(userForm.getId());
    }

    @PostMapping(value = "/admin/disable-user")
    public boolean disableUtilisateur(
            @RequestBody Utilisateur utilisateur
    ) {
        utilisateurService.disableUser(utilisateur.getId(), new Date());
        return true;
    }

    @PostMapping(value = "/admin/enable-user")
    public boolean enableUtilisateur(
            @RequestBody Utilisateur utilisateur
    ) {
        utilisateurService.enableUser(utilisateur.getId(), new Date());
        return true;
    }

    @PostMapping(value = "/admin/remove-user")
    public boolean removeUtilisateur(
            @RequestBody Utilisateur utilisateur
    ) {
        utilisateurService.removeUser(utilisateur.getId());
        return true;
    }

}
