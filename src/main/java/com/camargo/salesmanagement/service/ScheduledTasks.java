package com.camargo.salesmanagement.service;

import java.io.File;

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

    private File folder;

    public ScheduledTasks() {

    }

    public File createInFolderIfNotExistOrUse() {
        this.folder = new File(System.getProperty("user.dir") + folderIn);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        return folder;
    }

    public String createProcessedFolderIfNotExistOrUse() {
        this.folder = new File(System.getProperty("user.dir") + folderProcessed);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        return folderProcessed;
    }

    @Scheduled(fixedRate = 10000)
    public void scanFolderIn() {

        // Inicializa a classe para ler o arquivo, após a leitura move o mesmo para o
        // diretório de processados
        Reader read = Reader.getInstance();

        // Verifica se encontrou algum arquivo no diretório
        for (File file : createInFolderIfNotExistOrUse().listFiles()) {
            if (file.isFile()) {
                read.readFile(file.getAbsolutePath()); // Faz a leitura do arquivo

                file.renameTo(new File(System.getProperty("user.dir") + createProcessedFolderIfNotExistOrUse(),
                        file.getName()));

                logger.info("Arquivo: " + file.getName() + " lido no diretório: " + file.getAbsolutePath());
            }
        }
        System.out.println("Nenhum arquivo para ser processado.");
        read.getReport();

    }
}