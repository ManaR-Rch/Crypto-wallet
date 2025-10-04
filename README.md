# 🪙 Crypto Wallet Simulator

> A pedagogical Java 8 console application simulating a cryptocurrency wallet with Bitcoin and Ethereum support, featuring a mempool simulation and flexible data persistence.

[![Java](https://img.shields.io/badge/Java-8-orange.svg)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Compatible-blue.svg)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Build-Maven-red.svg)](https://maven.apache.org/)

---

## ✨ Features

- **Multi-Crypto Wallet Management**: Create and manage Bitcoin (BTC) and Ethereum (ETH) wallets
- **Smart Fee Calculation**: Three-tier fee priority system (ECONOMIQUE, STANDARD, RAPIDE)
- **Mempool Simulation**:
    - Auto-generation of 10 random transactions at startup
    - Priority-based sorting algorithm
    - Real-time position tracking with ETA calculation (position × 10 minutes)
- **Dual Persistence Layer**: Switch between in-memory (HashMap/ArrayList) and JDBC PostgreSQL repositories
- **CSV Export**: Transaction data export utility for external analysis
- **Comprehensive Logging**: Global logging system via `java.util.logging`
- **Unit Testing**: JUnit test suite for core functionality

---

## 📦 Architecture

```
com.myname.cryptowallet
├── app/                    # Application entry points
│   ├── Main.java
│   └── ConsoleApp.java
├── model/                  # Domain entities
│   ├── Transaction.java
│   ├── Wallet.java
│   └── enums/
│       ├── TypeCrypto.java
│       ├── FeePriority.java
│       └── TransactionStatus.java
├── service/                # Business logic
│   ├── MempoolService.java
│   └── FeeCalculator.java
├── repository/             # Data access layer
│   ├── interfaces/
│   └── implementations/
│       ├── InMemory/
│       └── JDBC/
└── util/                   # Utilities
    ├── AppLogger.java
    └── CSVExporter.java
```

---

## 🚀 Quick Start

### Prerequisites

- **JDK 1.8** (Java 8)
- **Maven** (recommended for build management)
- **PostgreSQL** (optional, for JDBC persistence)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd crypto-wallet-simulator
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   java -cp target/crypto-wallet-simulator.jar com.myname.cryptowallet.app.Main
   ```

---

## 🎮 Usage

Upon launch, you'll be greeted with an interactive console menu:

```
=== Crypto Wallet Simulator ===
1. Create a new wallet
2. Create a transaction
3. Check mempool position
4. Compare fee priorities
5. View mempool status
6. Exit
```

**Pro tip**: The mempool is automatically populated with 10 random transactions on startup for immediate testing!

---

## 🗄️ Database Setup (PostgreSQL)

### Configuration

Update your database credentials in `DatabaseConnection.java`:

```java
URL:      jdbc:postgresql://localhost:5432/cryptowallet
Username: postgres
Password: postgres
```

### Schema Initialization

Execute the provided SQL schema:

```bash
psql -U postgres -d cryptowallet -f sql/schema.sql
```

### JDBC Dependencies

Add PostgreSQL driver to your `pom.xml`:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.3</version>
</dependency>
```

---

## 🧪 Testing

Run the JUnit test suite:

```bash
mvn test
```

**Test Coverage**:
- `FeeCalculatorTest`: Validates fee calculation logic
- `MempoolServiceTest`: Verifies mempool sorting by fee priority

---

## 📊 CSV Export

Export transaction history for analysis:

```java
CSVExporter.exportToFile(transactions, "output/transactions.csv");
```

Output format:
```csv
ID,Type,Amount,Wallet,Priority,Status,Timestamp
```

---

## 🎯 Roadmap & Limitations

### Current Limitations
- ⚠️ Simplified address/amount validation
- ⚠️ Basic fee calculation (priority-based only)
- ⚠️ No real blockchain integration
- ⚠️ No authentication/security layer

### Future Enhancements
- [ ] Real blockchain API integration
- [ ] Advanced cryptographic validation
- [ ] Multi-user authentication system
- [ ] Dynamic fee estimation algorithms
- [ ] Transaction signing and verification
- [ ] REST API layer

---

## 📄 License

Free to use for educational purposes. This project is designed as a learning resource for understanding cryptocurrency wallet mechanics and Java application architecture.

---

## 🤝 Contributing

This is a pedagogical project. Feel free to fork, experiment, and learn! Contributions that enhance the educational value are welcome.

---

## 📚 Learning Resources

- [Bitcoin Developer Guide](https://developer.bitcoin.org/devguide/)
- [Ethereum Development Documentation](https://ethereum.org/en/developers/docs/)
- [Understanding Mempools](https://en.bitcoin.it/wiki/Transaction_Pool)

---

**Made with ☕ and Java 8**