# Spring-Boot — Résumé du dépôt (en français)

## 1. Vue d'ensemble

Ce dépôt est une **collection de 20 mini-projets pédagogiques** (tutoriels pas-à-pas) couvrant les fondamentaux du **framework Spring** puis de **Spring Boot**.  
Il est structuré comme un cours progressif inspiré de la série *EazyBytes* :

- **Examples 1 à 17** : Spring Core (sans Spring Boot) — injection de dépendances, contexte, scope des beans, AOP.  
- **Examples 18 à 20** : Spring Boot 3.x — application web MVC avec HTML statique et Thymeleaf.

---

## 2. Fichiers et dossiers à la racine

| Nom | Type | Description |
|-----|------|-------------|
| `README.md` | Fichier | Ce fichier de documentation |
| `example1/` … `example9/` | Dossiers | Projets Spring Core (numérotés 1 à 9) |
| `example_10/` … `example_20/` | Dossiers | Projets Spring Core/Boot (numérotés 10 à 20) |

Chaque dossier contient son propre projet Maven autonome avec :
- `pom.xml` — descripteur de build Maven
- `.gitignore` — fichiers à ignorer par Git
- `src/` — code source Java et ressources
- (pour les exemples 18-20) `mvnw` / `mvnw.cmd` — wrapper Maven

---

## 3. Outil de build

**Apache Maven** (version wrapper inclus pour les exemples 18-20).  
Chaque projet est indépendant et possède son propre `pom.xml`.

- Exemples 1-17 : `groupId = com.eazybytes`, dépendance principale `spring-context` **6.0.11** (Spring Framework pur, sans Spring Boot).  
- Exemples 18-20 : `groupId = com.eazybytes`, parent `spring-boot-starter-parent` **3.1.2**.

---

## 4. Description détaillée de chaque exemple

### Exemples 1 à 17 — Spring Core (sans Spring Boot)

