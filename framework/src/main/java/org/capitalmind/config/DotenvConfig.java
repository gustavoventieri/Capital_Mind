package org.capitalmind.config;


import io.github.cdimascio.dotenv.Dotenv;

public class DotenvConfig {
    static {
        Dotenv dotenv = Dotenv.load();
        // Itera sobre o Set e define cada variável de ambiente no System
        dotenv.entries().forEach(entry -> 
            System.setProperty(entry.getKey(), entry.getValue())
        );
    }
}

