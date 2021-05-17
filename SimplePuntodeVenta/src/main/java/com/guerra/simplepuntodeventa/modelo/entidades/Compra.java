/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guerra.simplepuntodeventa.modelo.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jaasiel
 */
@Entity
@Table(name = "compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c"),
    @NamedQuery(name = "Compra.findByIdCompra", query = "SELECT c FROM Compra c WHERE c.idCompra = :idCompra"),
    @NamedQuery(name = "Compra.findByFecha", query = "SELECT c FROM Compra c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "Compra.findByNoFactura", query = "SELECT c FROM Compra c WHERE c.noFactura = :noFactura"),
    @NamedQuery(name = "Compra.findBySerieFactura", query = "SELECT c FROM Compra c WHERE c.serieFactura = :serieFactura"),
    @NamedQuery(name = "Compra.findByTotal", query = "SELECT c FROM Compra c WHERE c.total = :total"),
    @NamedQuery(name = "Compra.findByEstado", query = "SELECT c FROM Compra c WHERE c.estado = :estado"),
    @NamedQuery(name = "Compra.findByFechaCommit", query = "SELECT c FROM Compra c WHERE c.fechaCommit = :fechaCommit"),
    @NamedQuery(name = "Compra.findByHoraCommit", query = "SELECT c FROM Compra c WHERE c.horaCommit = :horaCommit"),
    @NamedQuery(name = "Compra.findBySaldo", query = "SELECT c FROM Compra c WHERE c.saldo = :saldo")})
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_compra")
    private Integer idCompra;
    @Basic(optional = false)
    @Column(name = "fecha")
    private String fecha;
    @Basic(optional = false)
    @Column(name = "no_factura")
    private String noFactura;
    @Basic(optional = false)
    @Column(name = "serie_factura")
    private String serieFactura;
    @Basic(optional = false)
    @Column(name = "total")
    private double total;
    @Basic(optional = false)
    @Column(name = "estado")
    private int estado;
    @Basic(optional = false)
    @Column(name = "fecha_commit")
    @Temporal(TemporalType.DATE)
    private Date fechaCommit;
    @Basic(optional = false)
    @Column(name = "hora_commit")
    @Temporal(TemporalType.TIME)
    private Date horaCommit;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "saldo")
    private Double saldo;
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor")
    @ManyToOne(optional = false)
    private Proveedor idProveedor;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCompra")
    private List<HistorialAbonoProveedor> historialAbonoProveedorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCompra")
    private List<CuentaProveedor> cuentaProveedorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCompra")
    private List<DetalleCompra> detalleCompraList;

    public Compra() {
    }

    public Compra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public Compra(Integer idCompra, String fecha, String noFactura, String serieFactura, double total, int estado, Date fechaCommit, Date horaCommit) {
        this.idCompra = idCompra;
        this.fecha = fecha;
        this.noFactura = noFactura;
        this.serieFactura = serieFactura;
        this.total = total;
        this.estado = estado;
        this.fechaCommit = fechaCommit;
        this.horaCommit = horaCommit;
    }

    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNoFactura() {
        return noFactura;
    }

    public void setNoFactura(String noFactura) {
        this.noFactura = noFactura;
    }

    public String getSerieFactura() {
        return serieFactura;
    }

    public void setSerieFactura(String serieFactura) {
        this.serieFactura = serieFactura;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
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

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Proveedor getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Proveedor idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @XmlTransient
    public List<HistorialAbonoProveedor> getHistorialAbonoProveedorList() {
        return historialAbonoProveedorList;
    }

    public void setHistorialAbonoProveedorList(List<HistorialAbonoProveedor> historialAbonoProveedorList) {
        this.historialAbonoProveedorList = historialAbonoProveedorList;
    }

    @XmlTransient
    public List<CuentaProveedor> getCuentaProveedorList() {
        return cuentaProveedorList;
    }

    public void setCuentaProveedorList(List<CuentaProveedor> cuentaProveedorList) {
        this.cuentaProveedorList = cuentaProveedorList;
    }

    @XmlTransient
    public List<DetalleCompra> getDetalleCompraList() {
        return detalleCompraList;
    }

    public void setDetalleCompraList(List<DetalleCompra> detalleCompraList) {
        this.detalleCompraList = detalleCompraList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCompra != null ? idCompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        if ((this.idCompra == null && other.idCompra != null) || (this.idCompra != null && !this.idCompra.equals(other.idCompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guerra.simplepuntodeventa.modelo.entidades.Compra[ idCompra=" + idCompra + " ]";
    }
    
}
