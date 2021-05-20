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
@Table(name = "articulo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Articulo.findAll", query = "SELECT a FROM Articulo a"),
    @NamedQuery(name = "Articulo.findByIdArticulo", query = "SELECT a FROM Articulo a WHERE a.idArticulo = :idArticulo"),
    @NamedQuery(name = "Articulo.findByCod1", query = "SELECT a FROM Articulo a WHERE a.cod1 = :cod1"),
    @NamedQuery(name = "Articulo.findByCod2", query = "SELECT a FROM Articulo a WHERE a.cod2 = :cod2"),
    @NamedQuery(name = "Articulo.findByCod3", query = "SELECT a FROM Articulo a WHERE a.cod3 = :cod3"),
    @NamedQuery(name = "Articulo.findByNombre", query = "SELECT a FROM Articulo a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Articulo.findByDescripcion", query = "SELECT a FROM Articulo a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "Articulo.findByCantidad", query = "SELECT a FROM Articulo a WHERE a.cantidad = :cantidad"),
    @NamedQuery(name = "Articulo.findByMinExistencia", query = "SELECT a FROM Articulo a WHERE a.minExistencia = :minExistencia"),
    @NamedQuery(name = "Articulo.findByPrecioCompra", query = "SELECT a FROM Articulo a WHERE a.precioCompra = :precioCompra"),
    @NamedQuery(name = "Articulo.findByPrecioVenta", query = "SELECT a FROM Articulo a WHERE a.precioVenta = :precioVenta"),
    @NamedQuery(name = "Articulo.findByPorcentajeGanancia", query = "SELECT a FROM Articulo a WHERE a.porcentajeGanancia = :porcentajeGanancia"),
    @NamedQuery(name = "Articulo.findByGanancia", query = "SELECT a FROM Articulo a WHERE a.ganancia = :ganancia"),
    @NamedQuery(name = "Articulo.findByEstado", query = "SELECT a FROM Articulo a WHERE a.estado = :estado"),
    @NamedQuery(name = "Articulo.findByFechaCommit", query = "SELECT a FROM Articulo a WHERE a.fechaCommit = :fechaCommit"),
    @NamedQuery(name = "Articulo.findByHoraCommit", query = "SELECT a FROM Articulo a WHERE a.horaCommit = :horaCommit")})
public class Articulo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_articulo")
    private Integer idArticulo;
    @Basic(optional = false)
    @Column(name = "cod1")
    private String cod1;
    @Column(name = "cod2")
    private String cod2;
    @Column(name = "cod3")
    private String cod3;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    @Basic(optional = false)
    @Column(name = "min_existencia")
    private int minExistencia;
    @Basic(optional = false)
    @Column(name = "precio_compra")
    private double precioCompra;
    @Basic(optional = false)
    @Column(name = "precio_venta")
    private double precioVenta;
    @Basic(optional = false)
    @Column(name = "porcentaje_ganancia")
    private double porcentajeGanancia;
    @Basic(optional = false)
    @Column(name = "ganancia")
    private double ganancia;
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
    @OneToMany(mappedBy = "idArticulo")
    private List<DetalleVenta> detalleVentaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idArticulo")
    private List<Kardex> kardexList;
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")
    @ManyToOne(optional = false)
    private Categoria idCategoria;
    @JoinColumn(name = "id_marca", referencedColumnName = "id_marca")
    @ManyToOne(optional = false)
    private Marca idMarca;
    @JoinColumn(name = "id_ubicacion", referencedColumnName = "id_ubicacion")
    @ManyToOne(optional = false)
    private Ubicacion idUbicacion;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idArticulo")
    private List<DetalleCompra> detalleCompraList;

    public Articulo() {
    }

    public Articulo(Integer idArticulo) {
        this.idArticulo = idArticulo;
    }

    public Articulo(Integer idArticulo, String cod1, String nombre, String descripcion, int cantidad, int minExistencia, double precioCompra, double precioVenta, double porcentajeGanancia, double ganancia, int estado, Date fechaCommit, Date horaCommit) {
        this.idArticulo = idArticulo;
        this.cod1 = cod1;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.minExistencia = minExistencia;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.porcentajeGanancia = porcentajeGanancia;
        this.ganancia = ganancia;
        this.estado = estado;
        this.fechaCommit = fechaCommit;
        this.horaCommit = horaCommit;
    }

    public Integer getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Integer idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getCod1() {
        return cod1;
    }

    public void setCod1(String cod1) {
        this.cod1 = cod1;
    }

    public String getCod2() {
        return cod2;
    }

    public void setCod2(String cod2) {
        this.cod2 = cod2;
    }

    public String getCod3() {
        return cod3;
    }

    public void setCod3(String cod3) {
        this.cod3 = cod3;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getMinExistencia() {
        return minExistencia;
    }

    public void setMinExistencia(int minExistencia) {
        this.minExistencia = minExistencia;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public double getPorcentajeGanancia() {
        return porcentajeGanancia;
    }

    public void setPorcentajeGanancia(double porcentajeGanancia) {
        this.porcentajeGanancia = porcentajeGanancia;
    }

    public double getGanancia() {
        return ganancia;
    }

    public void setGanancia(double ganancia) {
        this.ganancia = ganancia;
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

    @XmlTransient
    public List<DetalleVenta> getDetalleVentaList() {
        return detalleVentaList;
    }

    public void setDetalleVentaList(List<DetalleVenta> detalleVentaList) {
        this.detalleVentaList = detalleVentaList;
    }

    @XmlTransient
    public List<Kardex> getKardexList() {
        return kardexList;
    }

    public void setKardexList(List<Kardex> kardexList) {
        this.kardexList = kardexList;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Marca getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Marca idMarca) {
        this.idMarca = idMarca;
    }

    public Ubicacion getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Ubicacion idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
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
        hash += (idArticulo != null ? idArticulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Articulo)) {
            return false;
        }
        Articulo other = (Articulo) object;
        if ((this.idArticulo == null && other.idArticulo != null) || (this.idArticulo != null && !this.idArticulo.equals(other.idArticulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.descripcion;
    }
    
}
