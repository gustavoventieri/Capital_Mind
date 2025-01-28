package gustavo.ventieri.capitalmind.application.repository;

import java.util.List;

import gustavo.ventieri.capitalmind.domain.investment.Investment;
import gustavo.ventieri.capitalmind.domain.user.User;

public interface InvestmentRepository {

    List<Investment> findAllByUserData(User user);
}