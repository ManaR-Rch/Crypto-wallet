package com.myname.cryptowallet.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Représente une transaction crypto simple.
 * Contient les informations nécessaires pour simuler un envoi de fonds.
 */
public class Transaction {

    /** Identifiant unique de la transaction. */
    private UUID id;
    /** Adresse source (expéditeur). */
    private String sourceAddress;
    /** Adresse destination (destinataire). */
    private String destinationAddress;
    /** Montant de la transaction. */
    private BigDecimal amount;
    /** Priorité des frais à appliquer. */
    private FeePriority priority;
    /** Statut courant de la transaction. */
    private TransactionStatus status;
    /** Date/heure de création. */
    private LocalDateTime createdAt;

    /**
     * Constructeur simple.
     * Initialise: id aléatoire, statut à PENDING et date de création à maintenant.
     * @param sourceAddress adresse expéditrice
     * @param destinationAddress adresse destinataire
     * @param amount montant à envoyer
     * @param priority priorité des frais
     */
    public Transaction(String sourceAddress, String destinationAddress, BigDecimal amount, FeePriority priority) {
        this.id = UUID.randomUUID();
        this.sourceAddress = sourceAddress;
        this.destinationAddress = destinationAddress;
        this.amount = amount;
        this.priority = priority;
        this.status = TransactionStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    // Getters et setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public FeePriority getPriority() {
        return priority;
    }

    public void setPriority(FeePriority priority) {
        this.priority = priority;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", sourceAddress='" + sourceAddress + '\'' +
                ", destinationAddress='" + destinationAddress + '\'' +
                ", amount=" + amount +
                ", priority=" + priority +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}


