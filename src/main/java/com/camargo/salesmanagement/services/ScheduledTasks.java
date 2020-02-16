package com.camargo.salesmanagement.services;

import java.io.File;

import com.camargo.salesmanagement.controller.Reader;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private File file;

    public ScheduledTasks() {
        this.file = new File(System.getProperty("user.dir") + "/data/in");
    }

    @Scheduled(fixedRate = 1000)
    public void reportCurrentTime() {

        for (File f : file.listFiles()) {
            if (f.isFile()) {
                System.out.println("Arquivo: " + f.getName() + " lido no diretório: " + f.getAbsolutePath());

                // Reader read = new Reader();

                Reader read = Reader.getInstance();
                read.readFile(f.getAbsolutePath());
                f.renameTo(new File(System.getProperty("user.dir") + "/data/in/processed", f.getName()));
            } else {
                System.out.println("Não foi encontrado nenhum arquivo no diretório de entrada.");
            }
        }

    }
}