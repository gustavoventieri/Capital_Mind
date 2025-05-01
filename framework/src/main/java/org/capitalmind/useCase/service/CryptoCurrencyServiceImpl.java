package org.capitalmind.useCase.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.capitalmind.adapter.client.coinGecko.CoinGeckoApi;
import org.capitalmind.adapter.mapper.CryptoCurrencyMapper;
import org.capitalmind.driver.repository.CryptoCurrencyRepositoryImpl;
import org.capitalmind.dto.request.CryptoCurrencyRequest;
import org.capitalmind.dto.response.CryptoCurrencyResponse;
import org.capitalmind.entity.CryptoCurrency;
import org.capitalmind.entity.User;
import org.capitalmind.exception.BadRequest;
import org.capitalmind.exception.NotFound;
import org.capitalmind.service.CryptoCurrencyService;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {


    private final CryptoCurrencyRepositoryImpl cryptoCurrencyRepositoryImpl;
    private final UserServiceImpl userServiceImpl;
    private final CryptoCurrencyMapper cryptoCurrencyMapper;
    private final CoinGeckoApi coinGeckoApi;



    @Override
    public void create(CryptoCurrencyRequest cryptoCurrencyRequest) {
        User user = this.userServiceImpl.validateAndGetUser(cryptoCurrencyRequest.userId());

        this.cryptoCurrencyRepositoryImpl.save(
            new CryptoCurrency(
                null,
                cryptoCurrencyRequest.name(),
                cryptoCurrencyRequest.description(),
                cryptoCurrencyRequest.quantity(),
                user, 
                Instant.now(),
                Instant.now()
            )
        );
    }

    @Override
    public void update(Long cryptoCurrencyId, CryptoCurrencyRequest cryptoCurrencyRequest) {
        // Busca a criptomoeda no banco de dados
        CryptoCurrency cryptoCurrency = this.cryptoCurrencyRepositoryImpl
            .findById(cryptoCurrencyId)
            .orElseThrow(() -> new NotFound("Crypto Currency Not Found"));

        // Atualiza os campos da criptomoeda
        cryptoCurrency.setName(cryptoCurrencyRequest.name());
        cryptoCurrency.setDescription(cryptoCurrencyRequest.description());
        cryptoCurrency.setQuantity(cryptoCurrencyRequest.quantity());
        cryptoCurrency.setUpdateAt(Instant.now());
        
        // Salva a criptomoeda atualizada
        this.cryptoCurrencyRepositoryImpl.save(cryptoCurrency);
    }

    @Override
    public List<CryptoCurrencyResponse> getAll(String userId) {
        User user = this.userServiceImpl.validateAndGetUser(userId);

        List<CryptoCurrency> cryptoCurrencies = this.cryptoCurrencyRepositoryImpl.findAllByUserData(user);

        return cryptoCurrencies.stream()
            .map(cryptoCurrency -> cryptoCurrencyMapper.toCryptoCurrencyResponse(
                cryptoCurrency, 
                this.getPriceInRealTime(cryptoCurrency.getName(), "brl", cryptoCurrency.getQuantity())))
            .collect(Collectors.toList());
    }

    @Override
    public CryptoCurrencyResponse getById(Long cryptoCurrencyId) {
          CryptoCurrency cryptoCurrency = this.cryptoCurrencyRepositoryImpl
            .findById(cryptoCurrencyId)
            .orElseThrow(() -> new NotFound("Crypto Currency Not Found"));

        // Mapeia a criptomoeda para DTO e calcula o preço
        return cryptoCurrencyMapper.toCryptoCurrencyResponse(cryptoCurrency, this.getPriceInRealTime(cryptoCurrency.getName(), "brl", cryptoCurrency.getQuantity()));
    }

    @Override
    public void deleteById(Long cryptoCurrencyId) {
        if (this.cryptoCurrencyRepositoryImpl.findById(cryptoCurrencyId).isEmpty()) {
            throw new NotFound("Crypto Currency Not Found");
        }

        // Exclui a criptomoeda do banco de dados
        this.cryptoCurrencyRepositoryImpl.delete(cryptoCurrencyId);
    }

    @Override
    public Double getPriceInRealTime(String ids, String currency, Double quantity) {
         Map<String, Object> response = this.coinGeckoApi.getPrice(ids, currency);
    
        // Obtém o preço da criptomoeda do mapa
        Object priceObject = response.get(ids);
    
        // Verifica se o preço é válido
        if (priceObject instanceof Map) {
            Map<String, ?> prices = (Map<String, ?>) priceObject;
            Object priceObj = prices.get(currency);
    
            if (priceObj instanceof Integer) {
                Integer price = (Integer) priceObj;
                System.out.println(price);
                // Calcula o preço total
                return price.doubleValue() * quantity;
            } else if (priceObj instanceof Double) {
                Double price = (Double) priceObj;
                System.out.println(price);
                // Calcula o preço total
                return price * quantity;
            } else {
                throw new BadRequest("Invalid price type");
            }
        }
    
        // Lança exceção se os dados da API forem inválidos
        throw new BadRequest("Invalid Data From Api");
    }



    
}
