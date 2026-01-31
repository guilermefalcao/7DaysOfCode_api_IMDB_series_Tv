# 🧪 Guia de Testes - Aula 6: Sistema de Favoritos

## 📋 Índice
1. [Testar no Navegador](#testar-no-navegador)
2. [Testar no Postman](#testar-no-postman)
3. [Testar com cURL](#testar-com-curl)
4. [Executar Testes Automatizados](#executar-testes-automatizados)
5. [Cenário Completo](#cenário-completo)

---

## 🌐 Testar no Navegador

### Passo 1: Iniciar Aplicação
```bash
mvnw.cmd spring-boot:run
```

### Passo 2: Preparar Dados (Buscar Filmes)
```
http://localhost:8080/api/movies/search?title=Matrix
```
✅ Isso cria filmes com IDs 1, 2, 3, 4

### Passo 3: Listar Favoritos (Vazio Inicialmente)
```
http://localhost:8080/api/favoritos
```
✅ Deve retornar: `[]`

### Passo 4: Verificar se é Favorito
```
http://localhost:8080/api/favoritos/check/1
```
✅ Deve retornar: `false`

### ⚠️ Limitação do Navegador
O navegador só faz requisições GET. Para POST, PUT e DELETE, use Postman ou cURL.

---

## 📮 Testar no Postman

### Configuração Inicial

1. Abra o Postman
2. Crie uma Collection: "7DaysOfCode - Aula 6 - Favoritos"
3. Adicione as requisições abaixo

---

### Requisição 1: POST - Adicionar Favorito

**Preparação:** Busque filmes primeiro
- Método: GET
- URL: `http://localhost:8080/api/movies/search?title=Matrix`
- Clique em "Send"

**Adicionar Favorito:**
- Método: **POST**
- URL: `http://localhost:8080/api/favoritos/1`
- Body: (vazio)
- Headers: (nenhum necessário)
- Clique em "Send"

**Resultado Esperado:**
- Status: `201 Created`
- Body: `"⭐ Filme adicionado aos favoritos: The Matrix"`

---

### Requisição 2: POST - Tentar Adicionar Duplicado

- Método: **POST**
- URL: `http://localhost:8080/api/favoritos/1`
- Clique em "Send" novamente

**Resultado Esperado:**
- Status: `409 Conflict`
- Body: `"⚠️ Filme já está nos favoritos: The Matrix"`

---

### Requisição 3: POST - Filme Inexistente

- Método: **POST**
- URL: `http://localhost:8080/api/favoritos/99999`

**Resultado Esperado:**
- Status: `404 Not Found`
- Body: `"❌ Filme não encontrado com ID: 99999"`

---

### Requisição 4: GET - Listar Todos os Favoritos

- Método: **GET**
- URL: `http://localhost:8080/api/favoritos`

**Resultado Esperado:**
- Status: `200 OK`
- Body:
```json
[
  {
    "id": 1,
    "title": "The Matrix",
    "urlImage": "https://...",
    "rating": "8.7",
    "year": "1999"
  }
]
```

---

### Requisição 5: GET - Buscar Favorito por ID

- Método: **GET**
- URL: `http://localhost:8080/api/favoritos/1`

**Resultado Esperado:**
- Status: `200 OK`
- Body: Dados do filme

---

### Requisição 6: GET - Verificar se é Favorito

- Método: **GET**
- URL: `http://localhost:8080/api/favoritos/check/1`

**Resultado Esperado:**
- Status: `200 OK`
- Body: `true`

---

### Requisição 7: DELETE - Remover Favorito

- Método: **DELETE**
- URL: `http://localhost:8080/api/favoritos/1`

**Resultado Esperado:**
- Status: `200 OK`
- Body: `"🗑️ Filme removido dos favoritos"`

---

### Requisição 8: DELETE - Remover Todos

**Preparação:** Adicione alguns favoritos primeiro

- Método: **DELETE**
- URL: `http://localhost:8080/api/favoritos`

**Resultado Esperado:**
- Status: `200 OK`
- Body: `"🗑️ X favoritos removidos"`

---

### Requisição 9: PUT - Substituir Lista de Favoritos

- Método: **PUT**
- URL: `http://localhost:8080/api/favoritos`
- Headers: `Content-Type: application/json`
- Body (raw JSON):
```json
[1, 2, 3]
```

**Resultado Esperado:**
- Status: `200 OK`
- Body: `"🔄 Lista de favoritos atualizada: 3 adicionados, 0 não encontrados"`

---

## 💻 Testar com cURL

### Windows PowerShell

```powershell
# 1. Buscar filmes (preparação)
Invoke-WebRequest -Uri "http://localhost:8080/api/movies/search?title=Matrix"

# 2. POST - Adicionar favorito
Invoke-WebRequest -Uri "http://localhost:8080/api/favoritos/1" -Method POST

# 3. GET - Listar favoritos
Invoke-WebRequest -Uri "http://localhost:8080/api/favoritos" | Select-Object -Expand Content

# 4. GET - Buscar por ID
Invoke-WebRequest -Uri "http://localhost:8080/api/favoritos/1" | Select-Object -Expand Content

# 5. GET - Verificar se é favorito
Invoke-WebRequest -Uri "http://localhost:8080/api/favoritos/check/1" | Select-Object -Expand Content

# 6. DELETE - Remover favorito
Invoke-WebRequest -Uri "http://localhost:8080/api/favoritos/1" -Method DELETE

# 7. DELETE - Remover todos
Invoke-WebRequest -Uri "http://localhost:8080/api/favoritos" -Method DELETE

# 8. PUT - Substituir lista
$body = '[1,2,3]'
Invoke-WebRequest -Uri "http://localhost:8080/api/favoritos" -Method PUT -Body $body -ContentType "application/json"
```

### Git Bash / Linux / macOS

```bash
# 1. Buscar filmes (preparação)
curl "http://localhost:8080/api/movies/search?title=Matrix"

# 2. POST - Adicionar favorito
curl -X POST http://localhost:8080/api/favoritos/1

# 3. GET - Listar favoritos
curl http://localhost:8080/api/favoritos

# 4. GET - Buscar por ID
curl http://localhost:8080/api/favoritos/1

# 5. GET - Verificar se é favorito
curl http://localhost:8080/api/favoritos/check/1

# 6. DELETE - Remover favorito
curl -X DELETE http://localhost:8080/api/favoritos/1

# 7. DELETE - Remover todos
curl -X DELETE http://localhost:8080/api/favoritos

# 8. PUT - Substituir lista
curl -X PUT http://localhost:8080/api/favoritos \
  -H "Content-Type: application/json" \
  -d '[1,2,3]'
```

---

## 🧪 Executar Testes Automatizados

### Executar Todos os Testes

```bash
mvnw.cmd test
```

### Executar Apenas Testes de Favoritos

```bash
mvnw.cmd test -Dtest=FavoritoControllerTests
```

### Resultado Esperado

```
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### Testes Implementados

1. ✅ `shouldAddFavoritoSuccessfully` - Adicionar favorito
2. ✅ `shouldReturnConflictWhenAddingDuplicateFavorito` - Duplicata
3. ✅ `shouldReturnNotFoundWhenAddingNonExistentMovie` - Filme inexistente
4. ✅ `shouldListAllFavoritos` - Listar todos
5. ✅ `shouldGetFavoritoById` - Buscar por ID
6. ✅ `shouldCheckIfIsFavorito` - Verificar favorito
7. ✅ `shouldRemoveFavorito` - Remover favorito
8. ✅ `shouldRemoveAllFavoritos` - Remover todos
9. ✅ `shouldReplaceFavoritosList` - Substituir lista
10. ✅ `shouldPerformCompleteFlow` - Fluxo completo

---

## 🎯 Cenário Completo de Teste

Execute este cenário do início ao fim:

```bash
# Passo 1: Buscar filmes
curl "http://localhost:8080/api/movies/search?title=Matrix"
curl "http://localhost:8080/api/movies/search?title=Inception"

# Passo 2: Verificar que favoritos está vazio
curl http://localhost:8080/api/favoritos
# Esperado: []

# Passo 3: Adicionar favoritos
curl -X POST http://localhost:8080/api/favoritos/1
curl -X POST http://localhost:8080/api/favoritos/2
curl -X POST http://localhost:8080/api/favoritos/5

# Passo 4: Listar favoritos
curl http://localhost:8080/api/favoritos
# Esperado: Array com 3 filmes

# Passo 5: Verificar se é favorito
curl http://localhost:8080/api/favoritos/check/1
# Esperado: true

curl http://localhost:8080/api/favoritos/check/99
# Esperado: false

# Passo 6: Buscar favorito específico
curl http://localhost:8080/api/favoritos/1
# Esperado: Dados do filme

# Passo 7: Tentar adicionar duplicado
curl -X POST http://localhost:8080/api/favoritos/1
# Esperado: 409 Conflict

# Passo 8: Remover um favorito
curl -X DELETE http://localhost:8080/api/favoritos/2

# Passo 9: Verificar que foi removido
curl http://localhost:8080/api/favoritos
# Esperado: Array com 2 filmes (1 e 5)

# Passo 10: Substituir lista completa
curl -X PUT http://localhost:8080/api/favoritos \
  -H "Content-Type: application/json" \
  -d '[3,4,6]'

# Passo 11: Verificar nova lista
curl http://localhost:8080/api/favoritos
# Esperado: Array com 3 filmes (3, 4 e 6)

# Passo 12: Limpar todos
curl -X DELETE http://localhost:8080/api/favoritos

# Passo 13: Verificar que está vazio
curl http://localhost:8080/api/favoritos
# Esperado: []
```

---

## 📊 Observar Logs no Console

### Ao adicionar favorito:
```
📥 POST /api/favoritos/1
⭐ Filme adicionado aos favoritos: The Matrix
```

### Ao tentar adicionar duplicado:
```
📥 POST /api/favoritos/1
⚠️ Filme já está nos favoritos: The Matrix
```

### Ao listar favoritos:
```
📋 GET /api/favoritos
⭐ Total de favoritos: 3
```

### Ao verificar favorito:
```
✓ GET /api/favoritos/check/1
⭐ É favorito
```

### Ao remover favorito:
```
🗑️ DELETE /api/favoritos/1
🗑️ Filme removido dos favoritos: ID=1
```

### Ao substituir lista:
```
🔄 PUT /api/favoritos
   IDs recebidos: [1, 2, 3]
🗑️ Todos os favoritos foram removidos
⭐ Filme adicionado aos favoritos: The Matrix
⭐ Filme adicionado aos favoritos: The Matrix Reloaded
⭐ Filme adicionado aos favoritos: The Matrix Revolutions
```

---

## ✅ Validações Esperadas

### Teste 1: Status HTTP Corretos
- POST sucesso: `201 Created`
- POST duplicado: `409 Conflict`
- POST não encontrado: `404 Not Found`
- GET sucesso: `200 OK`
- DELETE sucesso: `200 OK`
- PUT sucesso: `200 OK`

### Teste 2: Mensagens Corretas
- Sucesso: Contém "adicionado", "removido", "atualizada"
- Erro: Contém "não encontrado", "já está"

### Teste 3: Dados Persistem
- Adicionar favorito → Listar → Deve aparecer
- Remover favorito → Listar → Não deve aparecer

### Teste 4: Validações
- Não permite duplicados
- Não permite adicionar filme inexistente
- Remove corretamente

---

## 🐛 Troubleshooting

### Erro: "Filme não encontrado"
**Causa:** Filme não existe na lista geral
**Solução:** Busque filmes primeiro com `/api/movies/search?title=Matrix`

### Erro: "Já está nos favoritos"
**Causa:** Tentando adicionar duplicado
**Solução:** Normal, é a validação funcionando

### Erro: 404 ao buscar favorito
**Causa:** Favorito não existe
**Solução:** Adicione o favorito primeiro com POST

### Testes falhando
**Causa:** Aplicação não está rodando ou porta diferente
**Solução:** Verifique se aplicação está em `localhost:8080`

---

## 📝 Checklist de Testes

- [ ] POST adiciona favorito com sucesso
- [ ] POST retorna 409 para duplicado
- [ ] POST retorna 404 para filme inexistente
- [ ] GET lista todos os favoritos
- [ ] GET busca favorito por ID
- [ ] GET verifica se é favorito (true/false)
- [ ] DELETE remove favorito específico
- [ ] DELETE remove todos os favoritos
- [ ] PUT substitui lista de favoritos
- [ ] Logs aparecem no console
- [ ] Testes automatizados passam

---

## 🎉 Teste Final

Execute os testes automatizados:

```bash
mvnw.cmd test -Dtest=FavoritoControllerTests
```

✅ **Se todos os 10 testes passarem, a implementação está correta!**

---

**Dúvidas?** Consulte os comentários no código ou o README.md!
