package com.camargo.salesmanagement.repositories;

/**
 * Sale
 */

public class SaleMens {

    private static SaleMens sale;

    private String cpf;
    private String name;
    private double salary;

    private SaleMens() {

    }

    public static SaleMens getInstance() {
        if (sale == null) {
            sale = new SaleMens();
        }

        return sale;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

}