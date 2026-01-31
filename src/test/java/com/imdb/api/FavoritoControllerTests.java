package com.imdb.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de integração para endpoints de favoritos.
 * 
 * AULA 6: Testes para POST, GET, PUT e DELETE
 * 
 * @SpringBootTest: Sobe aplicação completa para testes
 * RANDOM_PORT: Evita conflitos de porta
 * TestRestTemplate: Cliente HTTP para testes
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FavoritoControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    /**
     * Configuração executada antes de cada teste.
     * Limpa favoritos e busca filmes para ter dados de teste.
     */
    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
        
        // Limpa favoritos antes de cada teste
        restTemplate.delete(baseUrl + "/api/favoritos");
        
        // Busca alguns filmes para ter IDs disponíveis
        restTemplate.getForEntity(
            baseUrl + "/api/movies/search?title=Matrix",
            String.class
        );
    }

    /**
     * Teste 1: POST - Adicionar favorito com sucesso.
     * 
     * Verifica:
     * - Status 201 CREATED
     * - Mensagem de sucesso
     */
    @Test
    void shouldAddFavoritoSuccessfully() {
        // Arrange: ID 1 deve existir após buscar Matrix
        Long filmeId = 1L;
        
        // Act: POST /api/favoritos/1
        ResponseEntity<String> response = restTemplate.postForEntity(
            baseUrl + "/api/favoritos/" + filmeId,
            null,
            String.class
        );
        
        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("adicionado aos favoritos"));
        
        System.out.println("✅ Teste POST: Favorito adicionado com sucesso");
    }

    /**
     * Teste 2: POST - Tentar adicionar favorito duplicado.
     * 
     * Verifica:
     * - Status 409 CONFLICT
     * - Mensagem de erro
     */
    @Test
    void shouldReturnConflictWhenAddingDuplicateFavorito() {
        // Arrange: Adiciona favorito primeiro
        Long filmeId = 1L;
        restTemplate.postForEntity(
            baseUrl + "/api/favoritos/" + filmeId,
            null,
            String.class
        );
        
        // Act: Tenta adicionar novamente
        ResponseEntity<String> response = restTemplate.postForEntity(
            baseUrl + "/api/favoritos/" + filmeId,
            null,
            String.class
        );
        
        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertTrue(response.getBody().contains("já está nos favoritos"));
        
        System.out.println("✅ Teste POST: Duplicata detectada corretamente");
    }

    /**
     * Teste 3: POST - Tentar adicionar filme inexistente.
     * 
     * Verifica:
     * - Status 404 NOT FOUND
     * - Mensagem de erro
     */
    @Test
    void shouldReturnNotFoundWhenAddingNonExistentMovie() {
        // Arrange: ID que não existe
        Long filmeId = 99999L;
        
        // Act
        ResponseEntity<String> response = restTemplate.postForEntity(
            baseUrl + "/api/favoritos/" + filmeId,
            null,
            String.class
        );
        
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("não encontrado"));
        
        System.out.println("✅ Teste POST: Filme inexistente tratado corretamente");
    }

    /**
     * Teste 4: GET - Listar todos os favoritos.
     * 
     * Verifica:
     * - Status 200 OK
     * - Lista não vazia após adicionar
     */
    @Test
    void shouldListAllFavoritos() {
        // Arrange: Adiciona alguns favoritos
        restTemplate.postForEntity(baseUrl + "/api/favoritos/1", null, String.class);
        restTemplate.postForEntity(baseUrl + "/api/favoritos/2", null, String.class);
        
        // Act: GET /api/favoritos
        ResponseEntity<String> response = restTemplate.getForEntity(
            baseUrl + "/api/favoritos",
            String.class
        );
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("\"id\":1"));
        assertTrue(response.getBody().contains("\"id\":2"));
        
        System.out.println("✅ Teste GET: Lista de favoritos retornada");
    }

    /**
     * Teste 5: GET - Buscar favorito por ID.
     * 
     * Verifica:
     * - Status 200 OK
     * - Dados do filme
     */
    @Test
    void shouldGetFavoritoById() {
        // Arrange: Adiciona favorito
        restTemplate.postForEntity(baseUrl + "/api/favoritos/1", null, String.class);
        
        // Act: GET /api/favoritos/1
        ResponseEntity<String> response = restTemplate.getForEntity(
            baseUrl + "/api/favoritos/1",
            String.class
        );
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("\"id\":1"));
        
        System.out.println("✅ Teste GET: Favorito encontrado por ID");
    }

    /**
     * Teste 6: GET - Verificar se é favorito.
     * 
     * Verifica:
     * - Status 200 OK
     * - Retorna true/false
     */
    @Test
    void shouldCheckIfIsFavorito() {
        // Arrange: Adiciona favorito
        restTemplate.postForEntity(baseUrl + "/api/favoritos/1", null, String.class);
        
        // Act: GET /api/favoritos/check/1
        ResponseEntity<Boolean> response = restTemplate.getForEntity(
            baseUrl + "/api/favoritos/check/1",
            Boolean.class
        );
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        
        System.out.println("✅ Teste GET: Verificação de favorito funcionando");
    }

    /**
     * Teste 7: DELETE - Remover favorito.
     * 
     * Verifica:
     * - Status 200 OK
     * - Mensagem de sucesso
     */
    @Test
    void shouldRemoveFavorito() {
        // Arrange: Adiciona favorito
        restTemplate.postForEntity(baseUrl + "/api/favoritos/1", null, String.class);
        
        // Act: DELETE /api/favoritos/1
        ResponseEntity<String> response = restTemplate.exchange(
            baseUrl + "/api/favoritos/1",
            HttpMethod.DELETE,
            null,
            String.class
        );
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("removido"));
        
        System.out.println("✅ Teste DELETE: Favorito removido com sucesso");
    }

    /**
     * Teste 8: DELETE - Remover todos os favoritos.
     * 
     * Verifica:
     * - Status 200 OK
     * - Mensagem com quantidade removida
     */
    @Test
    void shouldRemoveAllFavoritos() {
        // Arrange: Adiciona alguns favoritos
        restTemplate.postForEntity(baseUrl + "/api/favoritos/1", null, String.class);
        restTemplate.postForEntity(baseUrl + "/api/favoritos/2", null, String.class);
        
        // Act: DELETE /api/favoritos
        ResponseEntity<String> response = restTemplate.exchange(
            baseUrl + "/api/favoritos",
            HttpMethod.DELETE,
            null,
            String.class
        );
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("favoritos removidos"));
        
        System.out.println("✅ Teste DELETE: Todos os favoritos removidos");
    }

    /**
     * Teste 9: PUT - Substituir lista de favoritos.
     * 
     * Verifica:
     * - Status 200 OK
     * - Lista substituída corretamente
     */
    @Test
    void shouldReplaceFavoritosList() {
        // Arrange: Lista de IDs
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        HttpEntity<List<Long>> request = new HttpEntity<>(ids);
        
        // Act: PUT /api/favoritos
        ResponseEntity<String> response = restTemplate.exchange(
            baseUrl + "/api/favoritos",
            HttpMethod.PUT,
            request,
            String.class
        );
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("atualizada"));
        
        System.out.println("✅ Teste PUT: Lista de favoritos substituída");
    }

    /**
     * Teste 10: Fluxo completo - Adicionar, listar, remover.
     * 
     * Verifica integração entre operações.
     */
    @Test
    void shouldPerformCompleteFlow() {
        // 1. Adicionar favorito
        ResponseEntity<String> addResponse = restTemplate.postForEntity(
            baseUrl + "/api/favoritos/1",
            null,
            String.class
        );
        assertEquals(HttpStatus.CREATED, addResponse.getStatusCode());
        
        // 2. Verificar que foi adicionado
        ResponseEntity<Boolean> checkResponse = restTemplate.getForEntity(
            baseUrl + "/api/favoritos/check/1",
            Boolean.class
        );
        assertTrue(checkResponse.getBody());
        
        // 3. Listar favoritos
        ResponseEntity<String> listResponse = restTemplate.getForEntity(
            baseUrl + "/api/favoritos",
            String.class
        );
        assertTrue(listResponse.getBody().contains("\"id\":1"));
        
        // 4. Remover favorito
        ResponseEntity<String> deleteResponse = restTemplate.exchange(
            baseUrl + "/api/favoritos/1",
            HttpMethod.DELETE,
            null,
            String.class
        );
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        
        // 5. Verificar que foi removido
        ResponseEntity<Boolean> checkAfterDelete = restTemplate.getForEntity(
            baseUrl + "/api/favoritos/check/1",
            Boolean.class
        );
        assertFalse(checkAfterDelete.getBody());
        
        System.out.println("✅ Teste Fluxo Completo: Todas as operações funcionando");
    }
}
