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
}
