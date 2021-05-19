package com.guerra.simplepuntodeventa.modelo.funciones;

import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.Parameter;
import com.guerra.simplepuntodeventa.modelo.dao.ClienteDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.VentaDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Cliente;
import com.guerra.simplepuntodeventa.modelo.entidades.Venta;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jaasiel
 */
public class FuncionesClientes {

    private static ClienteDAOImpl clienteDAO;
    private static VentaDAOImpl ventaDAO;

    private FuncionesClientes() {

    }

    /**
     * Obtener el crédito disponible del cliente
     *
     * @param cliente
     * @return El credito que aun le queda disponible
     */
    public static Double creditoDisponible(Cliente cliente) {
        if (ventaDAO == null) {
            ventaDAO = DAOManager.getInstancia().getVentaDAO();
        }
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("cliente", cliente));
        p.add(new Parameter("tipo", 2));//credito
        p.add(new Parameter("estado", 1));//realizado

        List<Venta> creditos = ventaDAO.readByQuery(
                "SELECT v FROM Venta v WHERE v.estado = :estado "
                + "AND v.idCliente = :cliente AND v.tipoVenta = :tipo",
                p
        );
        return cliente.getLimite() - creditos.stream().mapToDouble(credito -> credito.getSaldo()).sum();
    }

    /**
     * Buscar un cliente por su nombre y estado
     *
     * @param buscar
     * @param estado
     * @return
     */
    public static List<Cliente> buscarClienteNombreEstado(String buscar, int estado) {
        if (clienteDAO == null) {
            clienteDAO = DAOManager.getInstancia().getClienteDAO();
        }
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("buscar", "%" + buscar + "%"));
        p.add(new Parameter("estado", estado));
        List<Cliente> clientes = clienteDAO.readByQuery(
                "SELECT c FROM Cliente c "
                + "WHERE c.estado = :estado "
                + "AND c.nombreCompleto LIKE :buscar "
                + "AND NOT c.idCliente = 1",
                p
        );
        return clientes;
    }

    /**
     * Consultar clientes con estado activo, excluyendo al cliente por defecto
     * de PUBLICO GENERAL
     *
     * @param estado
     * @return
     */
    public static List<Cliente> consultarClientesEstado(int estado) {
        if (clienteDAO == null) {
            clienteDAO = DAOManager.getInstancia().getClienteDAO();
        }
        List<Parameter> p = new ArrayList<>();
        p.add(new Parameter("estado", estado));

        List<Cliente> clientes = clienteDAO.readByQuery(
                "SELECT c FROM Cliente c "
                + "WHERE c.estado = :estado "
                + "AND NOT c.idCliente = 1",
                p
        );
        return clientes;
    }
}
