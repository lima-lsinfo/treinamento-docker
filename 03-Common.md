### Exemplo 1: Dockerfile para uma aplicação Java Spring Boot

```Dockerfile
# Imagem base do JDK 17 (ou versão que sua aplicação necessita)
FROM openjdk:17-jdk-alpine

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo JAR gerado pela aplicação para o container
COPY target/seu-app.jar .

# Define o comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "seu-app.jar"]
```

#### Explicação:
- `FROM`: Escolhe uma imagem base que já tenha o JDK (Java Development Kit) necessário para rodar a aplicação.
- `WORKDIR`: Define o diretório de trabalho onde os comandos seguintes serão executados.
- `COPY`: Copia o arquivo jar gerado pela sua aplicação para dentro do container.
- `ENTRYPOINT`: Define o comando que será executado quando o container iniciar, no caso, rodar o arquivo jar.

### Exemplo 2: Dockerfile para uma aplicação Node.js

```Dockerfile
# Imagem base do Node.js
FROM node:18-alpine

# Diretório de trabalho
WORKDIR /app

# Copiar arquivos package.json e package-lock.json para o container
COPY package*.json ./

# Instalar as dependências
RUN npm install

# Copiar o restante da aplicação
COPY . .

# Expor a porta usada pela aplicação
EXPOSE 3000

# Comando para rodar a aplicação
CMD ["npm", "start"]
```

#### Explicação:
- `RUN npm install`: Executa o comando para instalar todas as dependências listadas no `package.json`.
- `EXPOSE`: Expõe a porta que a aplicação usará, o que facilita a configuração de portas no ambiente de execução (como Docker Compose ou Kubernetes).

### Exemplo 3: Dockerfile para uma aplicação Python Flask

```Dockerfile
# Usando imagem base do Python
FROM python:3.11-slim

# Define o diretório de trabalho
WORKDIR /app

# Instala as dependências da aplicação
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copia o código da aplicação
COPY . .

# Define a variável de ambiente para desativar o modo de buffer do Python
ENV PYTHONUNBUFFERED=1

# Expor a porta usada pela aplicação Flask
EXPOSE 5000

# Comando para rodar a aplicação
CMD ["python", "app.py"]
```
