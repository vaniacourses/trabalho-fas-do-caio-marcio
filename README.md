# pdv
Sistema de ERP web desenvolvido em Java com Spring Framework 

# Entregas
 - Plano de testes: https://docs.google.com/document/d/16-GRfiF10-xN9RDf08mdy_ajifYkAQ5e/edit
 - Casos de testes: https://docs.google.com/spreadsheets/d/1rlLs6sKpDeE7iWGcPT-HcteD6nry4qia/edit (prints do repositório pasta: caso_testes_funcionais)
 - Imagens Sonar: pasta "sonar_images" no repositório
 - Imagens PITest: pasta "evidencias_testes/mutation_images" no repositório
 - ISO: https://g.co/gemini/share/02bbad3f0b60
 - Score Prints https://docs.google.com/document/d/1caCIoMR5diqkhQGeW-cCFvNl0pTCMrD3YypvbRggY7M/edit?usp=sharing
 - Prints de teste rodando em pasta: evidencias_testes

# Recursos
- Cadastro produtos/clientes/fornecedor
- Controle de estoque
- Gerenciar comandas
- Realizar venda
- Controle de fluxo de caixa
- Controle de pagar e receber
- Venda com cartões
- Gerenciar permissões de usuários por grupos
- Cadastrar novas formas de pagamentos
- Relatórios

# Instalação
OBRIGATÓRIO JDK 8!
Para instalar o sistema, você deve criar o banco de dado "pdv" no mysql e configurar o arquivo application.properties
com os dados do seu usuário root do mysql e rodar o projeto pelo Eclipse, VS code usando as extensões "Extension Pack for Java" e "Spring Boot Extension Pack", rodando pelo Dashboard do Sping Boot no menu lateral, ou gerar o jar do mesmo e execultar.

# Configurando Ambiente no VSCode
Leia o arquivo CONFIGURANDO_AMBIENTE.txt localizado na raiz do projeto.

# Logando no sistema
Para logar no sistema, use o usuário "gerente" e a senha "123".

# Tecnologias utilizadas
- Spring Framework 5
- Thymeleaf 3
- MySQL
- Hibernate
- FlyWay

# Execução com Docker
Para executar a aplicação utilizando o docker, utilize o seguinte comando na raiz do projeto:
```sh
docker compose up -d
```

