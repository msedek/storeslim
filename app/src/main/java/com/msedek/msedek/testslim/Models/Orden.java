package com.msedek.msedek.testslim.Models;

import java.io.Serializable;

public class Orden implements Serializable {

    private int ot_id;
    private String cli_dni;
    private String veh_placa;
    private String servicio;
    private String emp_dni;
    private String tal_id;

    private String estado_orden;

    public String getCli_dni() {
        return cli_dni;
    }

    public void setCli_dni(String cli_dni) {
        this.cli_dni = cli_dni;
    }

    public String getVeh_placa() {
        return veh_placa;
    }

    public void setVeh_placa(String veh_placa) {
        this.veh_placa = veh_placa;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getEmp_dni() {
        return emp_dni;
    }

    public void setEmp_dni(String emp_dni) {
        this.emp_dni = emp_dni;
    }

    public String getTal_id() {
        return tal_id;
    }

    public void setTal_id(String tal_id) {
        this.tal_id = tal_id;
    }

    public String getEstado_orden() {
        return estado_orden;
    }

    public void setEstado_orden(String estado_orden) {
        this.estado_orden = estado_orden;
    }

    public int getOt_id() {
        return ot_id;
    }

    public void setOt_id(int ot_id) {
        this.ot_id = ot_id;
    }
}
