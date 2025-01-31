package gustavo.ventieri.capitalmind.infrastructure.clients.brapi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import gustavo.ventieri.capitalmind.infrastructure.clients.brapi.dto.BrapiResponseDto;

@FeignClient(
    name = "BrapiClient",
    url = "https://brapi.dev"
    
)
public interface BrapiApi {

    @GetMapping(value = "/api/quote/{name}")
    BrapiResponseDto getRegularMarket(@RequestParam("token") String token, @PathVariable("name") String name);
    
}
   



