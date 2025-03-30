package org.capitalmind.driver.repository.client;

import java.util.List;

import org.capitalmind.entity.Investment;
import org.capitalmind.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentRepositoryOrm extends JpaRepository<Investment, Long>{
    List<Investment> findAllByUserData(User user);
}
