package gustavo.ventieri.capitalmind.infrastructure.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import gustavo.ventieri.capitalmind.application.dto.cryptoCurrency.CryptoCurrencyRequestDto;
import gustavo.ventieri.capitalmind.application.dto.cryptoCurrency.CryptoCurrencyResponseDto;
import gustavo.ventieri.capitalmind.application.service.CryptoCurrencyServiceInterface;
import gustavo.ventieri.capitalmind.domain.cryptoCurrency.CryptoCurrency;
import gustavo.ventieri.capitalmind.domain.user.User;
import gustavo.ventieri.capitalmind.infrastructure.clients.coinGecko.CoindGeckoApi;
import gustavo.ventieri.capitalmind.infrastructure.exception.ExternalApiException;
import gustavo.ventieri.capitalmind.infrastructure.exception.NotFoundException;
import gustavo.ventieri.capitalmind.infrastructure.mapper.cryptoCurrency.CryptoCurrencyMapper;
import gustavo.ventieri.capitalmind.infrastructure.persistence.CryptoCurrencyRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CryptoCurrencyService implements CryptoCurrencyServiceInterface{

    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final UserService userService;
    private final CryptoCurrencyMapper cryptoCurrencyMapper;
    private final CoindGeckoApi coinGeckoApi;

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
    public List<CryptoCurrencyResponseDto> getAll(String userId) {

        User user = this.userService.validateAndGetUser(userId);
        
        List<CryptoCurrency> cryptoCurrencies = this.cryptoCurrencyRepository.findAllByUserData(user);;
        return cryptoCurrencies.stream()
        .map(cryptoCurrency -> cryptoCurrencyMapper.toDto(cryptoCurrency, this.getPrice(cryptoCurrency.getName(), "brl", cryptoCurrency.getQuantity())))
        .collect(Collectors.toList());
    }

    @Override
    public CryptoCurrencyResponseDto getById(Long cryptoCurrencyId) {

        CryptoCurrency cryptoCurrency = this.cryptoCurrencyRepository.findById(cryptoCurrencyId).orElseThrow(() -> new NotFoundException("Crypto Currency Not Found"));

        return cryptoCurrencyMapper.toDto(cryptoCurrency, this.getPrice(cryptoCurrency.getName(), "brl", cryptoCurrency.getQuantity()));
    }

    @Override
    public void deleteById(Long cryptoCurrencyId) {

        if (!this.cryptoCurrencyRepository.existsById(cryptoCurrencyId)) throw new NotFoundException("Crypto Currency Not Found");

        this.cryptoCurrencyRepository.deleteById(cryptoCurrencyId);
    }

    @Override
    public Double getPrice(String ids, String currency, Double quantity) {
        
        Double price = this.getTotal(ids, currency, quantity);

        return price;

    }

    @SuppressWarnings("unchecked")
    private Double getTotal(String ids, String currency, Double quantity) {
        
        Map<String, Object> response = this.coinGeckoApi.getPrice(ids, currency);
    
        Object priceObject = response.get(ids);
    
        if (priceObject instanceof Map) {
            
           
            Map<String, Integer> prices = (Map<String, Integer>) priceObject;
    
            Integer price = prices.get(currency);
    
            Double total = price * quantity;
    
            return total;
        } 

        throw new ExternalApiException("Invalid Data From Api");
    }

}
