package gustavo.ventieri.capitalmind.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import gustavo.ventieri.capitalmind.repository.InvestmentRepository;
import org.springframework.stereotype.Service;

import gustavo.ventieri.capitalmind.dto.investment.InvestmentRequestDto;
import gustavo.ventieri.capitalmind.entity.Investment;
import gustavo.ventieri.capitalmind.repository.UserRepository;
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
