package gustavo.ventieri.capitalmind.application.service.investment;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import gustavo.ventieri.capitalmind.application.dto.investment.InvestmentRequestDto;
import gustavo.ventieri.capitalmind.domain.investment.Investment;
import gustavo.ventieri.capitalmind.infrastructure.persistence.JpaInvestmentRepository;
import gustavo.ventieri.capitalmind.infrastructure.persistence.JpaUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvestmentServiceImpl implements InvestmentService {

    private final JpaUserRepository userRepository;
    private final JpaInvestmentRepository investmentRepository;

    @Override
    public Boolean create(InvestmentRequestDto investmentRequestDto) {
        var userId = investmentRequestDto.userId();

        if (userId == null || userId.isEmpty()) {
            return false;
        }

        // Tenta buscar o usuário
        var userOptional = this.userRepository.findById(UUID.fromString(userId));

        return userOptional.map(user -> {
            var newInvestment = new Investment(
                null,
                investmentRequestDto.name(),
                investmentRequestDto.description(),
                investmentRequestDto.price(),
                user,
                Instant.now(),
                Instant.now()
            );

            @SuppressWarnings("unused")
            var investmentSaved = this.investmentRepository.save(newInvestment);
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

        var user = this.userRepository.findById(UUID.fromString(userId));

        if (user.isEmpty()) {
            return Optional.empty();
        }

        List<Investment> investments = this.investmentRepository.findAllByUserData(user.get());
        return Optional.of(investments);
    }
}
