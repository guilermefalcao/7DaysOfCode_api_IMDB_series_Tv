# 🧪 Guia Completo de Testes - Aula 5

## 📋 Índice
1. [Testes no Navegador](#testes-no-navegador)
2. [Testes no Postman](#testes-no-postman)
3. [Testes com cURL](#testes-com-curl)
4. [Observar Logs](#observar-logs)
5. [Validações Esperadas](#validações-esperadas)

---

## 🌐 Testes no Navegador

### Passo 1: Iniciar a Aplicação
```bash
mvnw.cmd spring-boot:run
```

Aguarde a mensagem: `Started ImdbApiApplication in X seconds`

### Passo 2: Buscar Filmes (Salva Automaticamente)

Abra no navegador:

1. **Buscar Matrix:**
   ```
   http://localhost:8080/api/movies/search?title=Matrix
   ```
   ✅ Deve retornar JSON com lista de filmes
   ✅ Filmes são salvos automaticamente em memória

2. **Buscar Inception:**
   ```
   http://localhost:8080/api/movies/search?title=Inception
   ```

3. **Buscar Interstellar:**
   ```
   http://localhost:8080/api/movies/search?title=Interstellar
   ```

### Passo 3: Consultar Lista em Memória

1. **Ver TODOS os filmes salvos:**
   ```
   http://localhost:8080/api/movies/memory
   ```
   ✅ Deve mostrar todos os filmes buscados anteriormente
   ✅ Cada filme deve ter um ID único

2. **Filtrar por "Matrix":**
   ```
   http://localhost:8080/api/movies/memory/filter?title=Matrix
   ```
   ✅ Deve mostrar apenas filmes com "Matrix" no título

3. **Filtrar por "Inception":**
   ```
   http://localhost:8080/api/movies/memory/filter?title=Inception
   ```

### Passo 4: Visualizar em HTML

1. **Ver todos em HTML:**
   ```
   http://localhost:8080/api/movies/memory/html
   ```
   ✅ Deve mostrar página bonita com Bootstrap
   ✅ Cards com todos os filmes

2. **Ver filtrados em HTML:**
   ```
   http://localhost:8080/api/movies/memory/html?title=Matrix
   ```
   ✅ Deve mostrar apenas filmes com "Matrix"

### Passo 5: Limpar Memória

```
http://localhost:8080/api/movies/memory/clear
```
✅ Deve mostrar: "🗑️ X filmes removidos da memória"

Verificar que está vazio:
```
http://localhost:8080/api/movies/memory
```
✅ Deve retornar: `[]`

---

## 📮 Testes no Postman

### Configuração Inicial

1. Abra o Postman
2. Crie uma nova Collection: "7DaysOfCode - Aula 5"
3. Adicione as requisições abaixo

### Requisição 1: Buscar Matrix
- **Método:** GET
- **URL:** `http://localhost:8080/api/movies/search?title=Matrix`
- **Headers:** (nenhum necessário)
- **Resultado Esperado:**
  ```json
  {
    "movies": [
      {
        "id": 1,
        "title": "The Matrix",
        "urlImage": "https://...",
        "rating": "8.7",
        "year": "1999"
      },
      ...
    ],
    "totalResults": "4",
    "count": 4
  }
  ```

### Requisição 2: Ver Memória
- **Método:** GET
- **URL:** `http://localhost:8080/api/movies/memory`
- **Resultado Esperado:**
  ```json
  [
    {
      "id": 1,
      "title": "The Matrix",
      "urlImage": "https://...",
      "rating": "8.7",
      "year": "1999"
    },
    ...
  ]
  ```

### Requisição 3: Filtrar por Título
- **Método:** GET
- **URL:** `http://localhost:8080/api/movies/memory/filter?title=Matrix`
- **Params:**
  - Key: `title`
  - Value: `Matrix`
- **Resultado Esperado:** Apenas filmes com "Matrix" no título

### Requisição 4: Buscar Inception
- **Método:** GET
- **URL:** `http://localhost:8080/api/movies/search?title=Inception`

### Requisição 5: Filtrar Inception
- **Método:** GET
- **URL:** `http://localhost:8080/api/movies/memory/filter?title=Inception`

### Requisição 6: HTML Todos
- **Método:** GET
- **URL:** `http://localhost:8080/api/movies/memory/html`
- **Resultado:** Página HTML (visualizar no navegador)

### Requisição 7: HTML Filtrado
- **Método:** GET
- **URL:** `http://localhost:8080/api/movies/memory/html?title=Matrix`

### Requisição 8: Limpar Memória
- **Método:** GET
- **URL:** `http://localhost:8080/api/movies/memory/clear`
- **Resultado Esperado:** `"🗑️ X filmes removidos da memória"`

### Requisição 9: Verificar Vazio
- **Método:** GET
- **URL:** `http://localhost:8080/api/movies/memory`
- **Resultado Esperado:** `[]`

---

## 💻 Testes com cURL

### Windows PowerShell

```powershell
# 1. Buscar Matrix
Invoke-WebRequest -Uri "http://localhost:8080/api/movies/search?title=Matrix" | Select-Object -Expand Content

# 2. Ver memória
Invoke-WebRequest -Uri "http://localhost:8080/api/movies/memory" | Select-Object -Expand Content

# 3. Filtrar Matrix
Invoke-WebRequest -Uri "http://localhost:8080/api/movies/memory/filter?title=Matrix" | Select-Object -Expand Content

# 4. Buscar Inception
Invoke-WebRequest -Uri "http://localhost:8080/api/movies/search?title=Inception" | Select-Object -Expand Content

# 5. Filtrar Inception
Invoke-WebRequest -Uri "http://localhost:8080/api/movies/memory/filter?title=Inception" | Select-Object -Expand Content

# 6. Limpar memória
Invoke-WebRequest -Uri "http://localhost:8080/api/movies/memory/clear" | Select-Object -Expand Content

# 7. Verificar vazio
Invoke-WebRequest -Uri "http://localhost:8080/api/movies/memory" | Select-Object -Expand Content
```

### Git Bash / Linux / macOS

```bash
# 1. Buscar Matrix
curl "http://localhost:8080/api/movies/search?title=Matrix"

# 2. Ver memória
curl http://localhost:8080/api/movies/memory

# 3. Filtrar Matrix
curl "http://localhost:8080/api/movies/memory/filter?title=Matrix"

# 4. Buscar Inception
curl "http://localhost:8080/api/movies/search?title=Inception"

# 5. Filtrar Inception
curl "http://localhost:8080/api/movies/memory/filter?title=Inception"

# 6. Limpar memória
curl http://localhost:8080/api/movies/memory/clear

# 7. Verificar vazio
curl http://localhost:8080/api/movies/memory
```

### cURL com formatação JSON (jq)

```bash
# Instalar jq (se não tiver)
# Windows: choco install jq
# Linux: sudo apt install jq
# macOS: brew install jq

# Usar com formatação
curl http://localhost:8080/api/movies/memory | jq .
curl "http://localhost:8080/api/movies/memory/filter?title=Matrix" | jq .
```

---

## 📊 Observar Logs

### Console da Aplicação

Ao executar os testes, observe o console da aplicação:

#### Ao buscar filmes:
```
✅ Busca por título: Matrix
   Total de resultados: 4
   Filmes encontrados: 4
💾 Filme salvo: ID=1, Título=The Matrix
💾 Filme salvo: ID=2, Título=The Matrix Reloaded
💾 Filme salvo: ID=3, Título=The Matrix Revolutions
💾 Filme salvo: ID=4, Título=The Matrix Revisited
💾 Total em memória: 4
```

#### Ao consultar memória:
```
💾 Total de filmes em memória: 4
```

#### Ao filtrar:
```
🔍 Filtro aplicado: Matrix
   Filmes encontrados: 4
```

#### Ao limpar:
```
🗑️ Todos os filmes foram removidos da memória
```

---

## ✅ Validações Esperadas

### Teste 1: IDs Incrementais

**Objetivo:** Verificar que IDs são gerados sequencialmente

**Passos:**
1. Limpar memória
2. Buscar "Matrix" (deve gerar IDs 1, 2, 3, 4)
3. Buscar "Inception" (deve gerar IDs 5, 6, 7, ...)
4. Ver memória completa

**Validação:**
```json
[
  {"id": 1, "title": "The Matrix", ...},
  {"id": 2, "title": "The Matrix Reloaded", ...},
  {"id": 3, "title": "The Matrix Revolutions", ...},
  {"id": 4, "title": "The Matrix Revisited", ...},
  {"id": 5, "title": "Inception", ...},
  {"id": 6, "title": "Inception: The Cobol Job", ...}
]
```

### Teste 2: Filtro Case-Insensitive

**Objetivo:** Verificar que filtro funciona independente de maiúsculas/minúsculas

**Passos:**
1. Buscar "Matrix"
2. Filtrar com "matrix" (minúsculo)
3. Filtrar com "MATRIX" (maiúsculo)
4. Filtrar com "MaTrIx" (misto)

**Validação:** Todos devem retornar os mesmos filmes

### Teste 3: Filtro Parcial

**Objetivo:** Verificar que filtro busca substring

**Passos:**
1. Buscar "Matrix"
2. Filtrar com "Mat"
3. Filtrar com "trix"
4. Filtrar com "Reloaded"

**Validação:**
- "Mat" → Todos os Matrix
- "trix" → Todos os Matrix
- "Reloaded" → Apenas "The Matrix Reloaded"

### Teste 4: Persistência em Memória

**Objetivo:** Verificar que dados persistem entre requisições

**Passos:**
1. Buscar "Matrix"
2. Buscar "Inception"
3. Ver memória (deve ter ambos)
4. Reiniciar aplicação
5. Ver memória (deve estar vazio)

**Validação:**
- Antes de reiniciar: Lista completa
- Depois de reiniciar: `[]`

### Teste 5: HTML com Filtro Opcional

**Objetivo:** Verificar parâmetro opcional

**Passos:**
1. Buscar "Matrix" e "Inception"
2. Acessar `/memory/html` (sem filtro)
3. Acessar `/memory/html?title=Matrix` (com filtro)

**Validação:**
- Sem filtro: Mostra todos
- Com filtro: Mostra apenas filtrados

---

## 🎯 Cenário de Teste Completo

Execute este cenário do início ao fim:

```bash
# 1. Limpar memória
curl http://localhost:8080/api/movies/memory/clear

# 2. Verificar que está vazio
curl http://localhost:8080/api/movies/memory
# Esperado: []

# 3. Buscar Matrix
curl "http://localhost:8080/api/movies/search?title=Matrix"
# Esperado: JSON com 4 filmes (IDs 1-4)

# 4. Buscar Inception
curl "http://localhost:8080/api/movies/search?title=Inception"
# Esperado: JSON com filmes (IDs 5+)

# 5. Buscar Interstellar
curl "http://localhost:8080/api/movies/search?title=Interstellar"
# Esperado: JSON com filmes (IDs continuam)

# 6. Ver todos em memória
curl http://localhost:8080/api/movies/memory
# Esperado: Todos os filmes buscados

# 7. Filtrar Matrix
curl "http://localhost:8080/api/movies/memory/filter?title=Matrix"
# Esperado: Apenas filmes com "Matrix"

# 8. Filtrar Inception
curl "http://localhost:8080/api/movies/memory/filter?title=Inception"
# Esperado: Apenas filmes com "Inception"

# 9. Filtrar "Inter"
curl "http://localhost:8080/api/movies/memory/filter?title=Inter"
# Esperado: Apenas filmes com "Inter" (Interstellar)

# 10. Limpar memória
curl http://localhost:8080/api/movies/memory/clear
# Esperado: "🗑️ X filmes removidos da memória"

# 11. Verificar que está vazio novamente
curl http://localhost:8080/api/movies/memory
# Esperado: []
```

---

## 🐛 Troubleshooting

### Erro: "Connection refused"
**Causa:** Aplicação não está rodando
**Solução:** Execute `mvnw.cmd spring-boot:run`

### Erro: "API Key inválida"
**Causa:** OMDB_API_KEY não configurada
**Solução:** Configure a variável de ambiente

### Erro: IDs duplicados
**Causa:** Não deveria acontecer (AtomicLong é thread-safe)
**Solução:** Reinicie a aplicação

### Memória não limpa
**Causa:** Endpoint não foi chamado
**Solução:** Chame `/memory/clear`

### Filtro não funciona
**Causa:** Parâmetro `title` não foi enviado
**Solução:** Adicione `?title=valor` na URL

---

## 📝 Checklist de Testes

- [ ] Aplicação inicia sem erros
- [ ] Buscar filmes retorna JSON válido
- [ ] Filmes são salvos automaticamente em memória
- [ ] IDs são gerados sequencialmente
- [ ] Endpoint `/memory` retorna lista completa
- [ ] Filtro por título funciona
- [ ] Filtro é case-insensitive
- [ ] Filtro busca substring
- [ ] HTML é gerado corretamente
- [ ] HTML com filtro funciona
- [ ] Limpar memória funciona
- [ ] Logs aparecem no console
- [ ] Memória é limpa ao reiniciar

---

## 🎉 Teste Final: Demonstração Completa

Execute este script para demonstrar todas as funcionalidades:

```bash
echo "=== TESTE COMPLETO - AULA 5 ==="
echo ""

echo "1. Limpando memória..."
curl -s http://localhost:8080/api/movies/memory/clear
echo ""

echo "2. Buscando Matrix..."
curl -s "http://localhost:8080/api/movies/search?title=Matrix" > /dev/null
echo "✅ Matrix salvo"

echo "3. Buscando Inception..."
curl -s "http://localhost:8080/api/movies/search?title=Inception" > /dev/null
echo "✅ Inception salvo"

echo "4. Consultando memória..."
curl -s http://localhost:8080/api/movies/memory | jq 'length'
echo "filmes em memória"

echo "5. Filtrando Matrix..."
curl -s "http://localhost:8080/api/movies/memory/filter?title=Matrix" | jq 'length'
echo "filmes com 'Matrix'"

echo "6. Filtrando Inception..."
curl -s "http://localhost:8080/api/movies/memory/filter?title=Inception" | jq 'length'
echo "filmes com 'Inception'"

echo ""
echo "=== TESTE COMPLETO ✅ ==="
```

---

## 📚 Próximos Passos

Após validar todos os testes:

1. ✅ Commitar código no Git
2. ✅ Fazer push para GitHub
3. ✅ Criar tag `aula5-completa`
4. ✅ Atualizar README.md
5. ✅ Preparar para Aula 6

---

**Dúvidas?** Consulte o README_AULAS.md para conceitos detalhados!
