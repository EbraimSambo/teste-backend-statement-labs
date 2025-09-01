# PROSEFA API

## 📌 Descrição
Este é o backend do **PROSEFA**, desenvolvido em **Java 17** com **Spring Boot 3**, estruturado segundo a **Arquitetura Hexagonal**.  
A aplicação fornece **APIs REST seguras com JWT**, utiliza **PostgreSQL** como banco de dados relacional e faz versionamento com **Flyway**.  

Tudo está preparado para execução local ou conteinerizada via **Docker**.
API REST construída em **Arquitetura Hexagonal (Ports & Adapters)** com **Spring Boot**, **PostgreSQL**, **swagge**, **Flyway**, **JWT** e **Docker**. 

O projeto utiliza **dois identificadores por recurso**:

* `id` (Long/serial) – para consultas internas e relacionamentos no banco (performático e simples em joins)
* `ref` (UUID) – exposto externamente em rotas e payloads para evitar exposição de ids sequenciais e facilitar integrações

> **Resumo rápido**: Suba com Docker (`docker-compose up -d`) ou rode com(`./mvnw spring-boot:run`), gere um usuário, faça login para obter o token JWT e consuma os endpoints usando `Authorization: Bearer <token>` e acesse os docs do sweger em http://localhost:8082/api/v1.0/swagger-ui/index.html/, ja tem um banco de dado conectado remotamente link do Postman https://app.getpostman.com/join-team?invite_code=628795a30f29d1c3b255e4433f0900353b1fc60038d69d0967633f89a7e97f34&target_code=2b5a71bdf89325a2cfe0ee79effebd05.

---

## Índice

