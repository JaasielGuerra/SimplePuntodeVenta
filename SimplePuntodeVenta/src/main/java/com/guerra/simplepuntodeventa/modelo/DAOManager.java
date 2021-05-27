/*
 * Objeto que contiene todos los objetos DAO para tener una unica instancia
 * haciendo uso del patron singleton
 */
package com.guerra.simplepuntodeventa.modelo;

import com.guerra.simplepuntodeventa.modelo.dao.ArticuloDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.CategoriaDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.ClienteDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.CompraDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.DetalleCompraDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.DetalleVentaDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.HistorialAbonoClienteDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.KardexDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.MarcaDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.CuentaClienteDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.CuentaProveedorDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.EmpresaDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.HistorialAbonoProveedorDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.ProveedorDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.ServicioDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.UbicacionDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.UsuarioDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.VentaDAOImpl;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jaasiel Guerra
 */
public class DAOManager {

    private static DAOManager dAOManager;

    private final EntityManagerFactory emf = EMFactory.getEMFactory();

    private final ArticuloDAOImpl articuloDAO;
    private final CategoriaDAOImpl categoriaDAO;
    private final ClienteDAOImpl clienteDAO;
    private final CompraDAOImpl compraDAO;
    private final DetalleCompraDAOImpl detalleCompraDAO;
    private final VentaDAOImpl ventaDAO;
    private final DetalleVentaDAOImpl detalleVentaDAO;
    private final HistorialAbonoClienteDAOImpl historialAbonoClienteDAO;
    private final CuentaClienteDAOImpl movimientoClienteDAO;
    private final ProveedorDAOImpl proveedorDAO;
    private final UsuarioDAOImpl usuarioDAO;
    private final KardexDAOImpl kardexDAO;
    private final MarcaDAOImpl marcaDAO;
    private final EmpresaDAOImpl empresaDAO;
    private final UbicacionDAOImpl ubicacionDAO;
    private final CuentaProveedorDAOImpl cuentaProveedorDAO;
    private final HistorialAbonoProveedorDAOImpl historialAbonoProveedorDAO;
    private final ServicioDAOImpl servicioDAO;

    //evitar instancias con new
    private DAOManager() {
        articuloDAO = new ArticuloDAOImpl(emf);
        categoriaDAO = new CategoriaDAOImpl(emf);
        clienteDAO = new ClienteDAOImpl(emf);
        compraDAO = new CompraDAOImpl(emf);
        detalleCompraDAO = new DetalleCompraDAOImpl(emf);
        ventaDAO = new VentaDAOImpl(emf);
        detalleVentaDAO = new DetalleVentaDAOImpl(emf);
        historialAbonoClienteDAO = new HistorialAbonoClienteDAOImpl(emf);
        movimientoClienteDAO = new CuentaClienteDAOImpl(emf);
        proveedorDAO = new ProveedorDAOImpl(emf);
        usuarioDAO = new UsuarioDAOImpl(emf);
        kardexDAO = new KardexDAOImpl(emf);
        marcaDAO = new MarcaDAOImpl(emf);
        empresaDAO = new EmpresaDAOImpl(emf);
        ubicacionDAO = new UbicacionDAOImpl(emf);
        cuentaProveedorDAO = new CuentaProveedorDAOImpl(emf);
        historialAbonoProveedorDAO = new HistorialAbonoProveedorDAOImpl(emf);
        servicioDAO = new ServicioDAOImpl(emf);
    }

    public static DAOManager getInstancia() {
        if (dAOManager == null) {
            dAOManager = new DAOManager();
        }
        return dAOManager;
    }

    public ArticuloDAOImpl getArticuloDAO() {
        return articuloDAO;
    }

    public CategoriaDAOImpl getCategoriaDAO() {
        return categoriaDAO;
    }

    public ClienteDAOImpl getClienteDAO() {
        return clienteDAO;
    }

    public CompraDAOImpl getCompraDAO() {
        return compraDAO;
    }

    public DetalleCompraDAOImpl getDetalleCompraDAO() {
        return detalleCompraDAO;
    }

    public VentaDAOImpl getVentaDAO() {
        return ventaDAO;
    }

    public DetalleVentaDAOImpl getDetalleVentaDAO() {
        return detalleVentaDAO;
    }

    public HistorialAbonoClienteDAOImpl getHistorialAbonoClienteDAO() {
        return historialAbonoClienteDAO;
    }

    public CuentaClienteDAOImpl getMovimientoClienteDAO() {
        return movimientoClienteDAO;
    }

    public ProveedorDAOImpl getProveedorDAO() {
        return proveedorDAO;
    }

    public UsuarioDAOImpl getUsuarioDAO() {
        return usuarioDAO;
    }

    public KardexDAOImpl getKardexDAO() {
        return kardexDAO;
    }

    public MarcaDAOImpl getMarcaDAO() {
        return marcaDAO;
    }

    public EmpresaDAOImpl getEmpresaDAO() {
        return empresaDAO;
    }

    public UbicacionDAOImpl getUbicacionDAO() {
        return ubicacionDAO;
    }

    public CuentaProveedorDAOImpl getCuentaProveedorDAO() {
        return cuentaProveedorDAO;
    }

    public HistorialAbonoProveedorDAOImpl getHistorialAbonoProveedorDAO() {
        return historialAbonoProveedorDAO;
    }

    public ServicioDAOImpl getServicioDAO() {
        return servicioDAO;
    }

}
