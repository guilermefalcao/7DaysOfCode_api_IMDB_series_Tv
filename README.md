# 7DaysOfCode - API IMDB

Projeto desenvolvido durante o desafio #7DaysOfCode da Alura - Dias 1, 2, 3, 4, 5 e 6 (COMPLETO).

## Descrição
API REST para consumir dados de filmes do IMDB usando a OMDb API (The Open Movie Database), com sistema completo de favoritos, filtros e testes de integração.

## Tecnologias Utilizadas
- Java 17
- Spring Boot 3.2.2
- Spring Web
- RestTemplate
- JUnit 5
- TestRestTemplate
- Maven

## Pré-requisitos
1. Java 17 ou superior instalado
2. Maven instalado
3. API Key da OMDb API (gratuita)

## Configuração do Ambiente

### 1. Obter API Key da OMDb
- Acesse: https://www.omdbapi.com/apikey.aspx
- Selecione "FREE! (1,000 daily limit)"
- Preencha seu email
- Verifique seu email e ative a chave
- Copie a API Key recebida

### 2. Configurar a API Key

**Opção 1 - Variável de Ambiente (Recomendado):**
```bash
# Windows PowerShell
$env:OMDB_API_KEY="SUA_CHAVE_AQUI"

# Windows CMD
set OMDB_API_KEY=SUA_CHAVE_AQUI
```

**Opção 2 - Arquivo Local:**
Edite o arquivo `src/main/resources/application.properties` e substitua `YOUR_API_KEY` pela sua chave:

```properties
omdb.api.key=SUA_CHAVE_AQUI
```

**IMPORTANTE:** Nunca commite sua API Key no repositório!

## Como Executar

```bash
./mvnw spring-boot:run
```

Ou no Windows:
```bash
mvnw.cmd spring-boot:run
```

## Como Executar os Testes

### Executar todos os testes:
```bash
mvnw test
```

### Executar testes no Windows:
```bash
mvnw.cmd test
```

### Testes Implementados:
- ✅ **contextLoads()** - Verifica se a aplicação inicializa
- ✅ **shouldReturnMoviesWhenSearchingByTitle()** - Testa busca por título
- ✅ **shouldReturnMovieWhenSearchingById()** - Testa busca por ID
- ✅ **shouldStartOnRandomPort()** - Verifica porta aleatória

## Como Testar

### Opção 1: Navegador

#### Buscar na API OMDb:
```
http://localhost:8080/api/movies/search?title=Matrix
http://localhost:8080/api/movies/tt0133093
http://localhost:8080/api/movies/html?title=Matrix
```

#### **NOVO (Dia 5 Parte 2):** Consultar lista em memória:
```
http://localhost:8080/api/movies/memory
http://localhost:8080/api/movies/memory/filter?title=Matrix
http://localhost:8080/api/movies/memory/html
http://localhost:8080/api/movies/memory/html?title=Matrix
http://localhost:8080/api/movies/memory/clear
```

### Opção 2: Postman

#### Testar busca na API:
1. **GET** `http://localhost:8080/api/movies/search?title=Matrix`
2. **GET** `http://localhost:8080/api/movies/tt0133093`

#### Testar lista em memória:
1. **GET** `http://localhost:8080/api/movies/memory` - Ver todos os filmes
2. **GET** `http://localhost:8080/api/movies/memory/filter?title=Matrix` - Filtrar
3. **GET** `http://localhost:8080/api/movies/memory/clear` - Limpar memória

### Opção 3: cURL
```bash
curl "http://localhost:8080/api/movies/search?title=Matrix"
curl http://localhost:8080/api/movies/tt0133093
```

### Opção 4: PowerShell
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/movies/search?title=Matrix"
Invoke-WebRequest -Uri http://localhost:8080/api/movies/tt0133093
```

## Estrutura do Projeto

```
src/main/java/com/imdb/api/
├── ImdbApiApplication.java      # Classe principal com Bean do RestTemplate
├── controller/
│   ├── MovieController.java     # Controller REST (refatorado - Dia 5)
│   └── FavoritoController.java  # Controller de Favoritos (NOVO - Dia 6)
├── client/
│   └── ImdbApiClient.java       # Cliente HTTP para OMDb API (Dia 5)
├── service/
│   └── MovieService.java        # Service para processar JSON e salvar em memória
├── repository/
│   ├── MovieRepository.java     # Repositório em memória (Dia 5)
│   └── FavoritoRepository.java  # Repositório de Favoritos (NOVO - Dia 6)
├── model/
│   ├── Movie.java               # Record que representa um filme (com ID)
│   └── MovieSearchResult.java   # Record com lista de filmes
└── generator/
    └── HTMLGenerator.java       # Gerador de HTML com Bootstrap

src/test/java/com/imdb/api/
├── ImdbApiApplicationTests.java # Testes de integração dos endpoints
└── FavoritoControllerTests.java # Testes de Favoritos (NOVO - Dia 6)

