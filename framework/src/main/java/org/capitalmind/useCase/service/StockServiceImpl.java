package org.capitalmind.useCase.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.capitalmind.adapter.client.brapi.BrapiApi;
import org.capitalmind.adapter.mapper.StockMapper;
import org.capitalmind.driver.repository.StockRepositoryImpl;
import org.capitalmind.dto.request.StockRequest;
import org.capitalmind.dto.response.BrapiResponse;
import org.capitalmind.dto.response.StockResponse;
import org.capitalmind.entity.Stock;
import org.capitalmind.entity.User;
import org.capitalmind.exception.NotFound;
import org.capitalmind.service.StockService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StockServiceImpl implements StockService {

    private final StockRepositoryImpl stockRepositoryImpl;
    private final UserServiceImpl userServiceImpl;
    private final StockMapper stockMapper;
    private final BrapiApi brapiApi;

    @Value("${api.brapi.dev.token}")
    private String BRAPI_TOKEN;


    /**
     * Cria uma nova ação associada a um usuário.
     */

    @Override
    public void create(StockRequest stockRequest) {
        User user = this.userServiceImpl.validateAndGetUser(stockRequest.userId());

        this.stockRepositoryImpl.save(
            new Stock(
                null,
                stockRequest.name(),
                stockRequest.description(),
                stockRequest.quantity(),
                user,
                Instant.now(),
                Instant.now()
            )
        );
    }


    /**
    * Atualiza uma ação existente.
    */
    @Override
    public void update(Long stockId, StockRequest stockRequest) {
       Stock stock = this.stockRepositoryImpl.findById(stockId)
            .orElseThrow(() -> new NotFound("Stock Not Found"));

        stock.setName(stockRequest.name());
        stock.setDescription(stockRequest.description());
        stock.setQuantity(stockRequest.quantity());
        stock.setUpdateAt(Instant.now());

        this.stockRepositoryImpl.save(stock);

    }

    /**
    * Obtém todas as ações de um usuário.
    */
    @Override
    public List<StockResponse> getAll(String userId) {
        User user = this.userServiceImpl.validateAndGetUser(userId);

          // Busca todas as ações associadas ao usuário
        List<Stock> stocks = this.stockRepositoryImpl.findAllByUserData(user);

        // Mapeia as ações para DTOs e calcula o valor total
        return stocks.stream()
            .map(stock -> stockMapper.toStockResponse(stock, getPriceInRealTime(stock.getQuantity(), stock.getName())))
            .collect(Collectors.toList());

    }  

    /**
    * Obtém uma ação pelo ID.
    */

    @Override
    public StockResponse getById(Long stockId) {
          Stock stock = this.stockRepositoryImpl.findById(stockId)
            .orElseThrow(() -> new NotFound("Stock Not Found"));

        // Mapeia a ação para DTO e retorna
        return stockMapper.toStockResponse(stock, getPriceInRealTime(stock.getQuantity(), stock.getName()));
    }


    /**
     * Exclui uma ação pelo ID.
     */

    @Override
    public void deleteById(Long stockId) {
      if (this.stockRepositoryImpl.findById(stockId).isEmpty()) {
            throw new NotFound("Stock Not Found");
        }

        // Exclui a ação do banco de dados
        this.stockRepositoryImpl.delete(stockId);
    }


    /**
    * Obtém o preço de uma ação, dado o nome e a quantidade.
    */
    @Override
    public Double getPriceInRealTime(Integer quantity, String name) {
          // Chama a API Brapi para obter os detalhes da ação
        BrapiResponse response = this.brapiApi.getRegularMarket(BRAPI_TOKEN, name);
        
        System.out.println(response + "AQUIIIIIIIIIIIIIIIIIIIII");
        // Extrai o preço da resposta da API
        Double price = response.results().getFirst().regularMarketPrice();
        
        // Retorna o valor total das ações
        return quantity * price;
    }

    
    
}
