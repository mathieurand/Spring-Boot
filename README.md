# Spring-Boot — Résumé du dépôt (en français)

Ce dépôt contient **20 exemples progressifs** illustrant les concepts fondamentaux de Spring Framework et Spring Boot.
Chaque exemple est un projet Maven autonome.

---

## 1. Structure racine

```
Spring-Boot/
├── README.md
├── example1/          ← Spring Context – création de beans via @Bean
├── example2/          ← Spring Context – plusieurs beans du même type (NoUniqueBeanDefinitionException)
├── example3/          ← Spring Context – nommage des beans (@Bean name/value)
├── example4/          ← Spring Context – bean primaire (@Primary)
├── example5/          ← Spring Context – stéréotypes (@Component + @ComponentScan)
├── example6/          ← Spring Context – cycle de vie (@PostConstruct / @PreDestroy)
├── example7/          ← Spring Context – enregistrement programmatique (registerBean + Supplier)
├── example8/          ← Spring Context – configuration XML (ClassPathXmlApplicationContext)
├── example9/          ← Spring Context – injection de dépendances (wiring direct via méthode)
├── example_10/        ← Spring Context – injection de dépendances (paramètre de méthode @Bean)
├── example_11/        ← Spring Context – @Autowired avec stéréotypes
├── example_12/        ← Spring Context – @Autowired + @Primary pour résoudre l'ambiguïté
├── example_13/        ← Spring Context – interfaces / implémentations multiples + @Qualifier
├── example_14/        ← Spring Context – portée Singleton (scope par défaut)
├── example_15/        ← Spring Context – cycle de vie des beans (logs d'initialisation)
├── example_16/        ← Spring Context – portée Prototype (@Scope("prototype"))
├── example_17/        ← Spring AOP – aspects (@Aspect, @Around, @Before, @AfterThrowing, @AfterReturning)
├── example_18/        ← Spring Boot MVC – page statique (HTML servi depuis /static)
├── example_19/        ← Spring Boot MVC + Thymeleaf – template avec variable de modèle
└── example_20/        ← Spring Boot MVC + Thymeleaf – page d'accueil complète (Bootstrap/W3Layouts)
```

---

## 2. Outil de construction

**Apache Maven** est utilisé dans tous les exemples (`pom.xml` à la racine de chaque dossier).

| Exemples | Parent / dépendance principale | Version Spring |
|---|---|---|
| example1 – example_17 | `spring-context` (Spring Framework pur, **pas** Spring Boot) | 6.0.11 |
| example_18 – example_20 | `spring-boot-starter-parent` (Spring Boot complet) | 3.1.2 |

Les exemples 18, 19 et 20 incluent également **Maven Wrapper** (`mvnw` / `mvnw.cmd`) pour lancer Maven sans installation préalable.

---

## 3. Point d'entrée Spring Boot

Les trois seuls projets Spring Boot complets (18, 19, 20) partagent la même classe principale :

```java
// src/main/java/com/eazybytes/eazyschool/EazyschoolApplication.java
@SpringBootApplication
public class EazyschoolApplication {
    public static void main(String[] args) {
        SpringApplication.run(EazyschoolApplication.class, args);
    }
}
```

> **Note (example_18)** : la ligne `@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })` est présente en commentaire, ce qui indique que la configuration JPA/DataSource a été désactivée intentionnellement.

Les exemples 1 à 17 utilisent directement `AnnotationConfigApplicationContext` (ou `ClassPathXmlApplicationContext` pour example8) ; ils n'ont pas de point d'entrée Spring Boot.

---

## 4. Contrôleurs (Controllers)

Seuls les exemples 18, 19 et 20 possèdent un contrôleur Spring MVC.

### example_18 — Contrôleur avec ressource statique
```java
@Controller
public class HomeController {
    @RequestMapping("/home")
    public String displayHomePage() {
        return "home.html";   // renvoie src/main/resources/static/home.html
    }
}
```

### example_19 — Contrôleur Thymeleaf avec Model
```java
@Controller
public class HomeController {
    @RequestMapping(value = {"", "/", "home"})
    public String displayHomePage(Model model) {
        model.addAttribute("username", "John Doe");
        return "home.html";   // renvoie src/main/resources/templates/home.html
    }
}
```

### example_20 — Contrôleur Thymeleaf (page complète)
```java
@Controller
public class HomeController {
    @RequestMapping(value = {"", "/", "home"})
    public String displayHomePage() {
        return "home.html";   // renvoie src/main/resources/templates/home.html
    }
}
```

---

## 5. Services

### example_13 à example_16
Classe `VehicleServices` (annotée `@Component`) qui injecte les interfaces `Speakers` et `Tyres` et expose les méthodes `playMusic()` et `moveVehicle()`.

### example_17 (AOP)
`VehicleServices` expose trois méthodes publiques décorées par des aspects :
- `playMusic(boolean vehicleStarted, Song song)` — annotée `@LogAspect`
- `moveVehicle(boolean vehicleStarted)`
- `applyBrake(boolean vehicleStarted)`

