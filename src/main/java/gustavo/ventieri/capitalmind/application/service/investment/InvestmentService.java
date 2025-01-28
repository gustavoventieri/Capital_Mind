package gustavo.ventieri.capitalmind.application.service.investment;

import java.util.List;
import java.util.Optional;

import gustavo.ventieri.capitalmind.application.dto.investment.InvestmentRequestDto;
import gustavo.ventieri.capitalmind.domain.investment.Investment;

public interface InvestmentService {

    Boolean create(InvestmentRequestDto investmentRequestDto);

    Optional<List<Investment>> getAll(String userId);
}
