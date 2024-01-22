package co.com.cetus.learning.crudjpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.cetus.learning.crudjpa.domain.Usuario;

@Repository
public interface IUserDAO extends JpaRepository<Usuario, String> {

    Usuario findByUser(String user);

}
