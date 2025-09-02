package com.myname.cryptowallet.repository;

import com.myname.cryptowallet.model.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface d'accès aux données des transactions.
 */
public interface TransactionRepository {

    /** Sauvegarde ou met à jour une transaction. */
    Transaction save(Transaction tx);

    /** Recherche par identifiant. */
    Optional<Transaction> findById(UUID id);

    /** Liste toutes les transactions. */
    List<Transaction> findAll();

    /** Supprime par identifiant. */
    void deleteById(UUID id);
}


