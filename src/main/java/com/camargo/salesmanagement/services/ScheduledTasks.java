package com.camargo.salesmanagement.services;

import java.io.File;
import java.util.Date;

import com.camargo.salesmanagement.controller.Reader;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    final static Logger logger = Logger.getLogger(ScheduledTasks.class);

    private File file;

    public ScheduledTasks() {
        this.file = new File(System.getProperty("user.dir") + "/data/in");
    }

    // @Scheduled(fixedRate = 10000)
    public void reportCurrentTime() {

        for (File f : file.listFiles()) {
            if (f.isFile()) {
                logger.info("Arquivo: " + f.getName() + " lido no diretório: " + f.getAbsolutePath());

                Reader read = Reader.getInstance();
                read.readFile(f.getAbsolutePath());
                f.renameTo(new File(System.getProperty("user.dir") + "/data/in/processed", f.getName()));
            } else {
                System.out.println(new Date() + " - Não foi encontrado nenhum arquivo no diretório de entrada.");
            }
        }

    }
}