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

    private final CryptoCurrencyRepository cryptoCurrencyRepository; // Repositório de criptomoedas
    private final UserService userService; // Serviço para validar e obter usuários
    private final CryptoCurrencyMapper cryptoCurrencyMapper; // Mapeador entre entidade e DTO
    private final CoindGeckoApi coinGeckoApi; // Cliente para consultar o preço de criptomoedas

    /**
     * Cria uma nova criptomoeda associada a um usuário.
     */
    @Override
    public void create(CryptoCurrencyRequestDto cryptoCurrencyRequestDto) {
        // Valida e obtém o usuário
        User user =  this.userService.validateAndGetUser(cryptoCurrencyRequestDto.userId());

        // Salva a criptomoeda no banco de dados
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

    /**
     * Atualiza uma criptomoeda existente.
     */
    @Override
    public void update(Long cryptoCurrencyId, CryptoCurrencyRequestDto cryptoCurrencyRequestDto) {
        // Busca a criptomoeda no banco de dados
        CryptoCurrency cryptoCurrency = this.cryptoCurrencyRepository
            .findById(cryptoCurrencyId)
            .orElseThrow(() -> new NotFoundException("Crypto Currency Not Found"));

        // Atualiza os campos da criptomoeda
        cryptoCurrency.setName(cryptoCurrencyRequestDto.name());
        cryptoCurrency.setDescription(cryptoCurrencyRequestDto.description());
        cryptoCurrency.setQuantity(cryptoCurrencyRequestDto.quantity());
        
        // Salva a criptomoeda atualizada
        this.cryptoCurrencyRepository.save(cryptoCurrency);
    }

    /**
     * Obtém todas as criptomoedas associadas a um usuário.
     */
    @Override
    public List<CryptoCurrencyResponseDto> getAll(String userId) {
        // Valida e obtém o usuário
        User user = this.userService.validateAndGetUser(userId);

        // Recupera todas as criptomoedas associadas ao usuário
        List<CryptoCurrency> cryptoCurrencies = this.cryptoCurrencyRepository.findAllByUserData(user);

        // Mapeia as criptomoedas para DTOs e calcula os preços
        return cryptoCurrencies.stream()
            .map(cryptoCurrency -> cryptoCurrencyMapper.toDto(
                cryptoCurrency, 
                this.getPrice(cryptoCurrency.getName(), "brl", cryptoCurrency.getQuantity())))
            .collect(Collectors.toList());
    }

    /**
     * Obtém uma criptomoeda por ID.
     */
    @Override
    public CryptoCurrencyResponseDto getById(Long cryptoCurrencyId) {
        // Busca a criptomoeda pelo ID
        CryptoCurrency cryptoCurrency = this.cryptoCurrencyRepository
            .findById(cryptoCurrencyId)
            .orElseThrow(() -> new NotFoundException("Crypto Currency Not Found"));

        // Mapeia a criptomoeda para DTO e calcula o preço
        return cryptoCurrencyMapper.toDto(cryptoCurrency, this.getPrice(cryptoCurrency.getName(), "brl", cryptoCurrency.getQuantity()));
    }

    /**
     * Exclui uma criptomoeda por ID.
     */
    @Override
    public void deleteById(Long cryptoCurrencyId) {
        // Verifica se a criptomoeda existe
        if (!this.cryptoCurrencyRepository.existsById(cryptoCurrencyId)) {
            throw new NotFoundException("Crypto Currency Not Found");
        }

        // Exclui a criptomoeda do banco de dados
        this.cryptoCurrencyRepository.deleteById(cryptoCurrencyId);
    }

    /**
     * Obtém o preço de uma criptomoeda com base no seu nome, moeda e quantidade.
     */
    @Override
    public Double getPrice(String ids, String currency, Double quantity) {
        // Obtém o preço total
        return this.getTotal(ids, currency, quantity);
    }

    /**
     * Método auxiliar que consulta o preço da criptomoeda na API externa e calcula o valor total.
     */
    @SuppressWarnings("unchecked")
    private Double getTotal(String ids, String currency, Double quantity) {
        // Obtém a resposta da API CoinGecko com o preço da criptomoeda
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
                throw new ExternalApiException("Invalid price type");
            }
        }
    
        // Lança exceção se os dados da API forem inválidos
        throw new ExternalApiException("Invalid Data From Api");
    }
    
}
