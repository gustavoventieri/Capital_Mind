package gustavo.ventieri.capitalmind.application.core.resources.investment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gustavo.ventieri.capitalmind.application.core.resources.investment.dto.InvestmentRequestDto;
import gustavo.ventieri.capitalmind.application.core.resources.investment.dto.InvestmentResponseDto;
import gustavo.ventieri.capitalmind.domain.investment.Investment;
import gustavo.ventieri.capitalmind.domain.investment.InvestmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/investment")
@RequiredArgsConstructor
public class InvestmentController {
    
    private final InvestmentService investmentService;


    @PostMapping("/create")
    public ResponseEntity<?> createInvestment(@RequestBody @Valid InvestmentRequestDto investmentRequestDto) {
        
        Boolean investmentSaved = this.investmentService.create(investmentRequestDto);
        if (investmentSaved) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Investment Created");
        }
    
        return ResponseEntity.badRequest().body("Investment Not Created");
    }


    @GetMapping("/all/{userId}")
    public ResponseEntity<?> getAllExpense(@PathVariable("userId") String userId) {

        Optional<List<Investment>> investmentsOptional = investmentService.getAll(userId);

        if (investmentsOptional.isPresent()) {

            List<InvestmentResponseDto> expenses = investmentsOptional.get().stream()
                .map(investment -> new InvestmentResponseDto(
                    investment.getInvestmentId(),
                    investment.getName(),
                    investment.getDescription(),
                    investment.getPrice()
                ))
                .collect(Collectors.toList());

            return ResponseEntity.ok(expenses);
        } 
        return ResponseEntity.badRequest().body("Invalid User Id");
        
    }

}
