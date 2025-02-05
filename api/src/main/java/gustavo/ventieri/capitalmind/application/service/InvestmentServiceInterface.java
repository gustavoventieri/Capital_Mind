package gustavo.ventieri.capitalmind.application.service;

import java.util.List;

import gustavo.ventieri.capitalmind.application.dto.investment.InvestmentRequestDto;
import gustavo.ventieri.capitalmind.application.dto.investment.InvestmentResponseDto;

public interface InvestmentServiceInterface {
    
    void create(InvestmentRequestDto investmentRequestDto);

    void update(Long investmentId, InvestmentRequestDto investmentRequestDto);

    List<InvestmentResponseDto> getAll(String userId);

    InvestmentResponseDto getById(Long investmentId);

    void deleteById(Long investmentId);

}
