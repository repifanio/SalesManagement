package com.camargo.salesmanagement.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.camargo.salesmanagement.services.Client;
import com.camargo.salesmanagement.services.Order;
import com.camargo.salesmanagement.services.OrderBuilder;
import com.camargo.salesmanagement.services.Sale;

import org.apache.log4j.Logger;

/**
 * Reader
 */
public class Reader {

    final static Logger logger = Logger.getLogger(Reader.class);

    private static Reader reader;

    ArrayList<Order> orders;
    ArrayList<Client> clients;
    ArrayList<Sale> sales;

    private Reader() {
        orders = new ArrayList<>();
        clients = new ArrayList<>();
        sales = new ArrayList<>();
    }

    public static Reader getInstance() {
        if (reader == null) {
            reader = new Reader();
        }
        return reader;
    }

    public void readFile(String url) {
        int count = 0;
        try {
            BufferedReader file = new BufferedReader(new FileReader(url));

            while (file.ready()) {
                count++;
                deconstructString(file, count);
            }
            file.close();

        } catch (IOException e) {
            logger.error("Falha ao ler o arquivo, verifique o stackTrace da falha: " + e.fillInStackTrace());
        } catch (NullPointerException e) {
            logger.error("Falha ao ler o arquivo, verifique o stackTrace da falha: " + e.fillInStackTrace());
        }

        Writer write = Writer.getInstance();
        write.setOrders(orders);
        write.setClients(clients);
        write.setSales(sales);

        write.writeFileOutput();
    }

    private void deconstructString(BufferedReader file, int numberLine) {
        try {

            String[] arrayPartes = file.readLine().split("ç");

            switch (arrayPartes[0]) {
            case "001":
                Sale sale = Sale.getInstance();
                sale.setCpf(arrayPartes[1]);
                sale.setName(arrayPartes[2]);
                sale.setSalary(Double.parseDouble(arrayPartes[3]));

                this.sales.add(sale);

                break;
            case "002":
                Client client = Client.getInstance();
                client.setCnpj(arrayPartes[1]);
                client.setName(arrayPartes[2]);
                client.setBusinesArea(arrayPartes[3]);

                this.clients.add(client);

                break;
            case "003":
                Order order = new OrderBuilder().setOrder(arrayPartes[1], arrayPartes[3]).setItemsOrder(arrayPartes[2])
                        .builder();
                this.orders.add(order);

                break;
            }
        } catch (Exception e) {
            logger.error("Falha ao ler a linha de número: " + numberLine
                    + ". A mesma não foi computada para o calculo final.");
        }
    }

}