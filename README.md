# 7DaysOfCode - API IMDB

Projeto desenvolvido durante o desafio #7DaysOfCode da Alura - Dia 1.

## Descrição
API REST para consumir dados de filmes do IMDB usando a OMDb API (The Open Movie Database), permitindo buscar informações sobre filmes.

## Tecnologias Utilizadas
- Java 17
- Spring Boot 4.0.2
- Spring Web
- RestTemplate
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

## Como Testar

### Opção 1: Navegador
Buscar filme por título:
```
http://localhost:8080/api/movies/search?title=Matrix
```

Buscar filme por ID do IMDB:
```
http://localhost:8080/api/movies/tt0133093
```

### Opção 2: Postman
1. Abra o Postman
2. Crie uma nova requisição GET
3. URL: `http://localhost:8080/api/movies/search?title=Inception`
4. Clique em "Send"

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
└── controller/
    └── MovieController.java     # Controller REST com endpoints GET
```

## Funcionamento

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

## Comandos Git para Subir no Repositório

```bash
cd "C:\1. Guilherme\00. Dataprev\0000. projeto conta\cursoSpringboot\7DaysOfCode_api_IMDB_series_Tv"

git add .
git commit -m "Dia 1: Implementação inicial - Consumo da OMDb API para buscar filmes"
git branch -M main
git remote add origin https://github.com/guilermefalcao/7DaysOfCode_api_IMDB_series_Tv.git
git push -u origin main
```

## Autor
Guilherme Falcão

## Curso
Alura - #7DaysOfCode - Dia 1

## Repositório
https://github.com/guilermefalcao/7DaysOfCode_api_IMDB_series_Tv
