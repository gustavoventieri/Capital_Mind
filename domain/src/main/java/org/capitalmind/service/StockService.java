package org.capitalmind.service;

import java.util.List;

import org.capitalmind.dto.request.StockRequest;
import org.capitalmind.dto.response.StockResponse;

public interface StockService{
    void create(StockRequest stockRequest);

    void update(Long stockId, StockRequest stockRequest);

    List<StockResponse> getAll(String userId);

    StockResponse getById(Long stockId);

    void deleteById(Long stockId);

    Double getPriceInRealTime(Integer quantity, String name);
}