src/test/resources/
└── application-test.properties  # Configurações para testes
```

## Funcionamento

### **Aplicação Principal:**
1. **ImdbApiApplication**: Classe principal que configura o Bean do RestTemplate
   - `@SpringBootApplication`: Marca a classe como aplicação Spring Boot
   - `@Bean`: Cria e disponibiliza o RestTemplate para injeção de dependência

2. **MovieController**: Controller REST que expõe os endpoints
   - `@RestController`: Define a classe como controlador REST
   - `@Autowired`: Injeta o RestTemplate automaticamente
   - `@GetMapping("/search")`: Busca filmes por título
   - `@GetMapping("/{imdbId}")`: Busca filme por ID do IMDB
   - Usa `RestTemplate.getForEntity()` para fazer requisição HTTP
   - Imprime o JSON no console e retorna como resposta

### **Testes de Integração:**
3. **ImdbApiApplicationTests**: Classe de testes que valida os endpoints
   - `@SpringBootTest(webEnvironment = RANDOM_PORT)`: Sobe a aplicação em porta aleatória
   - `@LocalServerPort`: Injeta a porta onde a aplicação está rodando
   - `RestTemplate`: Cliente HTTP para fazer requisições nos testes
   - **Testa status 200 OK** e **conteúdo não vazio** nas respostas
   - **Valida JSON** da OMDb API nos endpoints

### **Segurança:**
4. **Variáveis de Ambiente**: Chave da API protegida
   - `${OMDB_API_KEY:fallback}`: Lê da variável de ambiente
   - `.env` no .gitignore
   - **Nunca commita chaves** no repositório

## Resposta Esperada

### Busca por título (search?title=Matrix):
```json
{
  "Search": [
    {
      "Title": "The Matrix",
      "Year": "1999",
      "imdbID": "tt0133093",
      "Type": "movie",
      "Poster": "https://..."
    }
  ],
  "totalResults": "4",
  "Response": "True"
}
```

### Busca por ID (tt0133093):
```json
{
  "Title": "The Matrix",
  "Year": "1999",
  "Rated": "R",
  "Released": "31 Mar 1999",
  "Runtime": "136 min",
  "Genre": "Action, Sci-Fi",
  "Director": "Lana Wachowski, Lilly Wachowski",
  "Actors": "Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss",
  "Plot": "A computer hacker learns...",
  "imdbRating": "8.7",
  "imdbID": "tt0133093"
}
```


```

## Autor
Guilherme Falcão

## Curso
Alura - #7DaysOfCode - Dias 1, 2, 3, 4, 5 e 6 (COMPLETO)

## Repositório
https://github.com/guilermefalcao/7DaysOfCode_api_IMDB_series_Tv

---

## 🆕 Endpoints Disponíveis (Completo)

### Buscar na API OMDb:
| Endpoint | Método | Retorno | Descrição |
|----------|--------|---------|-----------|
| `/api/movies/search?title=Matrix` | GET | JSON | Lista de filmes da API |
| `/api/movies/{imdbId}` | GET | JSON | Detalhes do filme |
| `/api/movies/html?title=Matrix` | GET | HTML | Página web com filmes |
| `/api/movies/raw/search?title=Matrix` | GET | JSON | JSON bruto (legado) |

### Consultar lista em memória:
| Endpoint | Método | Retorno | Descrição |
|----------|--------|---------|-----------|
| `/api/movies/memory` | GET | JSON | Todos os filmes em memória |
| `/api/movies/memory/filter?title=Matrix` | GET | JSON | Filtrar filmes por título |
| `/api/movies/memory/html` | GET | HTML | Visualizar todos em HTML |
| `/api/movies/memory/html?title=Matrix` | GET | HTML | Visualizar filtrados em HTML |
| `/api/movies/memory/clear` | GET | String | Limpar lista em memória |

### **NOVO (Dia 6):** Sistema de Favoritos:
| Endpoint | Método | Retorno | Descrição |
|----------|--------|---------|-----------|
| `/api/favoritos/{id}` | POST | String | Adicionar filme aos favoritos |
| `/api/favoritos` | GET | JSON | Listar todos os favoritos |
| `/api/favoritos/{id}` | GET | JSON | Buscar favorito por ID |
| `/api/favoritos/check/{id}` | GET | Boolean | Verificar se é favorito |
| `/api/favoritos/{id}` | DELETE | String | Remover favorito |
| `/api/favoritos` | DELETE | String | Remover todos os favoritos |
| `/api/favoritos` | PUT | String | Substituir lista de favoritos |

---

## 🧪 Como Testar o Exercício da Aula 5

### Passo 1: Buscar filmes na API (salva automaticamente em memória)
```bash
# Buscar "Matrix"
curl "http://localhost:8080/api/movies/search?title=Matrix"

# Buscar "Inception"
curl "http://localhost:8080/api/movies/search?title=Inception"

# Buscar "Interstellar"
curl "http://localhost:8080/api/movies/search?title=Interstellar"
```

### Passo 2: Consultar lista em memória
```bash
# Ver TODOS os filmes salvos
curl http://localhost:8080/api/movies/memory

# Filtrar por "Matrix"
curl "http://localhost:8080/api/movies/memory/filter?title=Matrix"

# Filtrar por "Inception"
curl "http://localhost:8080/api/movies/memory/filter?title=Inception"
```

### Passo 3: Visualizar em HTML
```
# Abrir no navegador:
http://localhost:8080/api/movies/memory/html
http://localhost:8080/api/movies/memory/html?title=Matrix
```

### Passo 4: Limpar memória
```bash
curl http://localhost:8080/api/movies/memory/clear
```

### Observar logs no console:
```
✅ Busca por título: Matrix
   Total de resultados: 4
   Filmes encontrados: 4
💾 Total em memória: 4

🔍 Filtro aplicado: Matrix
   Filmes encontrados: 4

🗑️ 4 filmes removidos da memória
```
