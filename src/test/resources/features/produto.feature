Feature: Produtos

  Scenario: Criar produto

    Given que possuo os dados de um produto
    When realizo o cadastro do produto
    Then o status code da criacao deve ser 201

  Scenario: Validar campos obrigatorios

    Given que envio um produto sem id
    When realizo o cadastro do produto
    Then o campo id deve ser gerado automaticamente
    And o status code da criacao deve ser 201

  Scenario: Consultar produto por ID

    Given que possuo o id 1
    When realizo a consulta do produto
    Then o status code deve ser 200
    And o title deve ser "Essence Mascara Lash Princess"
