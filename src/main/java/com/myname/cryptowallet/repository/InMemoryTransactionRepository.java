package com.myname.cryptowallet.repository;

import com.myname.cryptowallet.model.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Implémentation en mémoire d'un repository de transactions.
 * Simule PostgreSQL via une HashMap.
 */
public class InMemoryTransactionRepository implements TransactionRepository {

    private final Map<UUID, Transaction> store = new HashMap<>();

    @Override
    public Transaction save(Transaction tx) {
        if (tx == null || tx.getId() == null) {
            return tx;
        }
        store.put(tx.getId(), tx);
        return tx;
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(UUID id) {
        store.remove(id);
    }
}



