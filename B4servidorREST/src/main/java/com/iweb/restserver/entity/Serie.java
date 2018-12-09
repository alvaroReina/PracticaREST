/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jose
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Serie.findAll", query = "SELECT s FROM Serie s")
    , @NamedQuery(name = "Serie.findById", query = "SELECT s FROM Serie s WHERE s.id = :id")
    , @NamedQuery(name = "Serie.findByTitle", query = "SELECT s FROM Serie s WHERE s.title = :title")
    , @NamedQuery(name = "Serie.findByScore", query = "SELECT s FROM Serie s WHERE s.score = :score")
    , @NamedQuery(name = "Serie.findByViews", query = "SELECT s FROM Serie s WHERE s.views = :views")
    , @NamedQuery(name = "Serie.findByPicture", query = "SELECT s FROM Serie s WHERE s.picture = :picture")})
public class Serie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    private String title;
    private Integer score;
    private Integer views;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    private String picture;
    @JoinColumn(name = "AUTHOR", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Userinfo author;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idserie", fetch = FetchType.LAZY)
    //@JsonManagedReference
    private List<Sketch> sketchList;

    public Serie() {
    }

    public Serie(Integer id) {
        this.id = id;
    }

    public Serie(Integer id, String title, String picture) {
        this.id = id;
        this.title = title;
        this.picture = picture;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Userinfo getAuthor() {
        return author;
    }

    public void setAuthor(Userinfo author) {
        this.author = author;
    }

    @XmlTransient
    @JsonIgnore
    public List<Sketch> getSketchList() {
        return sketchList;
    }

    public void setSketchList(List<Sketch> sketchList) {
        this.sketchList = sketchList;
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
        if (!(object instanceof Serie)) {
            return false;
        }
        Serie other = (Serie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iweb.restserver.entity.Serie[ id=" + id + " ]";
    }
    
}
