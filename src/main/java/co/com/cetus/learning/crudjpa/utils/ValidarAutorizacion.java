package co.com.cetus.learning.crudjpa.utils;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("all")
public class ValidarAutorizacion {

    public static void validarAutenticacion() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        @SuppressWarnings("unchecked")
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();

        log.info("Autorizaciones");
        for (GrantedAuthority ga : authorities) {
            log.info("Autorizacion #:" + ga.getAuthority());
        }
        log.info("Fin Autorizaciones");

        log.info("Llegue a recuperar");
    }

}
