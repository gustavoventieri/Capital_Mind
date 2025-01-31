package gustavo.ventieri.capitalmind.infrastructure.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import gustavo.ventieri.capitalmind.application.dto.cryptoCurrency.CryptoCurrencyRequestDto;
import gustavo.ventieri.capitalmind.application.service.CryptoCurrencyServiceInterface;
import gustavo.ventieri.capitalmind.domain.cryptoCurrency.CryptoCurrency;
import gustavo.ventieri.capitalmind.domain.user.User;
import gustavo.ventieri.capitalmind.infrastructure.exception.NotFoundException;
import gustavo.ventieri.capitalmind.infrastructure.persistence.CryptoCurrencyRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CryptoCurrencyService implements CryptoCurrencyServiceInterface{

    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final UserService userService;

    @Override
    public void create(CryptoCurrencyRequestDto cryptoCurrencyRequestDto) {
        
        User user =  this.userService.validateAndGetUser(cryptoCurrencyRequestDto.userId());
       
        this.cryptoCurrencyRepository.save( 

            new CryptoCurrency(
                null,
                cryptoCurrencyRequestDto.name(),
                cryptoCurrencyRequestDto.description(),
                cryptoCurrencyRequestDto.quantity(),
                user,
                Instant.now(),
                Instant.now()
            )

        ); 
    }

    @Override
    public void update(Long cryptoCurrencyId, CryptoCurrencyRequestDto cryptoCurrencyRequestDto) {
        
        CryptoCurrency cryptoCurrency = this.cryptoCurrencyRepository.findById(cryptoCurrencyId).orElseThrow(() -> new NotFoundException("Crypto Currency Not Found"));;

        cryptoCurrency.setName(cryptoCurrencyRequestDto.name());
        cryptoCurrency.setDescription(cryptoCurrencyRequestDto.description());
        cryptoCurrency.setQuantity(cryptoCurrencyRequestDto.quantity());
        
        this.cryptoCurrencyRepository.save(cryptoCurrency);
    }

    @Override
    public List<CryptoCurrency> getAll(String userId) {

        User user = this.userService.validateAndGetUser(userId);
        
        return this.cryptoCurrencyRepository.findAllByUserData(user);
    }

    @Override
    public CryptoCurrency getById(Long cryptoCurrencyId) {

        return this.cryptoCurrencyRepository.findById(cryptoCurrencyId).orElseThrow(() -> new NotFoundException("Crypto Currency Not Found"));
    }

    @Override
    public void deleteById(Long cryptoCurrencyId) {

        if (!this.cryptoCurrencyRepository.existsById(cryptoCurrencyId)) throw new NotFoundException("Crypto Currency Not Found");

        this.cryptoCurrencyRepository.deleteById(cryptoCurrencyId);
    }

}