Deux aspects (`@Aspect + @Component`) gèrent la logique transversale :
- **`VehicleStartCheckAspect`** (`@Order(1)`) : advice `@Before` — vérifie que le véhicule est démarré avant chaque méthode du service.
- **`LoggerAspect`** (`@Order(2)`) : advice `@Around` — mesure et journalise le temps d'exécution ; `@AfterThrowing` — capture les exceptions ; `@AfterReturning` — journalise le retour.

---

## 6. Entités / Beans métier

| Classe | Exemples | Rôle |
|---|---|---|
| `Vehicle` | 1 – 17 | Bean principal représentant un véhicule (champ `name`) |
| `Person` | 9 – 16 | Bean avec relation vers `Vehicle` (injection de dépendance) |
| `Song` | 17 | Modèle simple (champs `title`, `singerName`) |
| `Speakers` / `Tyres` | 13 – 17 | Interfaces avec implémentations (`BoseSpeakers`, `SonySpeakers`, `BridgeStoneTyres`, `MichelinTyres`) |

> **Aucun repository JPA** (`JpaRepository`) n'est présent dans ce dépôt. Les exemples ne font pas appel à une base de données.

---

## 7. Ressources (templates / static)

### Fichiers statiques
| Fichier | Exemple | Description |
|---|---|---|
| `src/main/resources/static/home.html` | example_18 | Page HTML simple « Welcome to Eazy School! » |
| `src/main/resources/beans.xml` | example8 | Configuration Spring au format XML (définition du bean `Vehicle`) |

### Templates Thymeleaf
| Fichier | Exemple | Description |
|---|---|---|
| `src/main/resources/templates/home.html` | example_19 | Affiche `Hey, John Doe !!! Welcome to Eazy School.` via Thymeleaf (`th:text`) |
| `src/main/resources/templates/home.html` | example_20 | Page d'accueil complète «Eazy School» avec Bootstrap, navigation, bannière, sections cours/témoignages/footer (design W3Layouts) |

---

## 8. Configuration

### example1 – example8
`ProjectConfig.java` avec `@Configuration` + méthodes `@Bean` déclarant manuellement les beans (Vehicle, String, Integer).

### example5 – example7
`@ComponentScan(basePackages = "com.example.beans")` pour détecter automatiquement les classes `@Component`.

### example6
Ajoute la dépendance `jakarta.annotation-api` pour utiliser `@PostConstruct` / `@PreDestroy` (cycle de vie des beans).

### example8
Utilise `ClassPathXmlApplicationContext("beans.xml")` à la place de la configuration Java.

### example9 – example12
Injection de dépendances entre beans : par appel de méthode (example9), par paramètre (example_10), via `@Autowired` (example_11), `@Autowired` + `@Primary` (example_12).

### example_13 – example_16
`@ComponentScan` sur plusieurs packages (`implementation`, `services`, `beans`). Démontre Singleton (example_14) vs Prototype (example_16).

### example_17
`@EnableAspectJAutoProxy` activé dans `ProjectConfig` pour supporter la programmation orientée aspects (AOP).

### example_18
`application.properties` — port commenté (`#server.port=8081`), `debug=true`.

### example_19
`application.properties` — options Thymeleaf commentées (cache, préfixe).

### example_20
`application.properties` — vide (configuration par défaut Spring Boot).

---

## 9. Tests

Les tests sont présents **uniquement dans les exemples 18, 19 et 20** :

```java
@SpringBootTest
class EazyschoolApplicationTests {
    @Test
    void contextLoads() {
        // Vérifie que le contexte Spring démarre sans erreur
    }
}
```

Les exemples 1 à 17 ne contiennent **aucun test automatisé**.

---

## 10. Instructions pour lancer les projets

### Exemples 1 à 17 (Spring Framework pur — ligne de commande)
```bash
cd example1          # (ou example2, example3, … example_17)
mvn compile
mvn exec:java -Dexec.mainClass="com.example.main.Example1"
```
Ou depuis un IDE (IntelliJ IDEA / Eclipse) : ouvrir le dossier comme projet Maven et exécuter la classe `ExampleX.main()`.

### Exemples 18, 19 et 20 (Spring Boot — serveur web)
```bash
cd example_18        # (ou example_19, example_20)
./mvnw spring-boot:run
# Sur Windows :
mvnw.cmd spring-boot:run
```
Le serveur démarre sur **http://localhost:8080** (port par défaut).

| Exemple | URL d'accès |
|---|---|
| example_18 | http://localhost:8080/home |
| example_19 | http://localhost:8080/ ou http://localhost:8080/home |
| example_20 | http://localhost:8080/ ou http://localhost:8080/home |

> **Prérequis** : Java 17+ et Maven 3.8+ (ou utiliser le Maven Wrapper fourni `./mvnw`).

---

## Résumé pédagogique

Ce dépôt est un **cours progressif** sur Spring / Spring Boot couvrant :

1. **Contexte Spring** — création et récupération de beans (examples 1–8)
2. **Injection de dépendances** — wiring, `@Autowired`, `@Primary`, `@Qualifier` (examples 9–13)
3. **Portées de beans** — Singleton vs Prototype (examples 14–16)
4. **AOP** — aspects, advices, pointcuts avec AspectJ (example 17)
5. **Spring Boot MVC** — pages statiques, Thymeleaf, contrôleurs (examples 18–20)
