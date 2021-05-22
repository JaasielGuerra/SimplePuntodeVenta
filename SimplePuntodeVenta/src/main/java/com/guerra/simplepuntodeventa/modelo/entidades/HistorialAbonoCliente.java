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
@Table(name = "historial_abono_cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistorialAbonoCliente.findAll", query = "SELECT h FROM HistorialAbonoCliente h"),
    @NamedQuery(name = "HistorialAbonoCliente.findByIdHistorialAbonoCliente", query = "SELECT h FROM HistorialAbonoCliente h WHERE h.idHistorialAbonoCliente = :idHistorialAbonoCliente"),
    @NamedQuery(name = "HistorialAbonoCliente.findByFecha", query = "SELECT h FROM HistorialAbonoCliente h WHERE h.fecha = :fecha"),
    @NamedQuery(name = "HistorialAbonoCliente.findByAbono", query = "SELECT h FROM HistorialAbonoCliente h WHERE h.abono = :abono"),
    @NamedQuery(name = "HistorialAbonoCliente.findByDocumento", query = "SELECT h FROM HistorialAbonoCliente h WHERE h.documento = :documento"),
    @NamedQuery(name = "HistorialAbonoCliente.findByHoraCommit", query = "SELECT h FROM HistorialAbonoCliente h WHERE h.horaCommit = :horaCommit"),
    @NamedQuery(name = "HistorialAbonoCliente.findByFechaCommit", query = "SELECT h FROM HistorialAbonoCliente h WHERE h.fechaCommit = :fechaCommit")})
public class HistorialAbonoCliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_historial_abono_cliente")
    private Integer idHistorialAbonoCliente;
    @Basic(optional = false)
    @Column(name = "fecha")
    private String fecha;
    @Basic(optional = false)
    @Column(name = "abono")
    private double abono;
    @Column(name = "documento")
    private String documento;
    @Basic(optional = false)
    @Column(name = "hora_commit")
    @Temporal(TemporalType.TIME)
    private Date horaCommit;
    @Basic(optional = false)
    @Column(name = "fecha_commit")
    @Temporal(TemporalType.DATE)
    private Date fechaCommit;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;
    @JoinColumn(name = "id_venta", referencedColumnName = "id_venta")
    @ManyToOne(optional = false)
    private Venta idVenta;

    public HistorialAbonoCliente() {
    }

    public HistorialAbonoCliente(Integer idHistorialAbonoCliente) {
        this.idHistorialAbonoCliente = idHistorialAbonoCliente;
    }

    public HistorialAbonoCliente(Integer idHistorialAbonoCliente, String fecha, double abono, Date horaCommit, Date fechaCommit) {
        this.idHistorialAbonoCliente = idHistorialAbonoCliente;
        this.fecha = fecha;
        this.abono = abono;
        this.horaCommit = horaCommit;
        this.fechaCommit = fechaCommit;
    }

    public Integer getIdHistorialAbonoCliente() {
        return idHistorialAbonoCliente;
    }

    public void setIdHistorialAbonoCliente(Integer idHistorialAbonoCliente) {
        this.idHistorialAbonoCliente = idHistorialAbonoCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getAbono() {
        return abono;
    }

    public void setAbono(double abono) {
        this.abono = abono;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Venta getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Venta idVenta) {
        this.idVenta = idVenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHistorialAbonoCliente != null ? idHistorialAbonoCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistorialAbonoCliente)) {
            return false;
        }
        HistorialAbonoCliente other = (HistorialAbonoCliente) object;
        if ((this.idHistorialAbonoCliente == null && other.idHistorialAbonoCliente != null) || (this.idHistorialAbonoCliente != null && !this.idHistorialAbonoCliente.equals(other.idHistorialAbonoCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guerra.spv.modelo.entidades.HistorialAbonoCliente[ idHistorialAbonoCliente=" + idHistorialAbonoCliente + " ]";
    }
    
}
