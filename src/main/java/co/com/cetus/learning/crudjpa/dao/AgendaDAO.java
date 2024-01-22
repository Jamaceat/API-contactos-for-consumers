package co.com.cetus.learning.crudjpa.dao;

import java.util.List;

import co.com.cetus.learning.crudjpa.domain.Contacto;

/**
 * AgendaDAO
 */
public interface AgendaDAO {

    void agregarContacto(Contacto contacto);

    Contacto recuperarContacto(String email);

    void eliminarContacto(String email);

    List<Contacto> devolverContactos();

    void eliminarContacto(int idContacto);

    Contacto recuperarContacto(int idContacto);

    void actualizarContacto(Contacto contacto);

}