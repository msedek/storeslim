package com.msedek.msedek.testslim.Models;


import java.io.Serializable;

public class Cita implements Serializable {

    private int c_id;
    private String tal_id;
    private String veh_placa;
    private String fecha;
    private String servicio;
    private String cli_dni;
    private String estado_orden;

    public String getTal_id() {
        return tal_id;
    }

    public void setTal_id(String tal_id) {
        this.tal_id = tal_id;
    }

    public String getVeh_placa() {
        return veh_placa;
    }

    public void setVeh_placa(String veh_placa) {
        this.veh_placa = veh_placa;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getCli_dni() {
        return cli_dni;
    }

    public void setCli_dni(String cli_dni) {
        this.cli_dni = cli_dni;
    }

    public String getEstado_orden() {
        return estado_orden;
    }

    public void setEstado_orden(String estado_orden) {
        this.estado_orden = estado_orden;
    }


    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }
}
