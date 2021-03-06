[Ler em Português-Brasil](https://github.com/murilocaet/customers/blob/master/README-ptbr.md)

[Project Brainstorming](https://github.com/murilocaet/customers/blob/master/Project-Brainstorming.md)

# Overview about the project

This project is about the creation of a rest API for Customer Management. It was used as System Architecture:

**- React as Front-end**

**- Spring Boot as Back-end**

**- Redis to store data on cache**

**- MongoDB as database storage**

**- Docker Images**

**- Docker-compose to start the environment**

**- Terraform to create, instantiate, and install all the necessary packages to run this project on a Ubuntu(version 18.4) VM on AWS.**

**- Swagger to document the API**

![React Front-end](https://github.com/murilocaet/customers/blob/master/img-project.png?raw=true)

[Project runing on AWS](http://ec2-44-201-241-253.compute-1.amazonaws.com).

[API documentation](http://ec2-44-201-241-253.compute-1.amazonaws.com:8100/swagger).

## Available Scripts

In the project you will see 4 directories: **backend-java**, **frontend-react**, **terraform** and **files**, 2 **dockerfile** and the **docker-compose.yml**.

## How to run?

Just execute the Docker-compose in the file path `docker-compose.yml path> docker-compose up -d` and open [localhost browser](http://localhost/).

*You can find links about Docker and Terraform below


### frontend-react

You can start the frontend manually as well. Enter into the **frontend-react** by the terminal and execute `npm start` to run the app.
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

#### Host settings

You can change settings the **API Host and Port** on the `environment.jsx`

`const HOST = "http://192.168.1.7:";`

`const PORT_API = "8081";`

### backend-java

Open the Maven Project in an IDE of your preference and run it.

The services are running at http://your-server-host:8081/api/customers/

*Running locally will need to change the MongoDB/Redis settings in `RedisConfig.java` and `application.yml`.

#### Endpoints

All endpoints were coded to send only the necessary data to Front-end, hence, the limit of rows will be the max items configured per page. In Search, the page items will be filtered in "real-time" and the other database items only after clicking on the search button.

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

All data that the API sends to the front-end comes from Redis. The System only goes to MongoDB when it is necessary to change a value or enter a new one. 
This strategy keeps the system faster than accessing the database all the time. In case of any data change, a flag is created and Redis updates its Cache 
in the next request, before collecting the data.

**Redis**, you can read more [at here](https://redis.io/).

**MongoDB**, you can read more [at here](https://docs.mongodb.com/manual/core/databases-and-collections/).


### Terraform

Terraform is an infrastructure as code (IaC) tool that allows you to build, change, and version infrastructure safely and efficiently. This includes both low-level 
components like compute instances, storage, and networking, as well as high-level components like DNS entries and SaaS features.

**Terraform CLI Documentation**, you can read more [at here](https://www.terraform.io/docs/cli/index.html).


### Docker / Docker Compose

Docker enables you to separate your applications from your infrastructure so you can deliver software quickly.

**The Docker platform**, you can read more [at here](https://docs.docker.com/get-started/overview/).

Compose is a tool for defining and running multi-container Docker applications. With Compose, you use a YAML file to configure your application’s services. 
Then, with a single command, you create and start all the services from your configuration.

**Docker Compose**, you can read more [at here](https://docs.docker.com/compose/).


### Docker Hub

Docker Hub is a service provided by Docker for finding and sharing container images with your team.

**Docker Hub**, you can read more [at here](https://docs.docker.com/docker-hub/).


### Swagger

Swagger can help you design and document your APIs at scale.

**Swagger API Tools**, you can read more [at here](https://swagger.io/tools/).

**Swagger Documentation From Your API Design**, you can read more [at here](https://swagger.io/solutions/api-documentation/).


## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).

### Code Splitting

This section has moved here: [https://facebook.github.io/create-react-app/docs/code-splitting](https://facebook.github.io/create-react-app/docs/code-splitting)

### Analyzing the Bundle Size

This section has moved here: [https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size](https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size)

### Making a Progressive Web App

This section has moved here: [https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app](https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app)

### Advanced Configuration

This section has moved here: [https://facebook.github.io/create-react-app/docs/advanced-configuration](https://facebook.github.io/create-react-app/docs/advanced-configuration)

### Deployment

This section has moved here: [https://facebook.github.io/create-react-app/docs/deployment](https://facebook.github.io/create-react-app/docs/deployment)

### `npm run build` fails to minify

This section has moved here: [https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify](https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify)
