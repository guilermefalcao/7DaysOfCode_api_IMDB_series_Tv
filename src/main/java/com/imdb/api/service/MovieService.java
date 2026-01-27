package com.imdb.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imdb.api.model.Movie;
import com.imdb.api.model.MovieSearchResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service responsável por processar JSON da OMDb API e converter em objetos Movie.
 * 
 * Separa a lógica de parsing JSON do controller, seguindo princípios SOLID:
 * - Single Responsibility: Apenas processa JSON
 * - Dependency Inversion: Controller depende da abstração (Service)
 */
@Service
public class MovieService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Processa JSON de busca por título e retorna lista de Movies.
     * 
     * JSON esperado da OMDb:
     * {
     *   "Search": [
     *     {"Title": "...", "Year": "...", "imdbID": "...", "Poster": "..."}
     *   ],
     *   "totalResults": "156"
     * }
     */
    public MovieSearchResult parseSearchResults(String json) {
        try {
            JsonNode root = objectMapper.readTree(json);
            JsonNode searchNode = root.get("Search");
            String totalResults = root.has("totalResults") ? root.get("totalResults").asText() : "0";
            
            if (searchNode == null || !searchNode.isArray()) {
                return new MovieSearchResult(List.of(), "0");
            }
            
            List<Movie> movies = new ArrayList<>();
            for (JsonNode movieNode : searchNode) {
                Movie movie = Movie.fromOmdbJson(
                    movieNode.get("Title").asText(),
                    movieNode.has("Poster") ? movieNode.get("Poster").asText() : null,
                    movieNode.has("imdbRating") ? movieNode.get("imdbRating").asText() : "N/A",
                    movieNode.get("Year").asText()
                );
                movies.add(movie);
            }
            
            return new MovieSearchResult(movies, totalResults);
            
        } catch (Exception e) {
            System.err.println("Erro ao processar JSON: " + e.getMessage());
            return new MovieSearchResult(List.of(), "0");
        }
    }

    /**
     * Processa JSON de busca por ID e retorna um Movie.
     * 
     * JSON esperado da OMDb:
     * {
     *   "Title": "...",
     *   "Year": "...",
     *   "imdbRating": "...",
     *   "Poster": "..."
     * }
     */
    public Movie parseMovieDetails(String json) {
        try {
            JsonNode root = objectMapper.readTree(json);
            
            return Movie.fromOmdbJson(
                root.get("Title").asText(),
                root.has("Poster") ? root.get("Poster").asText() : null,
                root.has("imdbRating") ? root.get("imdbRating").asText() : "N/A",
                root.get("Year").asText()
            );
            
        } catch (Exception e) {
            System.err.println("Erro ao processar JSON: " + e.getMessage());
            return Movie.fromOmdbJson("Erro ao carregar filme", "", "0.0", "0000");
        }
    }
}
