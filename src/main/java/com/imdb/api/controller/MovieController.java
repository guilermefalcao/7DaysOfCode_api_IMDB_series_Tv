package com.imdb.api.controller;

import com.imdb.api.client.ImdbApiClient;
import com.imdb.api.generator.HTMLGenerator;
import com.imdb.api.model.Movie;
import com.imdb.api.model.MovieSearchResult;
import com.imdb.api.repository.MovieRepository;
import com.imdb.api.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.StringWriter;
import java.util.List;

/**
 * Controller REST para endpoints de filmes.
 * 
 * REFATORAÇÃO DIA 5 (Parte 2):
 * - Adicionado filtro por título
 * - Endpoints para consultar lista em memória
 * - Integração com MovieRepository
 */
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private ImdbApiClient imdbApiClient;

    @Autowired
    private MovieService movieService;
    
    // NOVO: Injeta repositório para acessar lista em memória
    @Autowired
    private MovieRepository movieRepository;

    /**
     * Endpoint JSON: Busca filmes por título e retorna objetos Movie.
     * Filmes são salvos automaticamente no repositório em memória.
     */
    @GetMapping("/search")
    public MovieSearchResult searchMovies(@RequestParam String title) {
        String json = imdbApiClient.searchMoviesByTitle(title);
        MovieSearchResult result = movieService.parseSearchResults(json);
        
        System.out.println("✅ Busca por título: " + title);
        System.out.println("   Total de resultados: " + result.totalResults());
        System.out.println("   Filmes encontrados: " + result.count());
        System.out.println("💾 Total em memória: " + movieRepository.count());
        
        return result;
    }

    /**
     * Endpoint JSON: Busca filme por ID do IMDB.
     */
    @GetMapping("/{imdbId}")
    public Movie getMovieById(@PathVariable String imdbId) {
        String json = imdbApiClient.getMovieById(imdbId);
        Movie movie = movieService.parseMovieDetails(json);
        
        System.out.println("✅ Busca por ID: " + imdbId);
        System.out.println("   Filme: " + movie.title() + " (" + movie.year() + ")");
        
        return movie;
    }

    /**
     * NOVO: Endpoint para listar TODOS os filmes armazenados em memória.
     * 
     * Exemplo: GET /api/movies/memory
     */
    @GetMapping("/memory")
    public List<Movie> getAllMoviesInMemory() {
        List<Movie> movies = movieRepository.findAll();
        System.out.println("💾 Total de filmes em memória: " + movies.size());
        return movies;
    }

    /**
     * NOVO: Endpoint para FILTRAR filmes em memória por título.
     * 
     * EXERCÍCIO DA AULA 5: Filtro por título usando QueryParam
     * 
     * Exemplo: GET /api/movies/memory/filter?title=Matrix
     */
    @GetMapping("/memory/filter")
    public List<Movie> filterMoviesByTitle(@RequestParam String title) {
        List<Movie> filtered = movieRepository.findByTitleContaining(title);
        
        System.out.println("🔍 Filtro aplicado: " + title);
        System.out.println("   Filmes encontrados: " + filtered.size());
        
        return filtered;
    }

    /**
     * NOVO: Endpoint HTML com filtro por título.
     * Busca na API, salva em memória e gera HTML.
     */
    @GetMapping(value = "/html", produces = MediaType.TEXT_HTML_VALUE)
    public String searchMoviesHTML(@RequestParam String title) {
        String json = imdbApiClient.searchMoviesByTitle(title);
        MovieSearchResult result = movieService.parseSearchResults(json);
        
        StringWriter stringWriter = new StringWriter();
        HTMLGenerator htmlGenerator = new HTMLGenerator(stringWriter);
        htmlGenerator.generate(result.movies());
        
        return stringWriter.toString();
    }

    /**
     * NOVO: Endpoint HTML para visualizar filmes em memória com filtro opcional.
     * 
     * Exemplo: GET /api/movies/memory/html?title=Matrix
     */
    @GetMapping(value = "/memory/html", produces = MediaType.TEXT_HTML_VALUE)
    public String viewMoviesInMemoryHTML(@RequestParam(required = false) String title) {
        // Se title for fornecido, filtra; senão, retorna todos
        List<Movie> movies = (title != null && !title.isBlank()) 
            ? movieRepository.findByTitleContaining(title)
            : movieRepository.findAll();
        
        StringWriter stringWriter = new StringWriter();
        HTMLGenerator htmlGenerator = new HTMLGenerator(stringWriter);
        htmlGenerator.generate(movies);
        
        return stringWriter.toString();
    }

    /**
     * NOVO: Endpoint para limpar lista em memória.
     * Útil para testes.
     */
    @GetMapping("/memory/clear")
    public String clearMemory() {
        long count = movieRepository.count();
        movieRepository.deleteAll();
        return "🗑️ " + count + " filmes removidos da memória";
    }

    /**
     * Endpoint legado: Retorna JSON bruto da OMDb API.
     */
    @GetMapping("/raw/search")
    public String searchMoviesRaw(@RequestParam String title) {
        return imdbApiClient.searchMoviesByTitle(title);
    }

}
