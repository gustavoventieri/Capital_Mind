# Usa a imagem do OpenJDK 21 
FROM openjdk:21-jdk-slim

# Adiciona o Maven e PostgreSQL manualmente
RUN apt-get update && apt-get install -y maven postgresql-client netcat-openbsd

# Definir o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiar os arquivos do projeto para dentro do contêiner
COPY . .

# Construir o projeto
RUN mvn clean install -DskipTests

# Copiar o arquivo .jar gerado do contêiner de build
COPY target/capitalmind-0.0.1-SNAPSHOT.jar /app/capitalmind.jar

# Espera o bd estar pronto
COPY scripts/wait-for-postgres.sh /app/scripts/wait-for-postgres.sh
RUN chmod +x /app/scripts/wait-for-postgres.sh

# Expor a porta onde a aplicação estará disponível
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["/app/scripts/wait-for-postgres.sh", "java", "-jar", "/app/capitalmind.jar"]
