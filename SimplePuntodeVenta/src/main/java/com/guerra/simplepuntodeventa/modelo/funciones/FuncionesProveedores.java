package com.guerra.simplepuntodeventa.modelo.funciones;

import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.Parameter;
import com.guerra.simplepuntodeventa.modelo.dao.CompraDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.ProveedorDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Compra;
import com.guerra.simplepuntodeventa.modelo.entidades.Proveedor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jaasiel
 */
public class FuncionesProveedores {

    private static ProveedorDAOImpl proveedorDAO;
    private static CompraDAOImpl compraDAO;

    private FuncionesProveedores() {

    }

    /**
     * Consultar proveedores con estado activo, excluyendo al Proveedor por
     * defecto de PROVEEDOR PRINCIPAL
     *
     * @param estado
     * @return
     */
    public static List<Proveedor> consultarProveedoresEstado(int estado) {
        if (proveedorDAO == null) {
            proveedorDAO = DAOManager.getInstancia().getProveedorDAO();
        }
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("estado", estado));

        List<Proveedor> proveedores = proveedorDAO.readByQuery(
                "SELECT p FROM Proveedor p "
                + "WHERE p.estado = :estado "
                + "AND NOT p.idProveedor = 1",
                p
        );
        return proveedores;
    }

    /**
     * Buscar un proveedor por su nombre y estado
     *
     * @param buscar
     * @param estado
     * @return
     */
    public static List<Proveedor> buscarProveedorNombreEstado(String buscar, int estado) {
        if (proveedorDAO == null) {
            proveedorDAO = DAOManager.getInstancia().getProveedorDAO();
        }
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("buscar", "%" + buscar + "%"));
        p.add(new Parameter("estado", estado));
        List<Proveedor> proveedores = proveedorDAO.readByQuery(
                "SELECT p FROM Proveedor p "
                + "WHERE p.estado = :estado "
                + "AND p.nombre LIKE :buscar "
                + "AND NOT p.idProveedor = 1",
                p
        );
        return proveedores;
    }

    /**
     * Consultar las deudas a un proveedor
     *
     * @param pr
     * @return
     */
    public static List<Compra> consultarDeudasAProveedor(Proveedor pr) {
        if (compraDAO == null) {
            compraDAO = DAOManager.getInstancia().getCompraDAO();
        }
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("proveedor", pr));
        p.add(new Parameter("tipo", 2));
        p.add(new Parameter("estado", 1));

        List<Compra> deudas = compraDAO.readByQuery(""
                + "SELECT c FROM Compra c "
                + "WHERE c.idProveedor = :proveedor "
                + "AND c.tipoCompra = :tipo "
                + "AND c.estado = :estado", p);

        return deudas;
    }
}
