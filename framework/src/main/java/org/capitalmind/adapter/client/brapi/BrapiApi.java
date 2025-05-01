package org.capitalmind.adapter.client.brapi;


import org.capitalmind.dto.response.BrapiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

// Configurações para fazer requisição em api externa
@FeignClient(
    name = "BrapiClient",
    url = "https://brapi.dev"    
)

public interface BrapiApi {

    @GetMapping(value = "/api/quote/{name}")
    BrapiResponse getRegularMarket(@RequestParam("token") String token, @PathVariable("name") String name);
    
}