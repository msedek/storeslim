package com.msedek.msedek.testslim.Models;


import java.io.Serializable;

public class Empleado implements Serializable {


    private String emp_dni;
    private String emp_nombre;
    private String emp_pass;
    private String tal_id;
    private int    car_id;


    public String getEmp_dni() {
        return emp_dni;
    }

    public void setEmp_dni(String emp_dni) {
        this.emp_dni = emp_dni;
    }

    public String getEmp_nombre() {
        return emp_nombre;
    }

    public void setEmp_nombre(String emp_nombre) {
        this.emp_nombre = emp_nombre;
    }

    public String getEmp_pass() {
        return emp_pass;
    }

    public void setEmp_pass(String emp_pass) {
        this.emp_pass = emp_pass;
    }


    public String getTal_id() {
        return tal_id;
    }

    public void setTal_id(String tal_id) {
        this.tal_id = tal_id;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }
}
