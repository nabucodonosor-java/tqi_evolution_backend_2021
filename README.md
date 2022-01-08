# Desafio tqi_evolution_backend_2021
Aplicação consiste de uma ApiRest desenvolvida em Java na versão 11 e utilizando o framework Spring Boot. Com relação ao nível de maturidade do padrão REST, segundo o Richardson Maturity Model, classifico a API bank-tqi nível 2 pois ela faz o uso eficiente de URIs e dos verbos HTTP. Utilizei o projeto Lombok para criar automaticamente métodos getters, setters, hashcode e construtores e criei a classe ResourceExceptionHandler com a anotação @ControllerAdvice para que dessa forma, toda a exceção será direcionada para esta classe que lançará as exceções personalizadas. Durante o desenvolvimento separei o projeto em pacotes, segue abaixo descrição:
* model: onde ficam as classes que se tornarão entidades na aplicação e tabelas no DB;
* repositories: onde ficam as interfaces que estendem de JpaRepository, responsáveis pela comunicação com DB através de métodos preexistentes e querys personalizadas;
* dto: camada onde ficam as classes que são cópias das entidades e auxiliam na composição dos objetos retornados nas requisições, além de conter as anotações de validações;
* services: camada responsável pela comunicação entre os dto´s e os controllers, além de conter as regras de negócio, deixando os controllers apenas com suas respectivas responsabilidades ;
* controllers: onde ficam os rest controllers que são responsáveis por disponibilizar serviços rest´s (endpoints) seguindo o protocolo HTTP e com tipo de resposta no padrão JSON.

## Diagrama do Modelo de Domínio da Aplicação
![Web 1](https://personal-bucket-franco.s3.sa-east-1.amazonaws.com/modelo+de+dom%C3%ADnio.png)

## Anotações JPA e Validações
Todas as classes estão com as anotações @Id e @GeneratedValue(strategy = GenerationType.IDENTITY) portanto estão devidamente anotados os atributos que serão PRIMARY KEY do DB e sua geração será automática e auto-incremental.

### Classe Cliente
* Os atributos nome, e-mail, rg, cpf, renda e senha não permitirão valor nulo. Anotação JPA @Column(nullable = false);
* Os atributos e-mail, rg e cpf não permitem valores duplicados. Anotação JPA @Column(unique = true);
* Os atributos nome, e-mail, rg e cpf não permitirão valor em branco e nulos. Anotação @NotBlank e @NotNull;
* O atributo e-mail terá a validação @Email;
* O atributo renda não permitirá valor negativo. Anotação @Positive.

### Classe Endereco
* Os atributos tipoEndereco, cep, logradouro, complemento, bairro, localidade e uf não permitirão valor nulo. Anotação JPA @Column(nullable = false) e anotação de validação @NotBlank.
* O atributo uf só aceitará 2 caracteres. Anotação @Size(max = 2).

### Classe Emprestimo
* Os atributos dataEmprestimo, dataPrimeiraParcela, valor, qtdeParcelas não permitirão valor nulo. Anotação JPA @Column(nullable = false) e anotação de validação @NotBlank e @NotNull.;
* Os atributos dataEmprestimo e dataPrimeiraParcela persistirão a data no DB no padrão UTC, ignorando fuso horário;
* O atributo dataPrimeiraParcela não permitirá data anterior ou igual ao dia atual. Anotação @Future;
* Os atributos valor e qtdeParcelas não permitirão valores negativos. Anotação @Positive.

## Requisitos do Desafio e Observações de Implementação

**1) O cliente pode cadastrar: nome, e-mail, CPF, RG, endereço completo, renda e senha.**<br/>
**Endpoint: {{host}}/clients**<br/>
**Http Method: POST**<br/>
**Público: SIM**<br/>
Adotei a estratégia de transformar o endereço completo na entidade Endereco e através do relacionamento @OneToMany contido na entidade Cliente no atributo List<Endereco> com a propriedade cascade habilitada, Endereco tornou-se filha de Cliente e portanto toda mudança persistida (salva) em Cliente será propagada em Endereco. Utilizei o FecthType.LAZY para não retornar a lista de endereços no objeto retornado, tornando a aplicação mais rápida e performática. 
Outro ponto que gostaria de salientar é a utilização do tipo BigDecimal nos atributos que representam valor monetário, pois os tipos Double e Float não guardam a precisão do valor e como trata-se de uma API para um banco a precisão numérica é primordial.

