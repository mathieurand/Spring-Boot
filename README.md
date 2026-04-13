# Spring-Boot — Analyse du dépôt

## 📋 Vue d'ensemble

Ce dépôt est un **recueil de 20 exemples pédagogiques** couvrant le framework Spring (Core et Spring Boot).
Il est structuré en modules indépendants, chacun illustrant un concept précis.

- **Auteur/groupe** : `com.eazybytes`
- **Système de build** : Maven (fichier `pom.xml` dans chaque module)
- **Langage** : Java
- **Pas de module parent** : chaque exemple est un projet Maven autonome

---

## 🗂️ Structure du dépôt

```
Spring-Boot/
├── example1/          ← Spring Core : contexte de base
├── example2/          ← Spring Core : plusieurs beans du même type
├── example3/          ← Spring Core : beans nommés
├── example4/          ← Spring Core : @Primary
├── example5/          ← Spring Core : @Component
├── example6/          ← Spring Core : cycle de vie (@PostConstruct / @PreDestroy)
├── example7/          ← Spring Core : enregistrement programmatique de beans
├── example8/          ← Spring Core : configuration XML (beans.xml)
├── example9/          ← Spring Core : câblage de beans (Person + Vehicle)
├── example_10/        ← Spring Core : @Autowired, injection de dépendances
├── example_11/        ← Spring Core : @Autowired avec ambiguïté
├── example_12/        ← Spring Core : portées (Scope) singleton / prototype
├── example_13/        ← Spring Core : @ComponentScan + interfaces (Tyres, Speakers)
├── example_14/        ← Spring Core : singleton — vérification par hashcode
├── example_15/        ← Spring Core : initialisation paresseuse (@Lazy)
├── example_16/        ← Spring Core : prototype — vérification par hashcode
├── example_17/        ← Spring Core : AOP (@Aspect, @Around, @Before)
├── example_18/        ← Spring Boot : application web + HTML statique
├── example_19/        ← Spring Boot : application web + Thymeleaf (Model)
└── example_20/        ← Spring Boot : application web + Thymeleaf + assets CSS/images
```

---

## 🔍 Détail des exemples

### Partie 1 — Spring Framework Core (examples 1 à 17)

Ces projets utilisent **Spring Context 6.0.11** (sans Spring Boot).
Ils se lancent comme de simples programmes Java via la classe `main`.
**⚠️ Aucun wrapper `mvnw` fourni** → Maven doit être installé sur la machine.

| Exemple | Concept illustré |
|---------|-----------------|
| example1 | Création du contexte Spring (`AnnotationConfigApplicationContext`), premier `@Bean` |
| example2 | Exception `NoUniqueBeanDefinitionException` quand plusieurs beans du même type existent |
| example3 | Beans nommés, récupération par `context.getBean("nomDuBean", Classe.class)` |
| example4 | Annotation `@Primary` pour désigner le bean par défaut |
| example5 | Stéréotype `@Component`, scan automatique |
| example6 | Cycle de vie : `@PostConstruct` et `@PreDestroy` (dépendance `jakarta.annotation-api`) |
| example7 | Enregistrement programmatique d'un bean via `Supplier<T>` |
| example8 | Configuration **XML** (`beans.xml`) au lieu des annotations |
| example9 | Câblage de beans : `Person` possède un `Vehicle` (wiring manuel) |
| example_10 | `@Autowired` — injection automatique par type |
| example_11 | `@Autowired` avec plusieurs implémentations disponibles |
| example_12 | Portée **singleton** vs **prototype** (`@Scope`) |
| example_13 | `@ComponentScan` + interfaces `Tyres` / `Speakers` avec plusieurs implémentations |
| example_14 | Portée **singleton** — vérification que deux appels `getBean` retournent le même objet (même hashcode) |
| example_15 | Initialisation paresseuse avec `@Lazy` |
| example_16 | Portée **prototype** — vérification que chaque appel crée un objet différent |
| example_17 | **AOP** : `@Aspect`, `@Around`, `@Before`, logging et mesure de temps d'exécution |

**Packages présents** (examples 1–17) :
- `com.example.beans` — entités métier (`Vehicle`, `Person`)
- `com.example.config` — classe de configuration Spring (`ProjectConfig`)
- `com.example.main` — point d'entrée `main()`
- `com.example.implementation` — implémentations des interfaces (ex: `BridgeStoneTyres`, `BoseSpeakers`)
- `com.example.interfaces` — interfaces (`Tyres`, `Speakers`, `LogAspect`)
- `com.example.services` — services métier (`VehicleServices`)
- `com.example.aspects` — aspects AOP (`LoggerAspect`, `VehicleStartCheckAspect`)
- `com.example.model` — modèles de données (`Song`)

---

### Partie 2 — Spring Boot (examples 18, 19, 20)

Ces projets utilisent **Spring Boot 3.1.2** et ont un **wrapper `mvnw`** inclus.
Ils représentent une application web nommée **"EazySchool"** avec un niveau de complexité croissant.

| Exemple | Description | Dépendances clés |
|---------|-------------|-----------------|
| example_18 | Application web Spring Boot avec une page HTML **statique** | `spring-boot-starter-web` |
| example_19 | Application web avec moteur de templates **Thymeleaf** + attributs `Model` | `spring-boot-starter-web`, `spring-boot-starter-thymeleaf` |
| example_20 | Comme example_19 + page d'accueil complète avec **CSS et images** (assets statiques) | `spring-boot-starter-web`, `spring-boot-starter-thymeleaf` |

