package com.camargo.salesmanagement.repositories;

/**
 * Client
 */

public class Client {

    private static Client client;

    private String cnpj;
    private String name;
    private String businesArea;

    private Client() {

    }

    public static Client getInstance() {
        if (client == null) {
            client = new Client();
        }
        return client;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinesArea() {
        return businesArea;
    }

    public void setBusinesArea(String businesArea) {
        this.businesArea = businesArea;
    }
}