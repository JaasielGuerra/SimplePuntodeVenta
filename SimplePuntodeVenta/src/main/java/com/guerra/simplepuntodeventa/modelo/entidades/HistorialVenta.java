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
@Table(name = "historial_venta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistorialVenta.findAll", query = "SELECT h FROM HistorialVenta h"),
    @NamedQuery(name = "HistorialVenta.findByIdHistorialVenta", query = "SELECT h FROM HistorialVenta h WHERE h.idHistorialVenta = :idHistorialVenta"),
    @NamedQuery(name = "HistorialVenta.findByFecha", query = "SELECT h FROM HistorialVenta h WHERE h.fecha = :fecha"),
    @NamedQuery(name = "HistorialVenta.findByCantidad", query = "SELECT h FROM HistorialVenta h WHERE h.cantidad = :cantidad"),
    @NamedQuery(name = "HistorialVenta.findByTipo", query = "SELECT h FROM HistorialVenta h WHERE h.tipo = :tipo"),
    @NamedQuery(name = "HistorialVenta.findByTotal", query = "SELECT h FROM HistorialVenta h WHERE h.total = :total")})
public class HistorialVenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_historial_venta")
    private Integer idHistorialVenta;
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

    public HistorialVenta() {
    }

    public HistorialVenta(Integer idHistorialVenta) {
        this.idHistorialVenta = idHistorialVenta;
    }

    public HistorialVenta(Integer idHistorialVenta, Date fecha, long cantidad, int tipo, double total) {
        this.idHistorialVenta = idHistorialVenta;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.total = total;
    }

    public Integer getIdHistorialVenta() {
        return idHistorialVenta;
    }

    public void setIdHistorialVenta(Integer idHistorialVenta) {
        this.idHistorialVenta = idHistorialVenta;
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
        hash += (idHistorialVenta != null ? idHistorialVenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistorialVenta)) {
            return false;
        }
        HistorialVenta other = (HistorialVenta) object;
        if ((this.idHistorialVenta == null && other.idHistorialVenta != null) || (this.idHistorialVenta != null && !this.idHistorialVenta.equals(other.idHistorialVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guerra.simplepuntodeventa.modelo.entidades.HistorialVenta[ idHistorialVenta=" + idHistorialVenta + " ]";
    }
    
}
