package com.camargo.salesmanagement.repositories;

import java.util.ArrayList;

/**
 * Order
 */
public class Order {

    private String idOrder;
    private String nameVendor;
    private ArrayList<ItemsOrder> itemsOrder;

    public Order() {

    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getNameVendor() {
        return nameVendor;
    }

    public void setNameVendor(String nameVendor) {
        this.nameVendor = nameVendor;
    }

    public ArrayList<ItemsOrder> getItemsOrder() {
        return itemsOrder;
    }

    public void setItemsOrder(ArrayList<ItemsOrder> itemsOrder) {
        this.itemsOrder = itemsOrder;
    }

}