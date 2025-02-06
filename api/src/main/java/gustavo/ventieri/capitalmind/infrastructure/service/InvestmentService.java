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

    private final InvestmentRepository investmentRepository; // Repositório de investimentos
    private final UserService userService; // Serviço para validar e obter usuários
    private final InvestmentMapper investmentMapper; // Mapeador entre a entidade Investment e o DTO

    /**
     * Cria um novo investimento associado a um usuário.
     */
    @Override
    public void create(InvestmentRequestDto investmentRequestDto) {

        // Valida e obtém o usuário associado ao investimento
        User user = this.userService.validateAndGetUser(investmentRequestDto.userId());

        // Salva o investimento no banco de dados
        this.investmentRepository.save(
            new Investment(
                null, // ID será gerado automaticamente
                investmentRequestDto.name(), // Nome do investimento
                investmentRequestDto.description(), // Descrição do investimento
                investmentRequestDto.price(), // Preço do investimento
                user, // Usuário associado
                Instant.now(), // Timestamp de criação
                Instant.now() // Timestamp de atualização
            )
        );
    }

    /**
     * Atualiza um investimento existente.
     */
    @Override
    public void update(Long investmentId, InvestmentRequestDto investmentRequestDto) {

        // Busca o investimento pelo ID
        Investment investment = this.investmentRepository.findById(investmentId)
            .orElseThrow(() -> new NotFoundException("Investment Not Found"));

        // Atualiza os campos do investimento
        investment.setName(investmentRequestDto.name());
        investment.setDescription(investmentRequestDto.description());
        investment.setPrice(investmentRequestDto.price());

        // Salva o investimento atualizado no banco de dados
        this.investmentRepository.save(investment);
    }

    /**
     * Obtém todos os investimentos de um usuário.
     */
    @Override
    public List<InvestmentResponseDto> getAll(String userId) {

        // Valida e obtém o usuário associado aos investimentos
        User user = this.userService.validateAndGetUser(userId);

        // Recupera todos os investimentos associados ao usuário
        List<Investment> investments = this.investmentRepository.findAllByUserData(user);

        // Mapeia os investimentos para DTOs
        return investments.stream()
            .map(investment -> investmentMapper.toDto(investment))
            .collect(Collectors.toList());
    }

    /**
     * Obtém um investimento pelo ID.
     */
    @Override
    public InvestmentResponseDto getById(Long investmentId) {

        // Busca o investimento pelo ID
        Investment investment = this.investmentRepository.findById(investmentId)
            .orElseThrow(() -> new NotFoundException("Investment Not Found"));

        // Mapeia o investimento para DTO e retorna
        return investmentMapper.toDto(investment);
    }

    /**
     * Exclui um investimento pelo ID.
     */
    @Override
    public void deleteById(Long investmentId) {

        // Verifica se o investimento existe antes de tentar excluí-lo
        if (!this.investmentRepository.existsById(investmentId)) {
            throw new NotFoundException("Investment Not Found");
        }

        // Exclui o investimento do banco de dados
        this.investmentRepository.deleteById(investmentId);
    }
}
