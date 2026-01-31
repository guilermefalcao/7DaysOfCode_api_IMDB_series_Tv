# Comandos Git para Aula 6 - Sistema de Favoritos

## 📋 Copie e cole estes comandos no Git Bash

### Passo 1: Entrar na pasta do projeto
```bash
cd "c:/1. Guilherme/00. Dataprev/0000. projeto conta/cursoSpringboot/7DaysOfCode_api_IMDB_series_Tv"
```

### Passo 2: Verificar status
```bash
git status
```

### Passo 3: Adicionar arquivos
```bash
git add .
```

### Passo 4: Commit
```bash
git commit -m "feat: Aula 6 completa - Sistema de Favoritos com CRUD

- Criado FavoritoRepository para gerenciar favoritos
- Criado FavoritoController com POST, GET, PUT e DELETE
- Implementado POST /api/favoritos/{id} - Adicionar favorito
- Implementado GET /api/favoritos - Listar todos
- Implementado GET /api/favoritos/{id} - Buscar por ID
- Implementado GET /api/favoritos/check/{id} - Verificar favorito
- Implementado DELETE /api/favoritos/{id} - Remover favorito
- Implementado DELETE /api/favoritos - Remover todos
- Implementado PUT /api/favoritos - Substituir lista (opcional)
- Criado FavoritoControllerTests com 10 testes
- Testes para POST, GET, PUT e DELETE
- Documentação completa com comentários"
```

### Passo 5: Push para GitHub
```bash
git push origin main
```

### Passo 6: Criar tag
```bash
git tag -a aula6-completa -m "Aula 6 completa - Sistema de Favoritos"
```

### Passo 7: Enviar tag
```bash
git push origin aula6-completa
```

---

## 🎯 Comando Único (Copie Tudo de Uma Vez)

```bash
cd "c:/1. Guilherme/00. Dataprev/0000. projeto conta/cursoSpringboot/7DaysOfCode_api_IMDB_series_Tv" && git add . && git commit -m "feat: Aula 6 completa - Sistema de Favoritos com CRUD

- Criado FavoritoRepository para gerenciar favoritos
- Criado FavoritoController com POST, GET, PUT e DELETE
- Implementado POST /api/favoritos/{id} - Adicionar favorito
- Implementado GET /api/favoritos - Listar todos
- Implementado GET /api/favoritos/{id} - Buscar por ID
- Implementado GET /api/favoritos/check/{id} - Verificar favorito
- Implementado DELETE /api/favoritos/{id} - Remover favorito
- Implementado DELETE /api/favoritos - Remover todos
- Implementado PUT /api/favoritos - Substituir lista (opcional)
- Criado FavoritoControllerTests com 10 testes
- Testes para POST, GET, PUT e DELETE
- Documentação completa com comentários" && git push origin main && git tag -a aula6-completa -m "Aula 6 completa - Sistema de Favoritos" && git push origin aula6-completa
```

---

## ✅ Checklist antes de commitar

- [ ] Código compila sem erros
- [ ] Testes passando (mvnw test)
- [ ] Aplicação roda (mvnw spring-boot:run)
- [ ] Endpoints testados no Postman
- [ ] Logs aparecem no console
- [ ] Documentação atualizada

---

## 🧪 Executar testes antes de commitar

```bash
mvnw.cmd test
```

Aguarde: `BUILD SUCCESS` e `Tests run: X, Failures: 0, Errors: 0`

---

## 📊 Verificar o que será commitado

```bash
git diff
```

---

## 🔄 Se precisar desfazer

```bash
# Desfazer último commit (mantém alterações)
git reset --soft HEAD~1

# Desfazer alterações em arquivo específico
git checkout -- arquivo.java
```

---

## 🎉 Pronto!

Após executar os comandos, seu código estará no GitHub com a tag `aula6-completa`!
