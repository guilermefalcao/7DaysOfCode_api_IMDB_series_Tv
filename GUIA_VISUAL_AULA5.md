# 🎓 Aula 5 - Guia Visual Completo

## 📚 Índice Rápido

| Arquivo | Descrição |
|---------|-----------|
| `README.md` | Documentação geral do projeto |
| `README_AULAS.md` | Conceitos detalhados de todas as aulas |
| `RESUMO_AULA5.md` | Resumo executivo da Aula 5 |
| `GUIA_TESTES_AULA5.md` | Guia completo de testes |
| `EXEMPLOS_RESPOSTAS_AULA5.md` | Exemplos de respostas esperadas |
| `GIT_COMMANDS_AULA5.md` | Comandos Git para commitar |

---

## 🗺️ Mapa Mental da Aula 5

```
                    AULA 5
                      |
        +-------------+-------------+
        |                           |
   PARTE 1                      PARTE 2
 Refatoração                Filtros e Memória
        |                           |
        |                           |
   ImdbApiClient              MovieRepository
        |                           |
        |                           |
  Encapsulamento                  IDs
   HTTP Calls                  AtomicLong
        |                           |
        |                           |
    @Component                  @Repository
        |                           |
        |                           |
Single Responsibility          Stream.map
     Principle                 Stream.filter
        |                           |
        +-------------+-------------+
                      |
                 RESULTADO
                      |
        +-------------+-------------+
        |             |             |
    Código        Filtros      Endpoints
     Limpo       Funcionais     Novos
```

---

## 🏗️ Arquitetura em Camadas

```
┌─────────────────────────────────────────────┐
│           CLIENTE (Browser/Postman)         │
└─────────────────┬───────────────────────────┘
                  │ HTTP Request
                  ▼
┌─────────────────────────────────────────────┐
│         CONTROLLER (MovieController)        │
│  • Recebe requisições                       │
│  • Valida entrada                           │
│  • Orquestra fluxo                          │
│  • Retorna resposta                         │
└─────────────────┬───────────────────────────┘
                  │
        ┌─────────┴─────────┐
        │                   │
        ▼                   ▼
┌──────────────┐    ┌──────────────┐
│   CLIENT     │    │  REPOSITORY  │
│ImdbApiClient │    │MovieRepository│
│              │    │              │
│• Constrói URL│    │• Armazena    │
│• API Key     │    │• Filtra      │
│• HTTP Call   │    │• CRUD        │
└──────┬───────┘    └──────┬───────┘
       │                   │
       ▼                   ▲
┌──────────────┐           │
│  API EXTERNA │           │
│    OMDb      │           │
└──────┬───────┘           │
       │                   │
       ▼                   │
┌─────────────────────────┐│
│   SERVICE               ││
│  MovieService           ││
│                         ││
│• Parse JSON             ││
│• Adiciona IDs           ││
│• Salva no Repository ───┘│
└─────────────┬────────────┘
              │
              ▼
┌─────────────────────────┐
│   MODEL                 │
│  Movie (Record)         │
│                         │
│• id: Long               │
│• title: String          │
│• urlImage: String       │
│• rating: String         │
│• year: String           │
└─────────────┬───────────┘
              │
              ▼
┌─────────────────────────┐
│   GENERATOR             │
│  HTMLGenerator          │
│                         │
│• Gera HTML              │
│• Bootstrap              │
│• Cards                  │
└─────────────┬───────────┘
              │
              ▼
┌─────────────────────────┐
│   RESPOSTA              │
│  JSON ou HTML           │
└─────────────────────────┘
```

---

## 🔄 Fluxo de Dados

### Buscar e Salvar:
```
1. GET /api/movies/search?title=Matrix
   │
   ▼
2. MovieController.searchMovies("Matrix")
   │
   ▼
3. ImdbApiClient.searchMoviesByTitle("Matrix")
   │
   ▼
4. HTTP GET → OMDb API
   │
   ▼
5. JSON Response
   │
   ▼
6. MovieService.parseSearchResults(json)
   │
   ├─ Parse JSON
   ├─ Adiciona IDs (Stream.map)
   └─ movieRepository.saveAll(movies)
   │
   ▼
7. Filmes em Memória
   │
   ▼
8. Return MovieSearchResult
```

### Consultar Memória:
```
1. GET /api/movies/memory
   │
   ▼
2. MovieController.getAllMoviesInMemory()
   │
   ▼
3. MovieRepository.findAll()
   │
   ▼
4. Return List<Movie>
```

