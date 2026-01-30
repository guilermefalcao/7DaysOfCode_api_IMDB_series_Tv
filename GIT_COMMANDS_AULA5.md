# Comandos Git para Aula 5 - Completa

## 📋 Copie e cole estes comandos no Git Bash

### Opção 1: Commit Único (Recomendado)
```bash
# Entrar na pasta do projeto
cd "c:/1. Guilherme/00. Dataprev/0000. projeto conta/cursoSpringboot/7DaysOfCode_api_IMDB_series_Tv"

# Adicionar todos os arquivos modificados
git add .

# Commit com mensagem descritiva
git commit -m "feat: Aula 5 completa - Refatoração, filtros e lista em memória

- Criado ImdbApiClient para encapsular chamadas HTTP
- Criado MovieRepository para armazenamento em memória
- Adicionado campo ID no Movie (gerado com AtomicLong)
- Implementado filtro por título usando Stream.filter
- Novos endpoints: /memory, /memory/filter, /memory/html, /memory/clear
- Atualizado MovieService para gerar IDs e salvar automaticamente
- Aplicados princípios SOLID (Single Responsibility)
- Documentação atualizada (README.md e README_AULAS.md)"

# Enviar para o GitHub
git push origin main
```

---

### Opção 2: Commits Separados (Mais Detalhado)

#### Commit 1: Refatoração (Parte 1)
```bash
# Entrar na pasta do projeto
cd "c:/1. Guilherme/00. Dataprev/0000. projeto conta/cursoSpringboot/7DaysOfCode_api_IMDB_series_Tv"

# Adicionar apenas o ImdbApiClient
git add src/main/java/com/imdb/api/client/ImdbApiClient.java
git add src/main/java/com/imdb/api/controller/MovieController.java

git commit -m "feat(aula5-parte1): Criar ImdbApiClient e refatorar Controller

- Criado ImdbApiClient para encapsular chamadas HTTP à OMDb API
- Refatorado MovieController para usar ImdbApiClient
- Aplicado Single Responsibility Principle
- Código mais limpo e testável"

git push origin main
```

#### Commit 2: Repositório em Memória (Parte 2)
```bash
# Adicionar MovieRepository
git add src/main/java/com/imdb/api/repository/MovieRepository.java

git commit -m "feat(aula5-parte2): Criar MovieRepository para armazenamento em memória

- Criado MovieRepository com lista em memória
- Implementado CRUD completo (save, findAll, findById, delete)
- Usado AtomicLong para gerar IDs thread-safe
- Implementado filtro por título com Stream.filter"

git push origin main
```

#### Commit 3: Adicionar ID ao Movie
```bash
# Adicionar alterações no Movie
git add src/main/java/com/imdb/api/model/Movie.java

git commit -m "feat(aula5-parte2): Adicionar campo ID ao Movie

- Adicionado campo Long id ao record Movie
- Atualizado método fromOmdbJson para receber ID
- Documentado decisões de design"

git push origin main
```

#### Commit 4: Atualizar MovieService
```bash
# Adicionar alterações no MovieService
git add src/main/java/com/imdb/api/service/MovieService.java

git commit -m "feat(aula5-parte2): Atualizar MovieService para gerar IDs e salvar

- Injetado MovieRepository no MovieService
- Implementado geração de IDs com Stream.map
- Salvamento automático no repositório após busca
- Usado AtomicLong para IDs incrementais"

git push origin main
```

#### Commit 5: Novos Endpoints
```bash
# Adicionar alterações no Controller
git add src/main/java/com/imdb/api/controller/MovieController.java

git commit -m "feat(aula5-parte2): Adicionar endpoints para consultar lista em memória

- GET /api/movies/memory - Listar todos os filmes
- GET /api/movies/memory/filter?title=X - Filtrar por título
- GET /api/movies/memory/html - Visualizar em HTML
- GET /api/movies/memory/clear - Limpar memória
- Implementado filtro com @RequestParam(required=false)"

git push origin main
```

#### Commit 6: Documentação
```bash
# Adicionar documentação
git add README.md README_AULAS.md

git commit -m "docs(aula5): Atualizar documentação completa

- Atualizado README.md com novos endpoints
- Adicionado guia de testes da Aula 5 Parte 2
- Documentado conceitos: @Repository, AtomicLong, Stream.map/filter
- Adicionado exemplos de uso no Postman e cURL
- Atualizado estrutura do projeto"

git push origin main
```

---

## 🏷️ Criar Tag da Aula 5

```bash
# Criar tag anotada
git tag -a aula5-completa -m "Aula 5 completa: Refatoração, filtros e lista em memória"

# Enviar tag para o GitHub
git push origin aula5-completa
```

---

## 📊 Verificar Status

```bash
# Ver status dos arquivos
git status

# Ver histórico de commits
git log --oneline -5

# Ver diferenças antes de commitar
git diff
```

---

## 🔄 Se precisar desfazer algo

```bash
# Desfazer último commit (mantém alterações)
git reset --soft HEAD~1

# Desfazer alterações em arquivo específico
git checkout -- arquivo.java

# Ver o que foi commitado
git show HEAD
```

---

## ✅ Checklist antes de commitar

- [ ] Código compila sem erros
- [ ] Testes passando (mvnw test)
- [ ] Aplicação roda (mvnw spring-boot:run)
- [ ] Endpoints testados no Postman/navegador
- [ ] README.md atualizado
- [ ] README_AULAS.md atualizado
- [ ] Sem API Keys hardcoded
- [ ] Comentários e documentação adequados

---

## 🎯 Recomendação

Use a **Opção 1 (Commit Único)** se você quer simplicidade e rapidez.

Use a **Opção 2 (Commits Separados)** se você quer um histórico mais detalhado e organizado.

---

## 📝 Mensagem de Commit Sugerida (Copie e Cole)

```
feat: Aula 5 completa - Refatoração, filtros e lista em memória

- Criado ImdbApiClient para encapsular chamadas HTTP
- Criado MovieRepository para armazenamento em memória
- Adicionado campo ID no Movie (gerado com AtomicLong)
- Implementado filtro por título usando Stream.filter
- Novos endpoints: /memory, /memory/filter, /memory/html, /memory/clear
- Atualizado MovieService para gerar IDs e salvar automaticamente
- Aplicados princípios SOLID (Single Responsibility)
- Documentação atualizada (README.md e README_AULAS.md)
```
