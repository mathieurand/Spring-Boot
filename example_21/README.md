# Exemple 21 – Mini projet CRUD Spring Boot : Gestion de Produits

> **Objectif pédagogique** : montrer, étape par étape, comment construire une API REST CRUD complète
> avec Spring Boot, Spring Data JPA et une base H2 embarquée.

---

## Table des matières

1. [Prérequis](#1-prérequis)
2. [Initialisation du projet](#2-initialisation-du-projet)
3. [Structure du projet](#3-structure-du-projet)
4. [Dépendances Maven (pom.xml)](#4-dépendances-maven-pomxml)
5. [Entité JPA – Produit](#5-entité-jpa--produit)
6. [Repository](#6-repository)
7. [Service (logique métier)](#7-service-logique-métier)
8. [Contrôleur REST](#8-contrôleur-rest)
9. [Configuration (application.properties)](#9-configuration-applicationproperties)
10. [Lancer l'application](#10-lancer-lapplication)
11. [Tester l'API CRUD](#11-tester-lapi-crud)
12. [Console H2](#12-console-h2)

---

## 1. Prérequis

| Outil         | Version minimale |
|---------------|-----------------|
| Java (JDK)    | 17              |
| Maven         | 3.6+            |
| IDE (optionnel) | IntelliJ IDEA, Eclipse, VS Code |

Vérifier les installations :

```bash
java -version
mvn -version
```

---

## 2. Initialisation du projet

### Option A – Spring Initializr (recommandé)

1. Aller sur [https://start.spring.io](https://start.spring.io)
2. Remplir le formulaire :
   - **Project** : Maven
   - **Language** : Java
   - **Spring Boot** : 3.1.2
   - **Group** : `com.eazybytes`
   - **Artifact** : `example_21`
   - **Packaging** : Jar
   - **Java** : 17
3. Ajouter les dépendances : **Spring Web**, **Spring Data JPA**, **H2 Database**
4. Cliquer sur **GENERATE**, dézipper l'archive dans le dossier de ce projet.

### Option B – Ligne de commande Maven

```bash
# Créer le dossier et générer la structure de base
mvn archetype:generate \
  -DgroupId=com.eazybytes \
  -DartifactId=example_21 \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DinteractiveMode=false
```

---

## 3. Structure du projet

```
example_21/
│
├── pom.xml                          ← Dépendances et configuration Maven
│
└── src/
    ├── main/
    │   ├── java/com/eazybytes/example21/
    │   │   ├── Example21Application.java      ← Point d'entrée (@SpringBootApplication)
    │   │   ├── entity/
    │   │   │   └── Produit.java               ← Entité JPA (table SQL)
    │   │   ├── repository/
    │   │   │   └── ProduitRepository.java     ← Accès base de données (CRUD auto)
    │   │   ├── service/
    │   │   │   └── ProduitService.java        ← Logique métier
    │   │   └── controller/
    │   │       └── ProduitController.java     ← Endpoints REST HTTP
    │   │
    │   └── resources/
    │       └── application.properties        ← Configuration (BDD, port, etc.)
    │
    └── test/
        └── java/com/eazybytes/example21/
            └── Example21ApplicationTests.java ← Test de démarrage du contexte
```

### Rôle de chaque couche

```
Requête HTTP
     │
     ▼
┌──────────────────┐
│   Controller     │  ← Reçoit les requêtes HTTP, retourne du JSON
│  @RestController │
└────────┬─────────┘
         │ appelle
         ▼
┌──────────────────┐
│    Service       │  ← Contient la logique métier
│    @Service      │
└────────┬─────────┘
         │ appelle
         ▼
┌──────────────────┐
│   Repository     │  ← Accès base de données (SQL généré automatiquement)
│  JpaRepository   │
└────────┬─────────┘
         │
         ▼
┌──────────────────┐
│  Base H2 (SQL)   │  ← Stockage des données
└──────────────────┘
```

---

## 4. Dépendances Maven (pom.xml)

```xml
<dependencies>

    <!-- API REST + Tomcat embarqué + JSON (Jackson) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- JPA / Hibernate : mapper les classes Java en tables SQL -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- H2 : base de données embarquée en mémoire (pas d'installation) -->
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- JUnit 5 + Mockito pour les tests -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>

</dependencies>
```

> **Remarque** : grâce au `spring-boot-starter-parent`, les versions de toutes ces
> dépendances sont gérées automatiquement et sont garanties compatibles entre elles.

---

## 5. Entité JPA – Produit

```java
// src/main/java/com/eazybytes/example21/entity/Produit.java

@Entity                // → Hibernate crée la table "produit" automatiquement
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // → id auto-incrémenté
    private Long id;

    private String nom;    // → colonne "nom" dans la table
    private double prix;   // → colonne "prix" dans la table

    // Constructeur vide obligatoire pour JPA
    public Produit() {}

    // + getters / setters
}
```

**Ce que fait Hibernate au démarrage** :
```sql
CREATE TABLE produit (
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    nom   VARCHAR(255),
    prix  DOUBLE PRECISION
);
```

---

## 6. Repository

```java
// src/main/java/com/eazybytes/example21/repository/ProduitRepository.java

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    // Rien à écrire ! Spring génère automatiquement :
    // findAll(), findById(), save(), deleteById(), count(), existsById()…
}
```

`JpaRepository<Produit, Long>` signifie :
- **Produit** : type de l'entité gérée
- **Long** : type de la clé primaire (id)

---

## 7. Service (logique métier)

```java
// src/main/java/com/eazybytes/example21/service/ProduitService.java

@Service
public class ProduitService {

    private final ProduitRepository produitRepository;

    // Injection de dépendance par constructeur (bonne pratique)
    public ProduitService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    public List<Produit> getTousProduits()          { return produitRepository.findAll(); }
    public Optional<Produit> getProduitParId(Long id) { return produitRepository.findById(id); }
    public Produit sauvegarderProduit(Produit p)    { return produitRepository.save(p); }
    public void supprimerProduit(Long id)           { produitRepository.deleteById(id); }
    public boolean produitExiste(Long id)           { return produitRepository.existsById(id); }
}
```

---

## 8. Contrôleur REST

```java
// src/main/java/com/eazybytes/example21/controller/ProduitController.java

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    private final ProduitService produitService;

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping              // GET  /api/produits
    public ResponseEntity<List<Produit>> getTousProduits() { ... }

    @GetMapping("/{id}")     // GET  /api/produits/{id}
    public ResponseEntity<Produit> getProduitParId(@PathVariable Long id) { ... }

    @PostMapping             // POST /api/produits
    public ResponseEntity<Produit> creerProduit(@RequestBody Produit produit) { ... }

    @PutMapping("/{id}")     // PUT  /api/produits/{id}
    public ResponseEntity<Produit> mettreAJourProduit(@PathVariable Long id,
                                                       @RequestBody Produit produit) { ... }

    @DeleteMapping("/{id}")  // DELETE /api/produits/{id}
    public ResponseEntity<Void> supprimerProduit(@PathVariable Long id) { ... }
}
```

---

## 9. Configuration (application.properties)

```properties
# Base de données H2 en mémoire
spring.datasource.url=jdbc:h2:mem:produitdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Hibernate : recrée les tables à chaque démarrage
spring.jpa.hibernate.ddl-auto=create-drop

# Affiche le SQL généré dans la console (pour apprendre/déboguer)
spring.jpa.show-sql=true

# Active la console web H2 → http://localhost:8080/h2-console
spring.h2.console.enabled=true

# Port du serveur (défaut : 8080)
server.port=8080
```

---

## 10. Lancer l'application

### Via Maven (terminal)

```bash
# Se placer dans le dossier du projet
cd example_21

# Lancer l'application
mvn spring-boot:run
```

### Via IDE (IntelliJ IDEA)

1. Ouvrir `example_21` comme projet Maven
2. Ouvrir `Example21Application.java`
3. Cliquer sur le bouton **Run ▶** (ou `Shift+F10`)

### Créer un JAR exécutable

```bash
# Compiler et packager
mvn package

# Lancer le JAR
java -jar target/example_21-0.0.1-SNAPSHOT.jar
```

**L'application est prête quand on voit dans la console** :
```
Started Example21Application in X.XXX seconds
```

---

## 11. Tester l'API CRUD

L'API est accessible à `http://localhost:8080/api/produits`.

### Avec curl (terminal)

#### Créer un produit (POST)

```bash
curl -X POST http://localhost:8080/api/produits \
     -H "Content-Type: application/json" \
     -d '{"nom":"Cahier","prix":3.5}'
```

Réponse attendue (201 Created) :
```json
{"id":1,"nom":"Cahier","prix":3.5}
```

#### Créer un second produit

```bash
curl -X POST http://localhost:8080/api/produits \
     -H "Content-Type: application/json" \
     -d '{"nom":"Stylo","prix":1.2}'
```

#### Lister tous les produits (GET)

```bash
curl http://localhost:8080/api/produits
```

Réponse attendue (200 OK) :
```json
[
  {"id":1,"nom":"Cahier","prix":3.5},
  {"id":2,"nom":"Stylo","prix":1.2}
]
```

#### Récupérer un produit par id (GET)

```bash
curl http://localhost:8080/api/produits/1
```

Réponse attendue (200 OK) :
```json
{"id":1,"nom":"Cahier","prix":3.5}
```

#### Mettre à jour un produit (PUT)

```bash
curl -X PUT http://localhost:8080/api/produits/1 \
     -H "Content-Type: application/json" \
     -d '{"nom":"Cahier A4","prix":4.0}'
```

Réponse attendue (200 OK) :
```json
{"id":1,"nom":"Cahier A4","prix":4.0}
```

#### Supprimer un produit (DELETE)

```bash
curl -X DELETE http://localhost:8080/api/produits/1
```

Réponse attendue : `204 No Content` (corps vide)

#### Vérifier que le produit est supprimé

```bash
curl http://localhost:8080/api/produits/1
```

Réponse attendue : `404 Not Found`

### Avec Postman ou Insomnia

1. Créer une nouvelle collection
2. Ajouter les requêtes ci-dessus avec la méthode HTTP et l'URL correspondantes
3. Pour POST et PUT, aller dans l'onglet **Body → raw → JSON** et coller le corps JSON

---

## 12. Console H2

Spring Boot intègre une console web pour H2, accessible pendant l'exécution.

1. Ouvrir un navigateur et aller à : `http://localhost:8080/h2-console`
2. Remplir les champs :
   - **JDBC URL** : `jdbc:h2:mem:produitdb`
   - **User Name** : `sa`
   - **Password** : *(laisser vide)*
3. Cliquer sur **Connect**
4. On peut alors exécuter des requêtes SQL directement :
   ```sql
   SELECT * FROM PRODUIT;
   ```

---

## Résumé des annotations utilisées

| Annotation              | Rôle                                                              |
|-------------------------|-------------------------------------------------------------------|
| `@SpringBootApplication`| Point d'entrée – active l'auto-configuration et le scan          |
| `@Entity`               | Déclare une classe comme table JPA                               |
| `@Id`                   | Marque la clé primaire                                           |
| `@GeneratedValue`       | Génération automatique de l'id                                   |
| `@Repository`           | Bean Spring de type accès données                                |
| `@Service`              | Bean Spring de type logique métier                               |
| `@RestController`       | Bean Spring de type contrôleur REST (retourne du JSON)           |
| `@RequestMapping`       | Préfixe de route commun pour tous les endpoints du contrôleur    |
| `@GetMapping`           | Route HTTP GET                                                    |
| `@PostMapping`          | Route HTTP POST                                                   |
| `@PutMapping`           | Route HTTP PUT                                                    |
| `@DeleteMapping`        | Route HTTP DELETE                                                 |
| `@PathVariable`         | Extrait une variable de l'URL (ex : `/produits/{id}`)            |
| `@RequestBody`          | Désérialise le corps JSON de la requête en objet Java            |
