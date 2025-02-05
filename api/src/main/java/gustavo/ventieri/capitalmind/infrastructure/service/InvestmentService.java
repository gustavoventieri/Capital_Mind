package gustavo.ventieri.capitalmind.infrastructure.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import gustavo.ventieri.capitalmind.application.dto.investment.InvestmentRequestDto;
import gustavo.ventieri.capitalmind.application.dto.investment.InvestmentResponseDto;
import gustavo.ventieri.capitalmind.application.service.InvestmentServiceInterface;
import gustavo.ventieri.capitalmind.domain.investment.Investment;
import gustavo.ventieri.capitalmind.domain.user.User;
import gustavo.ventieri.capitalmind.infrastructure.exception.NotFoundException;
import gustavo.ventieri.capitalmind.infrastructure.mapper.investment.InvestmentMapper;
import gustavo.ventieri.capitalmind.infrastructure.persistence.InvestmentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvestmentService implements InvestmentServiceInterface {

    private final InvestmentRepository investmentRepository;
    private final UserService userService;
    private final InvestmentMapper investmentMapper;



    @Override
    public void create(InvestmentRequestDto investmentRequestDto) {

        User user =  this.userService.validateAndGetUser(investmentRequestDto.userId());

        this.investmentRepository.save(
        new Investment(
            null,
            investmentRequestDto.name(),
            investmentRequestDto.description(),
            investmentRequestDto.price(),
            user,
            Instant.now(),
            Instant.now()
        )
        );

    }

    @Override
    public void update(Long investmentId, InvestmentRequestDto investmentRequestDto) {

        Investment investment = this.investmentRepository.findById(investmentId).orElseThrow(() -> new NotFoundException("Investment Not Found"));;

        investment.setName(investmentRequestDto.name());
        investment.setDescription(investmentRequestDto.description());
        investment.setPrice(investmentRequestDto.price());
        
        this.investmentRepository.save(investment);

    }

    @Override
    public List<InvestmentResponseDto> getAll(String userId) {

        User user =  this.userService.validateAndGetUser(userId);

        List<Investment> investments = this.investmentRepository.findAllByUserData(user);

        return investments.stream()
            .map(investment -> investmentMapper.toDto(investment)            )
            .collect(Collectors.toList());
    }



    @Override
    public InvestmentResponseDto getById(Long investmentId) {
        
        Investment investment = this.investmentRepository.findById(investmentId).orElseThrow(() -> new NotFoundException("Investment Not Found"));

        
        return investmentMapper.toDto(investment);
    }


    @Override
    public void deleteById(Long investmentId) {

        if (!this.investmentRepository.existsById(investmentId)) throw new NotFoundException("Investment Not Found");
        
        this.investmentRepository.deleteById(investmentId);
    
    }
}
