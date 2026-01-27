package com.imdb.api.controller;

import com.imdb.api.model.Movie;
import com.imdb.api.model.MovieSearchResult;
import com.imdb.api.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

// Indica que esta classe é um controlador REST
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    // Injeta o RestTemplate configurado na classe principal
    @Autowired
    private RestTemplate restTemplate;

    // Injeta o MovieService para processar JSON
    @Autowired
    private MovieService movieService;

    // Lê a API Key do arquivo application.properties
    @Value("${omdb.api.key}")
    private String apiKey;

    // Endpoint GET que busca filmes por título e retorna objetos Movie
    // Exemplo: /api/movies/search?title=Matrix
    @GetMapping("/search")
    public MovieSearchResult searchMovies(@RequestParam String title) {
        // URL da OMDb API para buscar por título (usando HTTP)
        String url = "http://www.omdbapi.com/?s=" + title + "&apikey=" + apiKey;
        
        // Realiza a requisição HTTP GET e captura a resposta
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        
        // Processa o JSON e converte em objetos Movie
        MovieSearchResult result = movieService.parseSearchResults(response.getBody());
        
        // Imprime informações no console
        System.out.println("=== Busca por título: " + title + " ===");
        System.out.println("Total de resultados: " + result.totalResults());
        System.out.println("Filmes encontrados: " + result.count());
        result.movies().forEach(movie -> 
            System.out.println("  - " + movie.title() + " (" + movie.year() + ")")
        );
        
        // Retorna o objeto MovieSearchResult (Spring converte automaticamente para JSON)
        return result;
    }

    // Endpoint GET que busca filme por ID do IMDB e retorna objeto Movie
    // Exemplo: /api/movies/tt0133093
    @GetMapping("/{imdbId}")
    public Movie getMovieById(@PathVariable String imdbId) {
        // URL da OMDb API para buscar por ID (usando HTTP)
        String url = "http://www.omdbapi.com/?i=" + imdbId + "&apikey=" + apiKey;
        
        // Realiza a requisição HTTP GET e captura a resposta
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        
        // Processa o JSON e converte em objeto Movie
        Movie movie = movieService.parseMovieDetails(response.getBody());
        
        // Imprime informações no console
        System.out.println("=== Busca por ID: " + imdbId + " ===");
        System.out.println("Filme: " + movie.title() + " (" + movie.year() + ")");
        System.out.println("Nota: " + movie.rating());
        
        // Retorna o objeto Movie (Spring converte automaticamente para JSON)
        return movie;
    }

    // Endpoint legado que retorna JSON bruto (mantido para compatibilidade)
    @GetMapping("/raw/search")
    public String searchMoviesRaw(@RequestParam String title) {
        String url = "http://www.omdbapi.com/?s=" + title + "&apikey=" + apiKey;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

}
