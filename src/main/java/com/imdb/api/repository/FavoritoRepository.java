package com.imdb.api.repository;

import com.imdb.api.model.Movie;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositório em memória para armazenar filmes favoritos.
 * 
 * AULA 6: Sistema de Favoritos
 * 
 * DECISÕES DE DESIGN:
 * 
 * 1. POR QUE REPOSITÓRIO SEPARADO?
 *    - Separação de responsabilidades
 *    - Lista de favoritos é diferente da lista geral
 *    - Facilita operações específicas de favoritos
 * 
 * 2. POR QUE NÃO GERAR NOVOS IDs?
 *    - Favoritos referenciam filmes existentes
 *    - Mantém o ID original do filme
 *    - Evita duplicação de dados
 * 
 * 3. OPERAÇÕES SUPORTADAS:
 *    - Adicionar favorito (POST)
 *    - Listar favoritos (GET)
 *    - Remover favorito (DELETE)
 *    - Verificar se é favorito (GET)
 */
@Repository
public class FavoritoRepository {

    // Lista em memória para armazenar filmes favoritos
    private final List<Movie> favoritos = new ArrayList<>();

    /**
     * Adiciona um filme aos favoritos.
     * Verifica se já não está na lista antes de adicionar.
     * 
     * @param movie Filme a ser adicionado
     * @return true se adicionado, false se já existia
     */
    public boolean addFavorito(Movie movie) {
        // Verifica se já é favorito
        if (isFavorito(movie.id())) {
            System.out.println("⚠️ Filme já está nos favoritos: " + movie.title());
            return false;
        }
        
        favoritos.add(movie);
        System.out.println("⭐ Filme adicionado aos favoritos: " + movie.title());
        return true;
    }

    /**
     * Lista todos os filmes favoritos.
     * 
     * @return Lista de filmes favoritos
     */
    public List<Movie> findAll() {
        return new ArrayList<>(favoritos);
    }

    /**
     * Busca favorito por ID.
     * 
     * @param id ID do filme
     * @return Optional com filme se encontrado
     */
    public Optional<Movie> findById(Long id) {
        return favoritos.stream()
                .filter(movie -> movie.id().equals(id))
                .findFirst();
    }

    /**
     * Verifica se um filme é favorito.
     * 
     * @param id ID do filme
     * @return true se é favorito, false caso contrário
     */
    public boolean isFavorito(Long id) {
        return favoritos.stream()
                .anyMatch(movie -> movie.id().equals(id));
    }

    /**
     * Remove um filme dos favoritos.
     * 
     * @param id ID do filme a ser removido
     * @return true se removido, false se não encontrado
     */
    public boolean removeFavorito(Long id) {
        boolean removed = favoritos.removeIf(movie -> movie.id().equals(id));
        if (removed) {
            System.out.println("🗑️ Filme removido dos favoritos: ID=" + id);
        } else {
            System.out.println("⚠️ Filme não encontrado nos favoritos: ID=" + id);
        }
        return removed;
    }

    /**
     * Remove todos os favoritos.
     */
    public void deleteAll() {
        favoritos.clear();
        System.out.println("🗑️ Todos os favoritos foram removidos");
    }

    /**
     * Conta quantos favoritos existem.
     * 
     * @return Quantidade de favoritos
     */
    public long count() {
        return favoritos.size();
    }
}
