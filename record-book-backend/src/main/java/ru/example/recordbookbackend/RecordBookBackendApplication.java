package ru.example.recordbookbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecordBookBackendApplication {

    public static void main(String[] args) {
        SignatureValidator.validate();
        SpringApplication.run(RecordBookBackendApplication.class, args);
    }

}
