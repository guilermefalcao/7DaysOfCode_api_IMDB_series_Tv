package com.imdb.api.model;

/**
 * Record que representa um Filme.
 * 
 * DECISÕES DE DESIGN:
 * 
 * 1. POR QUE RECORD?
 *    - Imutabilidade: Dados de filmes não devem mudar após criação
 *    - Simplicidade: Record gera automaticamente getters, equals, hashCode e toString
 *    - Java 17: Aproveita recursos modernos da linguagem
 * 
 * 2. POR QUE IMUTÁVEL?
 *    - Segurança: Dados não podem ser alterados acidentalmente
 *    - Thread-safe: Pode ser compartilhado entre threads sem problemas
 *    - Previsibilidade: Estado do objeto não muda após criação
 * 
 * 3. POR QUE NÃO TEM SETTERS?
 *    - Records são imutáveis por design
 *    - Dados vêm da API externa e não devem ser modificados
 *    - Se precisar alterar, cria-se um novo objeto
 * 
 * 4. INTERFACE?
 *    - Não necessário neste momento
 *    - Pode ser adicionada futuramente se houver múltiplas implementações
 *    - YAGNI (You Aren't Gonna Need It) - não adicionar complexidade desnecessária
 */
public record Movie(
    String title,      // Título do filme
    String urlImage,   // URL da imagem do pôster
    String rating,     // Nota do filme (ex: "8.7")
    String year        // Ano de lançamento
) {
    /**
     * Construtor compacto para validações.
     * Garante que dados essenciais não sejam nulos.
     */
    public Movie {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Título não pode ser nulo ou vazio");
        }
        if (year == null || year.isBlank()) {
            throw new IllegalArgumentException("Ano não pode ser nulo ou vazio");
        }
    }
    
    /**
     * Método auxiliar para criar Movie a partir do JSON da OMDb API.
     * Trata valores nulos retornados pela API.
     */
    public static Movie fromOmdbJson(String title, String poster, String imdbRating, String year) {
        return new Movie(
            title != null ? title : "Título não disponível",
            poster != null && !poster.equals("N/A") ? poster : "",
            imdbRating != null && !imdbRating.equals("N/A") ? imdbRating : "0.0",
            year != null ? year : "Ano desconhecido"
        );
    }
}
