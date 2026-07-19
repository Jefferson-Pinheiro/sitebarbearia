# CRM WhatsApp — Frontend

Interface web (React + Vite) para visualizar leads e o funil de vendas do CRM.

## Rodando localmente

```bash
npm install
npm run dev
```

Abre em `http://localhost:5173`. Por padrão, aponta para o backend em `http://localhost:8080/api`.
Para mudar, crie um `.env` com:

```
VITE_API_BASE_URL=http://localhost:8080/api
```

## Telas

- `/` — lista de leads (nome, telefone, origem, status)
- `/funil` — leads agrupados em colunas por etapa (Novo, Contato feito, Negociando, Fechado, Perdido)

## Pré-requisito

O backend precisa estar rodando e com CORS liberado para `http://localhost:5173`
(ver `CorsConfig` no projeto backend).
