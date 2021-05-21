/*
 * Para tener la configuracion de la empresa de forma global en toda al aplicacion
 */
package com.guerra.simplepuntodeventa.global;

import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.entidades.Empresa;
import java.io.File;

/**
 *
 * @author Jaasiel
 */
public class ConfiguracionEmpresa {

    private static ConfiguracionEmpresa conf;
    private Empresa empresa;
    private final String rutaApp = System.getProperty("user.dir");

    private ConfiguracionEmpresa() {
        empresa = DAOManager.getInstancia().getEmpresaDAO().readOne(1);
    }

    public static ConfiguracionEmpresa getInstancia() {
        if (conf == null) {
            conf = new ConfiguracionEmpresa();
        }
        return conf;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setLogo(String logo) {
        this.empresa.setLogo(logo);
    }

    public void setTelefono(String telefono) {
        this.empresa.setTelefono(telefono);
    }

    public void setDireccion(String direccion) {
        this.empresa.setDireccion(direccion);
    }

    public void setNombre(String nombre) {
        this.empresa.setNombre(nombre);
    }

    public String getLogo() {
        return rutaApp + File.separator + "data" + File.separator + this.empresa.getLogo();
    }

    public String getNombre() {
        return empresa.getNombre();
    }

    public String getDireccion() {
        return empresa.getDireccion();
    }

    public String getTelefono() {
        return empresa.getTelefono();
    }

    public String getRutaData() {
        return rutaApp + File.separator + "data" + File.separator;
    }
}
