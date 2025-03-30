package org.capitalmind.driver.config.dotenv;


import io.github.cdimascio.dotenv.Dotenv;

public class DotenvConfig {
    static {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry -> 
            System.setProperty(entry.getKey(), entry.getValue())
        );
    }
}

