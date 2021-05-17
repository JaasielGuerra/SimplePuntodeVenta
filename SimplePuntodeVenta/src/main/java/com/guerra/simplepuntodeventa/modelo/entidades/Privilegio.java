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
@Table(name = "privilegio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Privilegio.findAll", query = "SELECT p FROM Privilegio p"),
    @NamedQuery(name = "Privilegio.findByIdPrivilegio", query = "SELECT p FROM Privilegio p WHERE p.idPrivilegio = :idPrivilegio"),
    @NamedQuery(name = "Privilegio.findByDescripcion", query = "SELECT p FROM Privilegio p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Privilegio.findByEstado", query = "SELECT p FROM Privilegio p WHERE p.estado = :estado"),
    @NamedQuery(name = "Privilegio.findByFechaCommit", query = "SELECT p FROM Privilegio p WHERE p.fechaCommit = :fechaCommit"),
    @NamedQuery(name = "Privilegio.findByHoraCommit", query = "SELECT p FROM Privilegio p WHERE p.horaCommit = :horaCommit"),
    @NamedQuery(name = "Privilegio.findByVentas", query = "SELECT p FROM Privilegio p WHERE p.ventas = :ventas"),
    @NamedQuery(name = "Privilegio.findByArticulos", query = "SELECT p FROM Privilegio p WHERE p.articulos = :articulos"),
    @NamedQuery(name = "Privilegio.findByInventario", query = "SELECT p FROM Privilegio p WHERE p.inventario = :inventario"),
    @NamedQuery(name = "Privilegio.findByCompras", query = "SELECT p FROM Privilegio p WHERE p.compras = :compras"),
    @NamedQuery(name = "Privilegio.findByClientes", query = "SELECT p FROM Privilegio p WHERE p.clientes = :clientes"),
    @NamedQuery(name = "Privilegio.findByReportes", query = "SELECT p FROM Privilegio p WHERE p.reportes = :reportes"),
    @NamedQuery(name = "Privilegio.findByConfiguracion", query = "SELECT p FROM Privilegio p WHERE p.configuracion = :configuracion"),
    @NamedQuery(name = "Privilegio.findByServicio", query = "SELECT p FROM Privilegio p WHERE p.servicio = :servicio"),
    @NamedQuery(name = "Privilegio.findByProveedores", query = "SELECT p FROM Privilegio p WHERE p.proveedores = :proveedores")})
public class Privilegio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_privilegio")
    private Integer idPrivilegio;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
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
    @Basic(optional = false)
    @Column(name = "ventas")
    private int ventas;
    @Basic(optional = false)
    @Column(name = "articulos")
    private int articulos;
    @Basic(optional = false)
    @Column(name = "inventario")
    private int inventario;
    @Basic(optional = false)
    @Column(name = "compras")
    private int compras;
    @Basic(optional = false)
    @Column(name = "clientes")
    private int clientes;
    @Basic(optional = false)
    @Column(name = "reportes")
    private int reportes;
    @Basic(optional = false)
    @Column(name = "configuracion")
    private int configuracion;
    @Basic(optional = false)
    @Column(name = "servicio")
    private int servicio;
    @Basic(optional = false)
    @Column(name = "proveedores")
    private int proveedores;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPrivilegio")
    private List<Usuario> usuarioList;

    public Privilegio() {
    }

    public Privilegio(Integer idPrivilegio) {
        this.idPrivilegio = idPrivilegio;
    }

    public Privilegio(Integer idPrivilegio, String descripcion, int estado, Date fechaCommit, Date horaCommit, int ventas, int articulos, int inventario, int compras, int clientes, int reportes, int configuracion, int servicio, int proveedores) {
        this.idPrivilegio = idPrivilegio;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaCommit = fechaCommit;
        this.horaCommit = horaCommit;
        this.ventas = ventas;
        this.articulos = articulos;
        this.inventario = inventario;
        this.compras = compras;
        this.clientes = clientes;
        this.reportes = reportes;
        this.configuracion = configuracion;
        this.servicio = servicio;
        this.proveedores = proveedores;
    }

    public Integer getIdPrivilegio() {
        return idPrivilegio;
    }

    public void setIdPrivilegio(Integer idPrivilegio) {
        this.idPrivilegio = idPrivilegio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public int getVentas() {
        return ventas;
    }

    public void setVentas(int ventas) {
        this.ventas = ventas;
    }

    public int getArticulos() {
        return articulos;
    }

    public void setArticulos(int articulos) {
        this.articulos = articulos;
    }

    public int getInventario() {
        return inventario;
    }

    public void setInventario(int inventario) {
        this.inventario = inventario;
    }

    public int getCompras() {
        return compras;
    }

    public void setCompras(int compras) {
        this.compras = compras;
    }

    public int getClientes() {
        return clientes;
    }

    public void setClientes(int clientes) {
        this.clientes = clientes;
    }

    public int getReportes() {
        return reportes;
    }

    public void setReportes(int reportes) {
        this.reportes = reportes;
    }

    public int getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(int configuracion) {
        this.configuracion = configuracion;
    }

    public int getServicio() {
        return servicio;
    }

    public void setServicio(int servicio) {
        this.servicio = servicio;
    }

    public int getProveedores() {
        return proveedores;
    }

    public void setProveedores(int proveedores) {
        this.proveedores = proveedores;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrivilegio != null ? idPrivilegio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Privilegio)) {
            return false;
        }
        Privilegio other = (Privilegio) object;
        if ((this.idPrivilegio == null && other.idPrivilegio != null) || (this.idPrivilegio != null && !this.idPrivilegio.equals(other.idPrivilegio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guerra.simplepuntodeventa.modelo.entidades.Privilegio[ idPrivilegio=" + idPrivilegio + " ]";
    }
    
}
