# Tools Challenge - API de Pagamentos

API REST para processamento e estorno de transações financeiras, desenvolvida com **Java 17** e **Spring Boot 4**.

## Como Executar
Para rodar a aplicação localmente, utilize o Gradle Wrapper:
```bash
./gradlew bootRun
```

## 🐳 Docker
Se desejar rodar via Docker, utilize os comandos:
1. **Construir a imagem:**
```bash
docker build -t tools-challenge .
```
2. Iniciar o container:
```bash
docker run -p 8080:8080 tools-challenge
```
A API estará disponível em http://localhost:8080.

## Endpoints Principais

    POST /api/pagamentos - Realiza um novo pagamento.

    POST /api/pagamentos/{id}/estorno - Cancela uma transação existente.

    GET /api/pagamentos - Lista o histórico de todas as transações.

    GET /api/pagamentos/{id} - Consulta os detalhes de uma transação específica.

## Decisões Técnicas:
Strategy Pattern: Utilizado para o processamento de pagamentos. Permite que novos métodos de pagamento (ex: Pix ou Boleto) sejam adicionados criando apenas uma nova classe, respeitando o princípio de Aberto/Fechado (SOLID).

Persistência em Memória: Implementada utilizando ConcurrentHashMap para garantir thread-safety e simplicidade, atendendo aos requisitos do desafio sem dependências externas de banco de dados.

Tratamento de Erros: Centralizado em um GlobalExceptionHandler para garantir que a API retorne erros padronizados em formato JSON (ex: 400 Bad Request para validações e 404 Not Found).

## Testes
A solução possui testes unitários e de integração cobrindo as camadas de serviço, repositório e tratamento de erros:
```bash
./gradlew test
```

## Postman

Para facilitar os testes, uma collection com todos os endpoints prontos está disponível na raiz do repositório:

    Arquivo: ToolsChallenge.postman_collection.json

Para usar: abra o Postman, clique em Import e selecione o arquivo mencionado.


