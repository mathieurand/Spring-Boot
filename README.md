# Spring-Boot — Guide des exemples (1 à 20)

Ce dépôt contient **20 projets indépendants** répartis dans les dossiers `example1` à `example9` puis `example_10` à `example_20`. Chaque dossier est un projet Maven autonome qui illustre un concept précis du framework Spring.

---

## 📋 Vue d'ensemble rapide

| Dossier      | Framework        | Sujet principal                                       |
|--------------|------------------|-------------------------------------------------------|
| example1     | Spring Core 6    | Création de beans avec `@Bean` / `@Configuration`     |
| example2     | Spring Core 6    | Plusieurs beans du même type — `NoUniqueBeanDefinitionException` |
| example3     | Spring Core 6    | Nommage des beans avec `@Bean(name=…)`                |
| example4     | Spring Core 6    | Bean prioritaire avec `@Primary`                      |
| example5     | Spring Core 6    | Détection automatique avec `@Component` / `@ComponentScan` |
| example6     | Spring Core 6    | Cycle de vie — `@PostConstruct` / `@PreDestroy`       |
| example7     | Spring Core 6    | Enregistrement programmatique de beans (`registerBean`) |
| example8     | Spring Core 6    | Configuration XML (`beans.xml`)                       |
| example9     | Spring Core 6    | Injection de dépendances via appel de méthode `@Bean` |
| example_10   | Spring Core 6    | Injection de dépendances via paramètre de méthode `@Bean` |
| example_11   | Spring Core 6    | `@Autowired` (champ, setter, constructeur)            |
| example_12   | Spring Core 6    | `@Qualifier` pour choisir un bean parmi plusieurs     |
| example_13   | Spring Core 6    | Architecture en couches (services + interfaces)       |
| example_14   | Spring Core 6    | Portée **Singleton** (scope par défaut)               |
| example_15   | Spring Core 6    | Initialisation paresseuse avec `@Lazy`                |
| example_16   | Spring Core 6    | Portée **Prototype** (`@Scope(SCOPE_PROTOTYPE)`)      |
| example_17   | Spring Core 6 + AOP | Programmation orientée aspect (`@Aspect`, `@Around`, `@Before`, …) |
| example_18   | **Spring Boot 3.1.2** | Application web — contrôleur MVC + page HTML statique |
| example_19   | **Spring Boot 3.1.2** | Application web — Thymeleaf + passage de modèle       |
| example_20   | **Spring Boot 3.1.2** | Application web — Thymeleaf (vue sans données modèle) |

> **Exemples 1 à 17** : projets Spring Core purs (pas Spring Boot). Ils s'exécutent comme des programmes Java classiques via Maven.  
> **Exemples 18 à 20** : projets Spring Boot 3.1.2 avec serveur web embarqué (Tomcat). Ils exposent une page web accessible depuis un navigateur.

---

## 📁 Description détaillée de chaque exemple

---

### example1 — Premier bean Spring : `@Bean` et `@Configuration`

**Concept :** Créer et récupérer des beans (objets gérés par Spring) depuis le contexte applicatif.  
Un bean `Vehicle`, un `String` et un `Integer` sont déclarés dans `ProjectConfig` via `@Bean`.  
On les récupère par type avec `context.getBean(...)`.

**Fichiers clés :**
- `ProjectConfig.java` — déclaration des beans
- `Vehicle.java` — bean personnalisé
- `Example1.java` — point d'entrée (`main`)

**Comment lancer :**
```bash
cd example1
mvn compile exec:java -Dexec.mainClass="com.example.main.Example1"
```
**Dans l'IDE :** Ouvrir le dossier `example1` comme projet Maven, puis exécuter `Example1.main()`.

---

### example2 — Plusieurs beans du même type → `NoUniqueBeanDefinitionException`

**Concept :** Quand plusieurs beans du même type existent dans le contexte, Spring ne sait pas lequel choisir si on appelle `getBean(Vehicle.class)` sans préciser le nom — il lève une `NoUniqueBeanDefinitionException`.  
L'exemple montre l'erreur (commentée) et la solution : passer le nom du bean.

**Fichiers clés :** `ProjectConfig.java` (3 beans `Vehicle`), `Example2.java`

**Comment lancer :**
```bash
cd example2
mvn compile exec:java -Dexec.mainClass="com.example.main.Example2"
```

