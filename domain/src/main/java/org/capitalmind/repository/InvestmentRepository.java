package org.capitalmind.repository;


import java.util.Optional;

import org.capitalmind.entity.Investment;


public interface InvestmentRepository {
    Investment save(Investment investment);

    Investment update(Investment investment);

    Optional<Investment> findById(Long investmentId);

    void delete(String investmentId);
}
