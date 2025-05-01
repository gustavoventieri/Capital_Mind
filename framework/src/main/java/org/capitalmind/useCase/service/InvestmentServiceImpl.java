package org.capitalmind.useCase.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.capitalmind.adapter.mapper.InvestmentMapper;
import org.capitalmind.driver.repository.InvestmentRepositoryImpl;
import org.capitalmind.dto.request.InvestmentRequest;
import org.capitalmind.dto.response.InvestmentResponse;
import org.capitalmind.entity.Investment;
import org.capitalmind.entity.User;
import org.capitalmind.exception.NotFound;
import org.capitalmind.service.InvestmentService;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class InvestmentServiceImpl implements InvestmentService{

    private final InvestmentRepositoryImpl investmentRepositoryImpl;
    private final UserServiceImpl userServiceImpl;
    private final InvestmentMapper investmentMapper;

    /**
    * Cria um novo investimento associado a um usuário.
    */
    @Override
    public void create(InvestmentRequest investmentRequest) {
       User user = this.userServiceImpl.validateAndGetUser(investmentRequest.userId());
       
       this.investmentRepositoryImpl.save(
            new Investment(
                null, 
                investmentRequest.name(),
                investmentRequest.description(),
                investmentRequest.price(),
                user, 
                Instant.now(),
                Instant.now()
            )
       );
    }

    /**
    * Atualiza um investimento existente.
    */
    @Override
    public void update(Long investmentId, InvestmentRequest investmentRequest) {
        Investment investment = this.investmentRepositoryImpl.findById(investmentId)
            .orElseThrow(() -> new NotFound("Investment Not Found"));

        investment.setName(investmentRequest.name());
        investment.setDescription(investmentRequest.description());
        investment.setPrice(investmentRequest.price());
        investment.setUpdateAt(Instant.now());

        this.investmentRepositoryImpl.update(investment);
    }

    /**
    * Obtém todos os investimentos de um usuário.
    */
    @Override
    public List<InvestmentResponse> getAll(String userId) {
        User user = this.userServiceImpl.validateAndGetUser(userId);

        List<Investment> investments = this.investmentRepositoryImpl.findAllByUserData(user);

        return investments.stream()
            .map(investment -> investmentMapper.toInvestmentResponse(investment))
            .collect(Collectors.toList());
    }

    /**
    * Obtém um investimento pelo ID.
    */
    @Override
    public InvestmentResponse getById(Long investmentId) {
        Investment investment = this.investmentRepositoryImpl.findById(investmentId)
            .orElseThrow(() -> new NotFound("Investment Not Found"));

        return investmentMapper.toInvestmentResponse(investment);
    }

    /**
    * Exclui um investimento pelo ID.
    */
    @Override
    public void deleteById(Long investmentId) {
           // Verifica se a despesa existe
        if (this.investmentRepositoryImpl.findById(investmentId).isEmpty()) {
            throw new NotFound("Expense Not Found");
        }

        // Exclui a despesa do banco de dados
        this.investmentRepositoryImpl.delete(investmentId);
    }
    
}
