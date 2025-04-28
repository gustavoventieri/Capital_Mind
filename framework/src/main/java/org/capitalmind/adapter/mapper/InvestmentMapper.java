package org.capitalmind.adapter.mapper;

import org.capitalmind.adapter.dto.request.InvestmentRequestImpl;
import org.capitalmind.dto.request.InvestmentRequest;
import org.capitalmind.dto.response.InvestmentResponse;
import org.capitalmind.entity.Investment;
import org.springframework.stereotype.Component;

@Component
public class InvestmentMapper {
     public InvestmentRequest toDomainInvestmentRequest(InvestmentRequestImpl investmentRequestImpl){
        return new InvestmentRequest(
            investmentRequestImpl.name(),
            investmentRequestImpl.description(), 
            investmentRequestImpl.price(),
            investmentRequestImpl.userId()
        );
    }

    public InvestmentResponse toInvestmentResponse(Investment investment){
        return new InvestmentResponse(
            investment.getInvestmentId(), 
            investment.getName(), 
            investment.getDescription(), 
            investment.getPrice()
        );
    }
}
