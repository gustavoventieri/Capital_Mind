package org.capitalmind.driver.repository;

import java.util.Optional;

import org.capitalmind.driver.repository.client.InvestmentRepositoryOrm;
import org.capitalmind.entity.Investment;
import org.capitalmind.repository.InvestmentRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Repository
public class InvestmentRepositoryImpl implements InvestmentRepository{

    private final InvestmentRepositoryOrm investmentRepositoryOrm;

    @Override
    public Investment save(Investment investment) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Investment update(Investment investment) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Optional<Investment> findById(Long investmentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public void delete(String investmentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
