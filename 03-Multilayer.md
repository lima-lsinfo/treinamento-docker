### Exemplo: Dockerfile multilayer com Spring Boot

```Dockerfile
# Etapa de build (imagem temporária para compilar a aplicação)
FROM maven:3.9.2-eclipse-temurin-17 AS builder

# Diretório de trabalho para o build
WORKDIR /build

# Copiar o arquivo pom.xml e baixar as dependências do projeto
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar o código-fonte da aplicação
COPY src ./src

# Compilar a aplicação e gerar o JAR
RUN mvn clean package -DskipTests

# Etapa de runtime (imagem final para rodar a aplicação)
FROM eclipse-temurin:17-jre-alpine

# Diretório de trabalho no container final
WORKDIR /app

# Copiar o JAR gerado na etapa anterior para a imagem final
COPY --from=builder /build/target/seu-app.jar .

# Porta exposta para a aplicação
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "seu-app.jar"]
```

### Explicação das camadas:

1. **Etapa de Build (`builder`)**:
   - Usa uma imagem base com o Maven e JDK 17 para compilar a aplicação.
   - O comando `COPY pom.xml .` copia apenas o arquivo `pom.xml`, e o comando `RUN mvn dependency:go-offline` baixa as dependências antes de copiar o código. Isso aproveita o cache do Docker, de modo que o download de dependências só será refeito se o `pom.xml` mudar.
   - Depois, o código é copiado e o JAR é gerado com `mvn clean package -DskipTests`.

2. **Etapa de Runtime**:
   - Usa uma imagem muito mais leve, apenas com o JRE (Java Runtime Environment), pois não precisamos do Maven ou do JDK para rodar a aplicação, apenas para compilar.
   - O `COPY --from=builder` copia o JAR gerado da etapa de build para esta imagem final.
   - O `ENTRYPOINT` define o comando que será executado para rodar a aplicação.

### Benefícios:
- **Menor tamanho de imagem final**: A imagem de runtime usa apenas o JRE, o que reduz o tamanho final em comparação com uma imagem contendo todo o Maven e o JDK.
- **Builds mais rápidos**: Se as dependências no `pom.xml` não mudarem, elas serão cacheadas e não precisarão ser baixadas novamente.
- **Isolamento do build e runtime**: A etapa de build é separada da etapa de execução, garantindo que a imagem final seja limpa e só contenha o necessário para rodar a aplicação.