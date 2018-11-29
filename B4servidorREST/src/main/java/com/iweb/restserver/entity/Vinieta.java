/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.entity;

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
 * @author jose
 */
@Entity
@Table(name = "VINIETA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vinieta.findAll", query = "SELECT v FROM Vinieta v")
    , @NamedQuery(name = "Vinieta.findById", query = "SELECT v FROM Vinieta v WHERE v.id = :id")
    , @NamedQuery(name = "Vinieta.findByNombre", query = "SELECT v FROM Vinieta v WHERE v.nombre = :nombre")
    , @NamedQuery(name = "Vinieta.findByFecha", query = "SELECT v FROM Vinieta v WHERE v.fecha = :fecha")
    , @NamedQuery(name = "Vinieta.findByPuntuacion", query = "SELECT v FROM Vinieta v WHERE v.puntuacion = :puntuacion")})
public class Vinieta implements Serializable {

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
    @Column(name = "PUNTUACION")
    private Integer puntuacion;
    @JoinColumn(name = "IDSERIE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Serie idserie;

    public Vinieta() {
    }

    public Vinieta(Integer id) {
        this.id = id;
    }

    public Vinieta(Integer id, String nombre) {
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

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
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
        if (!(object instanceof Vinieta)) {
            return false;
        }
        Vinieta other = (Vinieta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iweb.restserver.entity.Vinieta[ id=" + id + " ]";
    }
    
}
