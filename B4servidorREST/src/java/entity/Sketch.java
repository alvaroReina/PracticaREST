/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alvaro
 */
@Entity
@Table(name = "SKETCH")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sketch.findAll", query = "SELECT s FROM Sketch s")
    , @NamedQuery(name = "Sketch.findById", query = "SELECT s FROM Sketch s WHERE s.id = :id")
    , @NamedQuery(name = "Sketch.findByNombre", query = "SELECT s FROM Sketch s WHERE s.nombre = :nombre")
    , @NamedQuery(name = "Sketch.findByFecha", query = "SELECT s FROM Sketch s WHERE s.fecha = :fecha")})
public class Sketch implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "IDSERIE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Serie idserie;

    public Sketch() {
    }

    public Sketch(Integer id) {
        this.id = id;
    }

    public Sketch(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Serie getIdserie() {
        return idserie;
    }

    public void setIdserie(Serie idserie) {
        this.idserie = idserie;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sketch)) {
            return false;
        }
        Sketch other = (Sketch) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Sketch[ id=" + id + " ]";
    }
    
}