* [Arquitetura](#arquitetura)
* [Tecnologias](#tecnologias)
* [Estrutura de Pastas](#estrutura-de-pastas)
* [Getting Started](#getting-started)

    * [Pré-requisitos](#pré-requisitos)
    * [Configuração por ambiente](#configuração-por-ambiente)
    * [Subindo com Docker](#subindo-com-docker)
    * [Rodando local (sem Docker)](#rodando-local-sem-docker)
* [Migrações (Flyway)](#migrações-flyway)
* [Segurança & Autenticação (JWT)](#segurança--autenticação-jwt)
* [Convenções de Identificadores](#convenções-de-identificadores)
* [Padrão de Resposta & Erros](#padrão-de-resposta--erros)
* [Endpoints](#endpoints)

    * [Auth](#auth)
    * [Company](#company)
    * [Fiscal Stamp](#fiscal-stamp)
    * [Audit Log](#audit-log)
* [Variáveis de Ambiente](#variáveis-de-ambiente)
* [Comandos úteis](#comandos-úteis)
* [Licença](#licença)

---

## Arquitetura

Baseada em **Hexagonal Architecture (Ports & Adapters)**:

* **domain**: entidades de negócio, interfaces (ports) e casos de uso.
* **application**: serviços de aplicação e orquestração dos casos de uso.
* **adapters**: implementações concretas das portas (ex.: REST controllers no *primary*, JPA repositories no *secondary*).
* **infrastructure**: detalhes de segurança, utilitários e integrações técnicas (ex.: JWT, filtros, utils).

**Benefícios principais**:

* Domínio independente de frameworks
* Testabilidade elevada
* Substituição fácil de tecnologias (ex.: trocar persistência)

> O projeto está organizado por **feature** (`auth`, `company`, `fiscalStamp`, `auditLog`, `user`), cada uma contendo suas camadas.

---

## Tecnologias

* **Java** 17+
* **Spring Boot** (Web, Validation, Security)
* **Spring Data JPA**
* **PostgreSQL**
* **Flyway** (migrações)
* **JWT** (autenticação)
* **Docker / Docker Compose**
* **Maven**

---

## Estrutura de Pastas

Trecho simplificado (ver árvore completa no repositório):

```
src/main/java/PROSEFA/app
├── features
│   ├── auth
│   │   ├── adapters/primary/http/... (AuthController, DTOs)
│   │   └── infrastructure/security (JwtProvider, JwtAuthenticationFilter, SecurityConfig)
│   ├── company
│   │   ├── adapters (primary:http, secondary:jpa/repository/mappers)
│   │   ├── application (service, usecase)
│   │   └── domain (entity, repository, services, usecase)
│   ├── fiscalStamp
│   │   ├── adapters (primary:http, secondary:jpa/...)
│   │   ├── application (services, usecase)
│   │   └── domain (entity, repository, service, usecase)
│   ├── auditLog
│   │   ├── adapters (primary:http, memory; secondary:jpa/...)
│   │   ├── application (services)
│   │   └── domain (entity, repository, services)
│   └── user (secondary:jpa, application, domain)
└── shared (api, exception, interceptors, utils)
```

Arquivos importantes:

* `docker-compose.yml` – orquestra banco e aplicação
* `dockerfile` – build de imagem da API
* `src/main/resources/application.yml` – configurações
* `src/main/resources/db/migration` – scripts Flyway

---

## Getting Started

### Pré-requisitos

* **Docker** e **Docker Compose** (recomendado para subir rápido)
* Ou: **Java 17+**, **Maven**, **PostgreSQL** local

### Configuração por ambiente

Ajuste o `application.yml` conforme seu ambiente. Exemplo mínimo:

```yaml
server:
  port: 8082
  servlet:
    context-path: /api/v1.0

spring:
  mvc:
    throw-exception-if-no-handler-found: true
  web:
    resources:
      add-mappings: false
  flyway:
    baseline-on-migrate: true
  datasource:
    url: jdbc:postgresql://postgres:5432/statemen3
    username: ${POSTGRES_USER:ebraimsambo}
    password: ${POSTGRES_PASSWORD:password}
  jpa:
    hibernate:
      ddl-auto: validate
    properties:
      hibernate.jdbc.time_zone: UTC
      hibernate.format_sql: true
      hibernate.default_batch_fetch_size: 100
logging:
  level:
    root: INFO
```

> **Dica**: no Docker, o host do banco costuma ser o nome do serviço no `docker-compose` (ex.: `postgres`).

### Subindo com Docker

1. Configure variáveis no `.env` (opcional):

   ```env
   POSTGRES_DB=statemen3
   POSTGRES_USER=ebraimsambo
   POSTGRES_PASSWORD=changeme
   JWT_SECRET=super-secret
   JWT_EXPIRATION=3600
   ```
2. Suba os serviços:

   ```bash
   docker-compose up -d --build
   ```
3. Verifique logs da API:

   ```bash
   docker logs -f <nome-do-serviço-da-api>
   ```
4. A API ficará disponível em: `http://localhost:8082/api/v1.0`

### Rodando local (sem Docker)

1. Crie o banco no Postgres local e ajuste `spring.datasource.*` no `application.yml`.
2. Execute as migrações automaticamente ao subir.
3. Rode a aplicação:

   ```bash
   ./mvnw spring-boot:run
   # ou
   mvn clean package && java -jar target/*.jar
   ```

---

## Migrações (Flyway)

Scripts SQL em `src/main/resources/db/migration`. Ao iniciar a aplicação, o Flyway executa as migrações pendentes. Boas práticas:

* Cada alteração de schema em um novo arquivo `V<versão>__descrição.sql`.
* Nunca edite uma migração já aplicada.

---

## Segurança & Autenticação (JWT)

* **Login** retorna `token` JWT e dados básicos do usuário.
* As requisições autenticadas exigem header: `Authorization: Bearer <token>`.
* O filtro `JwtAuthenticationFilter` valida o token em cada chamada.
* **Config** em `features/auth/infrastructure/security`.

Variáveis de ambiente relevantes:

* `JWT_SECRET` – chave de assinatura
* `JWT_EXPIRATION` – expiração em segundos

---

## Convenções de Identificadores

* Internamente (DB e joins): `id` **Long** (auto-increment)
* Externo (rotas/payloads): `ref` **UUID**

**Exemplo de resposta**:

```json
{
  "ref": "2f0f86a3-6c4f-4d59-bd26-6f0b6f5a0a77",
  "name": "ACME Ltda",
  "status": "ACTIVE",
  "createdAt": "2025-08-30T10:20:30Z"
}
```

---

## Padrão de Resposta & Erros

A API utiliza um envelope padrão (`shared/api/ApiResponse`) e `GlobalExceptionHandler` para erros.

**Sucesso**

```json
{
  "success": true,
  "data": { "...payload..." },
  "message": "Operação realizada com sucesso"
}
```

**Erro**

```json
{
  "success": false,
  "error": "Bad Request",
  "message": "Detalhe do problema",
  "timestamp": "2025-08-30T15:10:00Z",
  "status": 400
}
```

---

## Endpoints

> **Prefixo**: definido por `server.servlet.context-path`, ex.: `/api/v1.0`.
>
> **Observação**: rotas reais podem variar conforme os `@RequestMapping` dos controllers. Abaixo, um guia com base na estrutura.

### Auth

* `POST /auth/register` – cria usuário

    * body:

      ```json
      { "username": "john", "password": "secret" }
      ```
* `POST /auth/login` – autentica e retorna token JWT

    * resposta:

      ```json
      { "token": "<jwt>", "user": { "username": "john" } }
      ```

### Company

* `POST /companies` – cria empresa

    * body (ex.):

      ```json
      {
        "name": "ACME",
        "type": "PRIVATE", // conforme enum TypeCompany
        "status": "ACTIVE"
      }
      ```
* `GET /companies` – lista empresas (paginado)
* `GET /companies/{ref}` – detalhe por `ref` (UUID)
* `PUT /companies/{ref}` – atualiza
* (Opcional) `PATCH /companies/{ref}/status?value=ACTIVE|INACTIVE` – troca status

### Fiscal Stamp

* `POST /fiscal-stamps` – cria selo fiscal

    * body (ex.):

      ```json
      {
        "companyRef": "2f0f86a3-6c4f-4d59-bd26-6f0b6f5a0a77",
        "code": "STP-0001"
      }
      ```
* `GET /fiscal-stamps` – lista
* `GET /fiscal-stamps/{ref}` – detalhe por `ref`
* `PUT /fiscal-stamps/{ref}` – atualiza
* `PUT /fiscal-stamps/{ref}/status?value=VALID|EXPIRED|CANCELLED` – altera status

### Audit Log

* `GET /audit-logs` – lista eventos (filtros por `companyRef`, ação, período)
* `GET /audit-logs/{ref}` – detalhe por `ref`

> **Dica**: algumas capturas de auditoria podem ocorrer em memória e serem persistidas conforme serviços de aplicação (`AuditLogCapture`, `AuditEvent`).

---

## Variáveis de Ambiente

| Variável              | Descrição                          | Default         |
| --------------------- | ---------------------------------- | --------------- |
| `POSTGRES_DB`         | Nome do banco                      | `statemen3`     |
| `POSTGRES_USER`       | Usuário do banco                   | `ebraimsambo`   |
| `POSTGRES_PASSWORD`   | Senha do banco                     | `password`      |
| `DB_HOST`             | Host do banco (Docker: `postgres`) | `postgres`      |
| `DB_PORT`             | Porta do banco                     | `5432`          |
| `JWT_SECRET`          | Segredo para assinar tokens        | **obrigatório** |
| `JWT_EXPIRATION`      | Expiração do token em segundos     | `3600`          |
| `SERVER_PORT`         | Porta da aplicação                 | `8082`          |
| `SERVER_CONTEXT_PATH` | Prefixo das rotas                  | `/api/v1.0`     |

> Sugestão: manter segredos fora do repositório (ex.: `.env`, variáveis no provedor de cloud, Vault etc.).

---

## Comandos úteis

```bash
# Subir tudo (Docker)
docker-compose up -d --build

# Ver logs
docker logs -f <serviço-da-api>

# Rodar local
./mvnw spring-boot:run

# Build jar
mvn clean package -DskipTests

# Testes
./mvnw test
```

---

## Exemplos de uso (cURL)

```bash
# 1) Registrar
curl -X POST http://localhost:8082/api/v1.0/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"admin","password":"changeme"}'

# 2) Login
TOKEN=$(curl -s -X POST http://localhost:8082/api/v1.0/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin","password":"changeme"}' | jq -r .token)

echo $TOKEN

# 3) Criar Company
curl -X POST http://localhost:8082/api/v1.0/companies \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"name":"ACME","type":"PRIVATE","nif":"AWIERERJREHJHDF"}'

# 4) Listar Companies
curl -H "Authorization: Bearer $TOKEN" \
  http://localhost:8082/api/v1.0/companies
```

---

## Notas de Design

* **Hexagonal**: cada *feature* isola domínio, portas e adaptadores.
* **DTOs/Mapper**: objetos de transporte na camada de adapters; mapeadores convertendo para entidades de domínio/entidades JPA.
* **Validações**: anotações Bean Validation nos DTOs (`@Valid`) e validadores customizados (ex.: `EnumValidator`).
* **Auditoria**: serviço de aplicação encapsula captura e persistência de `ActionAuditLog`.
* **Utilities**: `UtilUUID` para parsing/validação de UUID em controllers.

---

## Troubleshooting

* **`StackOverflowError` no login**: verifique recursões em `equals/hashCode/toString` das entidades/DTOs, ciclos de serialização ou mapeamentos bi-direcionais sem `@JsonIgnore`.
* **`UnsatisfiedDependencyException` ao subir**: checar construtores com dependências, falta de `@Component/@Service/@Repository`, e pacotes sob *component scan*.
* **Erro Flyway em `ALTER TABLE`**: garanta que a coluna não exista previamente e que a migração está em nova versão.
* **Context-path**: defina `server.servlet.context-path: /api/v1.0` para prefixar todas as rotas.

---

## Licença

Este projeto é disponibilizado sob a licença que você preferir. Atualize esta seção com a licença adotada (ex.: MIT, Apache-2.0).
