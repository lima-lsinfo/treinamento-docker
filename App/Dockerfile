# Cria uma imagem Docker para a aplicação de estoque
FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu as build

# Instala o Maven
RUN apt-get update && apt-get install -y maven

# Cria diretório para a aplicação
RUN mkdir /opt/app

# Define o diretório de trabalho dentro do contêiner
WORKDIR /opt/app

COPY sistema_pedidos/pom.xml ./

# Baixa as dependências do projeto
RUN mvn dependency:go-offline

# Copia o código fonte para o diretório de trabalho
COPY sistema_pedidos/src ./src

# Compila a aplicação
RUN mvn package -DskipTests

# Cria um argumento para o nome da aplicação
FROM openjdk:21-slim as final

# Copia o jar compilado da etapa anterior
COPY --from=build /opt/app/target/sistema_pedidos-1.0-SNAPSHOT.jar app.jar

# Define o comando padrão para rodar a aplicação
CMD ["java", "-jar", "app.jar"]