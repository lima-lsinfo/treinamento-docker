# **DockerHub**

O **DockerHub** é o maior repositório público de imagens de containers. Ele fornece uma vasta coleção de imagens oficiais para várias linguagens e ferramentas, além de permitir que os usuários criem e hospedem seus próprios repositórios de imagens.

#### Principais funcionalidades do DockerHub:

1. **Imagens oficiais**: Muitas imagens de software populares, como MySQL, Redis, Nginx e Node.js, estão disponíveis como "imagens oficiais" no DockerHub, mantidas pelas comunidades ou pelos próprios fornecedores do software.
2. **Repositórios públicos e privados**:
   - **Públicos**: Qualquer um pode ver e baixar a imagem.
   - **Privados**: Imagens só podem ser acessadas por pessoas ou equipes autorizadas (limitados na versão gratuita).
3. **Automated Builds**: Integração com repositórios de código (como GitHub ou Bitbucket) que permite construir e enviar automaticamente imagens para o DockerHub sempre que o código é atualizado.
4. **Tags**: Cada imagem pode ter várias versões (chamadas de "tags"). Por exemplo, uma imagem `myapp` pode ter as tags `1.0`, `2.0`, ou `latest` para representar diferentes versões da aplicação.

#### Usando o DockerHub

##### Fazer login no DockerHub via CLI:

```bash
docker login
```
Isso solicitará seu nome de usuário e senha DockerHub. Isso é necessário para fazer push de imagens para repositórios privados ou públicos.

##### Enviar uma imagem para o DockerHub:

1. Crie a imagem localmente:
   ```bash
   docker build -t nome-do-usuario/nome-da-imagem:tag .
   ```

2. Enviar a imagem para o DockerHub:
   ```bash
   docker push nome-do-usuario/nome-da-imagem:tag
   ```

3. Exemplo:
   ```bash
   docker build -t meuusuario/meuapp:v1 .
   docker push meuusuario/meuapp:v1
   ```

##### Baixar uma imagem do DockerHub:

```bash
docker pull nome-da-imagem:tag
```

Exemplo:
```bash
docker pull nginx:latest
```
