package gustavo.ventieri.capitalmind.domain.investment;

import java.util.List;

import gustavo.ventieri.capitalmind.domain.user.User;

public interface InvestmentRepository {

    List<Investment> findAllByUserData(User user);
}