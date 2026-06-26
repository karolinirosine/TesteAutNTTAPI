package steps;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.Assert.*;

public class ProdutoStepDefinitions {

    Response response;
    String body;
    int id;

    // Criação de produto com sucesso (Post)
    // Erro no enunciado POST = 201 não 200

    @Given("que possuo os dados de um produto")
    public void dadosProduto() {
        body = "{"
                + "\"id\": 195,"
                + "\"title\": \"Hyaluronic Acid Serum\","
                + "\"price\": 19,"
                + "\"discountPercentage\": 13.31,"
                + "\"stock\": 110,"
                + "\"description\": \"L'Oréal Paris introduces Hyaluron Expert Replumping Serum formulated with 1.5% Hyaluronic Acid\","
                + "\"brand\": \"L'Oreal Paris\","
                + "\"category\": \"skincare\""
                + "}";
        System.out.println("JSON Enviado:" + body);
    }

    @When("realizo o cadastro do produto")
    public void cadastroProduto() {
        response = RestAssured.given().contentType("application/json").body(body).post("https://dummyjson.com/products/add");
        System.out.println("JSON Cadastrado:");
        response.prettyPrint();
    }

    @Then("o status code da criacao deve ser {int}")
    public void validaCriacao(int statusEsperado) {
        System.out.println("Response Recebido: " + response.getStatusCode());
        System.out.println("Response Esperado: " + statusEsperado);

        assertEquals(statusEsperado, response.getStatusCode());
    }

    // Validação de campos obrigatórios

    @Given("que envio um produto sem id")
    public void produtoSemCampoObrigatorio() {
        //Foi decidido enviar o campo price para mostrar que o campo title não é obrigatório

        body = "{ \"price\": 50}";
        System.out.println("JSON Enviado:" + body);
    }

    @Then("o campo id deve ser gerado automaticamente")
    public void validaCampoObrigatorio() {
        //Conforme combinado com o Darlan em call 25/06 às 17:00
        //O campo title não é obrigatório como diz o enunciado
        //Apenas o id, pois é gerado automaticamente pelo dummyjson
        //Portanto, a escrita e o código foram alterados para apenas validar o id

        int idCadastrado = response.jsonPath().getInt("id");

        System.out.println("ID cadastrado:" + idCadastrado);
        assertTrue(idCadastrado > 0);
    }

    // Consulta de produto por ID (Get)
    // Erro no enunciado GET = 200 não 201

    @Given("que possuo o id {int}")
    public void setId(int idBDD) {
        this.id = idBDD;
    }

    @When("realizo a consulta do produto")
    public void consultaProduto() {
        response = RestAssured.get("https://dummyjson.com/products/" + id);
    }

    @And("o status code deve ser {int}")
    public void validaStatus(int statusEsperado) {
        System.out.println("Response Recebido: " + response.getStatusCode());
        System.out.println("Response Esperado: " + statusEsperado);
        assertEquals(statusEsperado, response.getStatusCode());
    }

    @Then("o title deve ser {string}")
    public void validaTitle(String titleEsperado) {
        String titleRecebido = response.jsonPath().getString("title");
        System.out.println("Title Recebido: " + titleRecebido);
        System.out.println("Title Esperado: " + titleEsperado);

        assertEquals(titleEsperado, titleRecebido);
    }
}