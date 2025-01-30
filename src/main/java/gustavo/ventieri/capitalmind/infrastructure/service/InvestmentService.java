package gustavo.ventieri.capitalmind.infrastructure.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import gustavo.ventieri.capitalmind.application.dto.investment.InvestmentRequestDto;
import gustavo.ventieri.capitalmind.application.service.InvestmentServiceInterface;
import gustavo.ventieri.capitalmind.domain.investment.Investment;
import gustavo.ventieri.capitalmind.domain.user.User;
import gustavo.ventieri.capitalmind.infrastructure.persistence.InvestmentRepository;
import gustavo.ventieri.capitalmind.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvestmentService implements InvestmentServiceInterface {

    private final UserRepository userRepository;
    private final InvestmentRepository investmentRepository;


    @Override
    public Boolean create(InvestmentRequestDto investmentRequestDto) {
        String userId = investmentRequestDto.userId();

        if (userId == null || userId.isEmpty()) {
            return false;
        }

        // Tenta buscar o usuário
        Optional<User> userOptional = this.userRepository.findById(UUID.fromString(userId));

        return userOptional.map(user -> {
            Investment newInvestment = new Investment(
                null,
                investmentRequestDto.name(),
                investmentRequestDto.description(),
                investmentRequestDto.price(),
                user,
                Instant.now(),
                Instant.now()
            );

            this.investmentRepository.save(newInvestment);

            return true;
        }).orElseGet(() -> {
            return false;
        });
    }


    @Override
    public Optional<List<Investment>> getAll(String userId) {
        if (userId == null || userId.isEmpty()) {
            return Optional.empty();
        }

        Optional<User> user = this.userRepository.findById(UUID.fromString(userId));

        if (user.isEmpty()) {
            return Optional.empty();
        }

        List<Investment> investments = this.investmentRepository.findAllByUserData(user.get());
        return Optional.of(investments);
    }


    @Override
    public Boolean update(Long investmentId, InvestmentRequestDto investmentRequestDto) {
        String userId = investmentRequestDto.userId();
        if (userId == null || userId.isEmpty()) {
            return false;
        }

        Optional<User> userOptional = this.userRepository.findById(UUID.fromString(userId));

        Optional<Investment> investmentOptional = this.investmentRepository.findById(investmentId);

        if (userOptional.isEmpty() || investmentOptional.isEmpty()) {
            return false;
        }
        
        Investment investment = investmentOptional.get();

        investment.setName(investmentRequestDto.name());
        investment.setDescription(investmentRequestDto.description());
        investment.setPrice(investmentRequestDto.price());
        
        this.investmentRepository.save(investment);

        return true;
    }


    @Override
    public Optional<Investment> getById(Long investmentId) {
        if (investmentId == null) {
            return Optional.empty();
        }

        Optional<Investment> investment = this.investmentRepository.findById(investmentId);
        
        return investment;
    }


    @Override
    public Boolean deleteById(Long investmentId) {

        if (!this.investmentRepository.existsById(investmentId)) {
            return false;
        }

        this.investmentRepository.deleteById(investmentId);
        
        return true;

    }
}
