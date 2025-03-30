package org.capitalmind.useCase.services;

import java.util.List;

import org.capitalmind.dto.request.InvestmentRequest;
import org.capitalmind.dto.response.InvestmentResponse;
import org.capitalmind.service.InvestmentService;
import org.springframework.stereotype.Service;


@Service
public class InvestmentServiceImpl implements InvestmentService{

    @Override
    public void create(InvestmentRequest investmentRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public void update(Long investmentId, InvestmentRequest investmentRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<InvestmentResponse> getAll(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public InvestmentResponse getById(Long investmentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public void deleteById(Long investmentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }
    
}
