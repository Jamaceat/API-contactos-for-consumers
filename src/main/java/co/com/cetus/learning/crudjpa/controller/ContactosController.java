package co.com.cetus.learning.crudjpa.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import co.com.cetus.learning.crudjpa.domain.Contacto;
import co.com.cetus.learning.crudjpa.service.AgendaService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@Slf4j
public class ContactosController {

    @Autowired
    private AgendaService service;

    @GetMapping(value = "contactos", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Contacto> getAllContacts() {
        validarAutenticacion();
        return service.recuperar();
    }

    @GetMapping(value = "contactos/{idContacto}")
    public Contacto getSingleContact(@PathVariable("idContacto") int idContacto) {
        validarAutenticacion();
        return service.buscarContacto(idContacto);
    }

    @PostMapping(value = "contactos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Contacto createContact(@RequestBody Contacto contacto) {
        if (service.agregarContacto(contacto)) {
            log.info("Entró a crear usuario");
            validarAutenticacion();
            return service.buscarContacto(contacto.getIdContacto());
        }
        return null;
    }

    @PutMapping(value = "contactos", consumes = MediaType.APPLICATION_JSON_VALUE)
    public int actualizarContacto(@RequestBody Contacto contacto) {
        service.actualizar(contacto);
        validarAutenticacion();
        if (service.buscarContacto(contacto.getIdContacto()) != null)
            return 1;
        return 0;
    }

    @DeleteMapping(value = "contactos/{idContacto}")
    public int deleteContacto(@PathVariable("idContacto") int idContacto) {
        validarAutenticacion();
        service.eliminarContacto(idContacto);
        if (service.buscarContacto(idContacto) == null)
            return 1;
        return 0;
    }

    public void validarAutenticacion() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();

        log.info("Autorizaciones");
        for (GrantedAuthority ga : authorities) {
            log.info("Autorizacion #:" + ga.getAuthority());
        }
        log.info("Fin Autorizaciones");
        log.info("Hace match con rol admin?:"
                + authorities.stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")));
        log.info("Llegue a recuperar");
    }

}
