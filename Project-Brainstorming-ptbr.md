[Read in English](https://github.com/murilocaet/customers/blob/master/Project-Brainstorming.md)

[README](https://github.com/murilocaet/customers/blob/master/README-ptbr.md)

# Visão geral sobre as etapas do projeto

## Ideias de Arquitetura

**1. Redis**

**2. Bancos de dados: MongoDB ou MySQL**

**3. Docker**

**4. Spring Boot**

**5. ReactJS ou AngularJS**

**6. Nginx**

**7. AWS**

**8. Balanceamento de carga**

**9. Terraform**

**10. Swagger**



### Redis - Usado

Decidi usá-lo para controlar o cache. Foi criado um flag para acionar a atualização do cache. Quando precisa de atualização, esta atualização é sempre realizada antes da próxima solicitação.
Usei um contêiner Redis

### Bancos de dados - Usado MongoDB

Decidi usar MongoDB em vez de MySQL apenas para estudar
Usei um contêiner MongoDB

### Docker - Usado

Decidi usar um arquivo Compose para compartilhar o projeto facilmente

### Spring Boot - Usado

Só porque é lindo :)

### ReactJS - Usado

Escolhi porque gosto mais do que o Angular, mas não há problema em trabalhar com o Angular. Uso os dois no trabalho

### Nginx - Usado

Estava pensando em criar um proxy_pass com upstream, mas desisti e usei apenas para armazenar o site no Ubuntu.
Dependendo dos objetivos do projeto, seria interessante usar

### AWS - Usado

Usado para compartilhar uma versão em execução do projeto

### Balanceamento de carga - Não Usado

O mesmo caso do balanceador de carga Nginx. Depende dos objetivos do projeto

### Terraform - Usado

Decidi usá-lo para praticar mais e porque torna o provisionamento mais rápido

### Swagger - Usado

Decidi usá-lo para compartilhar uma documentação online