---

### example3 — Nommage des beans (`@Bean(name=…)`)

**Concept :** Attribuer un nom personnalisé à chaque bean avec `@Bean(name=…)`, `@Bean(value=…)` ou `@Bean("…")`, puis les récupérer par leur nom.

**Fichiers clés :** `ProjectConfig.java` (beans `audiVehicle`, `hondaVehicle`, `ferrariVehicle`), `Example3.java`

**Comment lancer :**
```bash
cd example3
mvn compile exec:java -Dexec.mainClass="com.example.main.Example3"
```

---

### example4 — Bean par défaut avec `@Primary`

**Concept :** Quand plusieurs beans du même type coexistent, annoter l'un d'eux avec `@Primary` pour qu'il soit retourné par défaut lorsqu'aucun nom n'est précisé.

**Fichiers clés :** `ProjectConfig.java` (`@Primary` sur `ferrariVehicle`), `Example4.java`

**Comment lancer :**
```bash
cd example4
mvn compile exec:java -Dexec.mainClass="com.example.main.Example4"
```

---

### example5 — Détection automatique de beans : `@Component` + `@ComponentScan`

**Concept :** Au lieu de déclarer chaque bean manuellement, annoter la classe avec `@Component` et configurer `@ComponentScan` pour que Spring la détecte et l'enregistre automatiquement.

**Fichiers clés :** `ProjectConfig.java` (`@ComponentScan(basePackages = "com.example.beans")`), `Vehicle.java` (`@Component`), `Example5.java`

**Comment lancer :**
```bash
cd example5
mvn compile exec:java -Dexec.mainClass="com.example.main.Example5"
```

---

### example6 — Cycle de vie des beans : `@PostConstruct` / `@PreDestroy`

**Concept :** Exécuter du code **après la création** du bean (`@PostConstruct`) et **avant sa destruction** (`@PreDestroy`). Ici, le nom du véhicule est initialisé dans `@PostConstruct` et un message est affiché lors de la fermeture du contexte (`context.close()`).

**Dépendances spécifiques :** `jakarta.annotation-api`

**Fichiers clés :** `Vehicle.java` (annotations lifecycle), `Example6.java`

**Comment lancer :**
```bash
cd example6
mvn compile exec:java -Dexec.mainClass="com.example.main.Example6"
```

---

### example7 — Enregistrement programmatique de beans

**Concept :** Enregistrer dynamiquement un bean dans le contexte à l'exécution via `context.registerBean(...)` et un `Supplier<T>`. Le bean enregistré (Volkswagen ou Audi) dépend d'un nombre aléatoire.

**Fichiers clés :** `Example7.java`

**Comment lancer :**
```bash
cd example7
mvn compile exec:java -Dexec.mainClass="com.example.main.Example7"
```

---

### example8 — Configuration XML (`beans.xml`)

**Concept :** Avant les annotations, Spring utilisait des fichiers XML pour déclarer les beans. Cet exemple charge le contexte depuis `src/main/resources/beans.xml` via `ClassPathXmlApplicationContext`.

**Fichiers clés :** `beans.xml`, `Vehicle.java`, `Example8.java`

**Comment lancer :**
```bash
cd example8
mvn compile exec:java -Dexec.mainClass="com.example.main.Example8"
```

---

### example9 — Injection de dépendances par appel de méthode `@Bean`

**Concept :** Établir une relation entre deux beans (`Person` dépend de `Vehicle`) en appelant directement la méthode `vehicle()` dans la méthode `person()`. Spring garantit qu'un seul bean `Vehicle` sera créé.

**Fichiers clés :** `ProjectConfig.java`, `Person.java`, `Vehicle.java`, `Example9.java`

**Comment lancer :**
```bash
cd example9
mvn compile exec:java -Dexec.mainClass="com.example.main.Example9"
```

---

### example_10 — Injection de dépendances par paramètre de méthode `@Bean`

**Concept :** Alternative à example9 : passer le bean `Vehicle` en **paramètre** de la méthode `person(Vehicle vehicle)`. Spring injecte automatiquement le bean existant.

**Fichiers clés :** `ProjectConfig.java`, `Person.java`, `Vehicle.java`, `Example10.java`

**Comment lancer :**
```bash
cd example_10
mvn compile exec:java -Dexec.mainClass="com.example.main.Example10"
```

