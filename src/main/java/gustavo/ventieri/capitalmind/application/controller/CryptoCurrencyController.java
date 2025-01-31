package gustavo.ventieri.capitalmind.application.controller;

import java.util.List;

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

import gustavo.ventieri.capitalmind.application.dto.cryptoCurrency.CryptoCurrencyRequestDto;
import gustavo.ventieri.capitalmind.application.dto.cryptoCurrency.CryptoCurrencyResponseDto;
import gustavo.ventieri.capitalmind.infrastructure.service.CryptoCurrencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/crypto")
public class CryptoCurrencyController {
    
    private final CryptoCurrencyService cryptoCurrencyService;


    @PostMapping("/create")
    public ResponseEntity<String> createCryptoCurrency(@RequestBody @Valid CryptoCurrencyRequestDto cryptoCurrencyRequestDto) {
        
        this.cryptoCurrencyService.create(cryptoCurrencyRequestDto);
        
        return ResponseEntity.status(HttpStatus.CREATED).body("Crypto Currency Created");
    }

    @PutMapping("/update/{cryptoCurrencyId}")
    public ResponseEntity<String> updateCryptoCurrencyById(@PathVariable("cryptoCurrencyId") Long cryptoCurrencyId, @RequestBody @Valid CryptoCurrencyRequestDto cryptoCurrencyRequestDto){
        
        this.cryptoCurrencyService.update(cryptoCurrencyId, cryptoCurrencyRequestDto);
    
        return ResponseEntity.ok().body("Crypto Currency Updated");
    }

    @DeleteMapping("/delete/{cryptoCurrencyId}")
    public ResponseEntity<String> deleteCryptoCurrencyById(@PathVariable("cryptoCurrencyId") Long cryptoCurrencyId) {
       
        cryptoCurrencyService.deleteById(cryptoCurrencyId);

        return ResponseEntity.ok("Crypto Currency Deleted");
       
    }


    @GetMapping("/{cryptoCurrencyId}")
    public ResponseEntity<CryptoCurrencyResponseDto> getCryptoCurrencyById(@PathVariable("cryptoCurrencyId") Long cryptoCurrencyId){

        return ResponseEntity.ok(cryptoCurrencyService.getById(cryptoCurrencyId));
       
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<CryptoCurrencyResponseDto>> getAllCryptoCurrencys(@PathVariable("userId") String userId) {

        return ResponseEntity.ok(cryptoCurrencyService.getAll(userId));
        
    }

}
