# 📋 Exemplos de Respostas - Aula 5

## 🎯 Respostas Esperadas dos Endpoints

---

## 1️⃣ GET /api/movies/search?title=Matrix

### Request:
```
GET http://localhost:8080/api/movies/search?title=Matrix
```

### Response (200 OK):
```json
{
  "movies": [
    {
      "id": 1,
      "title": "The Matrix",
      "urlImage": "https://m.media-amazon.com/images/M/MV5BNzQzOTk3OTAtNDQ0Zi00ZTVkLWI0MTEtMDllZjNkYzNjNTc4L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg",
      "rating": "8.7",
      "year": "1999"
    },
    {
      "id": 2,
      "title": "The Matrix Reloaded",
      "urlImage": "https://m.media-amazon.com/images/M/MV5BODE0MzZhZTgtYzkwYi00YmI5LThlZWYtOWRmNWE5ODk0NzMxXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg",
      "rating": "7.2",
      "year": "2003"
    },
    {
      "id": 3,
      "title": "The Matrix Revolutions",
      "urlImage": "https://m.media-amazon.com/images/M/MV5BNzNlZTZjMDctZjYwNi00NzljLWIwN2QtZWZmYmJiYzQ0MTk2XkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg",
      "rating": "6.7",
      "year": "2003"
    },
    {
      "id": 4,
      "title": "The Matrix Revisited",
      "urlImage": "https://m.media-amazon.com/images/M/MV5BMTIzMTA4NDI4NF5BMl5BanBnXkFtZTYwNjg5Nzg4._V1_SX300.jpg",
      "rating": "6.8",
      "year": "2001"
    }
  ],
  "totalResults": "4",
  "count": 4
}
```

### Console Log:
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

---

## 2️⃣ GET /api/movies/memory

### Request:
```
GET http://localhost:8080/api/movies/memory
```

### Response (200 OK):
```json
[
  {
    "id": 1,
    "title": "The Matrix",
    "urlImage": "https://m.media-amazon.com/images/M/MV5BNzQzOTk3OTAtNDQ0Zi00ZTVkLWI0MTEtMDllZjNkYzNjNTc4L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg",
    "rating": "8.7",
    "year": "1999"
  },
  {
    "id": 2,
    "title": "The Matrix Reloaded",
    "urlImage": "https://m.media-amazon.com/images/M/MV5BODE0MzZhZTgtYzkwYi00YmI5LThlZWYtOWRmNWE5ODk0NzMxXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg",
    "rating": "7.2",
    "year": "2003"
  },
  {
    "id": 3,
    "title": "The Matrix Revolutions",
    "urlImage": "https://m.media-amazon.com/images/M/MV5BNzNlZTZjMDctZjYwNi00NzljLWIwN2QtZWZmYmJiYzQ0MTk2XkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg",
    "rating": "6.7",
    "year": "2003"
  },
  {
    "id": 4,
    "title": "The Matrix Revisited",
    "urlImage": "https://m.media-amazon.com/images/M/MV5BMTIzMTA4NDI4NF5BMl5BanBnXkFtZTYwNjg5Nzg4._V1_SX300.jpg",
    "rating": "6.8",
    "year": "2001"
  }
]
```

### Console Log:
```
💾 Total de filmes em memória: 4
```

---

## 3️⃣ GET /api/movies/memory/filter?title=Matrix

### Request:
```
GET http://localhost:8080/api/movies/memory/filter?title=Matrix
```

### Response (200 OK):
```json
[
  {
    "id": 1,
    "title": "The Matrix",
    "urlImage": "https://m.media-amazon.com/images/M/MV5BNzQzOTk3OTAtNDQ0Zi00ZTVkLWI0MTEtMDllZjNkYzNjNTc4L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg",
    "rating": "8.7",
    "year": "1999"
  },
  {
    "id": 2,
    "title": "The Matrix Reloaded",
    "urlImage": "https://m.media-amazon.com/images/M/MV5BODE0MzZhZTgtYzkwYi00YmI5LThlZWYtOWRmNWE5ODk0NzMxXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg",
    "rating": "7.2",
    "year": "2003"
  },
  {
    "id": 3,
    "title": "The Matrix Revolutions",
    "urlImage": "https://m.media-amazon.com/images/M/MV5BNzNlZTZjMDctZjYwNi00NzljLWIwN2QtZWZmYmJiYzQ0MTk2XkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg",
    "rating": "6.7",
    "year": "2003"
  },
  {
    "id": 4,
    "title": "The Matrix Revisited",
    "urlImage": "https://m.media-amazon.com/images/M/MV5BMTIzMTA4NDI4NF5BMl5BanBnXkFtZTYwNjg5Nzg4._V1_SX300.jpg",
    "rating": "6.8",
    "year": "2001"
  }
]
```

