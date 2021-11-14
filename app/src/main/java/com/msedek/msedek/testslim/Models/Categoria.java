package com.msedek.msedek.testslim.Models;

import java.io.Serializable;

public class Categoria  implements Serializable {


    private String cat_nombre;
    private int cat_id;


    public String getCat_nombre() {
        return cat_nombre;
    }

    public void setCat_nombre(String cat_nombre) {
        this.cat_nombre = cat_nombre;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }
}
