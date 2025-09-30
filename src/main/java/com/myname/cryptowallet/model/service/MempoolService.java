package com.myname.cryptowallet.service;

import com.myname.cryptowallet.model.FeePriority;
import com.myname.cryptowallet.model.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

/**
 * Service pour gérer un mempool simplifié.
 */
public class MempoolService {

    /** Liste interne représentant le mempool. */
    private final List<Transaction> mempool = new ArrayList<>();
    private final Random random = new Random();

    /**
     * Ajoute une transaction au mempool.
     * @param tx transaction à ajouter
     */
    public void addTransaction(Transaction tx) {
        if (tx != null) {
            this.mempool.add(tx);
            sortByFeeDesc();
        }
    }

    /** Vide le mempool. */
    public void clear() {
        this.mempool.clear();
    }

    /**
     * Génère 10 transactions aléatoires puis trie le mempool par fees (décroissant).
     */
    public void generateRandomTransactions() {
        this.mempool.clear();
        for (int i = 0; i < 10; i++) {
            Transaction tx = new Transaction(
                    randomAddress(),
                    randomAddress(),
                    randomAmount(),
                    randomPriority()
            );
            this.mempool.add(tx);
        }
        sortByFeeDesc();
    }

    /**
     * Retourne une copie triée du mempool (du plus haut fee au plus bas).
     */
    public List<Transaction> getSortedMempool() {
        List<Transaction> copy = new ArrayList<>(this.mempool);
        copy.sort(this::compareByFeeDesc);
        return copy;
    }

    /**
     * Calcule la position d'une transaction dans le mempool et le temps estimé.
     * Temps estimé = position × 10 minutes.
     * @param txId identifiant de la transaction
     * @return description lisible de la position et de l'ETA
     */
    public String getPositionAndEta(UUID txId) {
        List<Transaction> sorted = getSortedMempool();
        for (int i = 0; i < sorted.size(); i++) {
            if (sorted.get(i).getId().equals(txId)) {
                int position = i + 1; // positions humaines à partir de 1
                long minutes = position * 10L;
                return "Position: " + position + ", ETA: " + minutes + " min";
            }
        }
        return "Transaction non trouvée dans le mempool";
    }

    /**
     * Estime la position si une transaction avec cette priorité était soumise maintenant.
     * @param priority niveau de frais
     * @return position estimée (1 = en tête)
     */
    public int estimatePositionForPriority(FeePriority priority) {
        // On insère virtuellement un fee et on calcule la position qu'il aurait
        double feeValue = feeValue(priority);
        int position = 1;
        for (Transaction existing : getSortedMempool()) {
            if (feeValue <= feeValue(existing.getPriority())) {
                position++;
            }
        }
        return position;
    }

    /**
     * Donne les 3 estimations de position/ETA pour ECONOMIQUE, STANDARD, RAPIDE.
     */
    public List<String> compareFeeLevels() {
        List<String> lines = new ArrayList<>();
        for (FeePriority p : FeePriority.values()) {
            int pos = estimatePositionForPriority(p);
            long minutes = pos * 10L;
            lines.add(p + " -> position " + pos + ", ETA ~ " + minutes + " min");
        }
        return lines;
    }

    // --- Helpers internes ---

    private void sortByFeeDesc() {
        this.mempool.sort(this::compareByFeeDesc);
    }

    private int compareByFeeDesc(Transaction a, Transaction b) {
        int cmp = Double.compare(feeValue(b.getPriority()), feeValue(a.getPriority()));
        if (cmp != 0) return cmp;
        // À défaut, trier par date de création (plus récent en premier)
        return b.getCreatedAt().compareTo(a.getCreatedAt());
    }

    /**
     * Valeur numérique des frais, seulement basée sur la priorité pour simplifier.
     */
    private double feeValue(FeePriority priority) {
        if (priority == null) return 0.0;
        switch (priority) {
            case RAPIDE:
                return 3.0;
            case STANDARD:
                return 2.0;
            case ECONOMIQUE:
            default:
                return 1.0;
        }
    }

    private FeePriority randomPriority() {
        FeePriority[] values = FeePriority.values();
        return values[random.nextInt(values.length)];
    }

    private String randomAddress() {
        // Adresse fictive courte
        return "addr_" + Math.abs(random.nextInt());
    }

    private BigDecimal randomAmount() {
        // Montant entre 0.01 et 1.00
        double v = 0.01 + (1.00 - 0.01) * random.nextDouble();
        return new BigDecimal(v).setScale(8, RoundingMode.HALF_UP);
    }
}



