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
@Table(name = "historial_compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistorialCompra.findAll", query = "SELECT h FROM HistorialCompra h"),
    @NamedQuery(name = "HistorialCompra.findByIdHistorialVenta", query = "SELECT h FROM HistorialCompra h WHERE h.idHistorialCompra = :idHistorialCompra"),
    @NamedQuery(name = "HistorialCompra.findByFecha", query = "SELECT h FROM HistorialCompra h WHERE h.fecha = :fecha"),
    @NamedQuery(name = "HistorialCompra.findByCantidad", query = "SELECT h FROM HistorialCompra h WHERE h.cantidad = :cantidad"),
    @NamedQuery(name = "HistorialCompra.findByTipo", query = "SELECT h FROM HistorialCompra h WHERE h.tipo = :tipo"),
    @NamedQuery(name = "HistorialCompra.findByTotal", query = "SELECT h FROM HistorialCompra h WHERE h.total = :total")})
public class HistorialCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_historial_compra")
    private Integer idHistorialCompra;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private long cantidad;
    @Basic(optional = false)
    @Column(name = "tipo")
    private int tipo;
    @Basic(optional = false)
    @Column(name = "total")
    private double total;

    public HistorialCompra() {
    }

    public HistorialCompra(Integer idHistorialCompra) {
        this.idHistorialCompra = idHistorialCompra;
    }

    public HistorialCompra(Integer idHistorialCompra, Date fecha, long cantidad, int tipo, double total) {
        this.idHistorialCompra = idHistorialCompra;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.total = total;
    }

    public Integer getIdHistorialCompra() {
        return idHistorialCompra;
    }

    public void setIdHistorialCompra(Integer idHistorialCompra) {
        this.idHistorialCompra = idHistorialCompra;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHistorialCompra != null ? idHistorialCompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistorialCompra)) {
            return false;
        }
        HistorialCompra other = (HistorialCompra) object;
        if ((this.idHistorialCompra == null && other.idHistorialCompra != null) || (this.idHistorialCompra != null && !this.idHistorialCompra.equals(other.idHistorialCompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guerra.simplepuntodeventa.modelo.entidades.HistorialCompra[ idHistorialCompra=" + idHistorialCompra + " ]";
    }
    
}
