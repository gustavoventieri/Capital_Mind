package org.capitalmind.adapter.mapper;

import org.capitalmind.adapter.dto.request.ExpenseRequestImpl;
import org.capitalmind.dto.request.ExpenseRequest;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {
    
    public ExpenseRequest toDomainExpenseRequest(ExpenseRequestImpl expenseRequestImpl){
        return new ExpenseRequest(expenseRequestImpl.name(), expenseRequestImpl.description(), expenseRequestImpl.category(), expenseRequestImpl.price(), expenseRequestImpl.userId());
    }

}
