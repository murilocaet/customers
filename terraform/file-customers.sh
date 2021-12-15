#!/bin/bash

sudo apt install apt-transport-https ca-certificates curl software-properties-common -y
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu bionic stable"
sudo apt-get update
apt-cache search docker-ce
sudo apt install docker-ce -y
sudo chmod 666 /var/run/docker.sock
sudo apt install docker-compose -y

sudo mkdir /var/www/
sudo chmod o+w /var/www/
sudo mkdir /var/www/customers
sudo chmod o+w /var/www/customers
sudo mkdir /var/www/customers/files
sudo chmod o+w /var/www/customers/files
sudo mkdir /var/www/customers/files/front
sudo chmod o+w /var/www/customers/files/front


sudo mkdir /var/www/customers/files/swagger
sudo chmod o+w /var/www/customers/files/swagger

sudo mkdir /var/www/customers/files/mongo
sudo chmod o+w /var/www/customers/files/mongo
sudo mkdir /var/www/customers/files/mongo/db
sudo chmod o+w /var/www/customers/files/mongo/db

cd /var/www/customers

sudo bash -c "echo \"version: 3.3
services: 

  swaggerui:
    image: swaggerapi/swagger-ui
    container_name: swaggerui
    restart: always
    ports:
      - 8100:8080
    volumes:
      - ./files/swagger/openapi.json:/spec/openapi.json
    environment:
      BASE_URL: /swagger
      SWAGGER_JSON: /spec/openapi.json
    depends_on:
      - 'backend'

  mongodb:
    image: mongo:5.0
    container_name: mongodb
    restart: always
    environment:
      MONGO_INITDB_ROOT_USERNAME: murilo
      MONGO_INITDB_ROOT_PASSWORD: 12345
      MONGO_INITDB_DATABASE: customers
    ports:
      - '27017-27019:27017-27019'
    volumes:
      - ./files/mongo/database-init.js:/docker-entrypoint-initdb.d/mongo-init.js:ro
      - ./files/mongo/db:/data/db
    networks:
      - backserver

  redis:
    image: redis:latest
    container_name: redis
    environment:
       REDIS_PASSWORD: Redis12345!
    ports:
      - 6379:6379
    networks:
      - backserver
  
  backend:
    image: mccosta/customers-java
    container_name: backend
    ports:
      - 8081:8081
    networks:
      - backserver
      - frontend
    depends_on:
      - 'mongodb'
      - 'redis'
        
  customers:
    image: mccosta/customers:1.0
    container_name: customers
    ports:
      - 80:80
    volumes:
      - ./files/front/default.conf:/etc/nginx/conf.d/default.conf
    networks: 
      - frontend
    depends_on:
      - 'backend'

networks:
  frontend:
    driver: bridge
  backserver:
    driver: bridge\" > docker-compose.yml" 


sudo sed -i 's/3.3/\"3.3\"/' docker-compose.yml

cd /var/www/customers/files/mongo

sudo bash -c "echo \"
db.createUser(
	{
		user: 'murilo',
		pwd: '12345',
		roles: [
			{
				role: 'dbOwner',
				db: 'customers'
			},
			{
				role: 'readWrite',
				db: 'customers'
			}
		]
	}
);
db.createCollection('customer');
db.customer.insertMany([ 
	{
		_id: ObjectId('61a401d654a06c48b4f10f8c'),
		idCustomer: '1', 
		firstName: 'Murilo', 
		lastName: 'Costa', 
		email: 'teste@teste.com',
		birthDate: '1985-09-19', 
		state: 'BA', 
		city: 'Salvador',
		createAt: '2021-11-28 13:00',
		updateAt: '2021-11-28 13:00',
		removed: false,
		removedAt: null
	} 
]);\" > database-init.js" 

cd /var/www/customers/files/front

sudo bash -c "echo \"server {
    listen       80;
    server_name  localhost;

    location / {
        root   /var/www/customers;
        index  index.html index.htm;
    }
}\" > default.conf" 

cd /var/www/customers/

docker-compose up -d