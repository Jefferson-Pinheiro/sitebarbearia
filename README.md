# CRM WhatsApp

CRM de acompanhamento de vendas com integracao a API oficial do WhatsApp (Meta Business / Cloud API).
Backend em **Quarkus** (Java).

## Estrutura

Organizado por feature, nao por camada:

- `lead/` — cadastro e status dos leads no funil (entidades Panache)
- `pipeline/` — etapas configuraveis do funil de vendas
- `conversation/` — historico de conversas e mensagens trocadas via WhatsApp
- `whatsapp/` — cadastro de credenciais do Meta Business (WABA ID, phone_number_id, access_token)
  e integracao com a Graph API (envio via HttpClient e recebimento via webhook)
- `crypto/` — criptografia AES do access_token antes de persistir

## Rodando em modo dev

```bash
mvn quarkus:dev
```

(Se preferir usar o wrapper `./mvnw`, gere com `mvn -N io.takari:maven:wrapper`.)

Sobe em `http://localhost:8080` com live reload. Precisa de um PostgreSQL local
(ajuste `src/main/resources/application.yml` se necessario) — ou use o Dev Services
do Quarkus, que sobe um Postgres via Docker automaticamente se nenhum estiver configurado.

## Como cadastrar as credenciais do Meta Business

1. No painel do Meta Business, gere o `WABA ID`, o `phone_number_id` e o `access_token` do seu app do tipo WhatsApp.
2. Com a aplicacao rodando, faca um POST para `/api/whatsapp/credentials`:

```json
{
  "label": "numero-comercial-principal",
  "wabaId": "SEU_WABA_ID",
  "phoneNumberId": "SEU_PHONE_NUMBER_ID",
  "accessToken": "SEU_ACCESS_TOKEN"
}
```

O `accessToken` e criptografado (AES) antes de ser salvo no banco.

## Configurar o webhook no painel da Meta

Aponte a URL de callback para `https://SEU_DOMINIO/api/whatsapp/webhook`, usando o mesmo valor
definido em `whatsapp.webhook.verify-token` (application.yml) como "Verify Token".

## Frontend

Existe uma interface web em `frontend/` (React + Vite) para visualizar leads e o funil.
Os endpoints continuam os mesmos de antes, entao o frontend nao precisa de nenhuma mudanca.
Veja `frontend/README.md` para instrucoes de execucao.

## Pre-requisitos

- Java 17+
- Maven (ou o wrapper `./mvnw`, gerado automaticamente por `quarkus create`)
- PostgreSQL (ou Docker, para o Dev Services do Quarkus subir um automaticamente)

## Deploy gratuito (Render + Neon + GitHub Pages)

1. **Banco (Neon)**: crie um projeto gratuito em neon.tech, copie a connection string e
   troque o prefixo `postgres://` por `jdbc:postgresql://` (mantendo `?sslmode=require` no final).
2. **Backend (Render)**: conecte este repositorio no Render — ele detecta o `render.yaml`
   automaticamente. Preencha as variaveis marcadas como `sync: false` no painel:
   `DATABASE_URL` (a string do Neon), `TOKEN_ENCRYPTION_KEY` e `WHATSAPP_WEBHOOK_VERIFY_TOKEN`.
   Deploy free tier: o servico "dorme" apos 15 min sem uso (primeiro acesso demora ~30-60s).
3. **Frontend (GitHub Pages)**: em Settings > Pages do repositorio, defina "Source: GitHub Actions".
   Em Settings > Secrets and variables > Actions > Variables, adicione `VITE_API_BASE_URL`
   com a URL publica do backend no Render (ex: `https://crm-whatsapp-backend.onrender.com/api`).
   O workflow em `.github/workflows/deploy-frontend.yml` builda e publica a cada push em `main`.
4. Depois do primeiro deploy do frontend, atualize `FRONTEND_ORIGIN` no `render.yaml` (ou direto
   no painel do Render) com a URL real do GitHub Pages, para o CORS liberar as chamadas.

## Proximos passos sugeridos

- Extrair o payload do webhook e registrar as mensagens recebidas via `ConversationService`
- Criar um DTO de resposta para `WhatsAppCredential` que nao exponha o `accessToken`
- Adicionar autenticacao (hoje todas as rotas estao abertas) antes de expor publicamente
