package gustavo.ventieri.capitalmind.application.repository;

import java.util.List;

import gustavo.ventieri.capitalmind.domain.investment.Investment;
import gustavo.ventieri.capitalmind.domain.user.User;

public interface InvestmentRepositoryInterface {
    List<Investment> findAllByUserData(User user);
}
