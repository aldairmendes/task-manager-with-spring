# Task Manager API

API REST desenvolvida em Spring Boot para gestao de tarefas com autenticacao JWT e isolamento de dados por utilizador.

## Tecnologias

- Java 21+
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA
- MySQL
- Maven Wrapper

## Pre-requisitos

- Java JDK 21 ou superior instalado.
- Servidor MySQL a correr.

## Configuracao do Ambiente

Na raiz do projeto, crie um ficheiro chamado `.env` com o seguinte conteudo:

```env
DB_URL=jdbc:mysql://localhost:3306/task_manager_db?useSSL=false&serverTimezone=UTC
DB_USERNAME=root
DB_PASSWORD=sua_password_aqui
JWT_SECRET_KEY=sua_chave_secreta_com_mais_de_trinta_e_dois_caracteres
JWT_EXPIRATION=86400000
```

Como Executar
Ubuntu / Linux
Abra o terminal na pasta raiz do projeto e execute:

Bash
./mvnw spring-boot:run
macOS
Abra o terminal na pasta raiz do projeto e execute:

Bash
./mvnw spring-boot:run
Windows
Abra o Prompt de Comando (CMD) ou PowerShell na pasta raiz do projeto e execute:

DOS
mvnw.cmd spring-boot:run
Documentacao da API (Swagger)
Com a aplicacao a correr, aceda no navegador ao link:
http://localhost:8080/swagger-ui/index.html