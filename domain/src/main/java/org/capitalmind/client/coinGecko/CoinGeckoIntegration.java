package org.capitalmind.client.coinGecko;

import org.capitalmind.client.coinGecko.response.CoinGeckoResponse;

public interface CoinGeckoIntegration {
    CoinGeckoResponse getPrice(String id, String currency);
} 
