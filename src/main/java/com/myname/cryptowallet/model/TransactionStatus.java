package com.myname.cryptowallet.model;

/**
 * Statut d'une transaction dans le système.
 */
public enum TransactionStatus {
    /** En attente dans le mempool. */
    PENDING,
    /** Confirmée sur la blockchain. */
    CONFIRMED,
    /** Rejetée (non incluse/invalidée). */
    REJECTED
}


