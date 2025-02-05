package gustavo.ventieri.capitalmind.infrastructure.mapper.investment;

import org.springframework.stereotype.Component;

import gustavo.ventieri.capitalmind.application.dto.investment.InvestmentResponseDto;
import gustavo.ventieri.capitalmind.domain.investment.Investment;

@Component
public class InvestmentMapper {
   

     public InvestmentResponseDto toDto(Investment investment){
        return  new InvestmentResponseDto(
        investment.getInvestmentId(),
        investment.getName(),
        investment.getDescription(),
        investment.getPrice());

    }
}
