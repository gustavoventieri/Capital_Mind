package gustavo.ventieri.capitalmind.domain.investment;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import gustavo.ventieri.capitalmind.application.core.resources.investment.dto.InvestmentRequestDto;
import gustavo.ventieri.capitalmind.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvestmentService {
    private final UserRepository userRepository;
    private final InvestmentRepository investmentRepository;

     public Boolean create(InvestmentRequestDto investmentRequestDto) {
      
        var userId = investmentRequestDto.userId();
    
        if (userId == null || userId.isEmpty()) {
            return false; 
        }
    
        // Tenta buscar o usuário
        var userOptional = this.userRepository.findById(UUID.fromString(userId));
        
        return userOptional.map(user -> {
            
            
            var newInvestment = new Investment (
                null,
                investmentRequestDto.name(),
                investmentRequestDto.description(),
                investmentRequestDto.price(),
                user,
                Instant.now(),
                Instant.now()
            );
            

            @SuppressWarnings("unused")
            var expenseSaved = this.investmentRepository.save(newInvestment);
            return true;
        }).orElseGet(() -> {

           
            return false;
        });
    }

     public Optional<List<Investment>> getAll(String userId) {
        
        if (userId == null || userId.isEmpty()) {
            return Optional.empty();
        }
    
       
        var user = this.userRepository.findById(UUID.fromString(userId));
        
        
        if (user.isEmpty()) {
            return Optional.empty();
        }
    
        
        List<Investment> investments = this.investmentRepository.findAllByUserData(user.get());
        return Optional.of(investments);
    }


}
