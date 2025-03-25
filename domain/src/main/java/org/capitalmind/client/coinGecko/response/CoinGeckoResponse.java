package org.capitalmind.client.coinGecko.response;

import java.util.Map;

public record CoinGeckoResponse(Map<String, Object> price) {
}
