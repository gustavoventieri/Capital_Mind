package gustavo.ventieri.capitalmind.infrastructure.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import gustavo.ventieri.capitalmind.application.dto.investment.InvestmentRequestDto;
import gustavo.ventieri.capitalmind.application.service.InvestmentServiceInterface;
import gustavo.ventieri.capitalmind.domain.investment.Investment;
import gustavo.ventieri.capitalmind.domain.user.User;
import gustavo.ventieri.capitalmind.infrastructure.exception.InvalidDataException;
import gustavo.ventieri.capitalmind.infrastructure.exception.NotFoundException;
import gustavo.ventieri.capitalmind.infrastructure.persistence.InvestmentRepository;
import gustavo.ventieri.capitalmind.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvestmentService implements InvestmentServiceInterface {

    private final UserRepository userRepository;
    private final InvestmentRepository investmentRepository;


    @Override
    public void create(InvestmentRequestDto investmentRequestDto) {

        String userId = investmentRequestDto.userId();

        if (userId == null || userId.isEmpty()) throw new InvalidDataException("Data is Blank or Null");
        
        User user = this.userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new NotFoundException("User Not Found"));

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

    }

    @Override
    public void update(Long investmentId, InvestmentRequestDto investmentRequestDto) {

        String userId = investmentRequestDto.userId();

        if (userId == null || userId.isEmpty()) throw new InvalidDataException("Data is Blank or Null");

        this.userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new NotFoundException("User Not Found"));

        Investment investment = this.investmentRepository.findById(investmentId).orElseThrow(() -> new NotFoundException("Investment Not Found"));;

        investment.setName(investmentRequestDto.name());
        investment.setDescription(investmentRequestDto.description());
        investment.setPrice(investmentRequestDto.price());
        
        this.investmentRepository.save(investment);

    }

    @Override
    public List<Investment> getAll(String userId) {

        if (userId == null || userId.isEmpty()) throw new InvalidDataException("Data is Blank or Null");

        User user = this.userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new NotFoundException("User Not Found"));

        List<Investment> investments = this.investmentRepository.findAllByUserData(user);

        return investments;

    }



    @Override
    public Investment getById(Long investmentId) {
        if (investmentId == null) throw new InvalidDataException("Data is Blank or Null");
        
        return this.investmentRepository.findById(investmentId).orElseThrow(() -> new NotFoundException("Investment Not Found"));

    }


    @Override
    public void deleteById(Long investmentId) {

        if (!this.investmentRepository.existsById(investmentId)) throw new NotFoundException("Investment Not Found");
        
        this.investmentRepository.deleteById(investmentId);
    
    }
}
