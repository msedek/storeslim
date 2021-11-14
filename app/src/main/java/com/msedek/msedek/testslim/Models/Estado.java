package com.msedek.msedek.testslim.Models;

import java.io.Serializable;

public class Estado  implements Serializable {

    private String est_nombre;
    private int est_id;

    public String getEst_nombre() {
        return est_nombre;
    }

    public void setEst_nombre(String est_nombre) {
        this.est_nombre = est_nombre;
    }

    public int getEst_id() {
        return est_id;
    }

    public void setEst_id(int est_id) {
        this.est_id = est_id;
    }
}
