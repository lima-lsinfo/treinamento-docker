Uma imagem **Distroless** é uma abordagem minimalista que inclui apenas os binários e dependências necessários para rodar sua aplicação, sem incluir um sistema operacional completo como o Alpine ou Debian. Isso reduz significativamente o tamanho da imagem, melhora a segurança e a performance.

A seguir, vou te mostrar um exemplo de Dockerfile multicamadas usando uma imagem Distroless para uma aplicação Java Spring Boot.

### Exemplo: Dockerfile multicamadas com Distroless

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

# Etapa de runtime usando Distroless (imagem final para rodar a aplicação)
FROM gcr.io/distroless/java17-debian11

# Diretório de trabalho no container final
WORKDIR /app

# Copiar o JAR gerado na etapa anterior para a imagem final
COPY --from=builder /build/target/seu-app.jar .

# Porta exposta para a aplicação
EXPOSE 8080

# Comando para executar a aplicação
CMD ["seu-app.jar"]
```

### Explicação:

1. **Etapa de Build (`builder`)**:
   - Usa uma imagem Maven com o JDK 17 (Temurin) para compilar a aplicação.
   - O processo de build é semelhante ao exemplo anterior, copiando primeiro o `pom.xml` para baixar dependências, depois o código-fonte, e finalmente gerando o JAR.
   
2. **Etapa de Runtime**:
   - **Imagem Distroless**: A imagem `gcr.io/distroless/java17-debian11` é uma imagem minimalista que contém apenas o ambiente de runtime necessário para rodar uma aplicação Java 17, sem outras bibliotecas ou ferramentas de sistema.
   - O JAR gerado na etapa de build é copiado para a imagem Distroless.
   - **Comando `CMD`**: Diferente do `ENTRYPOINT` usado no exemplo anterior, aqui o `CMD` é utilizado para definir o arquivo `.jar` a ser executado, pois a imagem Distroless para Java já assume o comando `java -jar` por padrão.

### Benefícios:
- **Segurança**: Como a imagem não inclui um shell, pacotes ou ferramentas de sistema operacional, a superfície de ataque é drasticamente reduzida.
- **Tamanho reduzido**: A imagem final será significativamente menor, já que contém apenas o runtime Java necessário.
- **Performance**: Como a imagem é mais enxuta, o tempo de startup do container pode ser melhorado.
