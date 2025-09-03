package com.myname.cryptowallet.util;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Logger global de l'application basé sur java.util.logging.
 */
public final class AppLogger {

    private static final Logger LOGGER = Logger.getLogger("CryptoWalletLogger");
    private static volatile boolean initialized = false;

    private AppLogger() {
        // Empêche l'instanciation
    }

    /**
     * Initialisation paresseuse du logger.
     */
    private static void init() {
        if (initialized) return;
        synchronized (AppLogger.class) {
            if (initialized) return;
            LOGGER.setUseParentHandlers(false);
            ConsoleHandler handler = new ConsoleHandler();
            handler.setLevel(Level.ALL);
            handler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(handler);
            LOGGER.setLevel(Level.ALL);
            initialized = true;
        }
    }

    /** Log niveau INFO. */
    public static void info(String message) {
        init();
        LOGGER.info(message);
    }

    /** Log niveau WARN. */
    public static void warn(String message) {
        init();
        LOGGER.warning(message);
    }

    /** Log niveau ERROR. */
    public static void error(String message) {
        init();
        LOGGER.severe(message);
    }
}


