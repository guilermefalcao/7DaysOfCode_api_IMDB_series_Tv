package com.imdb.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Cliente HTTP para comunicação com a API OMDb (The Open Movie Database).
 * 
 * DECISÕES DE DESIGN:
 * 
 * 1. POR QUE @Component?
 *    - Marca a classe como componente Spring
 *    - Spring gerencia o ciclo de vida (cria, injeta, destrói)
 *    - Permite injeção em outras classes com @Autowired
 * 
 * 2. POR QUE ENCAPSULAR A CHAMADA HTTP?
 *    - Single Responsibility: Apenas se comunica com API externa
 *    - Facilita testes: Pode mockar o cliente
 *    - Reutilização: Outros controllers podem usar
 *    - Manutenção: Mudanças na API afetam apenas esta classe
 * 
 * 3. POR QUE @Value?
 *    - Lê propriedades do application.properties
 *    - Centraliza configuração
 *    - Facilita mudança de ambiente (dev, prod)
 * 
 * 4. VANTAGENS DO ENCAPSULAMENTO:
 *    - Controller não precisa saber detalhes da URL
 *    - Controller não precisa saber sobre API Key
 *    - Código mais limpo e legível
 */
@Component
public class ImdbApiClient {

    // Injeta o RestTemplate configurado na classe principal
    @Autowired
    private RestTemplate restTemplate;

    // Lê a API Key do arquivo application.properties
    // Sintaxe: ${propriedade:valorPadrao}
    @Value("${omdb.api.key}")
    private String apiKey;

    // URL base da API OMDb
    private static final String OMDB_API_URL = "http://www.omdbapi.com/";

    /**
     * Busca filmes por título na API OMDb.
     * 
     * Encapsula toda a lógica de:
     * - Construção da URL
     * - Adição da API Key
     * - Execução da requisição HTTP
     * - Retorno do JSON
     * 
     * @param title Título do filme a ser buscado
     * @return JSON com lista de filmes encontrados
     */
    public String searchMoviesByTitle(String title) {
        // Constrói URL com parâmetros: ?s=titulo&apikey=chave
        String url = OMDB_API_URL + "?s=" + title + "&apikey=" + apiKey;
        
        // Executa requisição HTTP GET
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        
        // Log para debug (pode ser removido em produção)
        System.out.println("🌐 Requisição para OMDb API: " + title);
        
        // Retorna o corpo da resposta (JSON)
        return response.getBody();
    }

    /**
     * Busca detalhes de um filme específico por ID do IMDB.
     * 
     * @param imdbId ID do filme no IMDB (ex: tt0133093)
     * @return JSON com detalhes completos do filme
     */
    public String getMovieById(String imdbId) {
        // Constrói URL com parâmetros: ?i=id&apikey=chave
        String url = OMDB_API_URL + "?i=" + imdbId + "&apikey=" + apiKey;
        
        // Executa requisição HTTP GET
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        
        // Log para debug
        System.out.println("🌐 Requisição para OMDb API: " + imdbId);
        
        // Retorna o corpo da resposta (JSON)
        return response.getBody();
    }

    /**
     * Método auxiliar para verificar se o cliente está configurado corretamente.
     * Útil para testes e debug.
     * 
     * @return true se API Key está configurada
     */
    public boolean isConfigured() {
        return apiKey != null && !apiKey.equals("YOUR_API_KEY");
    }
}
