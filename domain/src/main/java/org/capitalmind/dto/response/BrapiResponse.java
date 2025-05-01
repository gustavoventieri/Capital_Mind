package org.capitalmind.dto.response;

import java.util.List;

public record BrapiResponse(
   List<StockApiResponse> results
) {
} 