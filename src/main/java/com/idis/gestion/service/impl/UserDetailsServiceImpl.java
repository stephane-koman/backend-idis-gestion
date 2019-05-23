package com.idis.gestion.service.impl;

import com.idis.gestion.entities.Utilisateur;
import com.idis.gestion.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UtilisateurService utilisateurService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Utilisateur user = utilisateurService.findUserByUsername(username);

        if(user == null) throw new RuntimeException("Ce utilisateur n'existe pas");
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(r->{
            authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
        });
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
