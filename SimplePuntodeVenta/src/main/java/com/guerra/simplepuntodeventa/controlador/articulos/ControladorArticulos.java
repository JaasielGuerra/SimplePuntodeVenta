/*
 * Aqui se ejecutan los eventos de los articulos
 * CRUD de articulos
 */
package com.guerra.simplepuntodeventa.controlador.articulos;

import com.guerra.simplepuntodeventa.controlador.compras.IArticuloGuardado;
import com.guerra.simplepuntodeventa.global.Session;
import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.Estado;
import com.guerra.simplepuntodeventa.modelo.funciones.FuncionesArticulos;
import com.guerra.simplepuntodeventa.modelo.dao.ArticuloDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.CategoriaDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.MarcaDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.UbicacionDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Articulo;
import com.guerra.simplepuntodeventa.modelo.entidades.Categoria;
import com.guerra.simplepuntodeventa.modelo.entidades.Marca;
import com.guerra.simplepuntodeventa.modelo.entidades.Ubicacion;
import com.guerra.simplepuntodeventa.modelo.entidades.Usuario;
import com.guerra.simplepuntodeventa.modelo.funciones.FuncionesInventario;
import com.guerra.simplepuntodeventa.recursos.componentes.modelos.ModeloJTextFieldCodigoBarra;
import com.guerra.simplepuntodeventa.recursos.utilerias.PanelUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.ComboBoxUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.NumeroUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.TablaUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.ValidarJComboBox;
import com.guerra.simplepuntodeventa.recursos.utilerias.ValidarJSpinner;
import com.guerra.simplepuntodeventa.recursos.utilerias.ValidarJTextField;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjException;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjInfo;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjValidacion;
import com.guerra.simplepuntodeventa.vista.articulos.IfrmMenuArticulos;
import com.guerra.simplepuntodeventa.vista.articulos.PanEditarArticulo;
import com.guerra.simplepuntodeventa.vista.articulos.PanListArticulos;
import com.guerra.simplepuntodeventa.vista.articulos.PanNuevoArticulo;
import com.guerra.simplepuntodeventa.vista.articulos.VistaArticuloBean;
import com.guerra.simplepuntodeventa.vista.articulos.caracteristicas.DlgNuevaCategoria;
import com.guerra.simplepuntodeventa.vista.articulos.caracteristicas.DlgNuevaMarca;
import com.guerra.simplepuntodeventa.vista.articulos.caracteristicas.DlgNuevaUbicacion;
import com.guerra.simplepuntodeventa.vista.compras.DlgNuevoArticulo;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 *
 * @author Jaasiel
 */
public class ControladorArticulos {

    //constantes para el combo de buscar por
    private static final String POR_CODIGO = "CÓDIGO";
    private static final String POR_NOMBRE = "NOMBRE";
    private static final String POR_DESCRIPCION = "DESCRIPCIÓN";
    private static final String POR_MARCA = "MARCA";
    private static final String POR_CATEGORIA = "CATEGORÍA";

    private final PanNuevoArticulo panNuevoArticulo;
    private PanListArticulos panListArticulos;
    private PanEditarArticulo panEditarArticulo;

    private final DlgNuevaMarca dlgNuevaMarca = new DlgNuevaMarca(null, true);
    private final DlgNuevaCategoria dlgNuevaCategoria = new DlgNuevaCategoria(null, true);
    private final DlgNuevaUbicacion dlgNuevaUbicacion = new DlgNuevaUbicacion(null, true);
    private final DlgNuevaMarca dlgNuevaMarcaPanEditar = new DlgNuevaMarca(null, true);
    private final DlgNuevaCategoria dlgNuevaCategoriaPanEditar = new DlgNuevaCategoria(null, true);
    private final DlgNuevaUbicacion dlgNuevaUbicacionPanEditar = new DlgNuevaUbicacion(null, true);

