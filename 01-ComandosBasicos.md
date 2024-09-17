# Comandos Básicos

### 1. **Gerenciar Imagens**

- **Construir uma imagem a partir de um Dockerfile**:
  ```bash
  docker build -t nome-da-imagem .
  ```
  - `-t nome-da-imagem`: Nomeia a imagem.
  - `.`: Diretório atual contendo o Dockerfile.

- **Listar imagens disponíveis**:
  ```bash
  docker images
  ```

- **Remover uma imagem**:
  ```bash
  docker rmi nome-da-imagem
  ```

### 2. **Gerenciar Containers**

- **Criar e rodar um container**:
  ```bash
  docker run -d --name nome-do-container -p 8080:8080 nome-da-imagem
  ```
  - `-d`: Roda o container em segundo plano (modo *detached*).
  - `--name nome-do-container`: Nomeia o container.
  - `-p 8080:8080`: Mapeia a porta 8080 do container para a porta 8080 do host.

- **Listar containers em execução**:
  ```bash
  docker ps
  ```

- **Listar todos os containers (incluindo os parados)**:
  ```bash
  docker ps -a
  ```

- **Parar um container**:
  ```bash
  docker stop nome-do-container
  ```

- **Remover um container**:
  ```bash
  docker rm nome-do-container
  ```

### 3. **Inspecionar Containers**

- **Acessar um container em execução**:
  ```bash
  docker exec -it nome-do-container /bin/sh
  ```
  - `-it`: Inicia o terminal interativo.
  - `/bin/sh`: Abre um shell interativo no container (pode usar `/bin/bash` em containers com bash).

- **Visualizar logs de um container**:
  ```bash
  docker logs nome-do-container
  ```

### 4. **Gerenciar Volumes**

- **Criar um volume**:
  ```bash
  docker volume create nome-do-volume
  ```

- **Listar volumes disponíveis**:
  ```bash
  docker volume ls
  ```

- **Usar um volume em um container**:
  ```bash
  docker run -d --name nome-do-container -v nome-do-volume:/caminho/dentro/do/container nome-da-imagem
  ```
  - `-v`: Monta o volume no caminho especificado dentro do container.

- **Remover um volume**:
  ```bash
  docker volume rm nome-do-volume
  ```

### 5. **Networking**

- **Listar redes Docker**:
  ```bash
  docker network ls
  ```

- **Criar uma rede**:
  ```bash
  docker network create nome-da-rede
  ```

- **Conectar um container a uma rede**:
  ```bash
  docker network connect nome-da-rede nome-do-container
  ```

### 6. **Outros Comandos Úteis**

- **Ver o uso de recursos de containers em execução**:
  ```bash
  docker stats
  ```

- **Remover containers parados, imagens não utilizadas e volumes órfãos**:
  ```bash
  docker system prune
  ```
