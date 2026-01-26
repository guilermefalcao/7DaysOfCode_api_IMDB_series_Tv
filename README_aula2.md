# README - Aula 2: Testes de Integração

## 🎯 Objetivo da Aula 2
Implementar **testes de integração** para validar os endpoints da API IMDB criada no Dia 1.

## 📚 Conceitos Aprendidos

### **1. Testes de Integração**
- **O que são**: Testes que validam a integração entre componentes
- **Diferença dos unitários**: Testam o sistema completo funcionando
- **Vantagem**: Garantem que a API funciona de ponta a ponta

### **2. Anotações Spring Boot para Testes**

#### `@SpringBootTest(webEnvironment = RANDOM_PORT)`
- **Função**: Sobe a aplicação completa em uma porta aleatória
- **Por que porta aleatória**: Evita conflitos se a porta 8080 estiver ocupada
- **Resultado**: Aplicação real rodando para os testes

#### `@LocalServerPort`
- **Função**: Injeta a porta onde a aplicação está rodando
- **Uso**: `private int port;`
- **Vantagem**: Não precisa hardcodar a porta nos testes

### **3. RestTemplate vs TestRestTemplate**

#### **RestTemplate** (Usado na solução)
```java
private final RestTemplate restTemplate = new RestTemplate();
```
- **Vantagem**: Funciona em qualquer versão do Spring
- **Uso**: Cliente HTTP padrão

#### **TestRestTemplate** (Tentativa inicial)
```java
@Autowired
private TestRestTemplate restTemplate;
```
- **Problema**: Não disponível no Spring Boot 4.0.2
- **Solução**: Downgrade para Spring Boot 3.2.2

## 🔧 Implementação Realizada

### **1. Estrutura dos Testes**
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ImdbApiApplicationTests {
    
    @LocalServerPort
    private int port;
    
    private final RestTemplate restTemplate = new RestTemplate();
}
```

### **2. Tipos de Testes Implementados**

#### **Teste de Contexto**
```java
@Test
void contextLoads() {
    // Verifica se a aplicação Spring Boot inicializa
}
```

#### **Teste de Endpoint - Busca por Título**
```java
@Test
void shouldReturnMoviesWhenSearchingByTitle() {
    String url = "http://localhost:" + port + "/api/movies/search?title=Matrix";
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertFalse(response.getBody().isEmpty());
}
```

#### **Teste de Endpoint - Busca por ID**
```java
@Test
void shouldReturnMovieWhenSearchingById() {
    String url = "http://localhost:" + port + "/api/movies/tt0133093";
    // Validações similares...
}
```

### **3. Validações Implementadas**
- ✅ **Status HTTP 200 OK**
- ✅ **Corpo da resposta não nulo**
- ✅ **Corpo da resposta não vazio**
- ✅ **Conteúdo JSON válido da OMDb API**

## 🔐 Segurança com Variáveis de Ambiente

### **Problema Identificado**
- API Keys hardcoded no código
- Risco de commitar chaves no Git

### **Solução Implementada**
```properties
# application.properties
omdb.api.key=${OMDB_API_KEY:YOUR_API_KEY}
```

### **Como Funciona**
- `${OMDB_API_KEY}`: Lê da variável de ambiente
- `:YOUR_API_KEY`: Fallback se não encontrar a variável

### **Configuração**
```bash
# PowerShell
$env:OMDB_API_KEY="sua_chave_aqui"

# CMD
set OMDB_API_KEY=sua_chave_aqui
```

## 🚀 Execução dos Testes

### **Comando**
```bash
mvnw.cmd test
```

### **Resultado Esperado**
```
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

### **Saída dos Testes**
```
✅ Contexto da aplicação carregado com sucesso!
✅ Aplicação rodando na porta: 51988
✅ Teste de busca por título passou!
✅ Teste de busca por ID passou!
```

## 🔄 Problemas Enfrentados e Soluções

### **1. TestRestTemplate não encontrado**
- **Problema**: `NoClassDefFoundError: TestRestTemplate`
- **Causa**: Spring Boot 4.0.2 não tem essa classe
- **Solução**: Downgrade para Spring Boot 3.2.2

### **2. API Key inválida nos testes**
- **Problema**: Testes usando `YOUR_API_KEY`
- **Causa**: Configuração não estava lendo a variável de ambiente
- **Solução**: Configurar fallback nos arquivos de teste

### **3. Certificado SSL**
- **Problema**: Erro de certificado HTTPS
- **Solução**: Usar HTTP ao invés de HTTPS na OMDb API

## 📁 Arquivos Criados/Modificados

### **Novos Arquivos**
- `src/test/resources/application-test.properties`

### **Arquivos Modificados**
- `ImdbApiApplicationTests.java` - Implementação completa dos testes
- `pom.xml` - Downgrade Spring Boot 4.0.2 → 3.2.2
- `application.properties` - Configuração de variáveis de ambiente
- `.gitignore` - Proteção de arquivos sensíveis

## 🎓 Lições Aprendidas

1. **Testes de Integração** são essenciais para validar APIs
2. **Variáveis de Ambiente** são fundamentais para segurança
3. **Porta aleatória** evita conflitos em testes
4. **Fallback** garante funcionamento em diferentes ambientes
5. **RestTemplate** é mais compatível que TestRestTemplate

## 🔗 Próximos Passos
- Dia 3: Implementação de novos recursos
- Melhorias nos testes
- Refatoração do código