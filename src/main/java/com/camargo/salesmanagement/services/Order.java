package com.camargo.salesmanagement.services;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * Order
 */
public class Order {

    private static Order order;

    @Getter
    @Setter
    private String idOrder;
    @Getter
    @Setter
    private String nameVendor;
    @Getter
    @Setter
    private ArrayList<ItemsOrder> itemsOrder;

    private Order() {

    }

    public static Order getInstance() {
        if (order == null) {
            order = new Order();
        }

        return order;
    }
}