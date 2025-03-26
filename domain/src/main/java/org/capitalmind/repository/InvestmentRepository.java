package org.capitalmind.repository;

import java.util.List;
import java.util.Optional;

import org.capitalmind.entity.Investment;
import org.capitalmind.entity.User;

public interface InvestmentRepository {
    Investment save(Investment investment);

    Investment update(Investment investment);

    Optional<Investment> findById(Long investmentId);

    List<Investment> findAllByUserData(User user);

    void delete(String investmentId);
}
