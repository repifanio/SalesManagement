package com.camargo.salesmanagement.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.camargo.salesmanagement.services.Client;
import com.camargo.salesmanagement.services.ItemsOrder;
import com.camargo.salesmanagement.services.Order;
import com.camargo.salesmanagement.services.Sale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

// @uthor: Camargo 
// Date: 17/02/2020 
// commentary: Classe que tem por finalidade ler as informações geradas e criar o arquivo de saída.

@Component
public class Writer {

    public static String folderOut;

    @Value("${app.config.folders.out}")
    public void setfolderOut(String folderOut) {
        this.folderOut = folderOut;
    }

    private static Writer writer;

    final static Logger logger = Logger.getLogger(Reader.class);

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

    // Método que permite a implementação do pattern singleton
    public static Writer getInstance() {
        if (writer == null) {
            writer = new Writer();
        }

        return writer;
    }

    // Método responsável por verificar qual foi a maior venda.
    // Basicamente faz um loop dentro de todas as vendas armazenando sempre o
    // maifolderOutor
    // valor
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

    // Método insere em um map todos os vendedores e suas respectivas vendas
    public String insertOrdersOfVendors() {
        Map<String, Double> map = new HashMap<>();
        double varTest;

        for (Order order : orders) {

            ArrayList<ItemsOrder> items = order.getItemsOrder();

            for (ItemsOrder itemsOrder : items) {
                try {
                    varTest = map.get(order.getNameVendor());
                } catch (Exception ex) {
                    varTest = 0;
                }
                map.put(order.getNameVendor(), (varTest + (itemsOrder.getValueProduct() * itemsOrder.getQtdProduct())));
            }
        }

        return scanForMostBadVendor(map);

    }

    // Método que percorre o map comparando as vendas dos vendedores.
    public String scanForMostBadVendor(Map<String, Double> map) {
        double test = 99999999.00;
        String nameVendor = "";
        for (Map.Entry<String, Double> entry : map.entrySet()) {

            if (test > entry.getValue()) {
                test = entry.getValue();
                nameVendor = entry.getKey();
            }
        }

        return nameVendor;
    }

    // Método que consolida as informações no arquivo de saida.
    public void writeFileOutput() {
        try {

            FileWriter arq = new FileWriter(System.getProperty("user.dir") + folderOut + returnFileName());
            PrintWriter saveArq = new PrintWriter(arq);

            saveArq.printf("+--Resultado--+%n");
            saveArq.printf("O Número total de clientes é: " + clients.size() + "%n");
            saveArq.printf("O Número total de vendedores é: " + sales.size() + "%n");
            saveArq.printf("O Número total de vendas é: " + orders.size() + "%n");
            saveArq.printf("A venda mais cara foi a venda com ID: " + getBiggOrder() + "%n");
            saveArq.printf("O pior vendedor foi: " + insertOrdersOfVendors() + "%n");
            saveArq.printf("+-------------+%n");

            arq.close();

        } catch (IOException e) {
            logger.error("Falha ao gravar arquivo de retorno. StackTrace: " + e.fillInStackTrace());
        }

    }

    public String returnFileName() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        return "Report_" + formatter.format(new Date()) + ".dat";
    }
}