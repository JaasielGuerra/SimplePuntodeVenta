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
@Table(name = "cuenta_proveedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuentaProveedor.findAll", query = "SELECT c FROM CuentaProveedor c"),
    @NamedQuery(name = "CuentaProveedor.findByIdCuentaProveedor", query = "SELECT c FROM CuentaProveedor c WHERE c.idCuentaProveedor = :idCuentaProveedor"),
    @NamedQuery(name = "CuentaProveedor.findByFecha", query = "SELECT c FROM CuentaProveedor c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "CuentaProveedor.findByComentario", query = "SELECT c FROM CuentaProveedor c WHERE c.comentario = :comentario"),
    @NamedQuery(name = "CuentaProveedor.findByCargo", query = "SELECT c FROM CuentaProveedor c WHERE c.cargo = :cargo"),
    @NamedQuery(name = "CuentaProveedor.findByAbono", query = "SELECT c FROM CuentaProveedor c WHERE c.abono = :abono"),
    @NamedQuery(name = "CuentaProveedor.findBySaldo", query = "SELECT c FROM CuentaProveedor c WHERE c.saldo = :saldo"),
    @NamedQuery(name = "CuentaProveedor.findByHoraCommit", query = "SELECT c FROM CuentaProveedor c WHERE c.horaCommit = :horaCommit"),
    @NamedQuery(name = "CuentaProveedor.findByFechaCommit", query = "SELECT c FROM CuentaProveedor c WHERE c.fechaCommit = :fechaCommit")})
public class CuentaProveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cuenta_proveedor")
    private Integer idCuentaProveedor;
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
    @Column(name = "hora_commit")
    @Temporal(TemporalType.TIME)
    private Date horaCommit;
    @Basic(optional = false)
    @Column(name = "fecha_commit")
    @Temporal(TemporalType.DATE)
    private Date fechaCommit;
    @JoinColumn(name = "id_compra", referencedColumnName = "id_compra")
    @ManyToOne(optional = false)
    private Compra idCompra;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;

    public CuentaProveedor() {
    }

    public CuentaProveedor(Integer idCuentaProveedor) {
        this.idCuentaProveedor = idCuentaProveedor;
    }

    public CuentaProveedor(Integer idCuentaProveedor, String fecha, double cargo, double abono, double saldo, Date horaCommit, Date fechaCommit) {
        this.idCuentaProveedor = idCuentaProveedor;
        this.fecha = fecha;
        this.cargo = cargo;
        this.abono = abono;
        this.saldo = saldo;
        this.horaCommit = horaCommit;
        this.fechaCommit = fechaCommit;
    }

    public Integer getIdCuentaProveedor() {
        return idCuentaProveedor;
    }

    public void setIdCuentaProveedor(Integer idCuentaProveedor) {
        this.idCuentaProveedor = idCuentaProveedor;
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

    public Compra getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Compra idCompra) {
        this.idCompra = idCompra;
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
        hash += (idCuentaProveedor != null ? idCuentaProveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuentaProveedor)) {
            return false;
        }
        CuentaProveedor other = (CuentaProveedor) object;
        if ((this.idCuentaProveedor == null && other.idCuentaProveedor != null) || (this.idCuentaProveedor != null && !this.idCuentaProveedor.equals(other.idCuentaProveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guerra.simplepuntodeventa.modelo.entidades.CuentaProveedor[ idCuentaProveedor=" + idCuentaProveedor + " ]";
    }
    
}
