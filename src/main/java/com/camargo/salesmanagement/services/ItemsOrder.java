package com.camargo.salesmanagement.services;

import lombok.Getter;
import lombok.Setter;

/**
 * ItemsOrder
 */

@Getter
@Setter
public class ItemsOrder {

    private String idProduct;
    private int qtdProduct;
    private double valueProduct;

    public ItemsOrder() {

    }
}