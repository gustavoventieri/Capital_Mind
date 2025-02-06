package gustavo.ventieri.capitalmind.infrastructure.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;


// Configurações do swagger
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Capital Mind")
                .description("\n" + //
                                        "O Capital Mind é uma solução completa de controle financeiro pessoal, projetada para ajudar os usuários a gerenciar suas finanças de maneira eficiente, prática e segura. Com ele, é possível monitorar e controlar despesas, administrar investimentos em criptomoedas, ações e outros ativos, além de planejar objetivos financeiros de forma estratégica.")
                .version("1.0")
                .contact(new Contact()
                    .name("Gustavo Ventieri")
                    .url("https://www.capitalmind.com")
                    .email("contato@capitalmind.com"))
                .termsOfService("Termos de uso: Capital Mind")
                .license(new License()
                    .name("Licença - Capital Mind")
                    .url("https://www.capitalmind.com")));
    }
}