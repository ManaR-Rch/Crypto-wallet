package com.myname.cryptowallet.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Représente un portefeuille crypto simple.
 */
public class Wallet {

    /** Identifiant unique du wallet. */
    private UUID id;
    /** Adresse publique du wallet. */
    private String address;
    /** Solde courant du wallet. */
    private BigDecimal balance;
    /** Type de crypto de ce wallet (BTC/ETH). */
    private TypeCrypto cryptoType;
    /** Historique des transactions associées. */
    private List<Transaction> transactions;

    /**
     * Constructeur simple.
     * Initialise un wallet avec un solde à 0 et une liste vide de transactions.
     * @param address adresse publique du wallet
     * @param cryptoType type de crypto (BITCOIN/ETHEREUM)
     */
    public Wallet(String address, TypeCrypto cryptoType) {
        this.id = UUID.randomUUID();
        this.address = address;
        this.cryptoType = cryptoType;
        this.balance = BigDecimal.ZERO;
        this.transactions = new ArrayList<>();
    }

    /**
     * Ajoute une transaction au wallet et met à jour le solde de façon naïve.
     * Si l'adresse du wallet est destinataire, le solde augmente.
     * Si l'adresse du wallet est source, le solde diminue.
     * @param tx transaction à ajouter
     */
    public void addTransaction(Transaction tx) {
        if (tx == null) {
            return;
        }
        this.transactions.add(tx);

        if (this.address != null && this.address.equals(tx.getDestinationAddress())) {
            // Entrée de fonds
            if (tx.getAmount() != null) {
                this.balance = this.balance.add(tx.getAmount());
            }
        } else if (this.address != null && this.address.equals(tx.getSourceAddress())) {
            // Sortie de fonds
            if (tx.getAmount() != null) {
                this.balance = this.balance.subtract(tx.getAmount());
            }
        }
    }

    /**
     * Affiche le solde du wallet dans la console.
     */
    public void displayBalance() {
        System.out.println("Wallet " + address + " (" + cryptoType + ") - Solde: " + balance);
    }

    // Getters simples

    public UUID getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public TypeCrypto getCryptoType() {
        return cryptoType;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}


