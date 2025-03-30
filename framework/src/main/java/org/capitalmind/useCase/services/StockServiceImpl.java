package org.capitalmind.useCase.services;

import java.util.List;

import org.capitalmind.dto.request.StockRequest;
import org.capitalmind.dto.response.StockResponse;
import org.capitalmind.service.StockService;
import org.springframework.stereotype.Service;


@Service
public class StockServiceImpl implements StockService {

    @Override
    public void create(StockRequest stockRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public void update(Long stockId, StockRequest stockRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<StockResponse> getAll(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public StockResponse getById(Long stockId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public void deleteById(Long stockId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public Double getPrice(Integer quantity, String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPrice'");
    }
    
}
