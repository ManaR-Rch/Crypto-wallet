package com.myname.cryptowallet.repository;

import com.myname.cryptowallet.model.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface d'accès aux données des wallets.
 */
public interface WalletRepository {

    /** Sauvegarde ou met à jour un wallet. */
    Wallet save(Wallet wallet);

    /** Recherche par identifiant. */
    Optional<Wallet> findById(UUID id);

    /** Retourne tous les wallets. */
    List<Wallet> findAll();

    /** Supprime par identifiant. */
    void deleteById(UUID id);
}


