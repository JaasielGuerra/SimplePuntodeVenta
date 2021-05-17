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
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "Usuario.findByUser", query = "SELECT u FROM Usuario u WHERE u.user = :user"),
    @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password"),
    @NamedQuery(name = "Usuario.findByFechaCommit", query = "SELECT u FROM Usuario u WHERE u.fechaCommit = :fechaCommit"),
    @NamedQuery(name = "Usuario.findByHoraCommit", query = "SELECT u FROM Usuario u WHERE u.horaCommit = :horaCommit"),
    @NamedQuery(name = "Usuario.findByEstado", query = "SELECT u FROM Usuario u WHERE u.estado = :estado")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "user")
    private String user;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "fecha_commit")
    @Temporal(TemporalType.DATE)
    private Date fechaCommit;
    @Basic(optional = false)
    @Column(name = "hora_commit")
    @Temporal(TemporalType.TIME)
    private Date horaCommit;
    @Basic(optional = false)
    @Column(name = "estado")
    private int estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<Compra> compraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<HistorialAbonoCliente> historialAbonoClienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<Ubicacion> ubicacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<Venta> ventaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<Servicio> servicioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<Categoria> categoriaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<HistorialAbonoProveedor> historialAbonoProveedorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<CuentaProveedor> cuentaProveedorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<Cliente> clienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<CuentaCliente> cuentaClienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<Marca> marcaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<Kardex> kardexList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<Proveedor> proveedorList;
    @JoinColumn(name = "id_privilegio", referencedColumnName = "id_privilegio")
    @ManyToOne(optional = false)
    private Privilegio idPrivilegio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<Articulo> articuloList;

    public Usuario() {
    }

    public Usuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario(Integer idUsuario, String nombre, String user, String password, Date fechaCommit, Date horaCommit, int estado) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.user = user;
        this.password = password;
        this.fechaCommit = fechaCommit;
        this.horaCommit = horaCommit;
        this.estado = estado;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Compra> getCompraList() {
        return compraList;
    }

    public void setCompraList(List<Compra> compraList) {
        this.compraList = compraList;
    }

    @XmlTransient
    public List<HistorialAbonoCliente> getHistorialAbonoClienteList() {
        return historialAbonoClienteList;
    }

    public void setHistorialAbonoClienteList(List<HistorialAbonoCliente> historialAbonoClienteList) {
        this.historialAbonoClienteList = historialAbonoClienteList;
    }

    @XmlTransient
    public List<Ubicacion> getUbicacionList() {
        return ubicacionList;
    }

    public void setUbicacionList(List<Ubicacion> ubicacionList) {
        this.ubicacionList = ubicacionList;
    }

    @XmlTransient
    public List<Venta> getVentaList() {
        return ventaList;
    }

    public void setVentaList(List<Venta> ventaList) {
        this.ventaList = ventaList;
    }

    @XmlTransient
    public List<Servicio> getServicioList() {
        return servicioList;
    }

    public void setServicioList(List<Servicio> servicioList) {
        this.servicioList = servicioList;
    }

    @XmlTransient
    public List<Categoria> getCategoriaList() {
        return categoriaList;
    }

    public void setCategoriaList(List<Categoria> categoriaList) {
        this.categoriaList = categoriaList;
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
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    @XmlTransient
    public List<CuentaCliente> getCuentaClienteList() {
        return cuentaClienteList;
    }

    public void setCuentaClienteList(List<CuentaCliente> cuentaClienteList) {
        this.cuentaClienteList = cuentaClienteList;
    }

    @XmlTransient
    public List<Marca> getMarcaList() {
        return marcaList;
    }

    public void setMarcaList(List<Marca> marcaList) {
        this.marcaList = marcaList;
    }

    @XmlTransient
    public List<Kardex> getKardexList() {
        return kardexList;
    }

    public void setKardexList(List<Kardex> kardexList) {
        this.kardexList = kardexList;
    }

    @XmlTransient
    public List<Proveedor> getProveedorList() {
        return proveedorList;
    }

    public void setProveedorList(List<Proveedor> proveedorList) {
        this.proveedorList = proveedorList;
    }

    public Privilegio getIdPrivilegio() {
        return idPrivilegio;
    }

    public void setIdPrivilegio(Privilegio idPrivilegio) {
        this.idPrivilegio = idPrivilegio;
    }

    @XmlTransient
    public List<Articulo> getArticuloList() {
        return articuloList;
    }

    public void setArticuloList(List<Articulo> articuloList) {
        this.articuloList = articuloList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guerra.simplepuntodeventa.modelo.entidades.Usuario[ idUsuario=" + idUsuario + " ]";
    }
    
}
