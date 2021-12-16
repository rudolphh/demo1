package com.rudyah.demo1.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.AUGUST;
import static java.time.Month.DECEMBER;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner (StudentRepository repository){
        return args -> {
            Student imogen = new Student("imi", "imogen@rudyah.com", LocalDate.of(2018, AUGUST, 7));
            Student noah = new Student("noah", "noah@rudyah.com", LocalDate.of(2003, DECEMBER, 10));

            repository.saveAll(List.of(imogen, noah));
        };
    }
}
