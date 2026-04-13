package com.eazybytes.example21;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entrée de l'application Spring Boot.
 *
 * L'annotation @SpringBootApplication regroupe trois annotations :
 *   - @Configuration       : cette classe peut définir des beans Spring
 *   - @EnableAutoConfiguration : Spring Boot configure automatiquement
 *                               l'application selon les dépendances présentes
 *                               (ex : détecte H2 → configure DataSource)
 *   - @ComponentScan       : scanne le package courant et ses sous-packages
 *                            pour trouver les composants (@Service, @Repository,
 *                            @Controller, etc.)
 *
 * Pour lancer l'application :
 *   - Via Maven  : mvn spring-boot:run
 *   - Via IDE    : clic droit → Run 'Example21Application'
 *   - Via JAR    : mvn package  puis  java -jar target/example_21-0.0.1-SNAPSHOT.jar
 */
@SpringBootApplication
public class Example21Application {

    public static void main(String[] args) {
        SpringApplication.run(Example21Application.class, args);
    }

}
