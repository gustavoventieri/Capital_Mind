package org.capitalmind.adapter.controller;

import java.util.List;

import org.capitalmind.adapter.dto.request.InvestmentRequestImpl;
import org.capitalmind.adapter.mapper.InvestmentMapper;
import org.capitalmind.dto.request.InvestmentRequest;
import org.capitalmind.dto.response.InvestmentResponse;
import org.capitalmind.useCase.service.InvestmentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@RequestMapping("/investment")
public class InvestmentController {
    
    private final InvestmentServiceImpl investmentServiceImpl;
    private final InvestmentMapper investmentMapper;

    // Cria um novo investimento
    @PostMapping("/create")
    public ResponseEntity<String> createInvestment(@RequestBody @Valid InvestmentRequestImpl investmentRequestImpl) {
        
        InvestmentRequest investmentData = this.investmentMapper.toDomainInvestmentRequest(investmentRequestImpl);

        this.investmentServiceImpl.create(investmentData);

        return ResponseEntity.status(HttpStatus.CREATED).body("Investment Created");
    }

    // Recupera um investimento específico por ID
    @GetMapping("/{investmentId}")
    public ResponseEntity<InvestmentResponse> getInvestmentById(@PathVariable("investmentId") Long investmentId){
        
        return ResponseEntity.status(HttpStatus.OK).body(investmentServiceImpl.getById(investmentId));
    }
    
    // Lista todos os investimentos de um usuário por ID
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<InvestmentResponse>> getAllInvestment(@PathVariable("userId") String userId) {

        return ResponseEntity.status(HttpStatus.OK).body(investmentServiceImpl.getAll(userId));
    }

    // Atualiza um investimento por ID
    @PutMapping("/update/{investmentId}")
    public ResponseEntity<String> updateInvestmentById(@PathVariable("investmentId") Long investmentId, @RequestBody @Valid InvestmentRequestImpl investmentRequestImpl){

        InvestmentRequest investmentData = this.investmentMapper.toDomainInvestmentRequest(investmentRequestImpl);

        this.investmentServiceImpl.update(investmentId, investmentData);
        
        return ResponseEntity.status(HttpStatus.OK).body("Investment Updated");
    }

    // Remove um investimento por ID
    @DeleteMapping("/delete/{investmentId}")
    public ResponseEntity<String> deleteInvestmentById(@PathVariable("investmentId") Long investmentId) {
       
        this.investmentServiceImpl.deleteById(investmentId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }


}
