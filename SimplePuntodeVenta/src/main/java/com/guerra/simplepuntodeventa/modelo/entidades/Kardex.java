/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guerra.simplepuntodeventa.modelo.entidades;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jaasiel
 */
@Entity
@Table(name = "kardex")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kardex.findAll", query = "SELECT k FROM Kardex k"),
    @NamedQuery(name = "Kardex.findByIdKardex", query = "SELECT k FROM Kardex k WHERE k.idKardex = :idKardex"),
    @NamedQuery(name = "Kardex.findByFecha", query = "SELECT k FROM Kardex k WHERE k.fecha = :fecha"),
    @NamedQuery(name = "Kardex.findByConcepto", query = "SELECT k FROM Kardex k WHERE k.concepto = :concepto"),
    @NamedQuery(name = "Kardex.findByExistenciaAnterior", query = "SELECT k FROM Kardex k WHERE k.existenciaAnterior = :existenciaAnterior"),
    @NamedQuery(name = "Kardex.findByTipo", query = "SELECT k FROM Kardex k WHERE k.tipo = :tipo"),
    @NamedQuery(name = "Kardex.findByCantidad", query = "SELECT k FROM Kardex k WHERE k.cantidad = :cantidad"),
    @NamedQuery(name = "Kardex.findByExistenciaPosterior", query = "SELECT k FROM Kardex k WHERE k.existenciaPosterior = :existenciaPosterior"),
    @NamedQuery(name = "Kardex.findByHoraCommit", query = "SELECT k FROM Kardex k WHERE k.horaCommit = :horaCommit"),
    @NamedQuery(name = "Kardex.findByFechaCommit", query = "SELECT k FROM Kardex k WHERE k.fechaCommit = :fechaCommit")})
public class Kardex implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_kardex")
    private Integer idKardex;
    @Basic(optional = false)
    @Column(name = "fecha")
    private String fecha;
    @Basic(optional = false)
    @Column(name = "concepto")
    private String concepto;
    @Basic(optional = false)
    @Column(name = "existencia_anterior")
    private int existenciaAnterior;
    @Basic(optional = false)
    @Column(name = "tipo")
    private int tipo;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    @Basic(optional = false)
    @Column(name = "existencia_posterior")
    private int existenciaPosterior;
    @Basic(optional = false)
    @Column(name = "hora_commit")
    @Temporal(TemporalType.TIME)
    private Date horaCommit;
    @Basic(optional = false)
    @Column(name = "fecha_commit")
    @Temporal(TemporalType.DATE)
    private Date fechaCommit;
    @JoinColumn(name = "id_articulo", referencedColumnName = "id_articulo")
    @ManyToOne(optional = false)
    private Articulo idArticulo;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;

    public Kardex() {
    }

    public Kardex(Integer idKardex) {
        this.idKardex = idKardex;
    }

    public Kardex(Integer idKardex, String fecha, String concepto, int existenciaAnterior, int tipo, int cantidad, int existenciaPosterior, Date horaCommit, Date fechaCommit) {
        this.idKardex = idKardex;
        this.fecha = fecha;
        this.concepto = concepto;
        this.existenciaAnterior = existenciaAnterior;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.existenciaPosterior = existenciaPosterior;
        this.horaCommit = horaCommit;
        this.fechaCommit = fechaCommit;
    }

    public Integer getIdKardex() {
        return idKardex;
    }

    public void setIdKardex(Integer idKardex) {
        this.idKardex = idKardex;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public int getExistenciaAnterior() {
        return existenciaAnterior;
    }

    public void setExistenciaAnterior(int existenciaAnterior) {
        this.existenciaAnterior = existenciaAnterior;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getExistenciaPosterior() {
        return existenciaPosterior;
    }

    public void setExistenciaPosterior(int existenciaPosterior) {
        this.existenciaPosterior = existenciaPosterior;
    }

    public Date getHoraCommit() {
        return horaCommit;
    }

    public void setHoraCommit(Date horaCommit) {
        this.horaCommit = horaCommit;
    }

    public Date getFechaCommit() {
        return fechaCommit;
    }

    public void setFechaCommit(Date fechaCommit) {
        this.fechaCommit = fechaCommit;
    }

    public Articulo getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Articulo idArticulo) {
        this.idArticulo = idArticulo;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKardex != null ? idKardex.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kardex)) {
            return false;
        }
        Kardex other = (Kardex) object;
        if ((this.idKardex == null && other.idKardex != null) || (this.idKardex != null && !this.idKardex.equals(other.idKardex))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guerra.spv.modelo.entidades.Kardex[ idKardex=" + idKardex + " ]";
    }
    
}
