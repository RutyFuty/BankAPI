package ru.sberbank.dsmelnikov.bankapi.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sberbank.dsmelnikov.bankapi.service.impl.ClientServiceImpl;

@Configuration
public class ApplicationConfig {

    @Bean
    CommandLineRunner commandLineRunner(ClientServiceImpl clientService) {
        return args -> {

        };
    }

}
