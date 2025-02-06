package gustavo.ventieri.capitalmind.application.controller;

import java.util.List;

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

import gustavo.ventieri.capitalmind.application.dto.investment.InvestmentRequestDto;
import gustavo.ventieri.capitalmind.application.dto.investment.InvestmentResponseDto;
import gustavo.ventieri.capitalmind.infrastructure.service.InvestmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/investment")
@RequiredArgsConstructor
public class InvestmentController {
    
    private final InvestmentService investmentService;

    // Cria um novo investimento
    @PostMapping("/create")
    public ResponseEntity<String> createInvestment(@RequestBody @Valid InvestmentRequestDto investmentRequestDto) {
        
        this.investmentService.create(investmentRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("Investment Created");
    }

    // Recupera um investimento específico por ID
    @GetMapping("/{investmentId}")
    public ResponseEntity<InvestmentResponseDto> getInvestmentById(@PathVariable("investmentId") Long investmentId){
     
        return ResponseEntity.ok(investmentService.getById(investmentId));
    }
    
    // Lista todos os investimentos de um usuário por ID
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<InvestmentResponseDto>> getAllInvestment(@PathVariable("userId") String userId) {

        return ResponseEntity.ok(investmentService.getAll(userId));
    }

    // Atualiza um investimento por ID
    @PutMapping("/update/{investmentId}")
    public ResponseEntity<String> updateInvestmentById(@PathVariable("investmentId") Long investmentId, @RequestBody @Valid InvestmentRequestDto investmentRequestDto){

        this.investmentService.update(investmentId, investmentRequestDto);
        
        return ResponseEntity.ok().body("Investment Updated");
    }

    // Remove um investimento por ID
    @DeleteMapping("/delete/{investmentId}")
    public ResponseEntity<String> deleteInvestmentById(@PathVariable("investmentId") Long investmentId) {
       
        investmentService.deleteById(investmentId);

        return ResponseEntity.ok("Investment Deleted");
    }
}
