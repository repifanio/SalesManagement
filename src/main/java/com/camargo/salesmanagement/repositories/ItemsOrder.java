package com.camargo.salesmanagement.repositories;

/**
 * ItemsOrder
 */

public class ItemsOrder {

    private String idProduct;
    private int qtdProduct;
    private double valueProduct;

    public ItemsOrder() {

    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public int getQtdProduct() {
        return qtdProduct;
    }

    public void setQtdProduct(int qtdProduct) {
        this.qtdProduct = qtdProduct;
    }

    public double getValueProduct() {
        return valueProduct;
    }

    public void setValueProduct(double valueProduct) {
        this.valueProduct = valueProduct;
    }
}