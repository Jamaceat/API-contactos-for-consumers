package co.com.cetus.learning.crudjpa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.com.cetus.learning.crudjpa.dao.AgendaDaoImpl;
import co.com.cetus.learning.crudjpa.domain.Contacto;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AgendaServiceImpl implements AgendaService {
    final AgendaDaoImpl dao;

    AgendaServiceImpl(AgendaDaoImpl agendaDaoImpl) {
        this.dao = agendaDaoImpl;
    }

    @Override
    public boolean agregarContacto(Contacto contacto) {
        log.info("Agregando contacto " + contacto.getNombre());
        if (contacto.getIdContacto() == null) {
            dao.agregarContacto(contacto);
            return true;
        }
        return false;
    }

    @Override
    public List<Contacto> recuperar() {

        log.info("Recuperar capa 1 todos contactos");
        return dao.devolverContactos();
    }

    @Override
    public void actualizar(Contacto contacto) {
        log.info("Actualizando contacto");
        dao.actualizarContacto(contacto);
    }

    @Override
    public boolean eliminarContacto(int idContacto) {
        dao.eliminarContacto(idContacto);
        return dao.recuperarContacto(idContacto) == null;

    }

    @Override
    public Contacto buscarContacto(int idContacto) {
        return dao.recuperarContacto(idContacto);
    }

}
