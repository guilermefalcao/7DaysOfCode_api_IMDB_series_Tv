package com.imdb.api.controller;

import com.imdb.api.client.ImdbApiClient;
import com.imdb.api.generator.HTMLGenerator;
import com.imdb.api.model.Movie;
import com.imdb.api.model.MovieSearchResult;
import com.imdb.api.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.StringWriter;

/**
 * Controller REST para endpoints de filmes.
 * 
 * REFATORAÇÃO DIA 5:
 * - Removida lógica de chamada HTTP (agora no ImdbApiClient)
 * - Separados endpoints JSON e HTML
 * - Controller mais limpo e focado em orquestrar
 * - Seguindo Single Responsibility Principle
 */
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    // Injeta o cliente HTTP para comunicação com OMDb API
    // ANTES: Tinha RestTemplate e construía URLs manualmente
    // AGORA: Usa ImdbApiClient que encapsula toda a lógica HTTP
    @Autowired
    private ImdbApiClient imdbApiClient;

    // Injeta o MovieService para processar JSON
    @Autowired
    private MovieService movieService;

    /**
     * Endpoint JSON: Busca filmes por título e retorna objetos Movie.
     * 
     * SEPARAÇÃO DE RESPONSABILIDADES:
     * - Controller: Orquestra o fluxo
     * - ImdbApiClient: Faz chamada HTTP
     * - MovieService: Processa JSON
     * - Model: Representa dados
     * 
     * Exemplo: GET /api/movies/search?title=Matrix
     */
    @GetMapping("/search")
    public MovieSearchResult searchMovies(@RequestParam String title) {
        // 1. Busca JSON da API (encapsulado no client)
        String json = imdbApiClient.searchMoviesByTitle(title);
        
        // 2. Processa JSON e converte em objetos Movie
        MovieSearchResult result = movieService.parseSearchResults(json);
        
        // 3. Log para debug
        System.out.println("✅ Busca por título: " + title);
        System.out.println("   Total de resultados: " + result.totalResults());
        System.out.println("   Filmes encontrados: " + result.count());
        
        // 4. Retorna objeto (Spring converte para JSON automaticamente)
        return result;
    }

    /**
     * Endpoint JSON: Busca filme por ID do IMDB e retorna objeto Movie.
     * 
     * Exemplo: GET /api/movies/tt0133093
     */
    @GetMapping("/{imdbId}")
    public Movie getMovieById(@PathVariable String imdbId) {
        // 1. Busca JSON da API (encapsulado no client)
        String json = imdbApiClient.getMovieById(imdbId);
        
        // 2. Processa JSON e converte em objeto Movie
        Movie movie = movieService.parseMovieDetails(json);
        
        // 3. Log para debug
        System.out.println("✅ Busca por ID: " + imdbId);
        System.out.println("   Filme: " + movie.title() + " (" + movie.year() + ")");
        System.out.println("   Nota: " + movie.rating());
        
        // 4. Retorna objeto (Spring converte para JSON automaticamente)
        return movie;
    }

    /**
     * Endpoint HTML: Busca filmes e retorna página HTML com Bootstrap.
     * 
     * SEPARAÇÃO: Endpoint dedicado para HTML (não mistura com JSON)
     * 
     * Exemplo: GET /api/movies/html?title=Matrix
     */
    @GetMapping(value = "/html", produces = MediaType.TEXT_HTML_VALUE)
    public String searchMoviesHTML(@RequestParam String title) {
        // 1. Busca JSON da API
        String json = imdbApiClient.searchMoviesByTitle(title);
        
        // 2. Processa JSON
        MovieSearchResult result = movieService.parseSearchResults(json);
        
        // 3. Gera HTML
        StringWriter stringWriter = new StringWriter();
        HTMLGenerator htmlGenerator = new HTMLGenerator(stringWriter);
        htmlGenerator.generate(result.movies());
        
        // 4. Retorna HTML
        return stringWriter.toString();
    }

    /**
     * Endpoint legado: Retorna JSON bruto da OMDb API.
     * Mantido para compatibilidade com versões anteriores.
     * 
     * Exemplo: GET /api/movies/raw/search?title=Matrix
     */
    @GetMapping("/raw/search")
    public String searchMoviesRaw(@RequestParam String title) {
        return imdbApiClient.searchMoviesByTitle(title);
    }

}
