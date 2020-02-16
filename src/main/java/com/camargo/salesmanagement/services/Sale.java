package com.camargo.salesmanagement.services;

import lombok.Getter;
import lombok.Setter;

/**
 * Sale
 */
@Getter
@Setter
public class Sale {

    private static Sale sale;

    @Getter
    @Setter
    private String cpf;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private double salary;

    private Sale() {

    }

    public static Sale getInstance() {
        if (sale == null) {
            sale = new Sale();
        }

        return sale;
    }

}