**Détail example_18** :
- Contrôleur MVC : `HomeController` → route `/home` → renvoie `home.html` depuis `static/`
- Page HTML simple ("Welcome to Eazy School!")
- `application.properties` : port commenté, debug activé

**Détail example_19** :
- Contrôleur MVC : routes `""`, `"/"`, `"home"` → passe `username = "John Doe"` au modèle
- Template Thymeleaf (`th:text`) dans `templates/home.html`
- `application.properties` : cache Thymeleaf commenté

**Détail example_20** :
- Contrôleur MVC similaire à example_18 (sans passage de modèle)
- Page d'accueil complète avec CSS, images (bannières, équipe, blog, icônes)
- Assets dans `static/assets/` (CSS + 20+ images)
- Pas de base de données, pas de Spring Security dans ce dernier exemple

**Packages présents** (examples 18–20) :
- `com.eazybytes.eazyschool` — classe principale `EazyschoolApplication` (`@SpringBootApplication`)
- `com.eazybytes.eazyschool.controller` — contrôleurs MVC (`HomeController`)

**Fichiers de configuration** :
- `src/main/resources/application.properties` (options commentées, port configurable)
- `src/main/resources/templates/` — templates Thymeleaf
- `src/main/resources/static/` — assets statiques (HTML, CSS, images)

---

## ⚠️ Points manquants / problèmes identifiés

| Problème | Exemples concernés | Impact |
|----------|--------------------|--------|
| Pas de wrapper `mvnw` | example1 à example_17 | Maven doit être installé manuellement |
| README quasi vide | Tout le dépôt | Pas de documentation pour les débutants |
| Pas de base de données | example_18 à example_20 | Les exemples Spring Boot n'ont pas de couche JPA/SQL |
| Pas de tests unitaires réels | example1 à example_17 | Seul le boilerplate Spring Boot est présent dans 18–20 |
| Pas de `.gitignore` global | Tout le dépôt | Risque de committer `target/`, `.idea/`, etc. |
| `application.properties` minimal | example_18 à example_20 | Toutes les options sont commentées |

---

## 🚀 Comment lancer les projets localement

### Prérequis

- **Java 17** (requis pour tous les exemples — `maven.compiler.source=17` dans chaque `pom.xml`)
- **Maven 3.8+** (nécessaire pour TOUS les exemples 1–17 car pas de wrapper)
- Un IDE recommandé : IntelliJ IDEA ou Eclipse/STS

---

### Lancer un exemple Spring Core (example1 à example_17)

> ⚠️ Ces projets n'ont **pas** de wrapper `mvnw`. Il faut Maven installé globalement.

```bash
cd example1        # ou example2, example3, ..., example_17
mvn compile
mvn exec:java -Dexec.mainClass="com.example.main.Example1"
```

> Remplace `Example1` par le nom de la classe `main` du projet choisi (ex: `Example17`).

---

### Lancer un exemple Spring Boot (example_18, example_19, example_20)

Ces projets ont le wrapper `mvnw` inclus :

```bash
cd example_20          # ou example_18, example_19

# Sur Linux/macOS
./mvnw spring-boot:run

# Sur Windows
mvnw.cmd spring-boot:run
```

L'application démarre sur le port **8080** par défaut.
Ouvre ensuite ton navigateur sur :
- `http://localhost:8080/` (example_19 et example_20)
- `http://localhost:8080/home` (example_18)

Pour changer le port, décommente dans `application.properties` :
```properties
server.port=8081
```

---

## 📈 Prochaines étapes suggérées (pour un devoir)

Si tu dois compléter ce projet dans le cadre d'un devoir Spring Boot, voici les étapes recommandées en partant de **example_20** (le plus avancé) :

1. **Ajouter une base de données** — ajoute `spring-boot-starter-data-jpa` + H2 ou MySQL dans `pom.xml`
2. **Créer une entité** — ex: `Student`, `Course`, avec `@Entity`, `@Id`, `@GeneratedValue`
3. **Créer un Repository** — `interface StudentRepository extends JpaRepository<Student, Long>`
4. **Créer un Service** — logique CRUD dans une classe `@Service`
5. **Créer des routes REST ou MVC** — `@RestController` pour une API, ou `@Controller` + Thymeleaf pour un site
6. **Ajouter la validation** — `@NotBlank`, `@Size`, `@Valid` dans le contrôleur
7. **Configurer `application.properties`** — URL JDBC, `ddl-auto=update`
8. **Tester avec Postman ou le navigateur**
9. **Mettre à jour ce README** avec tes endpoints et instructions

---

## 📦 Résumé rapide

| Caractéristique | Valeur |
|-----------------|--------|
| Type de projet | Recueil d'exemples pédagogiques Spring |
| Système de build | Maven (`pom.xml` par module) |
| Spring Core | 6.0.11 (examples 1–17) |
| Spring Boot | 3.1.2 (examples 18–20) |
| Moteur de templates | Thymeleaf (examples 19–20) |
| Base de données | ❌ Aucune configurée |
| Spring Security | ❌ Absent |
| Tests | ⚠️ Boilerplate uniquement (examples 18–20) |
| Wrapper Maven | ✅ `mvnw` dans examples 18–20 uniquement |
| Assets statiques | ✅ CSS + images dans example_20 |
