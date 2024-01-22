package co.com.cetus.learning.crudjpa.service;

import java.util.List;

import co.com.cetus.learning.crudjpa.domain.Contacto;

public interface AgendaService {

    boolean agregarContacto(Contacto contacto);

    List<Contacto> recuperar();

    void actualizar(Contacto contacto);

    boolean eliminarContacto(int idContacto);

    Contacto buscarContacto(int idContacto);

}
