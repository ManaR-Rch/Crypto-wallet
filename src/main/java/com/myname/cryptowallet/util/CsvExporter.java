package com.myname.cryptowallet.util;

import com.myname.cryptowallet.model.Transaction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utilitaire pour exporter des transactions en CSV.
 */
public class CsvExporter {

    /**
     * Convertit une liste de transactions en texte CSV.
     * Colonnes: id,sourceAddress,destinationAddress,amount,priority,status,createdAt
     * @param transactions liste de transactions
     * @return CSV sous forme de chaîne
     */
    public String toCsv(List<Transaction> transactions) {
        String header = "id,sourceAddress,destinationAddress,amount,priority,status,createdAt";
        if (transactions == null || transactions.isEmpty()) {
            return header;
        }
        String body = transactions.stream()
                .map(t -> String.join(
                        ",",
                        safe(t.getId()),
                        safe(t.getSourceAddress()),
                        safe(t.getDestinationAddress()),
                        safe(t.getAmount()),
                        safe(t.getPriority()),
                        safe(t.getStatus()),
                        safe(t.getCreatedAt())
                ))
                .collect(Collectors.joining("\n"));
        return header + "\n" + body;
    }

    /**
     * Écrit un CSV dans un fichier.
     * @param transactions liste de transactions
     * @param filePath chemin du fichier de sortie
     */
    public void writeToFile(List<Transaction> transactions, String filePath) {
        String csv = toCsv(transactions);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(csv);
        } catch (IOException e) {
            AppLogger.error("Erreur d'écriture CSV: " + e.getMessage());
        }
    }

    // Convertit un objet en texte CSV sans virgule/retour ligne problématiques (simplifié)
    private String safe(Object o) {
        if (o == null) return "";
        String s = String.valueOf(o);
        s = s.replace('\n', ' ').replace('\r', ' ');
        if (s.contains(",")) {
            return '"' + s.replace("\"", "'") + '"';
        }
        return s;
    }
}
