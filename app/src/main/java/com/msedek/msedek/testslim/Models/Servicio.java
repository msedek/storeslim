package com.msedek.msedek.testslim.Models;

import java.io.Serializable;

public class Servicio  implements Serializable {

    private String ser_nombre;
    private String ser_descri;
    private String est_id;
    private int ser_id;

    public String getSer_nombre() {
        return ser_nombre;
    }

    public void setSer_nombre(String ser_nombre) {
        this.ser_nombre = ser_nombre;
    }

    public String getSer_descri() {
        return ser_descri;
    }

    public void setSer_descri(String ser_descri) {
        this.ser_descri = ser_descri;
    }

    public String getEst_id() {
        return est_id;
    }

    public void setEst_id(String est_id) {
        this.est_id = est_id;
    }

    public int getSer_id() {
        return ser_id;
    }

    public void setSer_id(int ser_id) {
        this.ser_id = ser_id;
    }
}