---

### example_11 — `@Autowired` : injection automatique

**Concept :** Utiliser `@Autowired` sur un **champ**, un **setter** ou un **constructeur** pour que Spring injecte automatiquement le bean correspondant. L'exemple illustre les trois modes (les deux premiers sont commentés).

**Fichiers clés :** `Person.java` (`@Autowired` sur setter), `Vehicle.java` (`@Component`), `Example11.java`

**Comment lancer :**
```bash
cd example_11
mvn compile exec:java -Dexec.mainClass="com.example.main.Example11"
```

---

### example_12 — `@Qualifier` : choisir un bean parmi plusieurs

**Concept :** Quand plusieurs beans du même type existent, `@Qualifier("nomDuBean")` permet de préciser lequel injecter, en contournant `@Primary`.

**Fichiers clés :** `ProjectConfig.java` (3 beans `Vehicle` dont un `@Primary`), `Person.java` (`@Qualifier("vehicle2")`), `Example12.java`

**Comment lancer :**
```bash
cd example_12
mvn compile exec:java -Dexec.mainClass="com.example.main.Example12"
```

---

### example_13 — Architecture en couches : services et interfaces

**Concept :** Introduction d'une couche service (`VehicleServices`) qui dépend d'interfaces (`Speakers`, `Tyres`) avec plusieurs implémentations (`BoseSpeakers`, `SonySpeakers`, `BridgeStoneTyres`, `MichelinTyres`). Spring injecte les implémentations via `@Autowired`.

**Fichiers clés :** `VehicleServices.java`, `Speakers.java`, `Tyres.java`, `BoseSpeakers.java`, `SonySpeakers.java`, `BridgeStoneTyres.java`, `MichelinTyres.java`

**Comment lancer :**
```bash
cd example_13
mvn compile exec:java -Dexec.mainClass="com.example.main.Example13"
```

---

### example_14 — Portée Singleton (comportement par défaut)

**Concept :** Par défaut, chaque bean Spring est un **singleton** : un seul objet est créé et réutilisé. L'exemple le démontre en comparant les hashcodes de deux références vers le même bean `VehicleServices`.

**Fichiers clés :** `VehicleServices.java` (portée par défaut), `Example14.java`

**Comment lancer :**
```bash
cd example_14
mvn compile exec:java -Dexec.mainClass="com.example.main.Example14"
```

---

### example_15 — Initialisation paresseuse avec `@Lazy`

**Concept :** Annoter un bean avec `@Lazy` retarde sa création jusqu'au premier accès (au lieu du démarrage du contexte). L'exemple affiche des messages « Avant » et « Après » la récupération du bean pour illustrer le moment d'initialisation.

**Fichiers clés :** `Person.java` (`@Lazy`), `Example15.java`

**Comment lancer :**
```bash
cd example_15
mvn compile exec:java -Dexec.mainClass="com.example.main.Example15"
```

---

### example_16 — Portée Prototype (`@Scope(SCOPE_PROTOTYPE)`)

**Concept :** Avec la portée **prototype**, Spring crée un **nouvel objet** à chaque appel de `getBean()`. L'exemple compare les hashcodes de deux appels pour montrer qu'ils sont différents (contrairement au singleton).

**Fichiers clés :** `VehicleServices.java` (`@Scope(BeanDefinition.SCOPE_PROTOTYPE)`), `Example16.java`

**Comment lancer :**
```bash
cd example_16
mvn compile exec:java -Dexec.mainClass="com.example.main.Example16"
```

---

### example_17 — Programmation Orientée Aspect (AOP)

**Concept :** Utilisation de `spring-aspects` pour intercepter des méthodes sans modifier leur code. Deux aspects sont définis :
- **`VehicleStartCheckAspect`** (`@Order(1)`) — vérifie si le véhicule est démarré avant chaque appel de service (`@Before`).
- **`LoggerAspect`** (`@Order(2)`) — mesure le temps d'exécution (`@Around`), journalise les retours (`@AfterReturning`) et les exceptions (`@AfterThrowing`). Supporte aussi une annotation personnalisée `@LogAspect`.

**Fichiers clés :** `LoggerAspect.java`, `VehicleStartCheckAspect.java`, `VehicleServices.java`, `LogAspect.java` (annotation), `Example17.java`

