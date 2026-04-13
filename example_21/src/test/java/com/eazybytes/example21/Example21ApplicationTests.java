package com.eazybytes.example21;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test de démarrage du contexte Spring.
 *
 * @SpringBootTest : charge le contexte Spring complet.
 * Ce test vérifie que l'application démarre correctement
 * sans erreur de configuration (beans, datasource, etc.).
 *
 * Pour lancer les tests :
 *   mvn test
 */
@SpringBootTest
class Example21ApplicationTests {

    @Test
    void contextLoads() {
        // Ce test réussit si le contexte Spring démarre sans erreur.
    }

}
