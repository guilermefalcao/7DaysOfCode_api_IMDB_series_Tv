package com.imdb.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

// Configura o teste para subir a aplicação em uma porta aleatória
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ImdbApiApplicationTests {

	// Injeta a porta aleatória onde a aplicação está rodando
	@LocalServerPort
	private int port;

	// Cria RestTemplate para fazer chamadas HTTP nos testes
	private final RestTemplate restTemplate = new RestTemplate();

	// Teste básico para verificar se o contexto da aplicação carrega
	@Test
	void contextLoads() {
		// Este teste verifica se a aplicação Spring Boot inicializa corretamente
		System.out.println("✅ Contexto da aplicação carregado com sucesso!");
	}

	// Teste de integração para o endpoint de busca por título
	@Test
	void shouldReturnMoviesWhenSearchingByTitle() {
		// Monta a URL do endpoint local com a porta aleatória
		String url = "http://localhost:" + port + "/api/movies/search?title=Matrix";
		
		// Faz a requisição GET para o endpoint
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		
		// Verifica se o status da resposta é 200 OK
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		// Verifica se o corpo da resposta não é nulo
		assertNotNull(response.getBody());
		
		// Verifica se a resposta contém dados (não está vazia)
		assertFalse(response.getBody().isEmpty());
		
		// Verifica se a resposta contém o JSON esperado da OMDb API
		assertTrue(response.getBody().contains("Search") || response.getBody().contains("Response"));
		
		System.out.println("✅ Teste de busca por título passou!");
	}

	// Teste de integração para o endpoint de busca por ID do IMDB
	@Test
	void shouldReturnMovieWhenSearchingById() {
		// Monta a URL do endpoint local com ID do filme Matrix
		String url = "http://localhost:" + port + "/api/movies/tt0133093";
		
		// Faz a requisição GET para o endpoint
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		
		// Verifica se o status da resposta é 200 OK
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		// Verifica se o corpo da resposta não é nulo
		assertNotNull(response.getBody());
		
		// Verifica se a resposta contém dados (não está vazia)
		assertFalse(response.getBody().isEmpty());
		
		// Verifica se a resposta contém informações específicas do filme
		assertTrue(response.getBody().contains("Title") || response.getBody().contains("Matrix"));
		
		System.out.println("✅ Teste de busca por ID passou!");
	}

	// Teste para verificar se a aplicação responde na porta correta
	@Test
	void shouldStartOnRandomPort() {
		// Verifica se a porta foi atribuída (maior que 0)
		assertTrue(port > 0);
		
		System.out.println("✅ Aplicação rodando na porta: " + port);
	}

}
