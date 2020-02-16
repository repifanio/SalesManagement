package com.camargo.salesmanagement.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.camargo.salesmanagement.services.Client;
import com.camargo.salesmanagement.services.Order;
import com.camargo.salesmanagement.services.OrderBuilder;
import com.camargo.salesmanagement.services.Sale;

/**
 * Reader
 */
public class Reader {

    private static Reader reader;

    ArrayList<Order> orders;
    ArrayList<Client> clients;
    ArrayList<Sale> sales;

    private Reader() {

    }

    public static Reader getInstance() {
        if (reader == null) {
            reader = new Reader();
        }
        return reader;
    }

    public void readFile(String url) {

        try {
            BufferedReader file = new BufferedReader(new FileReader(url));

            while (file.ready()) {

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
                    Order order = new OrderBuilder().setOrder(arrayPartes[1], arrayPartes[3])
                            .setItemsOrder(arrayPartes[2]).builder();
                    this.orders.add(order);

                    break;
                }
            }
            file.close();

        } catch (IOException e) {
            System.out.println("Failed on read file. See error log: " + e.toString());

        } catch (NullPointerException e) {
            System.out.println("Failed on read file. See error log: " + e.toString());

        } catch (NumberFormatException e) {
            System.out.println("Failed on read file. See error log: " + e.toString());

        }

        Writer write = Writer.getInstance();
        write.setOrders(orders);
        write.setClients(clients);
        write.setSales(sales);

        write.writeFileOutput();
    }

}