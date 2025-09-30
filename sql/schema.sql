-- Schéma PostgreSQL pour Crypto Wallet Simulator

CREATE TABLE IF NOT EXISTS wallets (
    id UUID PRIMARY KEY,
    address TEXT NOT NULL,
    balance NUMERIC(20,8) NOT NULL,
    crypto_type TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions (
    id UUID PRIMARY KEY,
    source_address TEXT NOT NULL,
    destination_address TEXT NOT NULL,
    amount NUMERIC(20,8) NOT NULL,
    priority TEXT NOT NULL,
    status TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL
);

-- Index utiles
CREATE INDEX IF NOT EXISTS idx_transactions_created_at ON transactions(created_at);
CREATE INDEX IF NOT EXISTS idx_transactions_priority ON transactions(priority);



