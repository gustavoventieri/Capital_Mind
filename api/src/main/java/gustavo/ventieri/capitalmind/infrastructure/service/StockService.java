package gustavo.ventieri.capitalmind.infrastructure.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import gustavo.ventieri.capitalmind.application.dto.stock.StockRequestDto;
import gustavo.ventieri.capitalmind.application.dto.stock.StockResponseDto;
import gustavo.ventieri.capitalmind.application.service.StockServiceInterface;
import gustavo.ventieri.capitalmind.domain.stock.Stock;
import gustavo.ventieri.capitalmind.domain.user.User;
import gustavo.ventieri.capitalmind.infrastructure.exception.NotFoundException;
import gustavo.ventieri.capitalmind.infrastructure.mapper.stock.StockMapper;
import gustavo.ventieri.capitalmind.infrastructure.clients.brapi.BrapiApi;
import gustavo.ventieri.capitalmind.infrastructure.clients.brapi.dto.BrapiResponseDto;
import gustavo.ventieri.capitalmind.infrastructure.persistence.StockRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService implements StockServiceInterface {

    private final StockRepository stockRepository; // Repositório de ações
    private final UserService userService; // Serviço para validação e obtenção do usuário
    private final StockMapper stockMapper; // Mapeador de entidades de ação para DTO
    private final BrapiApi brapiApi; // API para consulta do preço das ações

    // O token de autenticação da API externa é carregado a partir das configurações
    @Value("${api.brapi.dev.token}")
    private String BRAPI_TOKEN;

    /**
     * Cria uma nova ação associada a um usuário.
     */
    @Override
    public void create(StockRequestDto stockRequestDto) {

        // Valida e obtém o usuário
        User user = this.userService.validateAndGetUser(stockRequestDto.userId());
        
        // Salva a ação no banco de dados
        this.stockRepository.save(
            new Stock(
                null, // ID gerado automaticamente
                stockRequestDto.name(), // Nome da ação
                stockRequestDto.description(), // Descrição da ação
                stockRequestDto.quantity(), // Quantidade de ações
                user, // Usuário associado
                Instant.now(), // Data e hora de criação
                Instant.now() // Data e hora da última atualização
            )
        );
    }

    /**
     * Atualiza uma ação existente.
     */
    @Override
    public void update(Long stockId, StockRequestDto stockRequestDto) {

        // Busca a ação pelo ID
        Stock stock = this.stockRepository.findById(stockId)
            .orElseThrow(() -> new NotFoundException("Stock Not Found"));

        // Atualiza os dados da ação
        stock.setName(stockRequestDto.name());
        stock.setDescription(stockRequestDto.description());
        stock.setQuantity(stockRequestDto.quantity());

        // Salva a ação atualizada
        this.stockRepository.save(stock);
    }

    /**
     * Obtém todas as ações de um usuário.
     */
    @Override
    public List<StockResponseDto> getAll(String userId) {

        // Valida e obtém o usuário
        User user = this.userService.validateAndGetUser(userId);
        
        // Busca todas as ações associadas ao usuário
        List<Stock> stocks = this.stockRepository.findAllByUserData(user);

        // Mapeia as ações para DTOs e calcula o valor total
        return stocks.stream()
            .map(stock -> stockMapper.toDto(stock, getPrice(stock.getQuantity(), stock.getName())))
            .collect(Collectors.toList());
    }

    /**
     * Obtém uma ação pelo ID.
     */
    @Override
    public StockResponseDto getById(Long stockId) {

        // Busca a ação pelo ID
        Stock stock = this.stockRepository.findById(stockId)
            .orElseThrow(() -> new NotFoundException("Stock Not Found"));

        // Mapeia a ação para DTO e retorna
        return stockMapper.toDto(stock, getPrice(stock.getQuantity(), stock.getName()));
    }

    /**
     * Exclui uma ação pelo ID.
     */
    @Override
    public void deleteById(Long stockId) {

        // Verifica se a ação existe antes de tentar excluí-la
        if (!this.stockRepository.existsById(stockId)) {
            throw new NotFoundException("Stock Not Found");
        }

        // Exclui a ação do banco de dados
        this.stockRepository.deleteById(stockId);
    }

    /**
     * Obtém o preço de uma ação, dado o nome e a quantidade.
     */
    @Override
    public Double getPrice(Integer quantity, String name) {
        // Calcula o preço total com base na quantidade e preço unitário
        return this.getTotal(quantity, name);
    }

    /**
     * Faz a chamada à API externa para obter o preço de mercado de uma ação.
     */
    private Double getTotal(Integer quantity, String name) {
        // Chama a API Brapi para obter os detalhes da ação
        BrapiResponseDto response = this.brapiApi.getRegularMarket(BRAPI_TOKEN, name);
        
        // Extrai o preço da resposta da API
        Double price = response.results().getFirst().regularMarketPrice();
        
        // Retorna o valor total das ações
        return quantity * price;
    }
}
