package org.capitalmind.adapter.controller;

import java.util.List;

import org.capitalmind.adapter.dto.request.CryptoCurrencyRequestImpl;
import org.capitalmind.adapter.mapper.CryptoCurrencyMapper;
import org.capitalmind.dto.request.CryptoCurrencyRequest;
import org.capitalmind.dto.response.CryptoCurrencyResponse;
import org.capitalmind.useCase.service.CryptoCurrencyServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/crypto")
@AllArgsConstructor
public class CryptoCurrencyController {
    
    private final CryptoCurrencyServiceImpl cryptoCurrencyServiceImpl;
    private final CryptoCurrencyMapper cryptoCurrencyMapper;

      // Realiza a criação de uma despesa
    @PostMapping("/create")
    public ResponseEntity<String> createCrypto(@RequestBody @Valid CryptoCurrencyRequestImpl cryptoCurrencyRequestImpl) {
        
        CryptoCurrencyRequest cryptoData = cryptoCurrencyMapper.toDomainCryptoCurrencyRequest(cryptoCurrencyRequestImpl);

        this.cryptoCurrencyServiceImpl.create(cryptoData);
        
        return ResponseEntity.status(HttpStatus.CREATED).body("Crypto Created");
    }

     // Atualiza um despesa existente por ID
    @PutMapping("/update/{cryptoId}")
    public ResponseEntity<String> updateCryptoById(@PathVariable("cryptoId") Long cryptoId, @RequestBody @Valid CryptoCurrencyRequestImpl cryptoCurrencyRequestImpl){
        
        CryptoCurrencyRequest cryptoData = cryptoCurrencyMapper.toDomainCryptoCurrencyRequest(cryptoCurrencyRequestImpl);

        this.cryptoCurrencyServiceImpl.update(cryptoId, cryptoData);
    
        return ResponseEntity.status(HttpStatus.OK).body("Crypto Updated");
    }

    // Remove um despesa existente por ID
    @DeleteMapping("/delete/{cryptoId}")
    public ResponseEntity<String> deleteCryptoById(@PathVariable("cryptoId") Long cryptoId) {
       
        this.cryptoCurrencyServiceImpl.deleteById(cryptoId);
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Crypto Deleted");
        
    }

     // Lista todas as despesas de um usuário por ID
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<CryptoCurrencyResponse>> getAllCryptos(@PathVariable("userId") String userId) {

        return ResponseEntity.status(HttpStatus.OK).body(cryptoCurrencyServiceImpl.getAll(userId));
    }

    // Lista uma despesa por ID
    @GetMapping("/{cryptoId}")
    public ResponseEntity<CryptoCurrencyResponse> getCryptoById(@PathVariable("cryptoId") Long cryptoId){

     return ResponseEntity.status(HttpStatus.OK).body(cryptoCurrencyServiceImpl.getById(cryptoId));          
       
    }

}
