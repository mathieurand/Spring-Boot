# Spring-Boot — Inventaire du dépôt (branche `main`)

> **Résumé** : Ce dépôt contient **20 modules indépendants** (dossiers `example1` à `example_20`) qui illustrent, étape par étape, les concepts fondamentaux de Spring Framework puis de Spring Boot. Chaque module est un projet Maven autonome.

---

## 1. Structure au premier niveau

| Dossier | Type |
|---------|------|
| `example1` … `example9` | Projets Spring Framework pur (sans Spring Boot) |
| `example_10` … `example_17` | Projets Spring Framework pur (sans Spring Boot) |
| `example_18` … `example_20` | Projets **Spring Boot 3.1.2** (application web) |
| `README.md` | Ce fichier |

> **Total : 20 dossiers + 1 fichier README.**

---

## 2. Système de build

**Maven** exclusivement — chaque module possède son propre `pom.xml`.  
Il n'y a pas de `pom.xml` racine (pas de projet multi-module Maven au sens strict) ; chaque exemple se compile indépendamment.

---

## 3. Version Java & Spring

| Modules | Java | Framework |
|---------|------|-----------|
| `example1` → `example_17` | **Java 17** | **Spring Framework 6.0.11** (`spring-context`, `spring-aspects`) |
| `example_18` → `example_20` | **Java 17** | **Spring Boot 3.1.2** (parent `spring-boot-starter-parent`) |

> ⚠️ Les exemples 1 à 17 **n'utilisent pas Spring Boot** ; ils utilisent le conteneur Spring classique (`AnnotationConfigApplicationContext` ou `ClassPathXmlApplicationContext`).

---

## 4. Description détaillée de chaque module

### Groupe A — Spring Framework de base (exemples 1–12)

#### `example1` — Premier contexte Spring & définition de beans
- **Concept** : Création de beans via `@Configuration` + `@Bean`.
- **Packages** : `com.example.beans`, `com.example.config`, `com.example.main`
- **Classes clés** : `Vehicle`, `ProjectConfig`, `Example1`
- **Démo** : Comparaison bean hors-contexte vs bean Spring ; récupération par type (`String`, `Integer`, `Vehicle`).

#### `example2` — Beans multiples de même type
- **Concept** : Définition de plusieurs beans du même type ; récupération par nom.
- **Packages** : `com.example.beans`, `com.example.config`, `com.example.main`

#### `example3` — Injection de dépendances par méthode `@Bean`
- **Concept** : Un bean `@Bean` appelle directement la méthode d'un autre bean pour injecter la dépendance.
- **Packages** : `com.example.beans`, `com.example.config`, `com.example.main`

#### `example4` — `@Primary` pour résoudre les ambiguïtés
- **Concept** : Quand plusieurs beans du même type existent, `@Primary` désigne celui injecté par défaut.
- **Packages** : `com.example.beans`, `com.example.config`, `com.example.main`

#### `example5` — Stéréotypes : `@Component` + `@ComponentScan`
- **Concept** : Déclaration de beans via l'annotation `@Component` ; `@ComponentScan` active la détection automatique.
- **Packages** : `com.example.beans`, `com.example.config`, `com.example.main`

#### `example6` — Cycle de vie du bean : `@PostConstruct` / `@PreDestroy`
- **Concept** : Initialisation et destruction d'un bean via les annotations Jakarta EE du cycle de vie.
- **Packages** : `com.example.beans`, `com.example.config`, `com.example.main`

#### `example7` — Portée des beans : `@Scope` (SINGLETON / PROTOTYPE)
- **Concept** : Différence entre portée `SCOPE_SINGLETON` (une seule instance) et `SCOPE_PROTOTYPE` (nouvelle instance à chaque demande).
- **Packages** : `com.example.beans`, `com.example.config`, `com.example.main`

#### `example8` — Configuration XML (`beans.xml`)
- **Concept** : Définition des beans en XML (`<bean>`) chargé via `ClassPathXmlApplicationContext`.
- **Fichier ressource** : `src/main/resources/beans.xml`
- **Packages** : `com.example.beans`, `com.example.main`

#### `example9` — Injection par constructeur (beans imbriqués)
- **Concept** : Injection d'une dépendance `Vehicle` dans `Person` via la configuration `@Bean`.
- **Packages** : `com.example.beans`, `com.example.config`, `com.example.main`
- **Classes clés** : `Person`, `Vehicle`, `ProjectConfig`

#### `example_10` — `@Autowired` (champ / constructeur)
- **Concept** : Injection automatique avec `@Autowired` sur un champ ou constructeur.
- **Packages** : `com.example.beans`, `com.example.config`, `com.example.main`

#### `example_11` — `@Autowired` constructeur (injection obligatoire)
- **Concept** : Injection obligatoire via constructeur annoté `@Autowired`.
- **Packages** : `com.example.beans`, `com.example.config`, `com.example.main`