### Console Log:
```
🔍 Filtro aplicado: Matrix
   Filmes encontrados: 4
```

---

## 4️⃣ GET /api/movies/memory/filter?title=Reloaded

### Request:
```
GET http://localhost:8080/api/movies/memory/filter?title=Reloaded
```

### Response (200 OK):
```json
[
  {
    "id": 2,
    "title": "The Matrix Reloaded",
    "urlImage": "https://m.media-amazon.com/images/M/MV5BODE0MzZhZTgtYzkwYi00YmI5LThlZWYtOWRmNWE5ODk0NzMxXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg",
    "rating": "7.2",
    "year": "2003"
  }
]
```

### Console Log:
```
🔍 Filtro aplicado: Reloaded
   Filmes encontrados: 1
```

---

## 5️⃣ GET /api/movies/memory/html

### Request:
```
GET http://localhost:8080/api/movies/memory/html
```

### Response (200 OK - HTML):
```html
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Filmes em Memória - IMDB API</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
        .movie-card { transition: transform 0.3s; }
        .movie-card:hover { transform: translateY(-10px); }
        .rating-badge { position: absolute; top: 10px; right: 10px; }
    </style>
</head>
<body>
    <div class="container py-5">
        <h1 class="text-center text-white mb-5">🎬 Filmes em Memória</h1>
        <div class="row row-cols-1 row-cols-md-3 g-4">
            <div class="col">
                <div class="card movie-card h-100">
                    <img src="https://..." class="card-img-top" alt="The Matrix">
                    <span class="badge bg-warning rating-badge">⭐ 8.7</span>
                    <div class="card-body">
                        <h5 class="card-title">The Matrix</h5>
                        <p class="card-text text-muted">1999</p>
                    </div>
                </div>
            </div>
            <!-- Mais cards... -->
        </div>
    </div>
</body>
</html>
```

---

## 6️⃣ GET /api/movies/memory/html?title=Matrix

### Request:
```
GET http://localhost:8080/api/movies/memory/html?title=Matrix
```

### Response (200 OK - HTML):
Mesma estrutura HTML, mas apenas com filmes filtrados por "Matrix"

---

## 7️⃣ GET /api/movies/memory/clear

### Request:
```
GET http://localhost:8080/api/movies/memory/clear
```

### Response (200 OK):
```
🗑️ 4 filmes removidos da memória
```

### Console Log:
```
🗑️ Todos os filmes foram removidos da memória
```

---

## 8️⃣ GET /api/movies/memory (após clear)

### Request:
```
GET http://localhost:8080/api/movies/memory
```

### Response (200 OK):
```json
[]
```

### Console Log:
```
💾 Total de filmes em memória: 0
```

---

## 9️⃣ GET /api/movies/tt0133093

### Request:
```
GET http://localhost:8080/api/movies/tt0133093
```

### Response (200 OK):
```json
{
  "id": 1,
  "title": "The Matrix",
  "urlImage": "https://m.media-amazon.com/images/M/MV5BNzQzOTk3OTAtNDQ0Zi00ZTVkLWI0MTEtMDllZjNkYzNjNTc4L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg",
  "rating": "8.7",
  "year": "1999"
}
```

### Console Log:
```
✅ Busca por ID: tt0133093
   Filme: The Matrix (1999)
```

---

## 🔟 GET /api/movies/html?title=Matrix

### Request:
```
GET http://localhost:8080/api/movies/html?title=Matrix
```

### Response (200 OK - HTML):
Página HTML com Bootstrap mostrando filmes buscados da API OMDb

---

## 🧪 Cenário Completo: Múltiplas Buscas

### Passo 1: Buscar Matrix
```bash
curl "http://localhost:8080/api/movies/search?title=Matrix"
```
**Resultado:** 4 filmes (IDs 1-4)

### Passo 2: Buscar Inception
```bash
curl "http://localhost:8080/api/movies/search?title=Inception"
```
**Resultado:** ~10 filmes (IDs 5-14)

### Passo 3: Buscar Interstellar
```bash
curl "http://localhost:8080/api/movies/search?title=Interstellar"
```
**Resultado:** ~5 filmes (IDs 15-19)

### Passo 4: Ver Todos
```bash
curl http://localhost:8080/api/movies/memory
```
**Resultado:** ~19 filmes (todos os buscados)

### Passo 5: Filtrar Matrix
```bash
curl "http://localhost:8080/api/movies/memory/filter?title=Matrix"
```
**Resultado:** 4 filmes (apenas Matrix)

