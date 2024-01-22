package co.com.cetus.learning.crudjpa.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "contactos")
@NamedQuery(name = "Contacto.FindAll", query = "SELECT c from Contacto c")
public class Contacto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idContacto")
    private Integer idContacto;

    private int edad;

    private String email;

    private String nombre;

    public Contacto() {
    }

    public Contacto(Integer idContacto, int edad, String email, String nombre) {
        this.idContacto = idContacto;
        this.edad = edad;
        this.email = email;
        this.nombre = nombre;
    }

    public Integer getIdContacto() {
        return this.idContacto;
    }

    public void setIdContacto(Integer idContacto) {
        this.idContacto = idContacto;
    }

    public int getEdad() {
        return this.edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