**Comment lancer :**
```bash
cd example_17
mvn compile exec:java -Dexec.mainClass="com.example.main.Example17"
```

---

### example_18 — Spring Boot : application web avec page HTML statique

**Concept :** Premier projet **Spring Boot** (3.1.2). Inclut `spring-boot-starter-web` (Tomcat embarqué). Le contrôleur `HomeController` répond à `/home` et retourne une page HTML statique servie depuis `src/main/resources/static/home.html` (page d'accueil de l'école « Eazy School »).

**Fichiers clés :** `EazyschoolApplication.java`, `HomeController.java`, `static/home.html`, `application.properties`

**Comment lancer :**
```bash
cd example_18
./mvnw spring-boot:run
# Puis ouvrir : http://localhost:8080/home
```
**Sous Windows :**
```cmd
cd example_18
mvnw.cmd spring-boot:run
```
**Dans l'IDE (IntelliJ / Eclipse) :** Ouvrir `example_18` comme projet Maven, exécuter `EazyschoolApplication.main()`.

---

### example_19 — Spring Boot : Thymeleaf + données du modèle

**Concept :** Ajoute `spring-boot-starter-thymeleaf` et `spring-boot-devtools`. Le contrôleur passe un attribut `username` au modèle (`model.addAttribute("username", "John Doe")`), et le template Thymeleaf dans `src/main/resources/templates/home.html` l'affiche dynamiquement.

**Fichiers clés :** `HomeController.java`, `templates/home.html`, `application.properties`

**Comment lancer :**
```bash
cd example_19
./mvnw spring-boot:run
# Puis ouvrir : http://localhost:8080/
```

---

### example_20 — Spring Boot : Thymeleaf (vue sans données modèle)

**Concept :** Similaire à example_19 mais le contrôleur ne passe **aucune donnée** au modèle. La vue Thymeleaf est rendue directement sans variable dynamique. Illustre la différence entre une vue statique (Thymeleaf mais sans modèle) et une vue avec données (example_19).

**Fichiers clés :** `HomeController.java`, `templates/home.html`, `application.properties`

**Comment lancer :**
```bash
cd example_20
./mvnw spring-boot:run
# Puis ouvrir : http://localhost:8080/
```

---

## 🚀 Comment ouvrir et exécuter les projets

### Via l'IDE (IntelliJ IDEA / Eclipse / VS Code)

Chaque dossier est un **projet Maven indépendant**. Pour l'ouvrir :

1. **IntelliJ IDEA** : `File → Open` → sélectionner le dossier du projet (ex. `example1`). IntelliJ détecte automatiquement le `pom.xml`.
2. **Eclipse** : `File → Import → Maven → Existing Maven Projects` → sélectionner le dossier.
3. **VS Code** : Ouvrir le dossier directement (`File → Open Folder`), l'extension Java détecte le `pom.xml`.

Pour les **exemples 1 à 17**, exécuter la classe `main` correspondante (`Example1`, `Example2`, …, `Example17`).  
Pour les **exemples 18 à 20**, exécuter la classe `EazyschoolApplication` (annotée `@SpringBootApplication`).

### Via la ligne de commande (Maven)

**Exemples 1 à 17** (Spring Core — application console) :
```bash
cd example1   # ou example2, …, example_17
mvn compile exec:java -Dexec.mainClass="com.example.main.Example1"
# Remplacer Example1 par Example2, Example3, …, Example17 selon l'exemple
```

**Exemples 18 à 20** (Spring Boot — serveur web) :
```bash
cd example_18   # ou example_19, example_20
./mvnw spring-boot:run          # Linux / macOS
mvnw.cmd spring-boot:run        # Windows
```
Puis ouvrir dans un navigateur :
- example_18 : <http://localhost:8080/home>
- example_19 : <http://localhost:8080/>
- example_20 : <http://localhost:8080/>

### Prérequis

| Outil  | Version minimale |
|--------|-----------------|
| Java   | 17              |
| Maven  | 3.6+ (ou utiliser le wrapper `mvnw` inclus dans les exemples 18–20) |

---

## 📚 Progression pédagogique

```
Beans de base          → example1 à example8
Injection + câblage    → example9, example_10, example_11, example_12
Services & interfaces  → example_13
Scopes & cycle de vie  → example_14, example_15, example_16, example_6
AOP                    → example_17
Spring Boot web        → example_18, example_19, example_20
```