    private final ArticuloDAOImpl articuloDAO = DAOManager.getInstancia().getArticuloDAO();
    private final MarcaDAOImpl marcaDAO = DAOManager.getInstancia().getMarcaDAO();
    private final CategoriaDAOImpl categoriaDAO = DAOManager.getInstancia().getCategoriaDAO();
    private final UbicacionDAOImpl ubicacionDAO = DAOManager.getInstancia().getUbicacionDAO();

    private final ControladorNuevaMarcaDlg controladorNuevaMarcaDlg;
    private final ControladorNuevaCategoriaDlg controladorNuevaCategoriaDlg;
    private final ControladorNuevaUbicacion controladorNuevaUbicacionDlg;
    private ControladorNuevaMarcaDlg controladorNuevaMarcaDlgPanEditar;
    private ControladorNuevaCategoriaDlg controladorNuevaCategoriaPanDlgEditar;
    private ControladorNuevaUbicacion controladorNuevaUbicacionPanDlgEditar;

    private IArticuloGuardado articuloGuardado;

    private Articulo articuloEditar;

    public ControladorArticulos(VistaArticuloBean bean) {
        this.panNuevoArticulo = bean.getPanNuevoArticulo();
        this.panEditarArticulo = bean.getPanEditarArticulo();
        this.panListArticulos = bean.getPanListArticulos();
        this.controladorNuevaMarcaDlg = new ControladorNuevaMarcaDlg(dlgNuevaMarca, panNuevoArticulo.cbxMarca);
        this.controladorNuevaCategoriaDlg = new ControladorNuevaCategoriaDlg(dlgNuevaCategoria, panNuevoArticulo.cbxCategoria);
        this.controladorNuevaUbicacionDlg = new ControladorNuevaUbicacion(dlgNuevaUbicacion, panNuevoArticulo.cbxUbicacion);
        this.controladorNuevaMarcaDlgPanEditar = new ControladorNuevaMarcaDlg(dlgNuevaMarcaPanEditar, panEditarArticulo.cbxMarca);
        this.controladorNuevaCategoriaPanDlgEditar = new ControladorNuevaCategoriaDlg(dlgNuevaCategoriaPanEditar, panEditarArticulo.cbxCategoria);
        this.controladorNuevaUbicacionPanDlgEditar = new ControladorNuevaUbicacion(dlgNuevaUbicacionPanEditar, panEditarArticulo.cbxUbicacion);
        init();
    }

    public ControladorArticulos(DlgNuevoArticulo dlgNuevoArticulo, IArticuloGuardado articuloGuardado) {
        this.panNuevoArticulo = (PanNuevoArticulo) dlgNuevoArticulo.panNuevoArticulo;
        this.controladorNuevaMarcaDlg = new ControladorNuevaMarcaDlg(dlgNuevaMarca, panNuevoArticulo.cbxMarca);
        this.controladorNuevaCategoriaDlg = new ControladorNuevaCategoriaDlg(dlgNuevaCategoria, panNuevoArticulo.cbxCategoria);
        this.controladorNuevaUbicacionDlg = new ControladorNuevaUbicacion(dlgNuevaUbicacion, panNuevoArticulo.cbxUbicacion);
        this.articuloGuardado = articuloGuardado;
        this.panNuevoArticulo.spnCantidad.setEnabled(false);
        initPanNuevoArticulo();
    }

    private void initPanNuevoArticulo() {
        panNuevoArticulo.btnGuardar.addActionListener((e) -> {
            guardarAticulo();
        });
        panNuevoArticulo.txtCod1.addKeyListener(new ModeloJTextFieldCodigoBarra());
        panNuevoArticulo.txtCod2.addKeyListener(new ModeloJTextFieldCodigoBarra());
        panNuevoArticulo.txtCod3.addKeyListener(new ModeloJTextFieldCodigoBarra());
        panNuevoArticulo.btnNuevaMarca.addActionListener((e) -> {
            dlgNuevaMarca();
        });
        panNuevoArticulo.btnNuevaCateg.addActionListener((e) -> {
            dlgNuevaCategoria();
        });
        panNuevoArticulo.btnNuevaUbicacion.addActionListener((e) -> {
            dlgNuevaUbicacion();
        });
    }