### Filtrar:
```
1. GET /api/movies/memory/filter?title=Matrix
   │
   ▼
2. MovieController.filterMoviesByTitle("Matrix")
   │
   ▼
3. MovieRepository.findByTitleContaining("Matrix")
   │
   ├─ movies.stream()
   ├─ .filter(movie → title.contains("matrix"))
   └─ .collect(toList())
   │
   ▼
4. Return List<Movie> (filtrada)
```

---

## 📊 Comparação: Antes vs Depois

### ANTES (Dia 4):
```
Controller
    ├─ Constrói URL
    ├─ Adiciona API Key
    ├─ Faz HTTP Call
    ├─ Processa JSON
    └─ Retorna resposta

❌ Muitas responsabilidades
❌ Difícil de testar
❌ Código duplicado
❌ Sem armazenamento
```

### DEPOIS (Dia 5):
```
Controller
    └─ Orquestra fluxo
        │
        ├─ ImdbApiClient
        │   └─ HTTP Calls
        │
        ├─ MovieService
        │   └─ Processa JSON
        │
        └─ MovieRepository
            └─ Armazena dados

✅ Responsabilidades separadas
✅ Fácil de testar
✅ Código reutilizável
✅ Armazenamento em memória
```

---

## 🎯 Endpoints Visuais

### Buscar na API:
```
┌─────────────────────────────────────────┐
│ GET /api/movies/search?title=Matrix     │
├─────────────────────────────────────────┤
│ • Busca na OMDb API                     │
│ • Salva automaticamente em memória      │
│ • Retorna JSON com lista de filmes      │
└─────────────────────────────────────────┘
```

### Consultar Memória:
```
┌─────────────────────────────────────────┐
│ GET /api/movies/memory                  │
├─────────────────────────────────────────┤
│ • Lista TODOS os filmes em memória      │
│ • Retorna JSON com IDs                  │
│ • Não busca na API                      │
└─────────────────────────────────────────┘
```

### Filtrar:
```
┌─────────────────────────────────────────┐
│ GET /api/movies/memory/filter?title=X  │
├─────────────────────────────────────────┤
│ • Filtra filmes em memória              │
│ • Case-insensitive                      │
│ • Busca substring                       │
└─────────────────────────────────────────┘
```

### HTML:
```
┌─────────────────────────────────────────┐
│ GET /api/movies/memory/html?title=X    │
├─────────────────────────────────────────┤
│ • Visualiza em HTML                     │
│ • Bootstrap 5                           │
│ • Filtro opcional                       │
└─────────────────────────────────────────┘
```

### Limpar:
```
┌─────────────────────────────────────────┐
│ GET /api/movies/memory/clear            │
├─────────────────────────────────────────┤
│ • Remove todos os filmes                │
│ • Retorna mensagem de confirmação       │
│ • Útil para testes                      │
└─────────────────────────────────────────┘
```

---

## 🧩 Componentes Principais

### 1. ImdbApiClient
```java
@Component
public class ImdbApiClient {
    // Responsabilidade: Comunicação HTTP
    
    ┌─────────────────────────┐
    │ searchMoviesByTitle()   │
    │ getMovieById()          │
    └─────────────────────────┘
}
```

### 2. MovieRepository
```java
@Repository
public class MovieRepository {
    // Responsabilidade: Armazenamento
    
    ┌─────────────────────────┐
    │ save()                  │
    │ saveAll()               │
    │ findAll()               │
    │ findById()              │
    │ findByTitleContaining() │
    │ deleteAll()             │
    │ deleteById()            │
    │ count()                 │
    └─────────────────────────┘
}
```

### 3. MovieService
```java
@Service
public class MovieService {
    // Responsabilidade: Lógica de negócio
    
    ┌─────────────────────────┐
    │ parseSearchResults()    │
    │ parseMovieDetails()     │
    └─────────────────────────┘
}
```

### 4. MovieController
```java
@RestController
public class MovieController {
    // Responsabilidade: Orquestração
    
    ┌─────────────────────────┐
    │ searchMovies()          │
    │ getMovieById()          │
    │ getAllMoviesInMemory()  │
    │ filterMoviesByTitle()   │
    │ viewMoviesInMemoryHTML()│
    │ clearMemory()           │
    └─────────────────────────┘
}
```

---

## 🔑 Conceitos-Chave

### AtomicLong (Thread-Safe):
```
Thread 1: getAndIncrement() → 1
Thread 2: getAndIncrement() → 2
Thread 3: getAndIncrement() → 3

✅ Sem conflitos
✅ IDs únicos garantidos
```

### Stream.map (Transformação):
```
[Movie(null, "Matrix", ...)]
        ↓ map
[Movie(1, "Matrix", ...)]
        ↓ map
[Movie(2, "Reloaded", ...)]
        ↓ map
[Movie(3, "Revolutions", ...)]
```

