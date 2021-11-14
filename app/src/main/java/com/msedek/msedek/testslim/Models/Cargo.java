package com.msedek.msedek.testslim.Models;

import java.io.Serializable;

public class Cargo  implements Serializable {

    private String cargo;

    private int car_id;

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }


    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }
}
