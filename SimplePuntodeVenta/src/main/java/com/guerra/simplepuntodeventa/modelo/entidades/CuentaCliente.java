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
@Table(name = "cuenta_cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuentaCliente.findAll", query = "SELECT c FROM CuentaCliente c"),
    @NamedQuery(name = "CuentaCliente.findByIdCuentaCliente", query = "SELECT c FROM CuentaCliente c WHERE c.idCuentaCliente = :idCuentaCliente"),
    @NamedQuery(name = "CuentaCliente.findByFecha", query = "SELECT c FROM CuentaCliente c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "CuentaCliente.findByComentario", query = "SELECT c FROM CuentaCliente c WHERE c.comentario = :comentario"),
    @NamedQuery(name = "CuentaCliente.findByCargo", query = "SELECT c FROM CuentaCliente c WHERE c.cargo = :cargo"),
    @NamedQuery(name = "CuentaCliente.findByAbono", query = "SELECT c FROM CuentaCliente c WHERE c.abono = :abono"),
    @NamedQuery(name = "CuentaCliente.findBySaldo", query = "SELECT c FROM CuentaCliente c WHERE c.saldo = :saldo"),
    @NamedQuery(name = "CuentaCliente.findByFechaCommit", query = "SELECT c FROM CuentaCliente c WHERE c.fechaCommit = :fechaCommit"),
    @NamedQuery(name = "CuentaCliente.findByHoraCommit", query = "SELECT c FROM CuentaCliente c WHERE c.horaCommit = :horaCommit")})
public class CuentaCliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cuenta_cliente")
    private Integer idCuentaCliente;
    @Basic(optional = false)
    @Column(name = "fecha")
    private String fecha;
    @Column(name = "comentario")
    private String comentario;
    @Basic(optional = false)
    @Column(name = "cargo")
    private double cargo;
    @Basic(optional = false)
    @Column(name = "abono")
    private double abono;
    @Basic(optional = false)
    @Column(name = "saldo")
    private double saldo;
    @Basic(optional = false)
    @Column(name = "fecha_commit")
    @Temporal(TemporalType.DATE)
    private Date fechaCommit;
    @Basic(optional = false)
    @Column(name = "hora_commit")
    @Temporal(TemporalType.TIME)
    private Date horaCommit;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;
    @JoinColumn(name = "id_venta", referencedColumnName = "id_venta")
    @ManyToOne(optional = false)
    private Venta idVenta;

    public CuentaCliente() {
    }

    public CuentaCliente(Integer idCuentaCliente) {
        this.idCuentaCliente = idCuentaCliente;
    }

    public CuentaCliente(Integer idCuentaCliente, String fecha, double cargo, double abono, double saldo, Date fechaCommit, Date horaCommit) {
        this.idCuentaCliente = idCuentaCliente;
        this.fecha = fecha;
        this.cargo = cargo;
        this.abono = abono;
        this.saldo = saldo;
        this.fechaCommit = fechaCommit;
        this.horaCommit = horaCommit;
    }

    public Integer getIdCuentaCliente() {
        return idCuentaCliente;
    }

    public void setIdCuentaCliente(Integer idCuentaCliente) {
        this.idCuentaCliente = idCuentaCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public double getCargo() {
        return cargo;
    }

    public void setCargo(double cargo) {
        this.cargo = cargo;
    }

    public double getAbono() {
        return abono;
    }

    public void setAbono(double abono) {
        this.abono = abono;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Date getFechaCommit() {
        return fechaCommit;
    }

    public void setFechaCommit(Date fechaCommit) {
        this.fechaCommit = fechaCommit;
    }

    public Date getHoraCommit() {
        return horaCommit;
    }

    public void setHoraCommit(Date horaCommit) {
        this.horaCommit = horaCommit;
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
        hash += (idCuentaCliente != null ? idCuentaCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuentaCliente)) {
            return false;
        }
        CuentaCliente other = (CuentaCliente) object;
        if ((this.idCuentaCliente == null && other.idCuentaCliente != null) || (this.idCuentaCliente != null && !this.idCuentaCliente.equals(other.idCuentaCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guerra.spv.modelo.entidades.CuentaCliente[ idCuentaCliente=" + idCuentaCliente + " ]";
    }
    
}
