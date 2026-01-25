package com.imdb.api.controller;

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

    // Lê a API Key do arquivo application.properties
    @Value("${omdb.api.key}")
    private String apiKey;

    // Endpoint GET que busca filmes por título
    // Exemplo: /api/movies/search?title=Matrix
    @GetMapping("/search")
    public String searchMovies(@RequestParam String title) {
        // URL da OMDb API para buscar por título (usando HTTP)
        String url = "http://www.omdbapi.com/?s=" + title + "&apikey=" + apiKey;
        
        // Realiza a requisição HTTP GET e captura a resposta
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        
        // Imprime o JSON no console
        System.out.println("=== Busca por título: " + title + " ===");
        System.out.println(response.getBody());
        
        // Retorna o JSON como resposta da requisição
        return response.getBody();
    }

    // Endpoint GET que busca filme por ID do IMDB
    // Exemplo: /api/movies/tt0133093
    @GetMapping("/{imdbId}")
    public String getMovieById(@PathVariable String imdbId) {
        // URL da OMDb API para buscar por ID (usando HTTP)
        String url = "http://www.omdbapi.com/?i=" + imdbId + "&apikey=" + apiKey;
        
        // Realiza a requisição HTTP GET e captura a resposta
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        
        // Imprime o JSON no console
        System.out.println("=== Busca por ID: " + imdbId + " ===");
        System.out.println(response.getBody());
        
        // Retorna o JSON como resposta da requisição
        return response.getBody();
    }

}
