package gustavo.ventieri.capitalmind.application.service;

import java.util.List;

import gustavo.ventieri.capitalmind.application.dto.investment.InvestmentRequestDto;
import gustavo.ventieri.capitalmind.domain.investment.Investment;

public interface InvestmentServiceInterface {
    void create(InvestmentRequestDto investmentRequestDto);

    void update(Long investmentId, InvestmentRequestDto investmentRequestDto);

    List<Investment> getAll(String userId);

    Investment getById(Long investmentId);

    void deleteById(Long investmentId);
}
