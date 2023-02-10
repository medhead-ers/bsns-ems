package com.medhead.ers.bsns_ems.application.data.sample;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSampleLoader {
    @Bean
    CommandLineRunner initDatabase() {
        return args -> {};
    }


}
