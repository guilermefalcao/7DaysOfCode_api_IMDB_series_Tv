package com.imdb.api.model;

import java.util.List;

/**
 * Record que representa o resultado de uma busca de filmes.
 * 
 * Encapsula a lista de filmes retornada pela API OMDb.
 * Também é imutável para garantir consistência dos dados.
 */
public record MovieSearchResult(
    List<Movie> movies,      // Lista de filmes encontrados
    String totalResults      // Total de resultados disponíveis
) {
    /**
     * Construtor compacto para garantir lista não nula.
     */
    public MovieSearchResult {
        if (movies == null) {
            movies = List.of(); // Lista vazia ao invés de null
        }
        if (totalResults == null) {
            totalResults = "0";
        }
    }
    
    /**
     * Retorna quantidade de filmes na lista.
     */
    public int count() {
        return movies.size();
    }
    
    /**
     * Verifica se a busca retornou resultados.
     */
    public boolean hasResults() {
        return !movies.isEmpty();
    }
}
