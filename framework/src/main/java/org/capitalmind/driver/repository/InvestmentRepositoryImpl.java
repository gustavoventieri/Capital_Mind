package org.capitalmind.driver.repository;

import java.util.List;
import java.util.Optional;

import org.capitalmind.driver.repository.client.InvestmentRepositoryOrm;
import org.capitalmind.entity.Investment;
import org.capitalmind.entity.User;
import org.capitalmind.exception.InternalServerError;
import org.capitalmind.exception.NotFound;
import org.capitalmind.repository.InvestmentRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Repository
public class InvestmentRepositoryImpl implements InvestmentRepository{

    private final InvestmentRepositoryOrm investmentRepositoryOrm;

    @Override
    public Investment save(Investment investment) {
       try {
            return this.investmentRepositoryOrm.save(investment);
       } catch (Exception exc) {
            throw new InternalError(exc);
       }
    }

    @Override
    public Investment update(Investment investment) {
        try {
            return this.investmentRepositoryOrm.save(investment);
       } catch (Exception exc) {
            throw new InternalError(exc);
       }
    }

    @Override
    public Optional<Investment> findById(Long investmentId) {
      try {
            return this.investmentRepositoryOrm.findById(investmentId);
       } catch (NotFound exc) {
            throw new NotFound(exc);
       } catch (Exception exc){
            throw new InternalServerError(exc);
       }
    }

    @Override
    public void delete(Long investmentId) {
       try {
            investmentRepositoryOrm.deleteById(investmentId);
       } catch (NotFound exc) {
           throw new NotFound(exc);
       } catch (Exception exc){
            throw new InternalServerError(exc);
       }
    }

    @Override
    public List<Investment> findAllByUserData(User user) {
       try {
            return this.investmentRepositoryOrm.findAllByUserData(user); 
       } catch (NotFound exc) {
            throw new NotFound(exc);
       } catch (Exception exc){
            throw new InternalServerError(exc);
       }
    }
    
}
