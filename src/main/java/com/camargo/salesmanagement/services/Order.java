package com.camargo.salesmanagement.services;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * Order
 */
@Getter
@Setter
public class Order {

    private String idOrder;
    private String nameVendor;
    private ArrayList<ItemsOrder> itemsOrder;

    public Order() {

    }

}