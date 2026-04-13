package com.eazybytes.example21.controller;

import com.eazybytes.example21.entity.Produit;
import com.eazybytes.example21.service.ProduitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CONTRÔLEUR REST : ProduitController
 *
 * Le Controller reçoit les requêtes HTTP et renvoie des réponses JSON.
 * Il délègue la logique métier au Service.
 *
 * @RestController : combine @Controller + @ResponseBody
 *   → chaque méthode retourne directement du JSON (via Jackson)
 *   → pas besoin de vue (Thymeleaf, JSP…)
 *
 * @RequestMapping("/api/produits") : préfixe commun à toutes les routes
 *   de ce contrôleur. Toutes les URL commenceront par /api/produits.
 *
 * Résumé des endpoints CRUD exposés :
 * ┌─────────────────────────────────┬────────┬─────────────────────────────┐
 * │ URL                             │ Méthode│ Action                      │
 * ├─────────────────────────────────┼────────┼─────────────────────────────┤
 * │ /api/produits                   │ GET    │ Lister tous les produits    │
 * │ /api/produits/{id}              │ GET    │ Récupérer un produit par id │
 * │ /api/produits                   │ POST   │ Créer un nouveau produit    │
 * │ /api/produits/{id}              │ PUT    │ Mettre à jour un produit    │
 * │ /api/produits/{id}              │ DELETE │ Supprimer un produit        │
 * └─────────────────────────────────┴────────┴─────────────────────────────┘
 */
@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    private final ProduitService produitService;

    // Injection du Service par constructeur
    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    /**
     * GET /api/produits
     * Retourne la liste complète des produits.
     * Réponse : 200 OK + tableau JSON
     *
     * Exemple :
     *   curl http://localhost:8080/api/produits
     */
    @GetMapping
    public ResponseEntity<List<Produit>> getTousProduits() {
        List<Produit> produits = produitService.getTousProduits();
        return ResponseEntity.ok(produits);
    }

    /**
     * GET /api/produits/{id}
     * Retourne un produit selon son identifiant.
     * Réponse : 200 OK + objet JSON, ou 404 Not Found si absent
     *
     * @PathVariable : extrait la valeur {id} de l'URL
     *
     * Exemple :
     *   curl http://localhost:8080/api/produits/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitParId(@PathVariable Long id) {
        return produitService.getProduitParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/produits
     * Crée un nouveau produit.
     * Réponse : 201 Created + objet JSON du produit créé
     *
     * @RequestBody : désérialise le corps JSON de la requête en objet Produit
     *
     * Exemple :
     *   curl -X POST http://localhost:8080/api/produits \
     *        -H "Content-Type: application/json" \
     *        -d '{"nom":"Cahier","prix":3.5}'
     */
    @PostMapping
    public ResponseEntity<Produit> creerProduit(@RequestBody Produit produit) {
        Produit nouveau = produitService.sauvegarderProduit(produit);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouveau);
    }

    /**
     * PUT /api/produits/{id}
     * Met à jour un produit existant.
     * Réponse : 200 OK + objet JSON mis à jour, ou 404 Not Found si absent
     *
     * Exemple :
     *   curl -X PUT http://localhost:8080/api/produits/1 \
     *        -H "Content-Type: application/json" \
     *        -d '{"nom":"Cahier A4","prix":4.0}'
     */
    @PutMapping("/{id}")
    public ResponseEntity<Produit> mettreAJourProduit(@PathVariable Long id,
                                                       @RequestBody Produit produit) {
        if (!produitService.produitExiste(id)) {
            return ResponseEntity.notFound().build();
        }
        produit.setId(id);
        Produit misAJour = produitService.sauvegarderProduit(produit);
        return ResponseEntity.ok(misAJour);
    }

    /**
     * DELETE /api/produits/{id}
     * Supprime un produit par son identifiant.
     * Réponse : 204 No Content si suppression réussie, ou 404 Not Found si absent
     *
     * Exemple :
     *   curl -X DELETE http://localhost:8080/api/produits/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerProduit(@PathVariable Long id) {
        if (!produitService.produitExiste(id)) {
            return ResponseEntity.notFound().build();
        }
        produitService.supprimerProduit(id);
        return ResponseEntity.noContent().build();
    }

}
