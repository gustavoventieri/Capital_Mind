package org.capitalmind.client.brapi.response;

import java.util.List;

public record BrapiResponse(
   List<Double> regularMarketPrice
) {
} 