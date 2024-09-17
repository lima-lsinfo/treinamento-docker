### Exemplo básico de um arquivo `docker-compose.yml`

```yaml
version: '3.8'

services:
  web:
    image: nginx:latest
    ports:
      - "8080:80"
    volumes:
      - ./html:/usr/share/nginx/html
    networks:
      - frontend

  app:
    build: .
    ports:
      - "5000:5000"
    environment:
      - DATABASE_URL=mysql://user:password@db/dbname
    depends_on:
      - db
    networks:
      - backend

  db:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: dbname
      MYSQL_USER: user
      MYSQL_PASSWORD: password
    volumes:
      - db_data:/var/lib/mysql
    networks:
      - backend

volumes:
  db_data:

networks:
  frontend:
  backend:
```

### Explicação:

1. **version**: Define a versão do formato do `docker-compose.yml`. Usamos `3.8`, que é uma versão estável e amplamente usada.
   
2. **services**: Aqui, definimos os diferentes serviços (containers) que serão gerenciados pelo Docker Compose.

   - **web**: Um container Nginx que expõe a porta 80 para a porta 8080 do host.
     - `volumes`: Mapeia um diretório local para o diretório do Nginx dentro do container.
     - `networks`: Conecta o serviço `web` à rede `frontend`.

   - **app**: Um serviço para uma aplicação que será construído localmente a partir de um Dockerfile.
     - `build`: Indica que o container será construído a partir do Dockerfile presente no diretório atual.
     - `environment`: Define variáveis de ambiente, como a URL de conexão com o banco de dados.
     - `depends_on`: Faz com que o serviço `app` dependa do serviço `db`, ou seja, o banco de dados será iniciado antes do app.
     - `networks`: Conecta o serviço `app` à rede `backend`.

   - **db**: Um serviço MySQL que usa a imagem `mysql:8.0`.
     - `environment`: Variáveis de ambiente para configurar o banco de dados.
     - `volumes`: Armazena os dados do banco de dados persistidos usando um volume nomeado (`db_data`).
     - `networks`: Conecta o banco de dados à rede `backend`.

3. **volumes**: Define volumes nomeados para persistência de dados, como o volume `db_data` para o MySQL.

4. **networks**: Define redes internas para conectar os serviços. O `frontend` é usado pelo `web`, e o `backend` é usado pela aplicação e banco de dados.

### Comandos Docker Compose

- **Subir os containers definidos no `docker-compose.yml`**:
  ```bash
  docker-compose up
  ```
  - `-d`: Roda os containers em segundo plano.

- **Parar e remover todos os containers definidos**:
  ```bash
  docker-compose down
  ```

- **Rebuildar um serviço (útil após alterar o Dockerfile ou dependências)**:
  ```bash
  docker-compose up --build
  ```

- **Ver logs de todos os serviços**:
  ```bash
  docker-compose logs
  ```

- **Verificar o status dos serviços**:
  ```bash
  docker-compose ps
  ```
