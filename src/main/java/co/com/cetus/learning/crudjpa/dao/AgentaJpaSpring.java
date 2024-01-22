package co.com.cetus.learning.crudjpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.com.cetus.learning.crudjpa.domain.Contacto;
import jakarta.transaction.Transactional;

/**
 * AgentaJpaSpring
 */
public interface AgentaJpaSpring extends JpaRepository<Contacto, Integer> {

    Contacto findByEmail(String email);

    @Transactional
    @Modifying
    @Query("Delete from Contacto c WHERE c.email=?1")
    void eliminarPorEmail(String email);

}