### Stream.filter (Filtro):
```
[Movie(1, "Matrix", ...)]
[Movie(2, "Inception", ...)]
[Movie(3, "Matrix Reloaded", ...)]
        ↓ filter(title.contains("Matrix"))
[Movie(1, "Matrix", ...)]
[Movie(3, "Matrix Reloaded", ...)]
```

---

## 📈 Evolução do Projeto

```
DIA 1: API Básica
    └─ RestTemplate + Controller

DIA 2: Testes
    └─ @SpringBootTest + Assertions

DIA 3: Modelagem
    └─ Records + Service

DIA 4: HTML
    └─ HTMLGenerator + Bootstrap

DIA 5 (Parte 1): Refatoração
    └─ ImdbApiClient + Encapsulamento

DIA 5 (Parte 2): Memória e Filtros
    └─ MovieRepository + IDs + Filtros

✅ PROJETO COMPLETO ATÉ AQUI
```

---

## 🎯 Checklist Visual

### Código:
- [x] ✅ ImdbApiClient criado
- [x] ✅ MovieRepository criado
- [x] ✅ Movie com ID
- [x] ✅ MovieService atualizado
- [x] ✅ MovieController com novos endpoints

### Funcionalidades:
- [x] ✅ Buscar e salvar automaticamente
- [x] ✅ Consultar lista em memória
- [x] ✅ Filtrar por título
- [x] ✅ Visualizar em HTML
- [x] ✅ Limpar memória
- [x] ✅ IDs incrementais
- [x] ✅ Filtro case-insensitive

### Documentação:
- [x] ✅ README.md atualizado
- [x] ✅ README_AULAS.md atualizado
- [x] ✅ Guias criados

### Testes:
- [x] ✅ Testado no navegador
- [x] ✅ Testado no Postman
- [x] ✅ Testado com cURL
- [x] ✅ Logs validados

---

## 🚀 Próximos Passos

```
1. Testar Aplicação
   └─ Seguir GUIA_TESTES_AULA5.md

2. Validar Respostas
   └─ Comparar com EXEMPLOS_RESPOSTAS_AULA5.md

3. Commitar no Git
   └─ Usar GIT_COMMANDS_AULA5.md

4. Preparar Aula 6
   └─ Ordenação e filtros avançados
```

---

## 📚 Documentação Completa

| Arquivo | Quando Usar |
|---------|-------------|
| `README.md` | Visão geral do projeto |
| `README_AULAS.md` | Aprender conceitos detalhados |
| `RESUMO_AULA5.md` | Revisão rápida |
| `GUIA_TESTES_AULA5.md` | Testar funcionalidades |
| `EXEMPLOS_RESPOSTAS_AULA5.md` | Validar respostas |
| `GIT_COMMANDS_AULA5.md` | Commitar código |
| `GUIA_VISUAL_AULA5.md` | Este arquivo (visão geral) |

---

## 🎓 Resumo de Aprendizados

### Técnicos:
```
✅ @Repository
✅ @Component
✅ AtomicLong
✅ Stream.map
✅ Stream.filter
✅ @RequestParam(required=false)
✅ Lista em memória
✅ Encapsulamento HTTP
```

### Arquiteturais:
```
✅ Separação de responsabilidades
✅ Single Responsibility Principle
✅ Camadas bem definidas
✅ Baixo acoplamento
✅ Alta coesão
```

### Práticos:
```
✅ IDs automáticos
✅ Filtros case-insensitive
✅ Logs informativos
✅ Endpoints RESTful
✅ Código funcional
✅ Documentação completa
```

---

## 🎉 Parabéns!

Você completou a **Aula 5** do #7DaysOfCode!

### Conquistas Desbloqueadas:
- 🏆 Refatoração Mestre
- 🏆 Arquiteto de Software
- 🏆 Especialista em Streams
- 🏆 Guardião da Memória
- 🏆 Mestre dos Filtros

### Próximo Desafio:
**Aula 6**: Ordenação e filtros avançados

---

**Repositório:** https://github.com/guilermefalcao/7DaysOfCode_api_IMDB_series_Tv

**Autor:** Guilherme Falcão

**Curso:** Alura - #7DaysOfCode

---

## 🔗 Links Rápidos

- [Testar Aplicação](GUIA_TESTES_AULA5.md)
- [Ver Exemplos](EXEMPLOS_RESPOSTAS_AULA5.md)
- [Commitar Código](GIT_COMMANDS_AULA5.md)
- [Conceitos Detalhados](README_AULAS.md)
- [Resumo Executivo](RESUMO_AULA5.md)

---

**Dúvidas?** Consulte a documentação ou revise os conceitos no README_AULAS.md!
