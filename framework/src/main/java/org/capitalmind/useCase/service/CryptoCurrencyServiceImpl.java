package org.capitalmind.useCase.service;

import java.util.List;

import org.capitalmind.dto.request.CryptoCurrencyRequest;
import org.capitalmind.dto.response.CryptoCurrencyResponse;
import org.capitalmind.service.CryptoCurrencyService;
import org.springframework.stereotype.Service;


@Service
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {

    @Override
    public void create(CryptoCurrencyRequest cryptoCurrencyRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public void update(Long cryptoCurrencyId, CryptoCurrencyRequest cryptoCurrencyRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<CryptoCurrencyResponse> getAll(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public CryptoCurrencyResponse getById(Long cryptoCurrencyId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public void deleteById(Long cryptoCurrencyId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public Double getPriceInRealTime(String ids, String currency, Double quantity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPriceInRealTime'");
    }



    
}
