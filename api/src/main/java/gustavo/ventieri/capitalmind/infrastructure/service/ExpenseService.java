package gustavo.ventieri.capitalmind.infrastructure.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import gustavo.ventieri.capitalmind.application.dto.expense.ExpenseRequestDto;
import gustavo.ventieri.capitalmind.application.dto.expense.ExpenseResponseDto;
import gustavo.ventieri.capitalmind.application.service.ExpenseServiceInterface;
import gustavo.ventieri.capitalmind.domain.expense.Expense;
import gustavo.ventieri.capitalmind.domain.user.User;
import gustavo.ventieri.capitalmind.infrastructure.exception.NotFoundException;
import gustavo.ventieri.capitalmind.infrastructure.mapper.expense.ExpenseMapper;
import gustavo.ventieri.capitalmind.infrastructure.persistence.ExpenseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService implements ExpenseServiceInterface {

    private final ExpenseRepository expenseRepository; // Repositório de despesas
    private final UserService userService; // Serviço para validar e obter usuários
    private final ExpenseMapper expenseMapper; // Mapeador entre a entidade Expense e o DTO

    /**
     * Cria uma nova despesa associada a um usuário.
     */
    @Override
    public void create(ExpenseRequestDto expenseRequestDto) {
        // Valida e obtém o usuário
        User user = this.userService.validateAndGetUser(expenseRequestDto.userId());

        // Salva a despesa no banco de dados
        this.expenseRepository.save(
            new Expense(
                null,
                expenseRequestDto.name(),
                expenseRequestDto.description(),
                expenseRequestDto.category(),
                expenseRequestDto.price(),
                user,
                Instant.now(),
                Instant.now()
            )
        );
    }

    /**
     * Atualiza uma despesa existente.
     */
    @Override
    public void update(Long expenseId, ExpenseRequestDto expenseRequestDto) {
        // Busca a despesa no banco de dados
        Expense expense = this.expenseRepository.findById(expenseId)
            .orElseThrow(() -> new NotFoundException("Expense Not Found"));

        // Atualiza os campos da despesa
        expense.setName(expenseRequestDto.name());
        expense.setDescription(expenseRequestDto.description());
        expense.setCategory(expenseRequestDto.category());
        expense.setPrice(expenseRequestDto.price());

        // Salva a despesa atualizada no banco de dados
        this.expenseRepository.save(expense);
    }

    /**
     * Obtém todas as despesas associadas a um usuário.
     */
    @Override
    public List<ExpenseResponseDto> getAll(String userId) {
        // Valida e obtém o usuário
        User user = this.userService.validateAndGetUser(userId);

        // Recupera todas as despesas associadas ao usuário
        List<Expense> expenses = this.expenseRepository.findAllByUserData(user);

        // Mapeia as despesas para DTOs
        return expenses.stream()
            .map(expense -> expenseMapper.toDto(expense))
            .collect(Collectors.toList());
    }

    /**
     * Obtém uma despesa por ID.
     */
    @Override
    public ExpenseResponseDto getById(Long expenseId) {
        // Busca a despesa no banco de dados
        Expense expense = this.expenseRepository.findById(expenseId)
            .orElseThrow(() -> new NotFoundException("Expense Not Found"));

        // Mapeia a despesa para o DTO e retorna
        return expenseMapper.toDto(expense);
    }

    /**
     * Exclui uma despesa por ID.
     */
    @Override
    public void deleteById(Long expenseId) {
        // Verifica se a despesa existe
        if (!this.expenseRepository.existsById(expenseId)) {
            throw new NotFoundException("Expense Not Found");
        }

        // Exclui a despesa do banco de dados
        this.expenseRepository.deleteById(expenseId);
    }
}