### Cenários de Exceções
* Campos em branco ou nulos: será lançada uma MethodArgumentNotValidException retornando uma mensagem de Erro de Validação e uma lista com o nome do campo onde está o erro e a mensagem. HttpStatus de retorno escolhido foi o 400 BAD_REQUEST - Erro na requisição por parte do cliente;
* E-mail incorreto: caso e-mail não respeite o padrão será lançada uma MethodArgumentNotValidException retornando uma mensagem de Erro de Validação e uma lista com o nome do campo email e a mensagem “Digitar e-mail válido!”. 
HttpStatus de retorno escolhido foi o 400 BAD_REQUEST - Erro na requisição por parte do cliente;
* Campos duplicados: caso a requisição não respeite as regras de campos que não aceitam dados duplicados será lançada uma SQLException retornando um “Erro de chave primária” e a mensagem “Recurso já cadastrado!”. 
HttpStatus de retorno escolhido foi o 400 BAD_REQUEST - Erro na requisição por parte do cliente.

**2) A autenticação será realizada por e-mail e senha**<br/>
**Endpoint: {{host}}/oauth/token**<br/>
**Http Method: GET**<br/>
**Público: SIM**<br/>
Implementei autenticação e autorização com o OAuth2 seguindo o padrão JWT (Json Web Token). Apesar de o desafio não requisitar autorização por perfil de usuário, quando o cliente realiza o cadastro automaticamente lhe é atribuído o perfil de cliente (ROLE_CLIENTE) para que se no futuro seja requisitado recursos que necessitem de autorização em nível de perfil de usuário a funcionalidade já está implementada.

### Cenários de Exceções
* Caso e-mail ou senha estejam incorretos o OAuth2 gera a exceção com o erro “invalid_grant” e a descrição “Bad credentials”
HttpStatus de retorno 400 BAD_REQUEST.

**3) Para solicitar um empréstimo, precisamos do valor do empréstimo, data da primeira parcela e quantidade de parcelas. O máximo de parcelas será 60 e a data da primeira parcela deve ser no máximo 3 meses após o dia atual.**<br/>
**Solicitar empréstimo**<br/>
**Http Method: POST**<br/>
**URL: /loans**<br/>
**Público: NÃO**<br/>

### Cenários de Exceções
* Campos em branco ou nulos, Valor e/ou Quantidade de parcelas do empréstimo menor ou igual a zero, Data da primeira parcela anterior ou igual ao dia atual: será lançada uma MethodArgumentNotValidException retornando uma mensagem de Erro de Validação e uma lista com o nome do campo onde está o erro e a mensagem. 
HttpStatus de retorno escolhido foi o 400 BAD_REQUEST - Erro na requisição por parte do cliente.
* Data primeira parcela 3 meses após data atual: será lançada uma InvalidDateException retornando o erro “Data Inválida!” e a mensagem “Data da primeira parcela deve ser no máximo 3 meses após o dia atual”. 
HttpStatus de retorno escolhido foi o 400 BAD_REQUEST - Erro na requisição por parte do cliente.
* Qtde de parcelas superior a 60: será lançada uma InstallmentsPaymentException retornando o erro “Parcela Inválida!” e a mensagem “Máximo de 60 parcelas”. 
HttpStatus de retorno escolhido foi o 400 BAD_REQUEST - Erro na requisição por parte do cliente.

**4) Na listagem, devemos retornar no mínimo o código do empréstimo, o valor e a quantidade de parcelas**<br/>
**Http Method: GET**<br/>
**URL: /loans**<br/>
**Público: NÃO**<br/>
Aqui importante salientar a implementação da classe AuthService que através do método authenticated() retorna o usuário logado e assim permite, através de scripts JPQL utilizados no repositório EmprestimoRepository, que o usuário só terá acesso aos empréstimos feitos por ele.

**5) No detalhamento de empréstimo devemos retornar: código do empréstimo, valor, quantidade de parcelas, data da primeira parcela, e-mail do cliente e renda do cliente**<br/>
**Http Method: GET**<br/>
**URL: loans{id}**<br/>
**Público: NÃO**<br/>
Para retornar um objeto com os atributos requeridos criei o dto DetalhesEmprestimoDto.

### Cenários de Exceções
* Empréstimo com id inexistente: será lançada uma ResourceNotFoundException com o erro “Recurso não encontrado” e a mensagem “Empréstimo não encontrado!”;
* Empréstimo de outro cliente: será lançada uma UnauthorizedException com o erro “Não autorizado” e a mensagem “Acesso Negado!”.
