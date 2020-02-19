package com.camargo.salesmanagement.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.camargo.salesmanagement.repositories.Client;
import com.camargo.salesmanagement.repositories.Order;
import com.camargo.salesmanagement.repositories.OrderBuilder;
import com.camargo.salesmanagement.repositories.SaleMens;
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
    ArrayList<SaleMens> salesMens;

    private Reader() {
        orders = new ArrayList<>();
        clients = new ArrayList<>();
        salesMens = new ArrayList<>();
    }

    // Método que permite a implementação do pattern singleton
    public static Reader getInstance() {
        if (reader == null) {
            reader = new Reader();
        }
        return reader;
    }

    public void readFile(String url) {
        int numberLine = 0;
        try {
            BufferedReader file = new BufferedReader(new FileReader(url));

            // chama o método responsável por quebrar a linha do arquivo de acordo com as
            // especificações passadas para o projeto.
            while (file.ready()) {
                numberLine++;
                deconstructString(file.readLine(), numberLine); // envia o número da linha para que o log registre uma
                                                                // possível linha com falha de leitura
            }
            file.close();
        } catch (IOException e) {
            logger.error("Falha ao ler o arquivo, verifique o stackTrace da falha: " + e.fillInStackTrace());
        } catch (NullPointerException e) {
            logger.error("Falha ao ler o arquivo, verifique o stackTrace da falha: " + e.fillInStackTrace());
        }

        // Envia as informações lidas e processadas no arquivo para a classe responsável
        // por manipular os dados.

    }

    // Método para quebrar a string identificando se é: Cliente, Vendedor ou uma
    // venda.
    // A ideia esse método é instanciar um objeto já criado e inclui-lo em seu array
    // específico para posteriormente ser análisado.
    private void deconstructString(String line, int numberLine) {
        try {

            String[] informationsOfLineArray = line.split("ç");

            if (informationsOfLineArray.length == 4) { // Verifica se a linha está no formato correto

                switch (informationsOfLineArray[0]) {
                    case "001":
                        SaleMens salesMen = SaleMens.getInstance();
                        salesMen.setCpf(informationsOfLineArray[1]);
                        salesMen.setName(informationsOfLineArray[2]);
                        salesMen.setSalary(Double.parseDouble(informationsOfLineArray[3]));

                        this.salesMens.add(salesMen);

                        break;
                    case "002":
                        Client client = Client.getInstance();
                        client.setCnpj(informationsOfLineArray[1]);
                        client.setName(informationsOfLineArray[2]);
                        client.setBusinesArea(informationsOfLineArray[3]);

                        this.clients.add(client);

                        break;
                    case "003":
                        // Para inserir a venda, o sistema utiliza o pattern builder
                        Order order = new OrderBuilder()
                                .setOrder(informationsOfLineArray[1], informationsOfLineArray[3])
                                .setItemsOrder(informationsOfLineArray[2]).builder();
                        this.orders.add(order);

                        break;
                }
            } else {
                logger.error("Falha ao ler a linha de número: " + numberLine
                        + ". A mesma não foi computada para o calculo final.");
            }
            // Identifica qualquer linha com defeito, inclui a informaçã no log e continua o
            // processamento do arquivo.
        } catch (Exception e) {
            logger.error("Falha ao ler a linha de número: " + numberLine
                    + ". A mesma não foi computada para o calculo final.");
        }
    }

    public void getReport() {
        if ((orders.size() != 0) || (clients.size() != 0) || (salesMens.size() != 0)) {
            Writer write = Writer.getInstance();
            write.setOrders(orders);
            write.setClients(clients);
            write.setSales(salesMens);

            write.writeFileOutput();

            orders.clear();
            clients.clear();
            salesMens.clear();
        }
    }

}