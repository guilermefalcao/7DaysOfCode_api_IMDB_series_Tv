package com.imdb.api.generator;

import com.imdb.api.model.Movie;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

/**
 * Classe responsável por gerar HTML a partir de uma lista de filmes.
 * 
 * DECISÕES DE DESIGN:
 * 
 * 1. POR QUE RECEBER WRITER NO CONSTRUTOR?
 *    - Flexibilidade: Pode escrever em arquivo, string, ou qualquer destino
 *    - Testabilidade: Facilita testes unitários
 *    - Responsabilidade: Quem cria o Writer decide onde escrever
 * 
 * 2. POR QUE TEXT BLOCKS?
 *    - Legibilidade: HTML fica mais claro e fácil de manter
 *    - Java 17: Aproveita recursos modernos da linguagem
 *    - Menos escape: Não precisa escapar aspas duplas
 * 
 * 3. É BOA PRÁTICA GERAR HTML EM JAVA?
 *    - Não é ideal: Existem templates engines (Thymeleaf, Freemarker)
 *    - Para aprendizado: Válido para entender conceitos de OO
 *    - Produção: Use template engines
 */
public class HTMLGenerator {

    private final PrintWriter writer;

    /**
     * Construtor que recebe um Writer para escrever o HTML.
     * 
     * @param writer Destino onde o HTML será escrito
     */
    public HTMLGenerator(Writer writer) {
        this.writer = new PrintWriter(writer);
    }

    /**
     * Gera HTML completo com lista de filmes usando Bootstrap.
     * 
     * @param movies Lista de filmes a serem exibidos
     */
    public void generate(List<Movie> movies) {
        writer.println(generateHead());
        writer.println(generateBodyStart());
        writer.println(generateHeader());
        writer.println(generateMoviesGrid(movies));
        writer.println(generateBodyEnd());
        writer.flush(); // Garante que tudo foi escrito
    }

    /**
     * Gera o HEAD do HTML com meta tags e Bootstrap CSS.
     * Usa Text Blocks do Java 17 para melhor legibilidade.
     */
    private String generateHead() {
        return """
            <!DOCTYPE html>
            <html lang="pt-BR">
            <head>
                <meta charset="utf-8">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <title>IMDB Top Movies</title>
                <!-- Bootstrap CSS -->
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                <style>
                    body {
                        background-color: #f8f9fa;
                    }
                    .movie-card {
                        transition: transform 0.2s;
                        height: 100%;
                    }
                    .movie-card:hover {
                        transform: scale(1.05);
                        box-shadow: 0 8px 16px rgba(0,0,0,0.2);
                    }
                    .movie-poster {
                        height: 400px;
                        object-fit: cover;
                    }
                    .rating-badge {
                        position: absolute;
                        top: 10px;
                        right: 10px;
                        font-size: 1.2rem;
                    }
                </style>
            </head>
            """;
    }

    /**
     * Gera a abertura do BODY.
     */
    private String generateBodyStart() {
        return "<body>";
    }

    /**
     * Gera o cabeçalho da página com título.
     */
    private String generateHeader() {
        return """
            <div class="container-fluid bg-dark text-white py-4 mb-4">
                <div class="container">
                    <h1 class="display-4">🎬 IMDB Top Movies</h1>
                    <p class="lead">Explore os melhores filmes de todos os tempos</p>
                </div>
            </div>
            """;
    }

    /**
     * Gera o grid de filmes usando Bootstrap Cards.
     * Cada filme é exibido em um card com poster, título, ano e nota.
     * 
     * @param movies Lista de filmes
     * @return HTML do grid de filmes
     */
    private String generateMoviesGrid(List<Movie> movies) {
        StringBuilder html = new StringBuilder();
        
        html.append("<div class=\"container\">\n");
        html.append("    <div class=\"row row-cols-1 row-cols-md-3 row-cols-lg-4 g-4\">\n");
        
        // Itera sobre cada filme e gera um card
        for (Movie movie : movies) {
            html.append(generateMovieCard(movie));
        }
        
        html.append("    </div>\n");
        html.append("</div>\n");
        
        return html.toString();
    }

    /**
     * Gera um card Bootstrap para um filme específico.
     * 
     * Estrutura do Card:
     * - Imagem do poster (se disponível)
     * - Badge com nota (se disponível)
     * - Título do filme
     * - Ano de lançamento
     * 
     * @param movie Filme a ser exibido
     * @return HTML do card
     */
    private String generateMovieCard(Movie movie) {
        // Define imagem padrão se não houver poster
        String posterUrl = (movie.urlImage() != null && !movie.urlImage().isEmpty()) 
            ? movie.urlImage() 
            : "https://via.placeholder.com/300x450?text=No+Poster";
        
        // Define nota ou "N/A"
        String rating = (movie.rating() != null && !movie.rating().equals("N/A")) 
            ? movie.rating() 
            : "N/A";
        
        // Usa Text Block para gerar o card
        return """
            <div class="col">
                <div class="card movie-card h-100">
                    <div class="position-relative">
                        <img src="%s" class="card-img-top movie-poster" alt="%s">
                        <span class="badge bg-warning text-dark rating-badge">⭐ %s</span>
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">%s</h5>
                        <p class="card-text text-muted">
                            <small>📅 %s</small>
                        </p>
                    </div>
                </div>
            </div>
            """.formatted(posterUrl, movie.title(), rating, movie.title(), movie.year());
    }

    /**
     * Gera o fechamento do BODY e HTML.
     */
    private String generateBodyEnd() {
        return """
            <!-- Bootstrap JS -->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
            </body>
            </html>
            """;
    }
}
