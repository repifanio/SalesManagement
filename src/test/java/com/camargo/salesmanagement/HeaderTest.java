package com.camargo.salesmanagement;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import com.camargo.salesmanagement.service.Reader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HeaderTest {

    @Test
    public void readFileTest() {

        assertThrows(NullPointerException.class, new Executable() {

            @Override
            public void execute() throws Throwable {
                Reader read = Reader.getInstance();
                read.readFile("url");
            }
        });
    }
}