#### `example_12` — `@Qualifier` pour désambiguïser l'injection
- **Concept** : Quand plusieurs beans du même type existent, `@Qualifier("nomDuBean")` cible précisément celui à injecter.
- **Packages** : `com.example.beans`, `com.example.config`, `com.example.main`

---

### Groupe B — Injection avancée & interfaces (exemples 13–17)

#### `example_13` — Interfaces & implémentations multiples
- **Concept** : Injection de dépendances via interfaces (`Speakers`, `Tyres`) avec plusieurs implémentations.
- **Packages** : `com.example.beans`, `com.example.config`, `com.example.interfaces`, `com.example.implementation`, `com.example.services`, `com.example.main`
- **Interfaces** : `Speakers`, `Tyres`
- **Implémentations** : `BoseSpeakers`, `SonySpeakers`, `BridgeStoneTyres`, `MichelinTyres`
- **Service** : `VehicleServices` (orchestre haut-parleurs + pneus)

#### `example_14` — `@Lazy` (chargement paresseux)
- **Concept** : Un bean annoté `@Lazy` n'est instancié que lors de sa première utilisation.
- **Packages** : identiques à `example_13`

#### `example_15` — `@Value` (injection de valeurs littérales)
- **Concept** : Injection de valeurs scalaires (chaînes, entiers) directement dans les beans via `@Value`.
- **Packages** : identiques à `example_13`

#### `example_16` — `@Scope PROTOTYPE` sur un service
- **Concept** : Démonstration de la portée PROTOTYPE sur `VehicleServices` ; chaque injection crée une nouvelle instance.
- **Packages** : identiques à `example_13`

#### `example_17` — AOP (Programmation Orientée Aspect)
- **Concept** : Aspects transversaux avec Spring AOP.
- **Packages** : `com.example.aspects`, `com.example.config`, `com.example.interfaces`, `com.example.implementation`, `com.example.services`, `com.example.model`, `com.example.main`
- **Classes clés** :
  - `LoggerAspect` : `@Around`, `@AfterReturning`, `@AfterThrowing` — log des appels, mesure du temps d'exécution
  - `VehicleStartCheckAspect` : `@Before` / `@After` — vérification avant démarrage ; `@Order(1)` (priorité)
  - `LoggerAspect` a `@Order(2)`
  - Interface marqueur `LogAspect` (annotation personnalisée)
  - `Song` : bean modèle (`title`, `singerName`)

---

### Groupe C — Spring Boot MVC + Thymeleaf (exemples 18–20)

> Ces trois projets constituent une **application web scolaire** nommée **"Eazy School"** (`com.eazybytes.eazyschool`), développée de façon incrémentale.

#### `example_18` — Spring Boot Web minimal
- **Dépendances** : `spring-boot-starter-web`, `spring-boot-starter-test`
- **Controller** : `HomeController` — route `GET /home` → retourne `"home.html"`
- **Ressources statiques** : `src/main/resources/static/home.html` (page HTML simple)
- **Pas de Thymeleaf** — la vue est un fichier HTML statique servi directement.
- **`application.properties`** : `debug=true` (port et context-path commentés → port 8080 par défaut)
- **Classe principale** : `EazyschoolApplication` (`@SpringBootApplication`)
- **Tests** : `EazyschoolApplicationTests` (chargement du contexte)

#### `example_19` — Ajout de Thymeleaf + injection dans la vue
- **Dépendances** : `spring-boot-starter-web`, `spring-boot-starter-thymeleaf`, `spring-boot-devtools`
- **Controller** : `HomeController` — routes `GET /`, `GET /home`, `GET ""` → injecte `model.addAttribute("username", "John Doe")` → retourne `"home.html"`
- **Template Thymeleaf** : `src/main/resources/templates/home.html`
  - Utilise l'expression `th:text="'Hey, ' + ${username} + ' !!! Welcome to Eazy School.'"` 
- **`application.properties`** : commentaires sur `spring.thymeleaf.cache` et le préfixe templates

#### `example_20` — Application complète avec CSS & JS
- **Dépendances** : identiques à `example_19`
- **Controller** : `HomeController` — routes `GET /`, `GET /home`, `GET ""` → `home.html` (sans attribut de modèle)
- **Template Thymeleaf** : `src/main/resources/templates/home.html` (mise en page complète, HTML W3layouts)
- **Ressources statiques** (`src/main/resources/static/assets/`) :
  - CSS : `style-starter.css`
  - JS : `bootstrap.min.js`, `jquery-3.3.1.min.js`, `counter.js`, `theme-change.js`
- **`application.properties`** : vide (configuration par défaut)

---

## 5. Packages Java récapitulatifs

