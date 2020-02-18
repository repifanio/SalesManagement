package com.camargo.salesmanagement.repositories;

import lombok.Getter;
import lombok.Setter;

/**
 * Client
 */

public class Client {

    private static Client client;

    @Getter
    @Setter
    private String cnpj;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String businesArea;

    private Client() {

    }

    public static Client getInstance() {
        if (client == null) {
            client = new Client();
        }
        return client;
    }
}