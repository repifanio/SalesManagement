package com.camargo.salesmanagement.services;

import java.io.File;
import java.util.Date;

import com.camargo.salesmanagement.controller.Reader;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// @uthor: Camargo 
// Date: 17/02/2020 
// commentary: Classe que realiza a varredurao diretório especificado no arquivo application.properties

@Component
public class ScheduledTasks {

    private @Value("${app.config.folders.in}") String folderIn;

    private @Value("${app.config.folders.processed}") String folderProcessed;

    final static Logger logger = Logger.getLogger(ScheduledTasks.class);

    private File file;

    public ScheduledTasks() {

    }

    @Scheduled(fixedRate = 10000)
    public void scanFolderIn() {
        this.file = new File(System.getProperty("user.dir") + folderIn);

        // Verifica se encontrou algum arquivo no diretório
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                logger.info("Arquivo: " + f.getName() + " lido no diretório: " + f.getAbsolutePath());

                // Inicializa a classe para ler o arquivo, após a leitura move o mesmo para o
                // diretório de processados
                Reader read = Reader.getInstance();
                read.readFile(f.getAbsolutePath());
                f.renameTo(new File(System.getProperty("user.dir") + folderProcessed, f.getName()));
            } else {
                // Só coloca a mensagem no console para conhecimento. Não à necessidade de
                // 'logar' essa informação.
                System.out.println(new Date() + " - Não foi encontrado nenhum arquivo no diretório de entrada.");
            }
        }

    }
}