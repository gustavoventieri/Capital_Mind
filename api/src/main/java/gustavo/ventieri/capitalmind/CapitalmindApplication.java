package gustavo.ventieri.capitalmind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CapitalmindApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapitalmindApplication.class, args);
	}

}
