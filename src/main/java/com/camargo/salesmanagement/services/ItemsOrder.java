package com.camargo.salesmanagement.services;

import lombok.Getter;
import lombok.Setter;

/**
 * ItemsOrder
 */

public class ItemsOrder {

    private static ItemsOrder itemsOrder;

    @Getter
    @Setter
    private String idProduct;
    @Getter
    @Setter
    private int qtdProduct;
    @Getter
    @Setter
    private double valueProduct;

    private ItemsOrder() {

    }

    public static ItemsOrder getInstance() {
        if (itemsOrder == null) {
            itemsOrder = new ItemsOrder();
        }

        return itemsOrder;
    }
}