package com.camargo.salesmanagement.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.camargo.salesmanagement.repositories.Client;
import com.camargo.salesmanagement.repositories.ItemsOrder;
import com.camargo.salesmanagement.repositories.Order;
import com.camargo.salesmanagement.repositories.SaleMens;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// @uthor: Camargo 
// Date: 17/02/2020 
// commentary: Classe que tem por finalidade ler as informações geradas e criar o arquivo de saída.

@Component
public class Writer {

    public static String folderOut;

    @Value("${app.config.folders.out}")
    public void setfolderOut(String folderOut) {
        Writer.folderOut = folderOut;
    }

    private static Writer writer;

    final static Logger logger = Logger.getLogger(Reader.class);

    private ArrayList<Order> orders;
    private ArrayList<Client> clients;
    private ArrayList<SaleMens> sales;

    private Writer() {

    }

    // Método que permite a implementação do pattern singleton
    public static Writer getInstance() {
        if (writer == null) {
            writer = new Writer();
        }

        return writer;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    public ArrayList<SaleMens> getSales() {
        return sales;
    }

    public void setSales(ArrayList<SaleMens> sales) {
        this.sales = sales;
    }

    public String createOutFolderIfNotExistOrUse() {
        File folder = new File(System.getProperty("user.dir") + folderOut);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        return folderOut;
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
    public String insertOrdersOfVendorsOnMap() {
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
        double minValue = 99999999.00;
        String nameVendor = "";
        for (Map.Entry<String, Double> entry : map.entrySet()) {

            if (minValue > entry.getValue()) {
                minValue = entry.getValue();
                nameVendor = entry.getKey();
            }
        }

        return nameVendor;
    }

    // Método que consolida as informações no arquivo de saida.
    public void writeFileOutput() {
        try {

            SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm");

            FileWriter arq = new FileWriter(
                    System.getProperty("user.dir") + createOutFolderIfNotExistOrUse() + returnFileName());
            PrintWriter saveArq = new PrintWriter(arq);

            saveArq.printf("+--Resultado--+%n");
            saveArq.printf("--------------------- " + dataFormat.format(new Date()) + " ---------------------%n");
            saveArq.printf("Resumo da leitura do lote de arquivos:%n");
            saveArq.printf("O Número total de clientes é: " + clients.size() + "%n");
            saveArq.printf("O Número total de vendedores é: " + sales.size() + "%n");
            saveArq.printf("O Número total de vendas é: " + orders.size() + "%n");
            saveArq.printf("A venda mais cara foi a venda com ID: " + getBiggOrder() + "%n");
            saveArq.printf("O pior vendedor foi: " + insertOrdersOfVendorsOnMap() + "%n");
            saveArq.printf("+-------------+%n");

            arq.close();

        } catch (IOException e) {
            logger.error("Falha ao gravar arquivo de retorno. StackTrace: " + e.fillInStackTrace());
        }

    }

    public String returnFileName() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-SSS");
        return "Report_" + formatter.format(new Date()) + ".dat";
    }
}