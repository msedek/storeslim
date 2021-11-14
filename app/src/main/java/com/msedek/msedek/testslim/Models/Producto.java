package com.msedek.msedek.testslim.Models;

import java.io.Serializable;

public class Producto  implements Serializable {

    private String pro_nombre;
    private String pro_precio;
    private String pro_cantidad;
    private String cat_id;
    private int pro_id;


    public String getPro_nombre() {
        return pro_nombre;
    }

    public void setPro_nombre(String pro_nombre) {
        this.pro_nombre = pro_nombre;
    }

    public String getPro_precio() {
        return pro_precio;
    }

    public void setPro_precio(String pro_precio) {
        this.pro_precio = pro_precio;
    }

    public String getPro_cantidad() {
        return pro_cantidad;
    }

    public void setPro_cantidad(String pro_cantidad) {
        this.pro_cantidad = pro_cantidad;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }
}
