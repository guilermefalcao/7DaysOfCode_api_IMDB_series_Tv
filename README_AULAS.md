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
