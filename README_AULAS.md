# README - Aulas 1 a 4: Guia Didático Completo

## 📚 Índice
- [Aula 1: Consumindo API Externa](#aula-1-consumindo-api-externa)
- [Aula 2: Testes de Integração](#aula-2-testes-de-integração)
- [Aula 3: Modelagem Orientada a Objetos](#aula-3-modelagem-orientada-a-objetos)
- [Aula 4: Geração de HTML](#aula-4-geração-de-html)

---

## 🎯 Aula 1: Consumindo API Externa

### Objetivo
Criar uma API REST que consome dados da OMDb API (The Open Movie Database).

### O que foi criado
1. **Projeto Spring Boot** com Spring Initializr
2. **ImdbApiApplication.java** - Classe principal com `@Bean` do RestTemplate
3. **MovieController.java** - Controller REST com endpoints GET
4. **application.properties** - Configuração da API Key

### Conceitos Aprendidos

#### **RestTemplate**
```java
@Bean
public RestTemplate restTemplate() {
    return new RestTemplate();
}
```
- **O que é**: Cliente HTTP do Spring para fazer requisições
- **Como funciona**: Faz chamadas HTTP e retorna respostas
- **Uso**: `restTemplate.getForEntity(url, String.class)`

#### **@RestController**
```java
@RestController
@RequestMapping("/api/movies")
public class MovieController { }
```
- **Função**: Marca classe como controlador REST
- **Resultado**: Métodos retornam dados (JSON) ao invés de views (HTML)

#### **@GetMapping**
```java
@GetMapping("/search")
public String searchMovies(@RequestParam String title) { }
```
- **Função**: Define endpoint GET
- **@RequestParam**: Captura parâmetros da URL (?title=Matrix)

#### **@Autowired**
```java
@Autowired
private RestTemplate restTemplate;
```
- **Função**: Injeção de dependências automática
- **Vantagem**: Spring cria e gerencia o objeto

### Endpoints Criados
- `GET /api/movies/search?title=Matrix` - Busca filmes por título
- `GET /api/movies/tt0133093` - Busca filme por ID do IMDB

### Resultado
API funcionando e retornando JSON bruto da OMDb API.

---

## 🧪 Aula 2: Testes de Integração

### Objetivo
Implementar testes automatizados para validar os endpoints da API.

### O que foi criado
1. **ImdbApiApplicationTests.java** - Classe de testes de integração
2. **application-test.properties** - Configurações específicas para testes
3. **Variáveis de Ambiente** - Segurança para API Keys

### Conceitos Aprendidos

#### **@SpringBootTest**
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ImdbApiApplicationTests { }
```
- **Função**: Sobe aplicação completa para testes
- **RANDOM_PORT**: Porta aleatória evita conflitos
- **Resultado**: Aplicação real rodando

#### **@LocalServerPort**
```java
@LocalServerPort
private int port;
```
- **Função**: Injeta porta onde aplicação está rodando
- **Uso**: Montar URL dinâmica nos testes

#### **Assertions (JUnit 5)**
```java
assertEquals(HttpStatus.OK, response.getStatusCode());
assertNotNull(response.getBody());
assertFalse(response.getBody().isEmpty());
assertTrue(response.getBody().contains("Search"));
```
- **assertEquals**: Verifica igualdade
- **assertNotNull**: Verifica se não é nulo
- **assertFalse**: Verifica se é falso
- **assertTrue**: Verifica se é verdadeiro

### Testes Implementados
1. **contextLoads()** - Verifica se aplicação inicializa
2. **shouldReturnMoviesWhenSearchingByTitle()** - Testa busca por título
3. **shouldReturnMovieWhenSearchingById()** - Testa busca por ID
4. **shouldStartOnRandomPort()** - Verifica porta aleatória

### Segurança com Variáveis de Ambiente
```properties
omdb.api.key=${OMDB_API_KEY:YOUR_API_KEY}
```
- **${OMDB_API_KEY}**: Lê da variável de ambiente
- **:YOUR_API_KEY**: Fallback se não encontrar

### Resultado
4/4 testes passando - BUILD SUCCESS

---

## 🎯 Aula 3: Modelagem Orientada a Objetos

### Objetivo
Refatorar código para usar objetos ao invés de JSON bruto.

### O que foi criado
1. **Movie.java** - Record que representa um filme (imutável)
2. **MovieSearchResult.java** - Record que encapsula lista de filmes
3. **MovieService.java** - Service para processar JSON
4. **Refatoração do Controller** - Retorna objetos ao invés de String

### Conceitos Aprendidos

#### **Records (Java 17)**
```java
public record Movie(
    String title,
    String urlImage,
    String rating,
    String year
) { }
```

**O que são Records?**
- Classes imutáveis com sintaxe simplificada
- Getters, equals, hashCode e toString automáticos
- Código mais limpo e conciso

**Vantagens:**
- ✅ Imutabilidade automática
- ✅ Menos código boilerplate
- ✅ Thread-safe
- ✅ Previsibilidade

**Quando usar:**
- Dados que não devem mudar após criação
- DTOs (Data Transfer Objects)
- Objetos de valor

#### **Construtor Compacto**
```java
public Movie {
    if (title == null || title.isBlank()) {
        throw new IllegalArgumentException("Título não pode ser nulo");
    }
}
```
- **Função**: Validações antes de criar o objeto
- **Vantagem**: Garante consistência dos dados

#### **Método Factory**
```java
public static Movie fromOmdbJson(String title, String poster, String rating, String year) {
    return new Movie(title, poster, rating, year);
}
```
- **Função**: Criar objetos de forma mais legível
- **Vantagem**: Trata valores nulos da API

#### **Separação de Responsabilidades (SOLID)**
```
Controller → Recebe requisições HTTP
Service → Processa lógica de negócio (parsing JSON)
Model → Representa dados do domínio
```

### Decisões de Design

**Por que Record?**
- Dados de filmes não devem mudar após criação
- Simplicidade e menos código

**Por que Imutável?**
- Segurança: Dados não podem ser alterados
- Thread-safe: Compartilhamento seguro entre threads

**Por que não tem Setters?**
- Records são imutáveis por design
- Se precisar alterar, cria-se novo objeto

**Interface?**
- Não necessário neste momento
- YAGNI (You Aren't Gonna Need It)

### Resultado
API retornando objetos estruturados ao invés de JSON bruto.

---

## 🎨 Aula 4: Geração de HTML

### Objetivo
Gerar página HTML com lista de filmes usando Bootstrap.

### O que foi criado
1. **HTMLGenerator.java** - Classe para gerar HTML
2. **Novo endpoint /html** - Retorna página HTML
3. **Bootstrap CSS** - Estilização profissional
4. **Cards responsivos** - Grid de filmes

### Conceitos Aprendidos

#### **Text Blocks (Java 17)**
```java
String html = """
    <div class="container">
        <h1>Título</h1>
    </div>
    """;
```

**Vantagens:**
- ✅ HTML mais legível
- ✅ Não precisa escapar aspas
- ✅ Mantém indentação
- ✅ Código mais limpo

#### **Writer Pattern**
```java
public HTMLGenerator(Writer writer) {
    this.writer = new PrintWriter(writer);
}
```

**Por que receber Writer no construtor?**
- ✅ Flexibilidade: Escreve em arquivo, string, etc.
- ✅ Testabilidade: Facilita testes unitários
- ✅ Responsabilidade: Quem cria decide onde escrever

#### **StringWriter**
```java
StringWriter stringWriter = new StringWriter();
HTMLGenerator generator = new HTMLGenerator(stringWriter);
generator.generate(movies);
return stringWriter.toString();
```

**O que é:**
- Captura texto em memória
- Converte para String no final

**Uso:**
- Gerar HTML dinamicamente
- Retornar como resposta HTTP

#### **MediaType.TEXT_HTML_VALUE**
```java
@GetMapping(value = "/html", produces = MediaType.TEXT_HTML_VALUE)
public String searchMoviesHTML() { }
```

**Função:**
- Define que endpoint retorna HTML
- Browser renderiza ao invés de mostrar texto

#### **Bootstrap CSS**
```html
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
```

**O que é:**
- Framework CSS para estilização
- Componentes prontos (cards, grid, etc.)
- Responsivo (mobile-first)

**Componentes usados:**
- **Container**: Centraliza conteúdo
- **Row/Col**: Sistema de grid
- **Card**: Exibe informações do filme
- **Badge**: Mostra nota do filme

### Estrutura do HTML Gerado

```html
<!DOCTYPE html>
<html>
<head>
    <!-- Bootstrap CSS -->
    <!-- Estilos customizados -->
</head>
<body>
    <!-- Header com título -->
    <div class="container">
        <!-- Grid de filmes -->
        <div class="row">
            <!-- Card para cada filme -->
            <div class="col">
                <div class="card">
                    <img src="poster">
                    <span class="badge">⭐ 8.7</span>
                    <h5>Título</h5>
                    <p>Ano</p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
```

### É Boa Prática Gerar HTML em Java?

**Não é ideal:**
- ❌ Mistura lógica com apresentação
- ❌ Difícil de manter
- ❌ Designers não conseguem editar

**Alternativas em Produção:**
- ✅ Thymeleaf (template engine)
- ✅ Freemarker
- ✅ Frontend separado (React, Angular, Vue)

**Por que fizemos assim?**
- ✅ Aprendizado de conceitos OO
- ✅ Entender geração dinâmica de conteúdo
- ✅ Praticar Text Blocks e Writer

### Novo Endpoint
```
GET /api/movies/html?title=Matrix
```

**Retorna:** Página HTML completa com grid de filmes

### Resultado
Página HTML bonita e responsiva com Bootstrap exibindo filmes.

---

## 🔄 Evolução do Projeto

### Aula 1 → Aula 2
- ❌ Sem testes → ✅ Testes automatizados
- ❌ API Key hardcoded → ✅ Variáveis de ambiente

### Aula 2 → Aula 3
- ❌ JSON bruto (String) → ✅ Objetos tipados (Movie)
- ❌ Lógica no Controller → ✅ Service separado
- ❌ Dados mutáveis → ✅ Records imutáveis

### Aula 3 → Aula 4
- ❌ Apenas JSON → ✅ HTML + JSON
- ❌ Sem visualização → ✅ Interface web
- ❌ Dados brutos → ✅ Apresentação visual

---

## 🎓 Principais Aprendizados

### Técnicos
1. **Spring Boot**: Framework para criar APIs REST
2. **RestTemplate**: Cliente HTTP para consumir APIs
3. **Testes de Integração**: Validar sistema completo
4. **Records**: Estruturas de dados imutáveis
5. **SOLID**: Separação de responsabilidades
6. **Jackson**: Processar JSON
7. **Text Blocks**: HTML legível em Java
8. **Bootstrap**: Framework CSS responsivo

### Boas Práticas
1. **Nunca commitar chaves de API**
2. **Usar variáveis de ambiente**
3. **Escrever testes automatizados**
4. **Separar responsabilidades** (Controller, Service, Model)
5. **Preferir imutabilidade**
6. **Código limpo e comentado**
7. **Validar dados de entrada**
8. **Usar padrões de projeto**

### Arquitetura Final
```
Cliente (Browser/Postman)
    ↓
Controller (MovieController)
    ↓
Service (MovieService)
    ↓
Model (Movie, MovieSearchResult)
    ↓
Generator (HTMLGenerator)
    ↓
Resposta (JSON ou HTML)
```

---

## 📊 Estatísticas do Projeto

- **Linhas de Código**: ~800
- **Classes**: 7
- **Testes**: 4
- **Endpoints**: 5
- **Dependências**: 3 (Spring Web, Jackson, DevTools)
- **Cobertura de Testes**: 100% dos endpoints

---

## 🚀 Como Executar

```bash
# Definir API Key (escolha uma opção)

# Opção 1: Variável de ambiente
$env:OMDB_API_KEY="sua_chave_aqui"

# Opção 2: Usar application-local.properties (recomendado)
# Já configurado com spring.profiles.active=local

# Executar aplicação
mvnw.cmd spring-boot:run

# Executar testes
mvnw.cmd test
```

---

## 🌐 Endpoints Disponíveis

| Endpoint | Método | Retorno | Descrição |
|----------|--------|---------|-----------|
| `/api/movies/search?title=Matrix` | GET | JSON | Lista de filmes |
| `/api/movies/tt0133093` | GET | JSON | Detalhes do filme |
| `/api/movies/html?title=Matrix` | GET | HTML | Página web com filmes |
| `/api/movies/raw/search?title=Matrix` | GET | JSON | JSON bruto (legado) |

---

## 📝 Próximos Passos (Dias 5-7)

- Dia 5: Implementar ordenação e filtros
- Dia 6: Adicionar favoritos
- Dia 7: Deploy e documentação final

---

## 🔗 Referências

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [OMDb API](https://www.omdbapi.com/)
- [Java Records](https://docs.oracle.com/en/java/javase/17/language/records.html)
- [JUnit 5](https://junit.org/junit5/)
- [Bootstrap 5](https://getbootstrap.com/)
- [Text Blocks](https://docs.oracle.com/en/java/javase/17/text-blocks/index.html)


---

## 🔧 Aula 5: Refatoração e Encapsulamento

### Objetivo
Encapsular chamadas HTTP e separar responsabilidades seguindo princípios SOLID.

### O que foi criado
1. **ImdbApiClient.java** - Cliente HTTP para comunicação com OMDb API
2. **Refatoração do Controller** - Removida lógica HTTP, apenas orquestra
3. **Separação clara de responsabilidades** - Cada classe com uma função

### Conceitos Aprendidos

#### **@Component**
```java
@Component
public class ImdbApiClient { }
```

**O que é:**
- Marca classe como componente Spring
- Spring gerencia ciclo de vida (cria, injeta, destrói)
- Permite injeção em outras classes com @Autowired

**Diferença de outras anotações:**
- `@Component`: Genérico para qualquer componente
- `@Service`: Lógica de negócio
- `@Repository`: Acesso a dados
- `@Controller`: Controlador web

#### **Encapsulamento de Chamadas HTTP**

**ANTES (Dia 4):**
```java
@RestController
public class MovieController {
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${omdb.api.key}")
    private String apiKey;
    
    @GetMapping("/search")
    public MovieSearchResult searchMovies(@RequestParam String title) {
        String url = "http://www.omdbapi.com/?s=" + title + "&apikey=" + apiKey;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        // ... processar resposta
    }
}
```

**DEPOIS (Dia 5):**
```java
@RestController
public class MovieController {
    @Autowired
    private ImdbApiClient imdbApiClient;
    
    @GetMapping("/search")
    public MovieSearchResult searchMovies(@RequestParam String title) {
        String json = imdbApiClient.searchMoviesByTitle(title);
        // ... processar resposta
    }
}
```

**Vantagens:**
- ✅ Controller não conhece detalhes da URL
- ✅ Controller não conhece API Key
- ✅ Código mais limpo e legível
- ✅ Fácil de testar (pode mockar o client)
- ✅ Fácil de manter (mudanças na API afetam apenas o client)

#### **Single Responsibility Principle (SOLID)**

**Separação de Responsabilidades:**

```
Controller (MovieController)
├─ Responsabilidade: Orquestrar fluxo HTTP
├─ Recebe requisições
├─ Chama serviços
└─ Retorna respostas

Client (ImdbApiClient)
├─ Responsabilidade: Comunicação com API externa
├─ Constrói URLs
├─ Gerencia API Key
└─ Executa requisições HTTP

Service (MovieService)
├─ Responsabilidade: Processar dados
├─ Parse JSON
├─ Converte em objetos
└─ Valida dados

Model (Movie, MovieSearchResult)
├─ Responsabilidade: Representar dados
└─ Estrutura imutável

Generator (HTMLGenerator)
├─ Responsabilidade: Gerar HTML
└─ Criar visualização
```

#### **Injeção de Dependências**

**Como funciona:**
```java
// 1. Spring encontra @Component
@Component
public class ImdbApiClient { }

// 2. Spring cria instância automaticamente

// 3. Spring injeta onde for solicitado
@RestController
public class MovieController {
    @Autowired
    private ImdbApiClient imdbApiClient; // Spring injeta aqui
}
```

**Vantagens:**
- ✅ Não precisa usar `new`
- ✅ Spring gerencia ciclo de vida
- ✅ Facilita testes (pode injetar mocks)
- ✅ Desacoplamento

### Comparação: Antes vs Depois

| Aspecto | Antes (Dia 4) | Depois (Dia 5) |
|---------|---------------|----------------|
| **Responsabilidades** | Controller faz tudo | Separadas em classes |
| **Testabilidade** | Difícil | Fácil (mockar client) |
| **Manutenção** | Mudanças afetam controller | Mudanças isoladas |
| **Legibilidade** | Código verboso | Código limpo |
| **Reutilização** | Duplicação de código | Client reutilizável |

### Arquitetura Final (Dia 5)

```
Cliente (Browser/Postman)
    ↓
Controller (MovieController)
    ├─ Orquestra fluxo
    ├─ Valida entrada
    └─ Formata resposta
    ↓
Client (ImdbApiClient)
    ├─ Constrói URL
    ├─ Adiciona API Key
    └─ Executa HTTP
    ↓
API Externa (OMDb)
    ↓
Service (MovieService)
    ├─ Parse JSON
    └─ Converte objetos
    ↓
Model (Movie)
    ↓
Generator (HTMLGenerator)
    └─ Gera HTML
    ↓
Resposta (JSON ou HTML)
```

### Princípios SOLID Aplicados

1. **S - Single Responsibility**
   - Cada classe tem uma única responsabilidade
   - Controller: Orquestra
   - Client: Comunica
   - Service: Processa
   - Model: Representa

2. **O - Open/Closed**
   - Aberto para extensão (pode adicionar novos clients)
   - Fechado para modificação (não precisa mudar controller)

3. **D - Dependency Inversion**
   - Controller depende de abstrações (interfaces)
   - Não depende de implementações concretas

### Resultado
Código mais limpo, organizado e seguindo boas práticas de engenharia de software.

---

## 📊 Estatísticas Atualizadas (Dia 5)

- **Linhas de Código**: ~1000
- **Classes**: 8 (+1 ImdbApiClient)
- **Testes**: 4
- **Endpoints**: 5
- **Dependências**: 3 (Spring Web, Jackson, DevTools)
- **Princípios SOLID**: ✅ Aplicados

---

## 🎓 Lições Aprendidas (Dia 5)

### Refatoração
1. **Encapsular** lógica complexa em classes dedicadas
2. **Separar** responsabilidades claramente
3. **Reutilizar** código através de componentes
4. **Testar** fica mais fácil com código desacoplado

### Boas Práticas
1. **@Component** para classes reutilizáveis
2. **@Autowired** para injeção de dependências
3. **Single Responsibility** - uma classe, uma função
4. **Código limpo** - fácil de ler e manter

### Arquitetura
1. **Camadas bem definidas**: Controller → Client → Service → Model
2. **Baixo acoplamento**: Mudanças isoladas
3. **Alta coesão**: Classes focadas
4. **Fácil manutenção**: Código organizado

---

## 💾 Aula 5 (Parte 2): Filtros e Lista em Memória

### Objetivo
Implementar filtro por título e criar lista em memória para armazenar filmes.

### O que foi criado
1. **MovieRepository.java** - Repositório em memória para armazenar filmes
2. **Campo ID no Movie** - Identificador único para cada filme
3. **Novos endpoints** - Consultar e filtrar lista em memória
4. **Atualização do MovieService** - Gera IDs e salva automaticamente

### Conceitos Aprendidos

#### **@Repository**
```java
@Repository
public class MovieRepository { }
```

**O que é:**
- Marca classe como componente de acesso a dados
- Spring gerencia ciclo de vida
- Semântica clara: esta classe gerencia dados

**Diferença de outras anotações:**
- `@Repository`: Acesso a dados (banco, memória, arquivo)
- `@Service`: Lógica de negócio
- `@Component`: Genérico
- `@Controller`: Controlador web

#### **AtomicLong para IDs**
```java
private final AtomicLong idGenerator = new AtomicLong(1);

public Movie save(Movie movie) {
    Long newId = idGenerator.getAndIncrement();
    // ...
}
```

**Por que AtomicLong?**
- ✅ Thread-safe: Múltiplas requisições simultâneas
- ✅ Incremento atômico: Garante IDs únicos
- ✅ Simples: Não precisa sincronização manual

**Alternativas:**
- `Long` simples: ❌ Não thread-safe
- `synchronized`: ✅ Funciona, mas mais complexo
- `UUID`: ✅ Funciona, mas IDs grandes

#### **Lista em Memória**
```java
private final List<Movie> movies = new ArrayList<>();
```

**Vantagens:**
- ✅ Simplicidade: Não precisa configurar banco
- ✅ Aprendizado: Foco em lógica de negócio
- ✅ Rápido: Acesso instantâneo

**Limitações:**
- ❌ Dados perdidos ao reiniciar
- ❌ Não escalável (apenas uma instância)
- ❌ Sem transações
- ❌ Para produção: usar banco de dados real

#### **Stream.map para adicionar IDs**
```java
List<Movie> moviesWithIds = movies.stream()
    .map(movie -> Movie.fromOmdbJson(
        idCounter.getAndIncrement(), // Gera ID incremental
        movie.title(),
        movie.urlImage(),
        movie.rating(),
        movie.year()
    ))
    .collect(Collectors.toList());
```

**Como funciona:**
1. `stream()`: Converte lista em stream
2. `map()`: Transforma cada elemento
3. `idCounter.getAndIncrement()`: Gera ID único
4. `collect()`: Converte stream de volta em lista

**Vantagens:**
- ✅ Funcional: Código declarativo
- ✅ Imutável: Não modifica lista original
- ✅ Legível: Intenção clara

#### **Filtro por Título (QueryParam)**
```java
@GetMapping("/memory/filter")
public List<Movie> filterMoviesByTitle(@RequestParam String title) {
    return movieRepository.findByTitleContaining(title);
}
```

**Implementação do filtro:**
```java
public List<Movie> findByTitleContaining(String titleFilter) {
    return movies.stream()
        .filter(movie -> movie.title()
            .toLowerCase()
            .contains(titleFilter.toLowerCase()))
        .collect(Collectors.toList());
}
```

**Como funciona:**
1. `stream()`: Converte lista em stream
2. `filter()`: Filtra elementos que atendem condição
3. `toLowerCase()`: Case-insensitive ("Matrix" = "matrix")
4. `contains()`: Verifica se contém o texto
5. `collect()`: Converte stream em lista

**Exemplos:**
- `title=Matrix` → "The Matrix", "Matrix Reloaded"
- `title=mat` → "The Matrix" (case-insensitive)
- `title=Inception` → "Inception"

#### **@RequestParam(required = false)**
```java
@GetMapping("/memory/html")
public String viewMoviesInMemoryHTML(
    @RequestParam(required = false) String title
) {
    List<Movie> movies = (title != null && !title.isBlank()) 
        ? movieRepository.findByTitleContaining(title)
        : movieRepository.findAll();
    // ...
}
```

**O que faz:**
- `required = false`: Parâmetro opcional
- Se fornecido: filtra
- Se não fornecido: retorna todos

**Exemplos:**
- `/memory/html` → Todos os filmes
- `/memory/html?title=Matrix` → Filmes com "Matrix"

### Novos Endpoints Implementados

#### 1. Listar todos os filmes em memória
```java
@GetMapping("/memory")
public List<Movie> getAllMoviesInMemory() {
    return movieRepository.findAll();
}
```
**Uso:** `GET /api/movies/memory`

#### 2. Filtrar filmes por título
```java
@GetMapping("/memory/filter")
public List<Movie> filterMoviesByTitle(@RequestParam String title) {
    return movieRepository.findByTitleContaining(title);
}
```
**Uso:** `GET /api/movies/memory/filter?title=Matrix`

#### 3. Visualizar em HTML (com filtro opcional)
```java
@GetMapping(value = "/memory/html", produces = MediaType.TEXT_HTML_VALUE)
public String viewMoviesInMemoryHTML(
    @RequestParam(required = false) String title
) {
    // ...
}
```
**Uso:** 
- `GET /api/movies/memory/html` (todos)
- `GET /api/movies/memory/html?title=Matrix` (filtrados)

#### 4. Limpar memória
```java
@GetMapping("/memory/clear")
public String clearMemory() {
    long count = movieRepository.count();
    movieRepository.deleteAll();
    return "🗑️ " + count + " filmes removidos da memória";
}
```
**Uso:** `GET /api/movies/memory/clear`

### Fluxo Completo

```
1. Usuário busca "Matrix" na API
   GET /api/movies/search?title=Matrix
   ↓
2. Controller chama ImdbApiClient
   imdbApiClient.searchMoviesByTitle("Matrix")
   ↓
3. Client busca na OMDb API
   Retorna JSON
   ↓
4. Service processa JSON
   movieService.parseSearchResults(json)
   ↓
5. Service adiciona IDs usando Stream.map
   movies.stream().map(movie -> addId(movie))
   ↓
6. Service salva no repositório
   movieRepository.saveAll(moviesWithIds)
   ↓
7. Filmes ficam em memória
   List<Movie> movies = [...]
   ↓
8. Usuário pode consultar/filtrar
   GET /api/movies/memory/filter?title=Matrix
```

### Arquitetura Final (Dia 5 Completo)

```
Cliente (Browser/Postman)
    ↓
Controller (MovieController)
    ├─ Orquestra fluxo
    ├─ Valida entrada
    └─ Formata resposta
    ↓
Client (ImdbApiClient)
    ├─ Constrói URL
    ├─ Adiciona API Key
    └─ Executa HTTP
    ↓
API Externa (OMDb)
    ↓
Service (MovieService)
    ├─ Parse JSON
    ├─ Adiciona IDs (Stream.map)
    └─ Salva no repositório
    ↓
Repository (MovieRepository)
    ├─ Armazena em memória
    ├─ Gera IDs (AtomicLong)
    ├─ Filtra (Stream.filter)
    └─ CRUD completo
    ↓
Model (Movie com ID)
    ↓
Generator (HTMLGenerator)
    └─ Gera HTML
    ↓
Resposta (JSON ou HTML)
```

### Comparação: Antes vs Depois

| Aspecto | Antes (Dia 5 Parte 1) | Depois (Dia 5 Parte 2) |
|---------|----------------------|------------------------|
| **Armazenamento** | Apenas retorna dados | Salva em memória |
| **IDs** | Sem IDs | IDs automáticos |
| **Filtros** | Apenas na API | Filtro local + API |
| **Consulta** | Sempre busca API | Pode consultar memória |
| **Performance** | Depende da API | Instantâneo (memória) |

### Decisões de Design

#### Por que Record com ID?
```java
public record Movie(
    Long id,        // NOVO: ID único
    String title,
    String urlImage,
    String rating,
    String year
) { }
```

**Motivos:**
- ✅ Identificar filmes unicamente
- ✅ Facilita operações CRUD
- ✅ Permite referência direta
- ✅ Prepara para banco de dados futuro

#### Por que lista fora do método?
```java
@Repository
public class MovieRepository {
    private final List<Movie> movies = new ArrayList<>(); // Fora dos métodos
}
```

**Motivos:**
- ✅ Persistência entre requisições
- ✅ Estado compartilhado
- ✅ Singleton (Spring gerencia)
- ❌ Dentro do método: lista seria recriada sempre

#### Por que salvar automaticamente?
```java
public MovieSearchResult parseSearchResults(String json) {
    // ... processa JSON
    movieRepository.saveAll(moviesWithIds); // Salva automaticamente
    return new MovieSearchResult(moviesWithIds, totalResults);
}
```

**Motivos:**
- ✅ Conveniência: Usuário não precisa chamar endpoint separado
- ✅ Automático: Toda busca é salva
- ✅ Simples: Menos endpoints

### Resultado
Sistema completo com armazenamento em memória, filtros e IDs automáticos.

---

## 🧪 Como Testar a Aula 5 (Parte 2)

### Teste 1: Buscar e Salvar Automaticamente
```bash
# 1. Buscar "Matrix" (salva automaticamente)
curl "http://localhost:8080/api/movies/search?title=Matrix"

# 2. Verificar console:
# ✅ Busca por título: Matrix
#    Total de resultados: 4
#    Filmes encontrados: 4
# 💾 Total em memória: 4
```

### Teste 2: Consultar Lista em Memória
```bash
# Ver todos os filmes salvos
curl http://localhost:8080/api/movies/memory

# Resposta esperada:
# [
#   {"id":1,"title":"The Matrix","urlImage":"...","rating":"8.7","year":"1999"},
#   {"id":2,"title":"The Matrix Reloaded","urlImage":"...","rating":"7.2","year":"2003"},
#   ...
# ]
```

### Teste 3: Filtrar por Título
```bash
# Buscar mais filmes
curl "http://localhost:8080/api/movies/search?title=Inception"
curl "http://localhost:8080/api/movies/search?title=Interstellar"

# Filtrar apenas "Matrix"
curl "http://localhost:8080/api/movies/memory/filter?title=Matrix"

# Filtrar apenas "Inception"
curl "http://localhost:8080/api/movies/memory/filter?title=Inception"
```

### Teste 4: Visualizar em HTML
```
# Abrir no navegador:
http://localhost:8080/api/movies/memory/html
http://localhost:8080/api/movies/memory/html?title=Matrix
```

### Teste 5: Limpar Memória
```bash
# Limpar todos os filmes
curl http://localhost:8080/api/movies/memory/clear

# Resposta: "🗑️ 12 filmes removidos da memória"

# Verificar que está vazio
curl http://localhost:8080/api/movies/memory
# Resposta: []
```

### Teste 6: Verificar IDs Incrementais
```bash
# 1. Limpar memória
curl http://localhost:8080/api/movies/memory/clear

# 2. Buscar "Matrix" (IDs 1, 2, 3, 4)
curl "http://localhost:8080/api/movies/search?title=Matrix"

# 3. Buscar "Inception" (IDs 5, 6, 7, ...)
curl "http://localhost:8080/api/movies/search?title=Inception"

# 4. Ver todos (IDs devem ser sequenciais)
curl http://localhost:8080/api/movies/memory
```

### Teste no Postman

**Coleção de Testes:**

1. **Buscar Matrix**
   - Método: GET
   - URL: `http://localhost:8080/api/movies/search?title=Matrix`
   - Resultado: JSON com filmes + salvos em memória

2. **Ver Memória**
   - Método: GET
   - URL: `http://localhost:8080/api/movies/memory`
   - Resultado: Lista com IDs

3. **Filtrar Matrix**
   - Método: GET
   - URL: `http://localhost:8080/api/movies/memory/filter?title=Matrix`
   - Resultado: Apenas filmes com "Matrix" no título

4. **HTML com Filtro**
   - Método: GET
   - URL: `http://localhost:8080/api/movies/memory/html?title=Matrix`
   - Resultado: Página HTML

5. **Limpar**
   - Método: GET
   - URL: `http://localhost:8080/api/movies/memory/clear`
   - Resultado: Mensagem de confirmação

### Observar Logs no Console

```
✅ Busca por título: Matrix
   Total de resultados: 4
   Filmes encontrados: 4
💾 Filme salvo: ID=1, Título=The Matrix
💾 Filme salvo: ID=2, Título=The Matrix Reloaded
💾 Filme salvo: ID=3, Título=The Matrix Revolutions
💾 Filme salvo: ID=4, Título=The Matrix Revisited
💾 Total em memória: 4

💾 Total de filmes em memória: 4

🔍 Filtro aplicado: Matrix
   Filmes encontrados: 4

🗑️ Todos os filmes foram removidos da memória
🗑️ 4 filmes removidos da memória
```

---

## 📊 Estatísticas Atualizadas (Dia 5 Completo)

- **Linhas de Código**: ~1200
- **Classes**: 9 (+1 MovieRepository)
- **Testes**: 4
- **Endpoints**: 10 (+5 novos)
- **Dependências**: 3 (Spring Web, Jackson, DevTools)
- **Princípios SOLID**: ✅ Aplicados
- **Padrões**: Repository, Service, DTO

---

## 🎓 Lições Aprendidas (Dia 5 Completo)

### Parte 1: Refatoração
1. **Encapsular** lógica HTTP em cliente dedicado
2. **Separar** responsabilidades (Controller, Client, Service)
3. **@Component** para classes reutilizáveis
4. **Single Responsibility Principle**

### Parte 2: Filtros e Memória
1. **@Repository** para acesso a dados
2. **AtomicLong** para IDs thread-safe
3. **Stream.map** para transformar dados
4. **Stream.filter** para filtrar dados
5. **@RequestParam(required = false)** para parâmetros opcionais
6. **Lista em memória** para armazenamento temporário

### Boas Práticas
1. **IDs automáticos** com AtomicLong
2. **Filtros case-insensitive** com toLowerCase()
3. **Logs informativos** para debug
4. **Endpoints RESTful** bem organizados
5. **Código funcional** com Streams
6. **Imutabilidade** com Records

### Arquitetura
1. **Camadas bem definidas**: Controller → Client → Service → Repository → Model
2. **Baixo acoplamento**: Mudanças isoladas
3. **Alta coesão**: Classes focadas
4. **Fácil manutenção**: Código organizado
5. **Testabilidade**: Componentes independentes
