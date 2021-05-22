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
@Table(name = "historial_abono_proveedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistorialAbonoProveedor.findAll", query = "SELECT h FROM HistorialAbonoProveedor h"),
    @NamedQuery(name = "HistorialAbonoProveedor.findByIdHistorialAbonoProveedor", query = "SELECT h FROM HistorialAbonoProveedor h WHERE h.idHistorialAbonoProveedor = :idHistorialAbonoProveedor"),
    @NamedQuery(name = "HistorialAbonoProveedor.findByFecha", query = "SELECT h FROM HistorialAbonoProveedor h WHERE h.fecha = :fecha"),
    @NamedQuery(name = "HistorialAbonoProveedor.findByAbono", query = "SELECT h FROM HistorialAbonoProveedor h WHERE h.abono = :abono"),
    @NamedQuery(name = "HistorialAbonoProveedor.findByDocumento", query = "SELECT h FROM HistorialAbonoProveedor h WHERE h.documento = :documento"),
    @NamedQuery(name = "HistorialAbonoProveedor.findByFechaCommit", query = "SELECT h FROM HistorialAbonoProveedor h WHERE h.fechaCommit = :fechaCommit"),
    @NamedQuery(name = "HistorialAbonoProveedor.findByHoraCommit", query = "SELECT h FROM HistorialAbonoProveedor h WHERE h.horaCommit = :horaCommit")})
public class HistorialAbonoProveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_historial_abono_proveedor")
    private Integer idHistorialAbonoProveedor;
    @Basic(optional = false)
    @Column(name = "fecha")
    private String fecha;
    @Basic(optional = false)
    @Column(name = "abono")
    private double abono;
    @Column(name = "documento")
    private String documento;
    @Basic(optional = false)
    @Column(name = "fecha_commit")
    @Temporal(TemporalType.DATE)
    private Date fechaCommit;
    @Basic(optional = false)
    @Column(name = "hora_commit")
    @Temporal(TemporalType.TIME)
    private Date horaCommit;
    @JoinColumn(name = "id_compra", referencedColumnName = "id_compra")
    @ManyToOne(optional = false)
    private Compra idCompra;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;

    public HistorialAbonoProveedor() {
    }

    public HistorialAbonoProveedor(Integer idHistorialAbonoProveedor) {
        this.idHistorialAbonoProveedor = idHistorialAbonoProveedor;
    }

    public HistorialAbonoProveedor(Integer idHistorialAbonoProveedor, String fecha, double abono, Date fechaCommit, Date horaCommit) {
        this.idHistorialAbonoProveedor = idHistorialAbonoProveedor;
        this.fecha = fecha;
        this.abono = abono;
        this.fechaCommit = fechaCommit;
        this.horaCommit = horaCommit;
    }

    public Integer getIdHistorialAbonoProveedor() {
        return idHistorialAbonoProveedor;
    }

    public void setIdHistorialAbonoProveedor(Integer idHistorialAbonoProveedor) {
        this.idHistorialAbonoProveedor = idHistorialAbonoProveedor;
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
        hash += (idHistorialAbonoProveedor != null ? idHistorialAbonoProveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistorialAbonoProveedor)) {
            return false;
        }
        HistorialAbonoProveedor other = (HistorialAbonoProveedor) object;
        if ((this.idHistorialAbonoProveedor == null && other.idHistorialAbonoProveedor != null) || (this.idHistorialAbonoProveedor != null && !this.idHistorialAbonoProveedor.equals(other.idHistorialAbonoProveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guerra.spv.modelo.entidades.HistorialAbonoProveedor[ idHistorialAbonoProveedor=" + idHistorialAbonoProveedor + " ]";
    }
    
}
