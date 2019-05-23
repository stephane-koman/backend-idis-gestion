package com.idis.gestion.web.controls;

import com.idis.gestion.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class HeadersControls {

    public String getUsername(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(jwt.replace(SecurityConstants.TOKEN_PREFIX, ""))
                .getBody();

        return claims.getSubject();
    }

    public Collection<GrantedAuthority> getRoles(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(jwt.replace(SecurityConstants.TOKEN_PREFIX, ""))
                .getBody();

        ArrayList<Map<String, String>> roles = (ArrayList<Map<String, String>>) claims.get("roles");

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(r -> {
            authorities.add(new SimpleGrantedAuthority(r.get("authority")));
        });
        return authorities;
    }

    public boolean isAdmin(Collection<GrantedAuthority> authorities){

        AtomicBoolean admin = new AtomicBoolean(false);

        authorities.forEach(auth -> {
            if(auth.getAuthority().equals("ADMIN")){
                admin.set(true);
            }
        });
        return admin.get();
    }

    public int getIsAdmin(String jwt, int enable){

        int actif = 1;
        Collection<GrantedAuthority> authorities = getRoles(jwt);

        boolean isAdmin = isAdmin(authorities);
        if(isAdmin){
            actif = enable;
        }

        return actif;
    }
}
