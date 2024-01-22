package co.com.cetus.learning.crudjpa.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class Usuario {

    @Id
    private String user;

    @NotEmpty
    private String password;

    @OneToMany
    @JoinColumn(name = "user")
    private List<Rol> roles;
}
