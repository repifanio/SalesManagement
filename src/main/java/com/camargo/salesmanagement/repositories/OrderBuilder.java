package com.camargo.salesmanagement.repositories;

import java.util.ArrayList;

/**
 * OrerBuilder
 */
public class OrderBuilder {

    private Order instancia;

    public OrderBuilder() {
        this.instancia = new Order();
    }

    public OrderBuilder setOrder(String id, String nameVendor) {
        instancia.setIdOrder(id);
        instancia.setNameVendor(nameVendor);
        return this;
    }

    public OrderBuilder setItemsOrder(String items) {

        int endCharacterPosition = items.indexOf("]");
        String itemsCleared = items.substring(1, endCharacterPosition);

        String[] arrayItems = itemsCleared.split(",");

        for (String itemSale : arrayItems) {

            String[] item = itemSale.split("-");

            ItemsOrder itemsOrder = new ItemsOrder();
            itemsOrder.setIdProduct(item[0]);
            itemsOrder.setQtdProduct(Integer.parseInt(item[1]));
            itemsOrder.setValueProduct(Double.parseDouble(item[2]));

            if (instancia.getItemsOrder() == null) {
                instancia.setItemsOrder(new ArrayList<>());
            }

            instancia.getItemsOrder().add(itemsOrder);
        }
        return this;
    }

    public Order builder() {
        return instancia;
    }
}