    private void init() {
        initPanNuevoArticulo();
        panEditarArticulo.btnGuardar.addActionListener((e) -> {
            actualizarArticulo();
        });
        panEditarArticulo.txtCod1.addKeyListener(new ModeloJTextFieldCodigoBarra());
        panEditarArticulo.txtCod2.addKeyListener(new ModeloJTextFieldCodigoBarra());
        panEditarArticulo.txtCod3.addKeyListener(new ModeloJTextFieldCodigoBarra());
        panEditarArticulo.btnNuevaMarca.addActionListener((e) -> {
            dlgNuevaMarcaPanEditar();
        });
        panEditarArticulo.btnNuevaCateg.addActionListener((e) -> {
            dlgNuevaCategoriaPanEditar();
        });
        panEditarArticulo.btnNuevaUbicacion.addActionListener((ae) -> {
            dlgNuevaUbicacionPanEditar();
        });
        panListArticulos.btnEditar.addActionListener((e) -> {
            editarArticulo();
        });
        ComboBoxUtil.llenarComboEstado(panListArticulos.cbxEstado);
        panListArticulos.cbxEstado.addActionListener((e) -> {
            consultarArticuloComboEstado();
        });
        llenarComboConsultarArticuloBuscarPor();
        panListArticulos.txtBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                realizarBusquedaArticulo();
            }
        });
        panListArticulos.btnActivar.addActionListener((e) -> {
            activarArticulo();
        });
        panListArticulos.btnDesactivar.addActionListener((e) -> {
            desactivarArticulo();
        });
    }

    private void cargarCombosCaracteristicasEditarArticulo() {
        ComboBoxUtil.llenarComboDatos(
                panEditarArticulo.cbxMarca,
                marcaDAO.readByNameQuery("Marca.findByEstado", "estado", 1)
        );
        ComboBoxUtil.llenarComboDatos(
                panEditarArticulo.cbxCategoria,
                categoriaDAO.readByNameQuery("Categoria.findByEstado", "estado", 1)
        );
        ComboBoxUtil.llenarComboDatos(
                panEditarArticulo.cbxUbicacion,
                ubicacionDAO.readByNameQuery("Ubicacion.findByEstado", "estado", 1)
        );
    }

    private void resetValuesEditarArticulo() {
        panEditarArticulo.txtCod1.setText("");
        panEditarArticulo.txtCod2.setText("");
        panEditarArticulo.txtCod3.setText("");
        panEditarArticulo.txtNombre.setText("");
        panEditarArticulo.txtDescripcion.setText("");
        panEditarArticulo.spnMinCantidad.setValue(0);
        panEditarArticulo.spnPCompra.setValue(0.00D);
        panEditarArticulo.spnPVenta.setValue(0.00D);
        panEditarArticulo.cbxCategoria.setSelectedIndex(0);
        panEditarArticulo.cbxMarca.setSelectedIndex(0);
        articuloEditar = null;
    }

    private void llenarComboConsultarArticuloBuscarPor() {
        JComboBox cbxFiltroPor = panListArticulos.cbxBuscarPor;
        cbxFiltroPor.removeAllItems();
        cbxFiltroPor.addItem(POR_CODIGO);
        cbxFiltroPor.addItem(POR_NOMBRE);
        cbxFiltroPor.addItem(POR_DESCRIPCION);
        cbxFiltroPor.addItem(POR_MARCA);
        cbxFiltroPor.addItem(POR_CATEGORIA);
    }

    //llenar la tabla de articulos con datos
    private void llenarTablaArticulos(List<Articulo> datos) {
        TablaUtil.llenarTablaConEntity(
                panListArticulos.tblArticulos, datos,
                new String[]{"cod1", "nombre", "descripcion", "precioCompra",
                    "precioVenta", "minExistencia", "idMarca", "idCategoria", "idUbicacion", "estado"}, 0
        );
    }

    //////////metodos publicos/////////////////
    public void resetValuesNuevoArticulo() {
        ValidarJTextField.resetValues(new JTextField[]{
            panNuevoArticulo.txtCod1, panNuevoArticulo.txtCod2,
            panNuevoArticulo.txtCod3, panNuevoArticulo.txtDescripcion,
            panNuevoArticulo.txtNombre
        });
        ValidarJComboBox.resetValues(new JComboBox[]{
            panNuevoArticulo.cbxCategoria,
            panNuevoArticulo.cbxMarca,
            panNuevoArticulo.cbxUbicacion
        }, 0);
        ValidarJSpinner.resetValuesInteger(new JSpinner[]{
            panNuevoArticulo.spnMin
        }, 0);
        ValidarJSpinner.resetValuesInteger(
                new JSpinner[]{
                    panNuevoArticulo.spnCantidad
                }, 1
        );
        ValidarJSpinner.resetValuesDouble(new JSpinner[]{
            panNuevoArticulo.spnPCompra, panNuevoArticulo.spnPVenta
        }, 0.0D);
    }

    /**
     * Cargar los combos de las caracteristicas para un nuevo articulo
     */
    public void cargarCombosCaracteristicasNuevoArticulo() {
        ComboBoxUtil.llenarComboDatos(
                panNuevoArticulo.cbxMarca,
                marcaDAO.readByNameQuery("Marca.findByEstado", "estado", 1)
        );
        ComboBoxUtil.llenarComboDatos(
                panNuevoArticulo.cbxCategoria,
                categoriaDAO.readByNameQuery("Categoria.findByEstado", "estado", 1)
        );
        ComboBoxUtil.llenarComboDatos(
                panNuevoArticulo.cbxUbicacion,
                ubicacionDAO.readByNameQuery("Ubicacion.findByEstado", "estado", 1)
        );
    }

    public void consultarArticulos() {
        panListArticulos.cbxEstado.setSelectedIndex(0);
    }

    /////////////eventos///////////
    private void guardarAticulo() {
        //validar el formulario
        if (ValidarJTextField.camposVacios(new JTextField[]{
            panNuevoArticulo.txtCod1, panNuevoArticulo.txtNombre,
            panNuevoArticulo.txtDescripcion

        })) {

            MsjValidacion.msjJTextFieldRequeridos(panNuevoArticulo);

        } else if (!ValidarJComboBox.seleccionCombos(
                new JComboBox[]{panNuevoArticulo.cbxCategoria,
                    panNuevoArticulo.cbxMarca}, 0
        )) {

            MsjValidacion.msjJComboBoxdRequeridos(panNuevoArticulo);

        } else if (!ValidarJSpinner.valorDoubleMayorN(0, panNuevoArticulo.spnPCompra)
                || !ValidarJSpinner.valorDoubleMayorN(0, panNuevoArticulo.spnPVenta)) {

            MsjValidacion.msjValoresNumericosInvalidos(panNuevoArticulo);

        } else {//formulario valido

            if (FuncionesArticulos.codigoExistente(
                    panNuevoArticulo.txtCod1.getText().trim(),
                    panNuevoArticulo.txtCod2.getText().trim(),
                    panNuevoArticulo.txtCod3.getText().trim()
            )) {

                MsjInfo.msjCodigosExistentes(panNuevoArticulo);

            } else {

                Articulo a = new Articulo();

                a.setCod1(panNuevoArticulo.txtCod1.getText().trim().toUpperCase());
                a.setCod2(panNuevoArticulo.txtCod2.getText().trim().isEmpty() ? null : panNuevoArticulo.txtCod2.getText().trim().toUpperCase());
                a.setCod3(panNuevoArticulo.txtCod3.getText().trim().isEmpty() ? null : panNuevoArticulo.txtCod3.getText().trim().toUpperCase());
                a.setNombre(panNuevoArticulo.txtNombre.getText().trim());
                a.setDescripcion(panNuevoArticulo.txtDescripcion.getText().trim());
                a.setCantidad((Integer) panNuevoArticulo.spnCantidad.getValue());
                a.setMinExistencia((Integer) panNuevoArticulo.spnMin.getValue());
                a.setPrecioCompra((Double) panNuevoArticulo.spnPCompra.getValue());
                a.setPrecioVenta((Double) panNuevoArticulo.spnPVenta.getValue());
                Double ganancia = a.getPrecioVenta() - a.getPrecioCompra();
                Double porcentajeGanancia = (ganancia / a.getPrecioCompra()) * 100;
                a.setPorcentajeGanancia(NumeroUtil.redondear(porcentajeGanancia, 2));
                a.setGanancia(NumeroUtil.redondear(ganancia, 2));
                a.setEstado(1);//activo

                Marca m = (Marca) panNuevoArticulo.cbxMarca.getSelectedItem();
                Categoria c = (Categoria) panNuevoArticulo.cbxCategoria.getSelectedItem();
                Ubicacion u = (Ubicacion) panNuevoArticulo.cbxUbicacion.getSelectedItem();
                Usuario user = (Usuario) Session.getInstancia().getAttribute("user");

                a.setIdMarca(m);
                a.setIdCategoria(c);
                a.setIdUbicacion(u);
                a.setFechaCommit(new Date());
                a.setHoraCommit(new Date());
                a.setIdUsuario(user);

                try {

                    articuloDAO.create(a);

                    MsjInfo.msjRegistroExitoso(panNuevoArticulo);
                    resetValuesNuevoArticulo();
                    if (articuloGuardado != null) {
                        /**
                         * No sera null si este controlador es usado para el
                         * dialogo de nuevo articulo
                         */
                        articuloGuardado.guardadoExitoso(a);
                    }

                } catch (Exception e) {
                    MsjException.msjErrorGuardar(panNuevoArticulo, e.getMessage());
                }
            }
        }
    }

    private void dlgNuevaMarca() {
        controladorNuevaMarcaDlg.resetFormulario();
        dlgNuevaMarca.setLocationRelativeTo(panNuevoArticulo);
        dlgNuevaMarca.setVisible(true);
    }

    private void dlgNuevaCategoria() {
        controladorNuevaCategoriaDlg.resetFormulario();
        dlgNuevaCategoria.setLocationRelativeTo(panNuevoArticulo);
        dlgNuevaCategoria.setVisible(true);
    }

    private void dlgNuevaUbicacion() {
        controladorNuevaUbicacionDlg.resetFormulario();
        dlgNuevaUbicacion.setLocationRelativeTo(panNuevoArticulo);
        dlgNuevaUbicacion.setVisible(true);
    }

    private void actualizarArticulo() {
        if (ValidarJTextField.camposVacios(new JTextField[]{
            panEditarArticulo.txtCod1, panEditarArticulo.txtNombre,
            panEditarArticulo.txtDescripcion
        })) {
            MsjValidacion.msjJTextFieldRequeridos(panEditarArticulo);
        } else if (!ValidarJComboBox.seleccionCombos(
                new JComboBox[]{panEditarArticulo.cbxCategoria,
                    panEditarArticulo.cbxMarca}, 0
        )) {
            MsjValidacion.msjJComboBoxdRequeridos(panEditarArticulo);
        } else if (!ValidarJSpinner.valorDoubleMayorN(0, panEditarArticulo.spnPCompra)
                || !ValidarJSpinner.valorDoubleMayorN(0, panEditarArticulo.spnPVenta)) {
            MsjValidacion.msjValoresNumericosInvalidos(panEditarArticulo);
        } else {//formulario valido

            if (FuncionesArticulos.codigoExistente(
                    panEditarArticulo.txtCod1.getText().trim(),
                    panEditarArticulo.txtCod2.getText().trim(),
                    panEditarArticulo.txtCod3.getText().trim(),
                    articuloEditar
            )) {
                MsjInfo.msjCodigosExistentes(panEditarArticulo);
            } else {
                articuloEditar.setCod1(panEditarArticulo.txtCod1.getText().trim().toUpperCase());
                articuloEditar.setCod2(
                        panEditarArticulo.txtCod2.getText().trim()
                                .isEmpty() ? null : panEditarArticulo.txtCod2.getText().trim().toUpperCase()
                );
                articuloEditar.setCod3(
                        panEditarArticulo.txtCod3.getText().trim()
                                .isEmpty() ? null : panEditarArticulo.txtCod3.getText().trim().toUpperCase()
                );
                articuloEditar.setNombre(panEditarArticulo.txtNombre.getText().trim());
                articuloEditar.setDescripcion(panEditarArticulo.txtDescripcion.getText().trim());
                articuloEditar.setMinExistencia((Integer) panEditarArticulo.spnMinCantidad.getValue());
                articuloEditar.setPrecioCompra((Double) panEditarArticulo.spnPCompra.getValue());
                articuloEditar.setPrecioVenta((Double) panEditarArticulo.spnPVenta.getValue());
                Double ganancia = articuloEditar.getPrecioVenta() - articuloEditar.getPrecioCompra();
                Double porcentajeGanancia = (ganancia / articuloEditar.getPrecioCompra()) * 100;
                articuloEditar.setPorcentajeGanancia(NumeroUtil.redondear(porcentajeGanancia, 2));
                articuloEditar.setGanancia(NumeroUtil.redondear(ganancia, 2));

                Marca m = (Marca) panEditarArticulo.cbxMarca.getSelectedItem();
                Categoria c = (Categoria) panEditarArticulo.cbxCategoria.getSelectedItem();
                Ubicacion u = (Ubicacion) panEditarArticulo.cbxUbicacion.getSelectedItem();

                articuloEditar.setIdMarca(m);
                articuloEditar.setIdCategoria(c);
                articuloEditar.setIdUbicacion(u);


                try {
                    articuloDAO.update(articuloEditar);
                    MsjInfo.msjActualizacionExitosa(panEditarArticulo);
                    resetValuesEditarArticulo();
                    consultarArticulos();
                    PanelUtil.cambiarPanel(IfrmMenuArticulos.PANEL_CAMBIANTE, panListArticulos);
                } catch (Exception e) {
                    MsjException.msjErrorActualizar(panEditarArticulo, e.getMessage());
                }
            }
        }
    }

    private void dlgNuevaMarcaPanEditar() {
        controladorNuevaMarcaDlgPanEditar.resetFormulario();
        dlgNuevaMarcaPanEditar.setLocationRelativeTo(panEditarArticulo);
        dlgNuevaMarcaPanEditar.setVisible(true);
    }

    private void dlgNuevaCategoriaPanEditar() {
        controladorNuevaCategoriaPanDlgEditar.resetFormulario();
        dlgNuevaCategoriaPanEditar.setLocationRelativeTo(panEditarArticulo);
        dlgNuevaCategoriaPanEditar.setVisible(true);
    }

    private void dlgNuevaUbicacionPanEditar() {
        controladorNuevaUbicacionPanDlgEditar.resetFormulario();
        dlgNuevaUbicacionPanEditar.setLocationRelativeTo(panEditarArticulo);
        dlgNuevaUbicacionPanEditar.setVisible(true);
    }

    private void editarArticulo() {
        articuloEditar = TablaUtil.getEntityFilaSeleccionada(
                panListArticulos.tblArticulos, 0, Articulo.class
        );
        if (articuloEditar != null) {
            cargarCombosCaracteristicasEditarArticulo();
            panEditarArticulo.txtCod1.setText(articuloEditar.getCod1());
            panEditarArticulo.txtCod2.setText(articuloEditar.getCod2());
            panEditarArticulo.txtCod3.setText(articuloEditar.getCod3());
            panEditarArticulo.txtNombre.setText(articuloEditar.getNombre());
            panEditarArticulo.txtDescripcion.setText(articuloEditar.getDescripcion());
            panEditarArticulo.cbxMarca.setSelectedItem(articuloEditar.getIdMarca());
            panEditarArticulo.cbxCategoria.setSelectedItem(articuloEditar.getIdCategoria());
            panEditarArticulo.cbxUbicacion.setSelectedItem(articuloEditar.getIdUbicacion());
            panEditarArticulo.spnMinCantidad.setValue(articuloEditar.getMinExistencia());
            panEditarArticulo.spnPCompra.setValue(articuloEditar.getPrecioCompra());
            panEditarArticulo.spnPVenta.setValue(articuloEditar.getPrecioVenta());
            PanelUtil.cambiarPanel(IfrmMenuArticulos.PANEL_CAMBIANTE, panEditarArticulo);
        }
    }

    private void consultarArticuloComboEstado() {
        Estado e = (Estado) panListArticulos.cbxEstado.getSelectedItem();
        List articulos = articuloDAO.readByNameQuery(
                "Articulo.findByEstado", "estado",
                e.getValor()
        );
        llenarTablaArticulos(articulos);
    }

    private void realizarBusquedaArticulo() {
        //obtener el estado seleccionado y la cadena de texto a buscar 
        Estado e = (Estado) panListArticulos.cbxEstado.getSelectedItem();
        String busqueda = panListArticulos.txtBusqueda.getText().trim();

        //segun lo que se escoja en el combo, asi se busca
        switch (panListArticulos.cbxBuscarPor.getSelectedItem().toString()) {
            case POR_CODIGO: {
                List articulos = FuncionesArticulos.buscarArticulosPorCodigoEstado(
                        e.getValor(), busqueda
                );
                llenarTablaArticulos(articulos);
            }
            break;
            case POR_NOMBRE: {
                List articulos = FuncionesArticulos.buscarArticulosPorNombreEstado(
                        e.getValor(), busqueda
                );
                llenarTablaArticulos(articulos);
            }
            break;
            case POR_DESCRIPCION: {
                List articulos = FuncionesArticulos.buscarArticulosPorDescripcionEstado(
                        e.getValor(), busqueda
                );
                llenarTablaArticulos(articulos);
            }
            break;
            case POR_MARCA: {
                List articulos = FuncionesArticulos.buscarArticulosPorMarcaEstado(
                        e.getValor(), busqueda
                );
                llenarTablaArticulos(articulos);
            }
            break;
            case POR_CATEGORIA: {
                List articulos = FuncionesArticulos.buscarArticulosPorCategoriaEstado(
                        e.getValor(), busqueda
                );
                llenarTablaArticulos(articulos);
            }
            break;
            default:
                break;
        }
    }

    private void activarArticulo() {
        ////////////activando articulo///////
        articuloEditar = TablaUtil.getEntityFilaSeleccionada(
                panListArticulos.tblArticulos, 0, Articulo.class
        );
        if (articuloEditar != null) {//validar que si se selecciono fila
            if (articuloEditar.getEstado() == 1) {//ya esta activado
                MsjInfo.msjRegistroYaActivado(panListArticulos);
            } else {// sino pues se activa
                articuloEditar.setEstado(1);//activado
                try {
                    articuloDAO.update(articuloEditar);
                    consultarArticulos();
                } catch (Exception e) {
                    MsjException.msjErrorActualizar(
                            panListArticulos, e.getMessage()
                    );
                }
            }
        }
    }

    private void desactivarArticulo() {
        ///////descativando articulo////////////
        articuloEditar = TablaUtil.getEntityFilaSeleccionada(
                panListArticulos.tblArticulos, 0, Articulo.class
        );
        if (articuloEditar != null) {//validar que si se selecciono fila
            if (articuloEditar.getEstado() == 0) {//ya esta desactivado
                MsjInfo.msjRegistroYaDesactivado(panListArticulos);
            } else {//sino no esta desactivado pues se desactiva
                if (MsjInfo.msjConfimacionDesactivar(panListArticulos)
                        == JOptionPane.YES_OPTION) {//confirmar
                    articuloEditar.setEstado(0);//desactivado
                    try {
                        articuloDAO.update(articuloEditar);
                        consultarArticulos();
                    } catch (Exception e) {
                        MsjException.msjErrorActualizar(panListArticulos,
                                e.getMessage());
                    }
                }
            }
        }
    }

}