| Package | Rôle | Présent dans |
|---------|------|-------------|
| `com.example.beans` | Entités métier (`Vehicle`, `Person`) | ex1–ex12 |
| `com.example.config` | Configuration Spring (`@Configuration`) | ex1–ex16 |
| `com.example.main` | Point d'entrée `main()` | ex1–ex17 |
| `com.example.interfaces` | Interfaces métier (`Speakers`, `Tyres`, `LogAspect`) | ex13–ex17 |
| `com.example.implementation` | Implémentations concrètes | ex13–ex17 |
| `com.example.services` | Services applicatifs (`VehicleServices`) | ex13–ex17 |
| `com.example.aspects` | Aspects AOP (`LoggerAspect`, `VehicleStartCheckAspect`) | ex17 |
| `com.example.model` | Modèles supplémentaires (`Song`) | ex17 |
| `com.eazybytes.eazyschool.controller` | Contrôleurs MVC (`HomeController`) | ex18–ex20 |

---

## 6. Contrôleurs REST

> ⚠️ **Aucun `@RestController`** dans le dépôt.  
> Les exemples 18 à 20 utilisent `@Controller` (Spring MVC, retour de vues), **pas une API REST JSON**.

| Module | Contrôleur | Route(s) | Vue retournée |
|--------|-----------|----------|---------------|
| `example_18` | `HomeController` | `GET /home` | `home.html` (statique) |
| `example_19` | `HomeController` | `GET /`, `/home`, `""` | `home.html` (Thymeleaf + modèle) |
| `example_20` | `HomeController` | `GET /`, `/home`, `""` | `home.html` (Thymeleaf) |

---

## 7. Templates & ressources statiques

| Fichier | Type | Module |
|---------|------|--------|
| `static/home.html` | HTML statique | `example_18` |
| `templates/home.html` | Template Thymeleaf | `example_19`, `example_20` |
| `static/assets/css/style-starter.css` | Feuille de style CSS | `example_20` |
| `static/assets/js/bootstrap.min.js` | Bootstrap JS | `example_20` |
| `static/assets/js/jquery-3.3.1.min.js` | jQuery | `example_20` |
| `static/assets/js/counter.js` | Script compteur | `example_20` |
| `static/assets/js/theme-change.js` | Script changement de thème | `example_20` |

---

## 8. Configuration base de données

> **Aucune base de données** n'est configurée dans ce dépôt.  
> Il n'y a pas de `spring-boot-starter-data-jpa`, pas de driver H2/MySQL/PostgreSQL, pas de `spring.datasource.*` dans les `application.properties`.

---

## 9. Comment lancer chaque projet

### Prérequis
- **JDK 17** (ou supérieur)
- **Maven 3.6+** installé (ou utiliser le `mvnw` wrapper présent dans les exemples 18-20)

### Exemples 1 à 17 (Spring Framework, application console)

```bash
cd example1   # ou example2, …, example_17
mvn compile exec:java -Dexec.mainClass="com.example.main.Example1"
# Remplacer Example1 par Example2, Example3, … Example17 selon le module
```

> Ces exemples s'exécutent en ligne de commande et affichent les résultats dans la console. Ils ne démarrent pas de serveur web.

### Exemples 18 à 20 (Spring Boot, application web)

```bash
cd example_18   # ou example_19, example_20
mvn spring-boot:run
# L'application démarre sur http://localhost:8080
```

Avec le wrapper Maven (si disponible) :
```bash
./mvnw spring-boot:run
```

Ou en construisant un JAR exécutable :
```bash
mvn clean package
java -jar target/example_18-0.0.1-SNAPSHOT.jar
```

**URL d'accès** :
- `example_18` : http://localhost:8080/home
- `example_19` : http://localhost:8080/ ou http://localhost:8080/home
- `example_20` : http://localhost:8080/ ou http://localhost:8080/home

---

## 10. Prochaines étapes conseillées

Si tu dois **compléter ou étendre ce projet** dans le cadre d'un devoir, voici les pistes les plus courantes :

1. **Ajouter une API REST** : Crée un `@RestController` avec des endpoints CRUD (`GET`, `POST`, `PUT`, `DELETE`) dans `example_20` ou un nouveau module.

2. **Ajouter JPA + Base de données** : Ajoute `spring-boot-starter-data-jpa` + `h2` dans le `pom.xml`, crée une entité `@Entity` et un `JpaRepository`.

3. **Ajouter Spring Security** : Ajoute `spring-boot-starter-security` pour protéger les routes avec authentification.

4. **Ajouter la validation** : Utilise `spring-boot-starter-validation` avec `@NotBlank`, `@Size`, `@Valid` sur les DTOs.

5. **Écrire des tests** : Les exemples 18-20 ont déjà une classe `EazyschoolApplicationTests` — ajoute des tests `@WebMvcTest` ou `@SpringBootTest`.

6. **Documenter l'API** : Intègre `springdoc-openapi-starter-webmvc-ui` pour une interface Swagger/OpenAPI automatique.

---

*Inventaire généré le 13 avril 2026 — dépôt public [`mathieurand/Spring-Boot`](https://github.com/mathieurand/Spring-Boot), branche `main`.*