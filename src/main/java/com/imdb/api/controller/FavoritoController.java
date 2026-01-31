package com.imdb.api.controller;

import com.imdb.api.model.Movie;
import com.imdb.api.repository.FavoritoRepository;
import com.imdb.api.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST para gerenciar filmes favoritos.
 * 
 * AULA 6: Sistema de Favoritos com CRUD Completo
 * 
 * MÉTODOS HTTP:
 * - POST: Adicionar favorito
 * - GET: Listar/Buscar favoritos
 * - PUT: Atualizar (substituir lista)
 * - DELETE: Remover favorito
 * 
 * ENDPOINTS:
 * - POST   /api/favoritos/{filmeId}        - Adiciona favorito
 * - GET    /api/favoritos                  - Lista todos
 * - GET    /api/favoritos/{id}             - Busca por ID
 * - DELETE /api/favoritos/{id}             - Remove favorito
 * - DELETE /api/favoritos                  - Remove todos
 * - GET    /api/favoritos/check/{id}       - Verifica se é favorito
 */
@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private MovieRepository movieRepository;

    /**
     * POST: Adiciona um filme aos favoritos.
     * 
     * AULA 6: Método POST
     * 
     * @PathVariable: Captura ID da URL
     * ResponseEntity: Permite retornar status HTTP customizado
     * 
     * @param filmeId ID do filme a ser adicionado
     * @return ResponseEntity com mensagem e status
     */
    @PostMapping("/{filmeId}")
    public ResponseEntity<String> addFavorito(@PathVariable Long filmeId) {
        System.out.println("📥 POST /api/favoritos/" + filmeId);
        
        // Busca o filme na lista geral
        Optional<Movie> movieOpt = movieRepository.findById(filmeId);
        
        if (movieOpt.isEmpty()) {
            System.out.println("❌ Filme não encontrado: ID=" + filmeId);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("❌ Filme não encontrado com ID: " + filmeId);
        }
        
        Movie movie = movieOpt.get();
        
        // Adiciona aos favoritos
        boolean added = favoritoRepository.addFavorito(movie);
        
        if (added) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("⭐ Filme adicionado aos favoritos: " + movie.title());
        } else {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("⚠️ Filme já está nos favoritos: " + movie.title());
        }
    }

    /**
     * GET: Lista todos os filmes favoritos.
     * 
     * @return Lista de filmes favoritos
     */
    @GetMapping
    public ResponseEntity<List<Movie>> getAllFavoritos() {
        System.out.println("📋 GET /api/favoritos");
        
        List<Movie> favoritos = favoritoRepository.findAll();
        
        System.out.println("⭐ Total de favoritos: " + favoritos.size());
        
        return ResponseEntity.ok(favoritos);
    }

    /**
     * GET: Busca favorito por ID.
     * 
     * @param id ID do filme
     * @return Filme favorito ou 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getFavoritoById(@PathVariable Long id) {
        System.out.println("🔍 GET /api/favoritos/" + id);
        
        Optional<Movie> favorito = favoritoRepository.findById(id);
        
        if (favorito.isPresent()) {
            return ResponseEntity.ok(favorito.get());
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("❌ Favorito não encontrado com ID: " + id);
        }
    }

    /**
     * GET: Verifica se um filme é favorito.
     * 
     * @param id ID do filme
     * @return true/false
     */
    @GetMapping("/check/{id}")
    public ResponseEntity<Boolean> isFavorito(@PathVariable Long id) {
        System.out.println("✓ GET /api/favoritos/check/" + id);
        
        boolean isFav = favoritoRepository.isFavorito(id);
        
        System.out.println(isFav ? "⭐ É favorito" : "☆ Não é favorito");
        
        return ResponseEntity.ok(isFav);
    }

    /**
     * DELETE: Remove um filme dos favoritos.
     * 
     * AULA 6: Método DELETE
     * 
     * @param id ID do filme a ser removido
     * @return ResponseEntity com mensagem e status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeFavorito(@PathVariable Long id) {
        System.out.println("🗑️ DELETE /api/favoritos/" + id);
        
        boolean removed = favoritoRepository.removeFavorito(id);
        
        if (removed) {
            return ResponseEntity.ok("🗑️ Filme removido dos favoritos");
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("❌ Favorito não encontrado com ID: " + id);
        }
    }

    /**
     * DELETE: Remove todos os favoritos.
     * 
     * @return ResponseEntity com mensagem
     */
    @DeleteMapping
    public ResponseEntity<String> removeAllFavoritos() {
        System.out.println("🗑️ DELETE /api/favoritos (todos)");
        
        long count = favoritoRepository.count();
        favoritoRepository.deleteAll();
        
        return ResponseEntity.ok("🗑️ " + count + " favoritos removidos");
    }

    /**
     * PUT: Substitui lista de favoritos (OPCIONAL - Exercício Extra).
     * Recebe lista de IDs e substitui favoritos atuais.
     * 
     * AULA 6: Método PUT (Exercício Opcional)
     * 
     * @RequestBody: Recebe dados no corpo da requisição
     * 
     * @param filmesIds Lista de IDs dos filmes
     * @return ResponseEntity com mensagem
     */
    @PutMapping
    public ResponseEntity<String> replaceFavoritos(@RequestBody List<Long> filmesIds) {
        System.out.println("🔄 PUT /api/favoritos");
        System.out.println("   IDs recebidos: " + filmesIds);
        
        // Limpa favoritos atuais
        favoritoRepository.deleteAll();
        
        // Adiciona novos favoritos
        int added = 0;
        int notFound = 0;
        
        for (Long id : filmesIds) {
            Optional<Movie> movieOpt = movieRepository.findById(id);
            if (movieOpt.isPresent()) {
                favoritoRepository.addFavorito(movieOpt.get());
                added++;
            } else {
                notFound++;
                System.out.println("⚠️ Filme não encontrado: ID=" + id);
            }
        }
        
        String message = String.format(
            "🔄 Lista de favoritos atualizada: %d adicionados, %d não encontrados",
            added, notFound
        );
        
        return ResponseEntity.ok(message);
    }
}
