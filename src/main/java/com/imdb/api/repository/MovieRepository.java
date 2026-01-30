package com.imdb.api.repository;

import com.imdb.api.model.Movie;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Repositório em memória para armazenar filmes.
 * 
 * DECISÕES DE DESIGN:
 * 
 * 1. POR QUE @Repository?
 *    - Marca classe como componente de acesso a dados
 *    - Spring gerencia ciclo de vida
 *    - Semântica clara: esta classe gerencia dados
 * 
 * 2. POR QUE LISTA EM MEMÓRIA?
 *    - Simplicidade: Não precisa configurar banco de dados
 *    - Aprendizado: Foco em lógica de negócio
 *    - Temporário: Dados perdidos ao reiniciar aplicação
 * 
 * 3. POR QUE AtomicLong PARA ID?
 *    - Thread-safe: Múltiplas requisições simultâneas
 *    - Incremento automático: Garante IDs únicos
 *    - Simples: Não precisa sincronização manual
 * 
 * 4. LIMITAÇÕES:
 *    - Dados não persistem (perdidos ao reiniciar)
 *    - Não escalável (apenas uma instância)
 *    - Sem transações
 *    - Para produção: usar banco de dados real
 */
@Repository
public class MovieRepository {

    // Lista em memória para armazenar filmes
    // IMPORTANTE: Dados são perdidos ao reiniciar a aplicação
    private final List<Movie> movies = new ArrayList<>();

    // Gerador de IDs único e thread-safe
    // AtomicLong garante que múltiplas threads não gerem o mesmo ID
    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * Salva um filme na lista em memória, gerando ID automaticamente.
     * 
     * @param movie Filme sem ID
     * @return Filme com ID gerado
     */
    public Movie save(Movie movie) {
        // Gera novo ID incrementando o contador
        Long newId = idGenerator.getAndIncrement();
        
        // Cria novo Movie com ID (Records são imutáveis)
        Movie movieWithId = new Movie(
            newId,
            movie.title(),
            movie.urlImage(),
            movie.rating(),
            movie.year()
        );
        
        // Adiciona na lista
        movies.add(movieWithId);
        
        System.out.println("💾 Filme salvo: ID=" + newId + ", Título=" + movie.title());
        
        return movieWithId;
    }

    /**
     * Salva múltiplos filmes de uma vez.
     * Usa Stream.map para gerar IDs incrementais.
     * 
     * @param movieList Lista de filmes sem ID
     * @return Lista de filmes com IDs gerados
     */
    public List<Movie> saveAll(List<Movie> movieList) {
        return movieList.stream()
                .map(this::save)  // Para cada filme, chama save()
                .collect(Collectors.toList());
    }

    /**
     * Busca todos os filmes armazenados.
     * 
     * @return Lista de todos os filmes
     */
    public List<Movie> findAll() {
        return new ArrayList<>(movies); // Retorna cópia para evitar modificações externas
    }

    /**
     * Busca filme por ID.
     * 
     * @param id ID do filme
     * @return Optional com filme se encontrado
     */
    public Optional<Movie> findById(Long id) {
        return movies.stream()
                .filter(movie -> movie.id().equals(id))
                .findFirst();
    }

    /**
     * Filtra filmes por título (case-insensitive).
     * Busca filmes que CONTENHAM o texto no título.
     * 
     * EXERCÍCIO DA AULA 5: Filtro por título
     * 
     * @param titleFilter Texto para filtrar
     * @return Lista de filmes que contêm o texto no título
     */
    public List<Movie> findByTitleContaining(String titleFilter) {
        if (titleFilter == null || titleFilter.isBlank()) {
            return findAll();
        }
        
        return movies.stream()
                .filter(movie -> movie.title()
                        .toLowerCase()
                        .contains(titleFilter.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Conta quantos filmes estão armazenados.
     * 
     * @return Quantidade de filmes
     */
    public long count() {
        return movies.size();
    }

    /**
     * Remove todos os filmes da memória.
     * Útil para testes ou reset.
     */
    public void deleteAll() {
        movies.clear();
        System.out.println("🗑️ Todos os filmes foram removidos da memória");
    }

    /**
     * Remove filme por ID.
     * 
     * @param id ID do filme a ser removido
     * @return true se removido, false se não encontrado
     */
    public boolean deleteById(Long id) {
        boolean removed = movies.removeIf(movie -> movie.id().equals(id));
        if (removed) {
            System.out.println("🗑️ Filme removido: ID=" + id);
        }
        return removed;
    }
}
