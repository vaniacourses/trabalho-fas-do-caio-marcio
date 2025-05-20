# pdv
Sistema de ERP web desenvolvido em Java com Spring Framework 

# Links
 - Plano de testes: https://docs.google.com/document/d/16-GRfiF10-xN9RDf08mdy_ajifYkAQ5e/edit
 - Casos de testes: https://docs.google.com/spreadsheets/d/1rlLs6sKpDeE7iWGcPT-HcteD6nry4qia/edit?usp=drive_link&ouid=117460551475715822580&rtpof=true&sd=true


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

