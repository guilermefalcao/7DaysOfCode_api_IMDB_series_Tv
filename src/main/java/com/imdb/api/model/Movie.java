package com.imdb.api.model;

/**
 * Record que representa um Filme.
 * 
 * ATUALIZAÇÃO DIA 5 (Parte 2): Adicionado campo ID para armazenamento em memória
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
 * 4. POR QUE ADICIONAR ID?
 *    - Identificar filmes unicamente na lista em memória
 *    - Facilita operações de busca, atualização e deleção
 *    - Permite referência direta a um filme específico
 */
public record Movie(
    Long id,           // ID único do filme (gerado automaticamente)
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
     * 
     * @param id ID único do filme
     * @param title Título do filme
     * @param poster URL do poster
     * @param imdbRating Nota do IMDB
     * @param year Ano de lançamento
     * @return Movie com dados tratados
     */
    public static Movie fromOmdbJson(Long id, String title, String poster, String imdbRating, String year) {
        return new Movie(
            id,
            title != null ? title : "Título não disponível",
            poster != null && !poster.equals("N/A") ? poster : "",
            imdbRating != null && !imdbRating.equals("N/A") ? imdbRating : "0.0",
            year != null ? year : "Ano desconhecido"
        );
    }
}
