package com.eazybytes.example21.service;

import com.eazybytes.example21.entity.Produit;
import com.eazybytes.example21.repository.ProduitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * SERVICE : ProduitService
 *
 * La couche service contient la logique métier de l'application.
 * Elle fait le lien entre le Controller (couche présentation/HTTP)
 * et le Repository (couche accès aux données/SQL).
 *
 * Bonne pratique : ne jamais appeler directement le Repository depuis
 * le Controller. Passer systématiquement par le Service pour :
 *   - centraliser les règles métier (validation, calcul, etc.)
 *   - faciliter les tests unitaires (on peut mocker le service)
 *   - rendre le code plus maintenable
 *
 * @Service : déclare ce composant comme bean Spring de type "service".
 *            Spring l'instancie et l'injecte là où c'est nécessaire.
 *
 * Injection de dépendances via le constructeur (recommandé) :
 *   Spring injecte automatiquement le ProduitRepository
 *   au moment de la création du ProduitService.
 */
@Service
public class ProduitService {

    private final ProduitRepository produitRepository;

    // Injection par constructeur (pas besoin de @Autowired avec un seul constructeur)
    public ProduitService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    /**
     * Récupère tous les produits.
     * @return liste de tous les produits en base
     */
    public List<Produit> getTousProduits() {
        return produitRepository.findAll();
    }

    /**
     * Récupère un produit par son identifiant.
     * @param id identifiant du produit
     * @return Optional contenant le produit s'il existe, vide sinon
     */
    public Optional<Produit> getProduitParId(Long id) {
        return produitRepository.findById(id);
    }

    /**
     * Crée ou met à jour un produit.
     * Si le produit possède un id existant → mise à jour (UPDATE).
     * Si l'id est null ou inexistant         → création (INSERT).
     * @param produit le produit à sauvegarder
     * @return le produit sauvegardé (avec l'id généré si création)
     */
    public Produit sauvegarderProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    /**
     * Supprime un produit par son identifiant.
     * @param id identifiant du produit à supprimer
     */
    public void supprimerProduit(Long id) {
        produitRepository.deleteById(id);
    }

    /**
     * Vérifie si un produit existe pour l'id donné.
     * @param id identifiant à vérifier
     * @return true si le produit existe, false sinon
     */
    public boolean produitExiste(Long id) {
        return produitRepository.existsById(id);
    }

}
