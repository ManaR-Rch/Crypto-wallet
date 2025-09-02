package com.myname.cryptowallet.repository;

import com.myname.cryptowallet.model.Wallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Implémentation en mémoire d'un repository de wallets.
 * Simule PostgreSQL via une HashMap.
 */
public class InMemoryWalletRepository implements WalletRepository {

    private final Map<UUID, Wallet> store = new HashMap<>();

    @Override
    public Wallet save(Wallet wallet) {
        if (wallet == null || wallet.getId() == null) {
            return wallet;
        }
        store.put(wallet.getId(), wallet);
        return wallet;
    }

    @Override
    public Optional<Wallet> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Wallet> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(UUID id) {
        store.remove(id);
    }
}



