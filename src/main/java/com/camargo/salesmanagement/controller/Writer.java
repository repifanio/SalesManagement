package com.camargo.salesmanagement.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.camargo.salesmanagement.services.Client;
import com.camargo.salesmanagement.services.ItemsOrder;
import com.camargo.salesmanagement.services.Order;
import com.camargo.salesmanagement.services.Sale;

import lombok.Getter;
import lombok.Setter;

/**
 * Writer
 */

public class Writer {

    private static Writer writer;

    @Getter
    @Setter
    private ArrayList<Order> orders;
    @Getter
    @Setter
    private ArrayList<Client> clients;
    @Getter
    @Setter
    private ArrayList<Sale> sales;

    private Writer() {

    }

    public static Writer getInstance() {
        if (writer == null) {
            writer = new Writer();
        }

        return writer;
    }

    public String getBiggOrder() {
        double biggerSale = 0;
        double valueSale = 0;
        String idBiggerSale = "";

        for (Order order : orders) {

            ArrayList<ItemsOrder> items = order.getItemsOrder();

            for (ItemsOrder itemsOrder : items) {
                valueSale = (itemsOrder.getQtdProduct() * itemsOrder.getValueProduct());

                if (valueSale > biggerSale) {
                    biggerSale = valueSale;
                    idBiggerSale = itemsOrder.getIdProduct();
                }
            }
        }

        return idBiggerSale + " com valor total de: " + String.valueOf(biggerSale);
    }

    public String getPiorVendedor() {
        Map<String, Double> map = new HashMap<>();

        for (Order order : orders) {

            ArrayList<ItemsOrder> items = order.getItemsOrder();

            for (ItemsOrder itemsOrder : items) {
                double varTest;
                try {
                    varTest = map.get(order.getNameVendor());
                } catch (Exception ex) {
                    varTest = 0;
                }
                map.put(order.getNameVendor(), (varTest + (itemsOrder.getValueProduct() * itemsOrder.getQtdProduct())));
            }
        }

        return verificaPiorVendedor(map);

    }

    public String verificaPiorVendedor(Map<String, Double> map) {
        double test = 99999999.00;
        String nomeVendedor = "";
        for (Map.Entry<String, Double> entry : map.entrySet()) {

            if (test > entry.getValue()) {
                test = entry.getValue();
                nomeVendedor = entry.getKey();
            }
        }

        return nomeVendedor;
    }

    public void writeFileOutput() {
        try {
            FileWriter arq = new FileWriter(System.getProperty("user.dir") + "/data/out/testeResposta.dat");
            PrintWriter gravarArq = new PrintWriter(arq);

            gravarArq.printf("+--Resultado--+%n");
            gravarArq.printf("O Número total de clientes é: " + clients.size() + "%n");
            gravarArq.printf("O Número total de vendedores é: " + sales.size() + "%n");
            gravarArq.printf("A venda mais cara foi a venda com ID: " + getBiggOrder() + "%n");
            gravarArq.printf("O pior vendedor foi: " + getPiorVendedor() + "%n");
            gravarArq.printf("+-------------+%n");

            arq.close();
        } catch (

        IOException e) {

        }

    }
}