Ces exemples utilisent `AnnotationConfigApplicationContext` (ou `ClassPathXmlApplicationContext` pour l'exemple 8) pour créer et gérer le contexte Spring.  
**Classe entrypoint** : `com.example.main.ExampleN` (méthode `main`).  
**Package de configuration** : `com.example.config.ProjectConfig`.

---

#### Example 1 — Premier bean `@Bean` / `@Configuration`
- **Concept** : Créer des beans manuellement avec `@Bean` dans une classe `@Configuration`.  
- **Beans** : `Vehicle` (nom « Audi 8 »), `String` (« Hello World »), `Integer` (16).  
- **Fichiers clés** :
  - `com.example.beans.Vehicle` — POJO avec champ `name`
  - `com.example.config.ProjectConfig` — déclare 3 beans via `@Bean`
  - `com.example.main.Example1` — récupère les beans par type (`getBean(Vehicle.class)`, `getBean(String.class)`, `getBean(Integer.class)`)

---

#### Example 2 — `NoUniqueBeanDefinitionException`
- **Concept** : Ambiguïté quand plusieurs beans du même type sont déclarés sans nom précis.  
- **Beans** : `vehicle1`, `vehicle2`, `vehicle3` de type `Vehicle`.  
- **Fichiers clés** :
  - `com.example.config.ProjectConfig` — 3 beans `Vehicle` sans `@Primary`
  - `com.example.main.Example2` — appel `getBean(Vehicle.class)` → exception (commenté / illustré)

---

#### Example 3 — Nommage des beans (`@Bean(name=…)`)
- **Concept** : Donner des noms explicites aux beans avec `@Bean(name=…)`, `@Bean(value=…)`, `@Bean("…")`.  
- **Beans nommés** : `audiVehicle`, `hondaVehicle`, `ferrariVehicle`.  
- **Fichiers clés** :
  - `com.example.config.ProjectConfig` — 3 beans nommés
  - `com.example.main.Example3` — récupère chaque bean par son nom

---

#### Example 4 — `@Primary`
- **Concept** : Désigner un bean par défaut parmi plusieurs beans du même type via `@Primary`.  
- **Bean primaire** : `ferrariVehicle`.  
- **Fichiers clés** :
  - `com.example.config.ProjectConfig` — `@Primary` sur `vehicle3()`
  - `com.example.main.Example4` — `getBean(Vehicle.class)` retourne le bean `@Primary`

---

#### Example 5 — `@Component` et `@ComponentScan`
- **Concept** : Enregistrer un bean automatiquement via les annotations stéréotypes `@Component`.  
- **Fichiers clés** :
  - `com.example.beans.Vehicle` — annotée `@Component`, méthode `printHello()`
  - `com.example.config.ProjectConfig` — `@ComponentScan(basePackages = "com.example.beans")`
  - `com.example.main.Example5` — appelle `vehicle.printHello()`

---

#### Example 6 — Cycle de vie du bean (`@PostConstruct` / `@PreDestroy`)
- **Concept** : Exécuter du code lors de l'initialisation et de la destruction d'un bean (Jakarta Annotation API).  
- **Dépendance supplémentaire** : `jakarta.annotation-api`.  
- **Fichiers clés** :
  - `com.example.beans.Vehicle` — méthodes annotées `@PostConstruct` et `@PreDestroy`
  - `com.example.main.Example6` — appelle `context.close()` pour déclencher `@PreDestroy`

---

#### Example 7 — Enregistrement programmatique de beans
- **Concept** : Enregistrer un bean à la volée avec `context.registerBean(…, Supplier)`.  
- **Fichiers clés** :
  - `com.example.main.Example7` — choisit aléatoirement d'enregistrer `volkswagen` ou `audi`, gère `NoSuchBeanDefinitionException`

---

#### Example 8 — Configuration XML (`beans.xml`)
- **Concept** : Déclarer les beans via un fichier XML classique (`ClassPathXmlApplicationContext`).  
- **Fichiers clés** :
  - `src/main/resources/beans.xml` — `<bean id="vehicle" class="com.example.beans.Vehicle">` avec `<property name="name" value="Honda"/>`
  - `com.example.main.Example8` — charge le contexte XML

---

#### Example 9 — Câblage de beans (wiring par appel de méthode)
- **Concept** : Établir une relation `Person` → `Vehicle` en appelant `vehicle()` depuis `person()` dans la config.  
- **Beans** : `Vehicle` (Toyota), `Person` (Lucy).  
- **Fichiers clés** :
  - `com.example.beans.Person` — champ `Vehicle`
  - `com.example.config.ProjectConfig` — `person()` appelle `vehicle()` directement

---

#### Example 10 — Câblage par injection de paramètre
- **Concept** : Câblage alternatif — Spring injecte automatiquement `vehicle` comme paramètre de la méthode `person(Vehicle vehicle)`.  
- **Fichiers clés** :
  - `com.example.config.ProjectConfig` — `person(Vehicle vehicle)` reçoit le bean en paramètre

---

#### Example 11 — `@Autowired` (setter et constructeur)
- **Concept** : Injection automatique via `@Autowired` sur setter et constructeur.  
- **Fichiers clés** :
  - `com.example.beans.Person` — `@Autowired` sur `setVehicle()` (setter injection), constructeur commenté
  - `com.example.config.ProjectConfig` — `@ComponentScan` uniquement

---

#### Example 12 — `@Qualifier`
- **Concept** : Lever l'ambiguïté avec `@Qualifier("nomDuBean")` quand plusieurs beans du même type existent.  
- **Fichiers clés** :
  - `com.example.beans.Person` — constructeur `@Autowired` avec `@Qualifier("vehicle2")`
  - `com.example.config.ProjectConfig` — 3 beans `Vehicle`, dont un `@Primary`

---

#### Example 13 — Interfaces, implémentations et services
- **Concept** : Architecture en couches — interfaces `Speakers` et `Tyres`, implémentations multiples, service `VehicleServices`.  
- **Interfaces** : `com.example.interfaces.Speakers`, `com.example.interfaces.Tyres`  
- **Implémentations** :
  - `BoseSpeakers`, `SonySpeakers` (implémentent `Speakers`)
  - `BridgeStoneTyres`, `MichelinTyres` (implémentent `Tyres`)
- **Service** : `com.example.services.VehicleServices` — `playMusic()`, `moveVehicle()`  
- **Fichiers clés** :
  - `com.example.main.Example13` — accède aux services via `person.getVehicle().getVehicleServices()`

---

#### Example 14 — Portée Singleton (Scope Singleton)
- **Concept** : Le scope par défaut Spring est **singleton** — deux appels `getBean()` retournent le même objet.  
- **Fichiers clés** :
  - `com.example.services.VehicleServices` — scope singleton (par défaut)
  - `com.example.main.Example14` — compare `hashCode()` des deux instances récupérées

---

#### Example 15 — Initialisation paresseuse (`@Lazy`)
- **Concept** : Bean créé uniquement lors de la première utilisation, pas au démarrage du contexte.  
- **Fichiers clés** :
  - `com.example.beans.Person` — annotée `@Component(value="personBean") @Lazy`
  - `com.example.main.Example15` — affiche des messages avant/après `getBean(Person.class)`

---

#### Example 16 — Portée Prototype (`@Scope(SCOPE_PROTOTYPE)`)
- **Concept** : Chaque appel à `getBean()` retourne une **nouvelle instance**.  
- **Fichiers clés** :
  - `com.example.services.VehicleServices` — `@Scope(BeanDefinition.SCOPE_PROTOTYPE)`
  - `com.example.main.Example16` — compare `hashCode()` → valeurs différentes

---

#### Example 17 — Programmation Orientée Aspect (AOP)
- **Concept** : Intercepter des méthodes avec `@Aspect`, `@Around`, `@Before`, `@AfterThrowing`, `@AfterReturning`.  
- **Dépendance supplémentaire** : `spring-aspects` 6.0.11.  
- **Aspects** :
  - `com.example.aspects.LoggerAspect` (`@Order(2)`) — loggue le temps d'exécution (`@Around`), les exceptions (`@AfterThrowing`), le résultat (`@AfterReturning`)
  - `com.example.aspects.VehicleStartCheckAspect` (`@Order(1)`) — vérifie si le véhicule est démarré avant d'appeler un service (`@Before`)
- **Interface d'annotation** : `com.example.interfaces.LogAspect` — annotation personnalisée pour activer le logging
- **Modèle** : `com.example.model.Song` — champs `title`, `singerName`
- **Service** : `com.example.services.VehicleServices` — `playMusic(boolean, Song)`, `moveVehicle(boolean)`, `applyBrake(boolean)`
- **Config** : `@EnableAspectJAutoProxy` activé dans `ProjectConfig`

---

### Exemples 18 à 20 — Spring Boot 3.1.2 (Application Web « Eazy School »)

Ces trois projets sont des **applications web Spring Boot** autonomes.  
**Classe entrypoint** : `com.eazybytes.eazyschool.EazyschoolApplication` (annotée `@SpringBootApplication`).  
**Lancement** : `./mvnw spring-boot:run` ou `mvn spring-boot:run` dans le dossier de l'exemple.

---

#### Example 18 — Spring Boot MVC avec page HTML statique
- **Dépendances** : `spring-boot-starter-web`, `spring-boot-starter-test`
- **Contrôleur** : `com.eazybytes.eazyschool.controller.HomeController`
  - Endpoint : `GET /home` → retourne `"home.html"`
- **Ressources statiques** : `src/main/resources/static/home.html` — page « Welcome to Eazy School! »
- **Configuration** (`application.properties`) : port et context-path commentés ; `debug=true`
- **Tests** : `EazyschoolApplicationTests` — `@SpringBootTest` / `contextLoads()`
- **Port par défaut** : 8080

---

#### Example 19 — Spring Boot + Thymeleaf + Model
- **Dépendances** : `spring-boot-starter-web`, `spring-boot-starter-thymeleaf`, `spring-boot-devtools`, `spring-boot-starter-test`
- **Contrôleur** : `com.eazybytes.eazyschool.controller.HomeController`
  - Endpoints : `GET /`, `GET /home` → retourne `"home.html"` en passant l'attribut `username = "John Doe"` via `Model`
- **Template Thymeleaf** : `src/main/resources/templates/home.html` — utilise `xmlns:th="http://www.thymeleaf.org"`, affiche `th:text="${username}"`
- **Configuration** (`application.properties`) : paramètres Thymeleaf commentés
- **DevTools** : rechargement automatique activé
- **Tests** : `EazyschoolApplicationTests` — `contextLoads()`
- **Port par défaut** : 8080

---

#### Example 20 — Spring Boot + Thymeleaf (sans Model)
- **Dépendances** : identiques à example_19
- **Contrôleur** : `com.eazybytes.eazyschool.controller.HomeController`
  - Endpoints : `GET /`, `GET /home` → retourne `"home.html"` **sans** `Model`
- **Template Thymeleaf** : `src/main/resources/templates/home.html`
- **Configuration** (`application.properties`) : vide
- **Tests** : `EazyschoolApplicationTests` — `contextLoads()`
- **Port par défaut** : 8080

---

## 5. Récapitulatif des couches applicatives

| Couche | Exemples concernés | Classes / Annotations |
|--------|-------------------|-----------------------|
| **Beans / Entités** | 1-17 | `Vehicle`, `Person`, `Song` — POJOs simples |
| **Configuration** | 1-16 | `ProjectConfig` (`@Configuration`, `@Bean`, `@ComponentScan`) |
| **Interfaces** | 13-17 | `Speakers`, `Tyres`, `LogAspect` |
| **Implémentations** | 13-17 | `BoseSpeakers`, `SonySpeakers`, `BridgeStoneTyres`, `MichelinTyres` |
| **Services** | 13-17 | `VehicleServices` (`@Component`) |
| **Aspects (AOP)** | 17 | `LoggerAspect`, `VehicleStartCheckAspect` (`@Aspect`) |
| **Contrôleurs MVC** | 18-20 | `HomeController` (`@Controller`, `@RequestMapping`) |
| **Ressources statiques** | 18 | `src/main/resources/static/home.html` |
| **Templates Thymeleaf** | 19-20 | `src/main/resources/templates/home.html` |
| **Tests** | 18-20 | `EazyschoolApplicationTests` (`@SpringBootTest`) |

> Les exemples 1-17 n'ont **pas de tests unitaires** ; ils sont conçus pour être exécutés manuellement via la méthode `main`.

---

## 6. Configuration

### Exemples 1-17
Pas de fichier `application.properties`. La configuration est entièrement Java :
- `@Configuration` + `@Bean` (exemples 1-4, 9-10)
- `@ComponentScan` (exemples 5-7, 11-17)
- XML `beans.xml` (exemple 8)
- `@EnableAspectJAutoProxy` (exemple 17)

### Exemples 18-20
Fichier `src/main/resources/application.properties` :
- **Example 18** : `debug=true` (port et context-path commentés)
- **Example 19** : paramètres Thymeleaf commentés (`spring.thymeleaf.cache`, `spring.thymeleaf.prefix`)
- **Example 20** : fichier vide

---

## 7. Instructions pour lancer chaque exemple

### Prérequis
- **Java 17+** (Spring Framework 6 / Spring Boot 3 requièrent Java 17)
- **Maven 3.8+** (ou utiliser le wrapper `mvnw` inclus dans les exemples 18-20)

### Exemples 1 à 17 (applications standalone)

Ces projets ne sont **pas** des applications Spring Boot. Ils se lancent comme une application Java standard :

```bash
# Se placer dans le dossier de l'exemple (ex: example1)
cd example1

# Compiler et packager
mvn package -q

# Exécuter la classe main
mvn exec:java -Dexec.mainClass="com.example.main.Example1"
```

> Remplacer `Example1` par `Example2`, …, `Example17` selon l'exemple souhaité.

Alternativement, importer dans IntelliJ IDEA ou Eclipse (Import > Maven Project) et lancer la méthode `main` directement.

### Exemples 18 à 20 (applications Spring Boot)

```bash
# Se placer dans le dossier de l'exemple (ex: example_18)
cd example_18

# Lancer avec le wrapper Maven (Linux/Mac)
./mvnw spring-boot:run

# Lancer avec le wrapper Maven (Windows)
mvnw.cmd spring-boot:run

# Ou avec Maven installé
mvn spring-boot:run
```

L'application démarre sur **http://localhost:8080**.

| Exemple | URL à tester |
|---------|-------------|
| example_18 | http://localhost:8080/home |
| example_19 | http://localhost:8080/ ou http://localhost:8080/home |
| example_20 | http://localhost:8080/ ou http://localhost:8080/home |

### Lancer les tests (exemples 18-20)

```bash
cd example_18   # ou example_19, example_20
./mvnw test
```

---

## 8. Progression pédagogique suggérée

| Ordre | Exemple | Concept clé |
|-------|---------|------------|
| 1 | example1 | Premier bean `@Bean` |
| 2 | example2 | Ambiguïté de beans |
| 3 | example3 | Nommage des beans |
| 4 | example4 | `@Primary` |
| 5 | example5 | `@Component` + `@ComponentScan` |
| 6 | example6 | Cycle de vie (`@PostConstruct`/`@PreDestroy`) |
| 7 | example7 | Enregistrement programmatique |
| 8 | example8 | Configuration XML |
| 9 | example9 | Wiring par appel de méthode |
| 10 | example_10 | Wiring par paramètre |
| 11 | example_11 | `@Autowired` |
| 12 | example_12 | `@Qualifier` |
| 13 | example_13 | Interfaces + implémentations + services |
| 14 | example_14 | Scope Singleton |
| 15 | example_15 | `@Lazy` |
| 16 | example_16 | Scope Prototype |
| 17 | example_17 | AOP (`@Aspect`, `@Around`, `@Before`, etc.) |
| 18 | example_18 | Spring Boot MVC + HTML statique |
| 19 | example_19 | Spring Boot + Thymeleaf + Model |
| 20 | example_20 | Spring Boot + Thymeleaf (sans Model) |