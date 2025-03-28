package org.capitalmind.service;

import java.util.List;

import org.capitalmind.dto.request.InvestmentRequest;
import org.capitalmind.dto.response.InvestmentResponse;

public interface InvestmentService {
    void create(InvestmentRequest investmentRequest);

    void update(Long investmentId, InvestmentRequest investmentRequest);

    List<InvestmentResponse> getAll(String userId);

    InvestmentResponse getById(Long investmentId);

    void deleteById(Long investmentId);
}
