# Sistema Raízes do Nordeste
Projeto multidisciplinar final da trilha de Back-End do curso CST de Análise e Desenvolvimento de Sistemas da faculdade UNINTER. <br>

Esta API RESTful foi desenvolvida para atender a uma franquia de restaurantes de comida nordestina, disponibilizando com segurança e estabilidade o gerenciamento da operação, oferecendo controle de pedidos, estoque e fidelização de clientes, mantendo os padrões exigidos pela LGPD no tratamento de dados sensíveis. <br>

## Tecnologias Utilizadas
* **Java 21**
* **Spring Boot 3** (Web, Data JPA, Validation)
* **Spring Security** (Autenticação e Autorização)
* **MySQL** (Banco de dados relacional)
* **Lombok** (Redução de Boilerplate e Logs com @Slf4j)
* **Swagger/OpenAPI 3** (Documentação de API)

## Arquitetura e Padrões 
O projeto conta com uma arquitetura monolítica organizada através do padrão de Arquitetura em Camadas (Layered Architecture), foram aplicados padrões da industria como:
* **DTO Pattern:** Isolamento das entidades de banco de dados utilizando Data Transfer Objects (com validações da JSR-380).
* **Fail Fast & Guard Clauses:** Validações de regras de negócio antecipadas nos serviços.
* **Logs de Auditoria:** Rastreabilidade de ações sensíveis e conformidade básica com a LGPD (anonimização de dados).

## Como rodar projeto localmente

### 1. Requisitos
* Java (JDK) 21
* Maven 
* Servidor MySQL (na porta padrão 3306)

### 2. Banco de dados e Variáveis de ambiente
O sistema exige a criação de um banco de dados no servidor MySQL, acesso o servidor e execute:
```mysql
CREATE DATABASE raizes_do_nordeste;
```
O sistema também exige que as variáveis de ambiente sejam configuradas, acesse **application-exemplo.properties** renomeie para **application.properties** e configure:
```properties
spring.application.name=sistema-raizes-do-nordeste

spring.datasource.url=jdbc:mysql://localhost:3306/raizes_do_nordeste?useTimezone=true&serverTimezone=UTC
spring.datasource.username=SEU_USUARIO_MYSQL
spring.datasource.password=SUA_SENHA_MYSQL
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

chave.secreta=SUA_CHAVE_SECRETA_SEGURA
senha.anonimizado=SENHA_PADRAO_DE_ANONIMIZACAO

spring.data.web.pageable.default-page-size=10
spring.data.web.pageable.max-page-size=100
spring.data.web.pageable.one-indexed-parameters=true
```
### 3. Execução do sistema
Ao acessar o terminal, navegue até o diretorio raiz do projeto e execute:
```Bash
mvn clean install
mvn spring-boot:run
```

## Documentação da API (Swagger/OpenAPI)
Está API encontra-se totalmente documentada por meio da ferramenta Swagger (OpenAPI), com o projeto sendo executado acesse: <br> 
http://localhost:8080/swagger-ui.html<br>
Nessa interface gráfica todos os endpoints podem ser encontrados separados por domínio, acompanhados por seus schemas e códigos de erro correspondentes.

## Seeder de Entidades
Ao executar do projeto diversas entidades necessárias para que o sistema possa ser utilizado em testes serão criadas automaticamente:
- 1 Unidade
- 10 Itens
- 10 Itens de Menu para a Unidade criada
- 10 Itens receberam Estoque na Unidade criada
- 1 Usuário Administrador
- 2 Funcionários com cargo Gerente
- 3 Funcionários com cargo Atendente
- 3 Funcionários com cargo Cozinheiro
- 1 Funcionário com cargo Administrativo
- 2 Clientes
- 4 Pedidos diferentes
- 1 Promoção para a Unidade criada

## Testes (Postman)
Na raiz do repositório do projeto o arquivo *Raizes do Nordeste - Teste Postman.postman_collection.json* contem 10 cenário de testes exigidos, 6 com resultados positivos e 4 com resultados negativos

### Como testar
Para realizar o teste deve-se possuir a aplicação Postman adquirida:
1. Deve dentro da aplicação importar o arquivo,
2. Após a importação deve-se executar as requisições em ordem T01,T02 e assim por diante
3. As requisições que necessitam de token de permissão serão automaticamente preenchidas com o token gerado na requisição T02 por meio de um script embutido