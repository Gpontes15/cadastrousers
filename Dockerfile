# Usar uma imagem do OpenJDK 21 para rodar a aplicação
FROM openjdk:21-jdk-slim

# Definir diretório de trabalho dentro do container
WORKDIR /app

# Copiar o JAR gerado pelo Maven para dentro do container
COPY target/*.jar app.jar

# Expor a porta que o Spring Boot usa
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
