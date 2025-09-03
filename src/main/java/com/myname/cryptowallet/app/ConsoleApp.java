package com.myname.cryptowallet.app;

import com.myname.cryptowallet.model.FeePriority;
import com.myname.cryptowallet.model.Transaction;
import com.myname.cryptowallet.model.TypeCrypto;
import com.myname.cryptowallet.model.Wallet;
import com.myname.cryptowallet.service.MempoolService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import com.myname.cryptowallet.util.AppLogger;

/**
 * Application console simple pour interagir avec le wallet.
 */
public class ConsoleApp {

    private final MempoolService mempoolService = new MempoolService();
    private Wallet currentWallet;

    /**
     * Initialise et lance l'application console.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // Pré-génère des transactions aléatoires
        mempoolService.generateRandomTransactions();

        while (running) {
            printMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    createWallet(scanner);
                    break;
                case "2":
                    createTransaction(scanner);
                    break;
                case "3":
                    viewMempoolPosition(scanner);
                    break;
                case "4":
                    compareFees();
                    break;
                case "5":
                    showMempool();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    AppLogger.warn("Choix invalide");
            }
        }
        AppLogger.info("Au revoir.");
    }

    private void printMenu() {
        System.out.println();
        System.out.println("=== Crypto Wallet Simulator ===");
        System.out.println("1) Créer un wallet");
        System.out.println("2) Créer une transaction");
        System.out.println("3) Voir ma position dans le mempool");
        System.out.println("4) Comparer les 3 fee levels");
        System.out.println("5) Consulter l'état du mempool");
        System.out.println("0) Quitter");
        System.out.print("> ");
    }

    private void createWallet(Scanner scanner) {
        System.out.print("Adresse du wallet: ");
        String address = scanner.nextLine();
        System.out.print("Type (1=BITCOIN, 2=ETHEREUM): ");
        String t = scanner.nextLine();
        TypeCrypto type = "2".equals(t) ? TypeCrypto.ETHEREUM : TypeCrypto.BITCOIN;
        currentWallet = new Wallet(address, type);
        AppLogger.info("Wallet créé: " + currentWallet.getAddress() + " (" + currentWallet.getCryptoType() + ")");
        currentWallet.displayBalance();
    }

    private void createTransaction(Scanner scanner) {
        if (currentWallet == null) {
            AppLogger.error("Créez d'abord un wallet (option 1).");
            return;
        }

        System.out.print("Adresse destination: ");
        String dest = scanner.nextLine();
        System.out.print("Montant: ");
        String amtStr = scanner.nextLine();
        BigDecimal amount = new BigDecimal(amtStr);
        System.out.print("Priority (1=ECONOMIQUE, 2=STANDARD, 3=RAPIDE): ");
        String p = scanner.nextLine();
        FeePriority pr = "3".equals(p) ? FeePriority.RAPIDE : ("2".equals(p) ? FeePriority.STANDARD : FeePriority.ECONOMIQUE);

        Transaction tx = new Transaction(currentWallet.getAddress(), dest, amount, pr);
        currentWallet.addTransaction(tx);
        mempoolService.addTransaction(tx);
        AppLogger.info("Transaction créée: " + tx.getId());
        currentWallet.displayBalance();
    }

    private void viewMempoolPosition(Scanner scanner) {
        System.out.print("ID de la transaction (UUID): ");
        String id = scanner.nextLine();
        try {
            System.out.println(mempoolService.getPositionAndEta(java.util.UUID.fromString(id)));
        } catch (IllegalArgumentException e) {
            AppLogger.error("UUID invalide");
        }
    }

    private void compareFees() {
        List<String> lines = mempoolService.compareFeeLevels();
        for (String line : lines) {
            System.out.println(line);
        }
    }

    private void showMempool() {
        List<Transaction> txs = mempoolService.getSortedMempool();
        if (txs.isEmpty()) {
            AppLogger.warn("Mempool vide.");
            return;
        }
        int i = 1;
        for (Transaction t : txs) {
            System.out.println(i + ") " + t.getId() + " | " + t.getPriority() + " | " + t.getAmount());
            i++;
        }
    }
}