### Passo 6: Filtrar Inception
```bash
curl "http://localhost:8080/api/movies/memory/filter?title=Inception"
```
**Resultado:** ~10 filmes (apenas Inception)

### Passo 7: Filtrar "Inter"
```bash
curl "http://localhost:8080/api/movies/memory/filter?title=Inter"
```
**Resultado:** ~5 filmes (apenas Interstellar)

---

## 📊 Validações de IDs

### Cenário: IDs Incrementais

```bash
# 1. Limpar
curl http://localhost:8080/api/movies/memory/clear

# 2. Buscar Matrix (IDs 1-4)
curl "http://localhost:8080/api/movies/search?title=Matrix"

# 3. Buscar Inception (IDs 5+)
curl "http://localhost:8080/api/movies/search?title=Inception"

# 4. Ver memória
curl http://localhost:8080/api/movies/memory
```

**Resultado Esperado:**
```json
[
  {"id": 1, "title": "The Matrix", ...},
  {"id": 2, "title": "The Matrix Reloaded", ...},
  {"id": 3, "title": "The Matrix Revolutions", ...},
  {"id": 4, "title": "The Matrix Revisited", ...},
  {"id": 5, "title": "Inception", ...},
  {"id": 6, "title": "Inception: The Cobol Job", ...},
  ...
]
```

✅ **IDs são sequenciais e únicos**

---

## 🔍 Validações de Filtro

### Cenário: Case-Insensitive

```bash
# Buscar Matrix
curl "http://localhost:8080/api/movies/search?title=Matrix"

# Filtrar com diferentes cases
curl "http://localhost:8080/api/movies/memory/filter?title=matrix"
curl "http://localhost:8080/api/movies/memory/filter?title=MATRIX"
curl "http://localhost:8080/api/movies/memory/filter?title=MaTrIx"
```

✅ **Todos retornam os mesmos filmes**

### Cenário: Substring

```bash
# Filtrar com substring
curl "http://localhost:8080/api/movies/memory/filter?title=Mat"
curl "http://localhost:8080/api/movies/memory/filter?title=trix"
curl "http://localhost:8080/api/movies/memory/filter?title=Reloaded"
```

✅ **Busca qualquer parte do título**

---

## ❌ Casos de Erro

### Erro 1: API Key Inválida
```json
{
  "timestamp": "2024-01-15T10:30:00.000+00:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Error calling OMDb API",
  "path": "/api/movies/search"
}
```

### Erro 2: Título Não Encontrado
```json
{
  "movies": [],
  "totalResults": "0",
  "count": 0
}
```

### Erro 3: Parâmetro Faltando
```json
{
  "timestamp": "2024-01-15T10:30:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Required request parameter 'title' is not present",
  "path": "/api/movies/search"
}
```

---

## 📝 Notas Importantes

### IDs:
- ✅ Gerados automaticamente com AtomicLong
- ✅ Sequenciais (1, 2, 3, ...)
- ✅ Thread-safe
- ✅ Únicos por filme

### Filtros:
- ✅ Case-insensitive
- ✅ Busca substring
- ✅ Retorna lista vazia se não encontrar

### Memória:
- ⚠️ Dados perdidos ao reiniciar aplicação
- ⚠️ Não persiste em banco de dados
- ⚠️ Apenas para aprendizado

### HTML:
- ✅ Bootstrap 5
- ✅ Responsivo
- ✅ Cards com hover effect
- ✅ Badges com rating

---

## 🎯 Validação Final

Execute este teste completo e compare com as respostas esperadas:

```bash
# 1. Limpar
curl http://localhost:8080/api/movies/memory/clear
# Esperado: "🗑️ X filmes removidos da memória"

# 2. Verificar vazio
curl http://localhost:8080/api/movies/memory
# Esperado: []

# 3. Buscar Matrix
curl "http://localhost:8080/api/movies/search?title=Matrix"
# Esperado: JSON com 4 filmes (IDs 1-4)

# 4. Ver memória
curl http://localhost:8080/api/movies/memory
# Esperado: Array com 4 filmes

# 5. Filtrar
curl "http://localhost:8080/api/movies/memory/filter?title=Matrix"
# Esperado: Array com 4 filmes

# 6. Limpar
curl http://localhost:8080/api/movies/memory/clear
# Esperado: "🗑️ 4 filmes removidos da memória"
```

✅ **Se todas as respostas correspondem, a implementação está correta!**

---

**Dúvidas?** Consulte `GUIA_TESTES_AULA5.md` para mais detalhes!
