package com.eazybytes.example21.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * ENTITÉ JPA : Produit
 *
 * Une entité représente une table dans la base de données.
 * Chaque instance de cette classe correspond à une ligne dans la table "produit".
 *
 * Annotations principales :
 *   @Entity          : indique à JPA/Hibernate que cette classe est une entité
 *                      persistante (une table sera créée automatiquement)
 *   @Id              : marque le champ comme clé primaire de la table
 *   @GeneratedValue  : la valeur de l'id est générée automatiquement
 *                      (IDENTITY = auto-incrémenté par la base de données)
 *
 * Colonnes créées dans la table "produit" :
 *   id    BIGINT  (clé primaire, auto-incrémentée)
 *   nom   VARCHAR
 *   prix  DOUBLE
 */
@Entity
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private double prix;

    // --- Constructeurs ---

    /** Constructeur sans argument requis par JPA */
    public Produit() {
    }

    /** Constructeur pratique pour créer un Produit avec nom et prix */
    public Produit(String nom, double prix) {
        this.nom = nom;
        this.prix = prix;
    }

    // --- Getters & Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Produit{id=" + id + ", nom='" + nom + "', prix=" + prix + "}";
    }

}
