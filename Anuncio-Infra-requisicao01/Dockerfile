# Imagem com Maven e JDK 17
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Diretório de trabalho
WORKDIR /app

# Copia todos os arquivos do projeto
COPY . .

# Compila o projeto sem rodar testes
RUN mvn clean package -DskipTests

# -------------------------------
# Nova imagem somente com o JAR
# -------------------------------
FROM eclipse-temurin:17-jre

# Diretório para a aplicação
WORKDIR /app

# Copia apenas o JAR gerado pelo builder
COPY --from=builder /app/target/AnuncuiosLoc-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta usada pelo Spring Boot
EXPOSE 8080

# Comando para iniciar a API
ENTRYPOINT ["java", "-jar", "app.jar"]
