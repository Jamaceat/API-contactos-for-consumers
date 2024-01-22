package co.com.cetus.learning.crudjpa.service;

import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.cetus.learning.crudjpa.dao.IUserDAO;
import co.com.cetus.learning.crudjpa.domain.Rol;
import co.com.cetus.learning.crudjpa.domain.Usuario;
import lombok.extern.slf4j.Slf4j;

@Service("userDetailsService")
@Slf4j
public class UserService implements UserDetailsService {

    private IUserDAO userDAO;

    UserService(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) {

        Usuario usuario = userDAO.findByUser(username);

        if (usuario == null) {

            throw new UsernameNotFoundException(username);
        }

        ArrayList<GrantedAuthority> roles = new ArrayList<>();

        log.info("-------------------------------------------------------------------");
        for (Rol rol : usuario.getRoles()) {
            roles.add(new SimpleGrantedAuthority(rol.getRol_pk()));
            log.info("los roles para " + usuario.getUser() + " es " + rol.getRol_pk());
        }
        log.info("-------------------------------------------------------------------");

        return new User(usuario.getUser(), usuario.getPassword(), roles);
    }

}
