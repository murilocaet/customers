[Read in English](https://github.com/murilocaet/customers/blob/master/README.md)

[Ideias e tomadas de decisão do projeto](https://github.com/murilocaet/customers/blob/master/Project-Brainstorming-ptbr.md)


# Visão geral sobre o projeto

Este projeto trata da criação de uma API rest para Gerenciamento de Clientes. Foi usado como Arquitetura para o Sistema:

**- ReactJS como front-end**
	 
**- Spring Boot como back-end**
	 
**- Redis para armazenar dados em cache**
	 
**- MongoDB como armazenamento de banco de dados**
	 
**- Imagens Docker**
	 
**- Docker-compose para iniciar o ambiente**
	 
**- Terraform para criar, instanciar e instalar todos os pacotes necessários para executar este projeto em uma VM Ubuntu (versão 18.4) no AWS.**

**-Swagger para documentar a API**

![ReactJS Front-end](https://github.com/murilocaet/customers/blob/master/img-project.png?raw=true)

[Projeto em execução na AWS](http://ec2-54-152-102-37.compute-1.amazonaws.com).

[Documentação da API](http://ec2-54-152-102-37.compute-1.amazonaws.com:8100/swagger).

## Scripts Disponíveis

No projeto, você verá 4 diretórios: ** backend-java **, ** frontend-react **, ** terraform ** e ** arquivos **, 2 ** dockerfile ** e o ** docker-compose .yml **.

## Como rodar?

Basta executar o Docker-compose no caminho do arquivo `docker-compose.yml path> docker-compose up -d` e abrir o [navegador em localhost](http://localhost/).

* Você encontrará links sobre Docker e Terraform abaixo

### frontend-react

Você também pode iniciar o frontend manualmente. Entre no **frontend-react** pelo terminal e execute `npm start` para executar o aplicativo.
Abra [http://localhost:3000](http://localhost:3000) para visualizá-lo no navegador.


### backend-java

Abra o Projeto Maven em um IDE de sua preferência e execute-o.

Os serviços estão sendo executados em http://your-server-host:8081/api/customers/

#### Endpoints

Todos os endpoints foram codificados para enviar apenas os dados necessários ao front-end, portanto, o limite de linhas será o máximo de itens configurados 
por página. Na Busca, os itens da página serão filtrados em "tempo real" e os demais itens do banco de dados somente após clicar no botão de busca.

**GET findAll**
.../api/customers?page=1&pageSize=5&searchName=

**Response**

	{
		"customers": [
		
			{
				"databaseId": {
					"timestamp": 1638186568,
					"date": "2021-11-29T11:49:28.000+00:00"
				},
				"idCustomer": "61a4be48c0219a3dc19215a3",
				"firstName": "Teste 002",
				"lastName": "Dois",
				"email": "teste2@gmail.com",
				"birthDate": "1985-09-19",
				"state": "BA",
				"city": "Salvador",
				"createAt": "2021-11-29 09:49",
				"updateAt": "2021-11-29 09:49",
            	"enable": true,
				"removed": false,
				"removedAt": null
			},
			...
		],
		"page": 1,
		"count": 1,
		"pageSize": 5,
		"totalItens": 2,
		"searchName": "",
		"note": "",
		"success": "",
		"error": []
	}


**GET findById**
.../api/customers/1

**Response**

	{
		"customers": [
			{
				"databaseId": {
					"timestamp": 1638138326,
					"date": "2021-11-28T22:25:26.000+00:00"
				},
				"idCustomer": "1",
				"firstName": "Murilo",
				"lastName": "Costa",
				"email": "murilo.caet@gmail.com",
				"birthDate": "1985-09-19",
				"state": "BA",
				"city": "Salvador",
				"createAt": "2021-11-29 09:49",
				"updateAt": "2021-11-29 09:49",
            	"enable": true,
				"removed": false,
				"removedAt": null
			}
		],
		"page": 1,
		"count": 1,
		"pageSize": 5,
		"totalItens": 0,
		"searchName": "",
		"note": "",
		"success": "",
		"error": []
	}

**POST addCustomer**
.../api/customers

**payload**

	{
		"customer": {
			"firstName": "Teste 008",
			"lastName": "Oito",
			"email": "teste8@gmail.com",
			"birthDate": "1985-09-19",
			"state": "BA",
			"city": "Salvador",
			"createAt": "2021-11-29 09:49",
			"updateAt": "2021-11-29 09:49",
			"enable": true,
			"removed": false,
			"removedAt": null
		}
	}
	
**Response**

	{
		"customers": [
			{
				"databaseId": {
					"timestamp": 1638186611,
					"date": "2021-11-29T11:50:11.000+00:00"
				},
				"idCustomer": "61a4be73c0219a3dc19215a9",
				"firstName": "Teste 008",
				"lastName": "Oito",
				"email": "teste8@gmail.com",
				"birthDate": "1985-09-19",
				"state": "BA",
				"city": "Salvador",
				"createAt": "2021-11-29 09:49",
				"updateAt": "2021-11-29 09:49",
            	"enable": true,
				"removed": false,
				"removedAt": null
			}
		],
		"page": 1,
		"count": 5,
		"totalPages": 1,
		"note": "",
		"success": "",
		"error": []
	}

**PUT updateCustomer**
.../api/customers/1

**payload**

	{
		"customer": {
			"databaseId": {
				"timestamp": 1638138326,
				"date": "2021-11-28T22:25:26.000+00:00"
			},
			"idCustomer": "1",
			"firstName": "Murilo",
			"lastName": "Costa",
			"email": "murilo.caet@gmail.com",
			"birthDate": "1985-09-19",
			"state": "BA",
			"city": "Salvador",
			"createAt": "2021-11-29 09:49",
			"updateAt": "2021-11-29 09:49",
			"enable": true,
			"removed": false,
			"removedAt": null
		}
	}
	
**Response**

	{
		"customers": [
			{
				"databaseId": {
					"timestamp": 1638138326,
					"date": "2021-11-28T22:25:26.000+00:00"
				},
				"idCustomer": "1",
				"firstName": "Murilo",
				"lastName": "Costa",
				"email": "murilo.caet@gmail.com",
				"birthDate": "1985-09-19",
				"state": "BA",
				"city": "Salvador",
				"createAt": "2021-11-29 09:49",
				"updateAt": "2021-11-29 09:49",
            	"enable": true,
				"removed": false,
				"removedAt": null
			}
		],
		"page": 1,
		"count": 1,
		"pageSize": 5,
		"totalItens": 0,
		"searchName": "",
		"note": "",
		"success": "",
		"error": []
	}

**DELETE deleteCustomer**
.../api/customers/1

**Response**

	{
		"customers": [
			{
				"databaseId": {
					"timestamp": 1638138326,
					"date": "2021-11-28T22:25:26.000+00:00"
				},
				"idCustomer": "1",
				"firstName": "Murilo",
				"lastName": "Costa",
				"email": "murilo.caet@gmail.com",
				"birthDate": "1985-09-19",
				"state": "BA",
				"city": "Salvador",
				"createAt": "2021-11-29 09:49",
				"updateAt": "2021-11-29 09:49",
            	"enable": true,
				"removed": true,
				"removedAt": "2021-11-30 09:49"
			}
		],
		"totalPages": 1,
		"note": "",
		"success": "",
		"error": []
	}

**PATCH activateAllCustomers**
.../api/customers/activateAll

**Response**

	{
		"customers": [
			{
				"databaseId": {
					"timestamp": 1638162066,
					"date": "2021-11-29T05:01:06.000+00:00"
				},
				"idCustomer": "61a45e92c0219a3dc1921598",
				"firstName": "Spring",
				"lastName": "Boot",
				"email": "spring@teste.com",
				"birthDate": "1985-09-19",
				"state": "BA",
				"city": "Salvador",
				"createAt": "2021-11-29 03:01",
				"updateAt": "2021-11-29 03:01",
            	"enable": true,
				"removed": false,
				"removedAt": null
			},
			...
		],
		"totalPages": 1,
		"note": "",
		"success": "",
		"error": []
	}

**PATCH activateCustomerById**
.../api/customers/activate/1

**Response**

	{
		"customers": [
			{
				"databaseId": {
					"timestamp": 1638162066,
					"date": "2021-11-29T05:01:06.000+00:00"
				},
				"idCustomer": "61a45e92c0219a3dc1921598",
				"firstName": "Spring",
				"lastName": "Boot",
				"email": "spring@teste.com",
				"birthDate": "1985-09-19",
				"state": "BA",
				"city": "Salvador",
				"createAt": "2021-11-29 03:01",
				"updateAt": "2021-11-29 03:01",
            	"enable": true,
				"removed": false,
				"removedAt": null
			},
			...
		],
		"totalPages": 1,
		"note": "",
		"success": "",
		"error": []
	}

**PATCH disableAllCustomers**
.../api/customers/disableAll

**Response**

	{
		"customers": [
			{
				"databaseId": {
					"timestamp": 1638162066,
					"date": "2021-11-29T05:01:06.000+00:00"
				},
				"idCustomer": "61a45e92c0219a3dc1921598",
				"firstName": "Spring",
				"lastName": "Boot",
				"email": "spring@teste.com",
				"birthDate": "1985-09-19",
				"state": "BA",
				"city": "Salvador",
				"createAt": "2021-11-29 03:01",
				"updateAt": "2021-11-29 03:01",
            	"enable": false,
				"removed": false,
				"removedAt": null
			},
			...
		],
		"totalPages": 1,
		"note": "",
		"success": "",
		"error": []
	}

**PATCH disableCustomerById**
.../api/customers/disable/1

**Response**

	{
		"customers": [
			{
				"databaseId": {
					"timestamp": 1638162066,
					"date": "2021-11-29T05:01:06.000+00:00"
				},
				"idCustomer": "61a45e92c0219a3dc1921598",
				"firstName": "Spring",
				"lastName": "Boot",
				"email": "spring@teste.com",
				"birthDate": "1985-09-19",
				"state": "BA",
				"city": "Salvador",
				"createAt": "2021-11-29 03:01",
				"updateAt": "2021-11-29 03:01",
            	"enable": false,
				"removed": false,
				"removedAt": null
			},
			...
		],
		"totalPages": 1,
		"note": "",
		"success": "",
		"error": []
	}


#### Redis e MongoDB

Todos os dados que a API envia ao front-end vêm do Redis. O sistema só vai para o MongoDB quando é necessário alterar um valor ou inserir um novo.
Essa estratégia mantém o sistema mais rápido do que acessar o banco de dados o tempo todo. Em caso de alteração de dados, um sinalizador é criado e o Redis atualiza seu Cache
na próxima solicitação, antes de coletar os dados.

**Redis**, leia mais [aqui](https://redis.io/).

**MongoDB**, leia mais [aqui](https://docs.mongodb.com/manual/core/databases-and-collections/).


### Terraform

Terraform é uma ferramenta de infraestrutura como código (IaC) que permite criar, alterar e criar versões de infraestrutura com segurança e eficiência. Isso inclui ambos os de baixo nível
componentes como instâncias de computação, armazenamento e rede, bem como componentes de alto nível, como entradas DNS e recursos SaaS.

**Terraform CLI Documentation**, leia mais [aqui](https://www.terraform.io/docs/cli/index.html).


### Docker / Docker Compose

O Docker permite que você separe seus aplicativos de sua infraestrutura para que possa entregar o software rapidamente.

**The Docker platform**, leia mais [aqui](https://docs.docker.com/get-started/overview/).

Compose é uma ferramenta para definir e executar aplicativos Docker de vários contêineres. Com o Compose, você usa um arquivo YAML para configurar os serviços do seu aplicativo.
Então, com um único comando, você cria e inicia todos os serviços de sua configuração.

**Docker Compose**, leia mais [aqui](https://docs.docker.com/compose/).


### Docker Hub

Docker Hub é um serviço fornecido pela Docker para localizar e compartilhar imagens de contêineres com sua equipe.

**Docker Hub**, leia mais [aqui](https://docs.docker.com/docker-hub/).


### Swagger

Swagger pode ajudá-lo a projetar e documentar suas APIs em escala.

**Swagger API - Ferramentas**, leia mais [aqui](https://swagger.io/tools/).

**Swagger - Documentação da API**, leia mais [aqui](https://swagger.io/solutions/api-documentation/).


## Saber mais

Você pode saber mais na [documentação do aplicativo Criar React] (https://facebook.github.io/create-react-app/docs/getting-started).

Para aprender o React, verifique a [documentação do React](https://reactjs.org/).

### Divisão de Código

Esta seção foi movida para aqui: [https://facebook.github.io/create-react-app/docs/code-splitting](https://facebook.github.io/create-react-app/docs/code-splitting)

### Analisando o tamanho do pacote

Esta seção foi movida para aqui: [https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size](https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size)

### Criando um Progressive Web App

Esta seção foi movida para aqui: [https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app](https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app)

### Configuração avançada

Esta seção foi movida para aqui: [https://facebook.github.io/create-react-app/docs/advanced-configuration](https://facebook.github.io/create-react-app/docs/advanced-configuration)

### Implantação

Esta seção foi movida para aqui: [https://facebook.github.io/create-react-app/docs/deployment](https://facebook.github.io/create-react-app/docs/deployment)

### `npm run build` fails to minify

Esta seção foi movida para aqui: [https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify](https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify)
