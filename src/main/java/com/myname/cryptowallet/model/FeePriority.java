package com.myname.cryptowallet.model;

/**
 * Niveau de priorité des frais (fee) pour une transaction.
 */
public enum FeePriority {
    /** Frais minimum, confirmation plus lente. */
    ECONOMIQUE,
    /** Équilibre coût/délai. */
    STANDARD,
    /** Frais plus élevés, confirmation plus rapide. */
    RAPIDE
}



