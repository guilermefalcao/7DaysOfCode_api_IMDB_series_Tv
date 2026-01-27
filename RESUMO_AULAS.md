# Resumo das Aulas - 7DaysOfCode API IMDB

## 📚 Aula 1: Consumindo API Externa

### Objetivo
Criar uma API REST que consome dados da OMDb API (The Open Movie Database).

### O que foi criado
1. **Projeto Spring Boot** com Spring Initializr
2. **ImdbApiApplication.java** - Classe principal com `@Bean` do RestTemplate
3. **MovieController.java** - Controller REST com endpoints GET
4. **application.properties** - Configuração da API Key

### Conceitos Aprendidos
- **RestTemplate**: Cliente HTTP do Spring para fazer requisições
- **@RestController**: Marca classe como controlador REST
- **@GetMapping**: Define endpoints GET
- **@Autowired**: Injeção de dependências automática
- **@Value**: Lê propriedades do application.properties

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
- **@SpringBootTest**: Sobe aplicação completa para testes
- **webEnvironment = RANDOM_PORT**: Porta aleatória para evitar conflitos
- **@LocalServerPort**: Injeta porta onde aplicação está rodando
- **RestTemplate em testes**: Cliente HTTP para testar endpoints
- **Assertions**: assertEquals, assertNotNull, assertFalse, assertTrue

### Testes Implementados
1. **contextLoads()** - Verifica se aplicação inicializa
2. **shouldReturnMoviesWhenSearchingByTitle()** - Testa busca por título
3. **shouldReturnMovieWhenSearchingById()** - Testa busca por ID
4. **shouldStartOnRandomPort()** - Verifica porta aleatória

### Segurança
- **${OMDB_API_KEY:fallback}**: Lê variável de ambiente com fallback
- **.env** no .gitignore: Protege chaves de API
- **Nunca commitar chaves** no repositório

### Resultado
4/4 testes passando - BUILD SUCCESS

---

## 🎯 Aula 3: Modelagem Orientada a Objetos

### Objetivo
Refatorar código para usar objetos ao invés de JSON bruto, aplicando princípios de OO.

### O que foi criado
1. **Movie.java** - Record que representa um filme (imutável)
2. **MovieSearchResult.java** - Record que encapsula lista de filmes
3. **MovieService.java** - Service para processar JSON e converter em objetos
4. **Refatoração do Controller** - Retorna objetos ao invés de String

### Conceitos Aprendidos

#### **Records (Java 17)**
- **O que são**: Classes imutáveis com sintaxe simplificada
- **Vantagens**:
  - Imutabilidade automática
  - Getters, equals, hashCode e toString gerados automaticamente
  - Código mais limpo e conciso
- **Quando usar**: Dados que não devem mudar após criação

#### **Decisões de Design**

**Por que Record?**
- Dados de filmes não devem mudar após criação
- Simplicidade e menos código boilerplate
- Aproveita recursos modernos do Java 17

**Por que Imutável?**
- Segurança: Dados não podem ser alterados acidentalmente
- Thread-safe: Pode ser compartilhado entre threads
- Previsibilidade: Estado não muda após criação

**Por que não tem Setters?**
- Records são imutáveis por design
- Dados vêm da API externa e não devem ser modificados
- Se precisar alterar, cria-se um novo objeto

**Interface?**
- Não necessário neste momento
- YAGNI (You Aren't Gonna Need It)
- Pode ser adicionada futuramente se necessário

#### **Separação de Responsabilidades (SOLID)**
- **Controller**: Recebe requisições HTTP
- **Service**: Processa lógica de negócio (parsing JSON)
- **Model**: Representa dados do domínio

### Estrutura Final
```
src/main/java/com/imdb/api/
├── ImdbApiApplication.java
├── controller/
│   └── MovieController.java
├── service/
│   └── MovieService.java
└── model/
    ├── Movie.java
    └── MovieSearchResult.java
```

### Novos Endpoints
- `GET /api/movies/search?title=Matrix` - Retorna MovieSearchResult
- `GET /api/movies/tt0133093` - Retorna Movie
- `GET /api/movies/raw/search?title=Matrix` - Retorna JSON bruto (legado)

### Exemplo de Resposta (Aula 3)
```json
{
  "movies": [
    {
      "title": "The Matrix",
      "urlImage": "https://...",
      "rating": "8.7",
      "year": "1999"
    }
  ],
  "totalResults": "156"
}
```

### Resultado
API retornando objetos estruturados ao invés de JSON bruto, código mais organizado e orientado a objetos.

---

## 🔄 Evolução do Projeto

### Aula 1 → Aula 2
- ❌ Sem testes → ✅ Testes automatizados
- ❌ API Key hardcoded → ✅ Variáveis de ambiente

### Aula 2 → Aula 3
- ❌ JSON bruto (String) → ✅ Objetos tipados (Movie)
- ❌ Lógica no Controller → ✅ Service separado
- ❌ Dados mutáveis → ✅ Records imutáveis

---

## 🎓 Principais Aprendizados

### Técnicos
1. **Spring Boot**: Framework para criar APIs REST
2. **RestTemplate**: Cliente HTTP para consumir APIs
3. **Testes de Integração**: Validar sistema completo
4. **Records**: Estruturas de dados imutáveis
5. **SOLID**: Separação de responsabilidades
6. **Jackson**: Processar JSON

### Boas Práticas
1. **Nunca commitar chaves de API**
2. **Usar variáveis de ambiente**
3. **Escrever testes automatizados**
4. **Separar responsabilidades** (Controller, Service, Model)
5. **Preferir imutabilidade**
6. **Código limpo e comentado**

### Arquitetura
```
Cliente → Controller → Service → API Externa
                ↓
              Model (Records)
                ↓
            Resposta JSON
```

---

## 📊 Estatísticas do Projeto

- **Linhas de Código**: ~500
- **Classes**: 5
- **Testes**: 4
- **Endpoints**: 3
- **Dependências**: 3 (Spring Web, Jackson, DevTools)
- **Cobertura de Testes**: 100% dos endpoints

---

## 🚀 Como Executar

```bash
# Definir API Key
$env:OMDB_API_KEY="sua_chave_aqui"

# Executar aplicação
mvnw.cmd spring-boot:run

# Executar testes
mvnw.cmd test
```

---

## 📝 Próximos Passos (Dias 4-7)

- Dia 4: Implementar cache
- Dia 5: Adicionar paginação
- Dia 6: Criar interface HTML
- Dia 7: Deploy e documentação

---

## 🔗 Referências

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [OMDb API](https://www.omdbapi.com/)
- [Java Records](https://docs.oracle.com/en/java/javase/17/language/records.html)
- [JUnit 5](https://junit.org/junit5/)
