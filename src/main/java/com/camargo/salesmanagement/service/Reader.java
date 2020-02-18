package com.camargo.salesmanagement.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.camargo.salesmanagement.repositories.Client;
import com.camargo.salesmanagement.repositories.Order;
import com.camargo.salesmanagement.repositories.OrderBuilder;
import com.camargo.salesmanagement.repositories.Sale;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

// @uthor: Camargo 
// Date: 17/02/2020 
// commentary: Classe que realiza a leitura do arquivo e inicializa a escrita do arquivo de retorno

@Component
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

    // Método que permite a implementação do pattern singleton
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

            // chama o método responsável por quebrar a linha do arquivo de acordo com as
            // especificações passadas para o projeto.
            while (file.ready()) {
                count++;
                deconstructString(file, count); // envia esse count para que o log registre uma possível linha com falha
                                                // de leitura
            }
            file.close();

        } catch (IOException e) {
            logger.error("Falha ao ler o arquivo, verifique o stackTrace da falha: " + e.fillInStackTrace());
        } catch (NullPointerException e) {
            logger.error("Falha ao ler o arquivo, verifique o stackTrace da falha: " + e.fillInStackTrace());
        }

        // Envia as informações lidas e processadas no arquivo para a classe responsável
        // por manipular os dados.
        Writer write = Writer.getInstance();
        write.setOrders(orders);
        write.setClients(clients);
        write.setSales(sales);

        // Chama o método que manipula as inormações e gera o arquivo de saída.
        write.writeFileOutput();
    }

    // Método para quebrar a string identificando se é: Cliente, Vendedor ou uma
    // venda.
    // A ideia esse método é instanciar um objeto já criado e inclui-lo em seu array
    // específico para posteriormente ser análisado.
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
                    // Para inserir a venda, o sistema utiliza o pattern builder
                    Order order = new OrderBuilder().setOrder(arrayPartes[1], arrayPartes[3])
                            .setItemsOrder(arrayPartes[2]).builder();
                    this.orders.add(order);

                    break;
            }
            // Identifica qualquer linha com defeito, inclui a informaçã no log e continua o
            // processamento do arquivo.
        } catch (Exception e) {
            logger.error("Falha ao ler a linha de número: " + numberLine
                    + ". A mesma não foi computada para o calculo final.");
        }
    }

}