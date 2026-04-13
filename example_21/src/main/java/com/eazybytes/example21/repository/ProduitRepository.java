package com.eazybytes.example21.repository;

import com.eazybytes.example21.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * REPOSITORY : ProduitRepository
 *
 * Un repository Spring Data JPA est une interface qui hérite de JpaRepository.
 * Spring génère automatiquement l'implémentation au démarrage : pas besoin
 * d'écrire le code SQL manuellement pour les opérations CRUD de base.
 *
 * JpaRepository<Produit, Long> fournit directement :
 *   - findAll()          → SELECT * FROM produit
 *   - findById(id)       → SELECT * FROM produit WHERE id = ?
 *   - save(produit)      → INSERT ou UPDATE selon si l'id existe
 *   - deleteById(id)     → DELETE FROM produit WHERE id = ?
 *   - count()            → SELECT COUNT(*) FROM produit
 *   - existsById(id)     → SELECT COUNT(*) > 0 FROM produit WHERE id = ?
 *
 * On peut aussi ajouter des méthodes de recherche personnalisées en suivant
 * la convention de nommage de Spring Data, par exemple :
 *   List<Produit> findByNom(String nom);
 *   List<Produit> findByPrixLessThan(double prix);
 */
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

}
