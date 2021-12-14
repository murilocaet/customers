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
    image: mccosta/customers
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


cd /var/www/customers/files/swagger

sudo bash -c "echo \"{
  'openapi': '3.0.1',
  'info': {
    'title': 'Customer Management',
    'description': 'This project is about the creation of a rest API for Customer Management. It was used as System Architecture:
      
      - React as Front-end
      
      - Spring Boot as Back-end
      
      - Redis to store data on cache
      
      - MongoDB as database storage
      
      - Docker Images
      
      - Docker-compose to start the environment
      
      - Terraform to create, instantiate, and install all the necessary packages to run this project on a Ubuntu(version 18.4) VM on AWS.
      
      - Swagger to document the API',
    
    'termsOfService': 'https://github.com/murilocaet/customers',
    'contact': {
      'name': 'Murilo Costa',
      'email': 'murilo.caet@gmail.com',
      'url': 'https://github.com/murilocaet'
    },
    'license': {
      'name': 'Software livre',
      'url': 'https://github.com/murilocaet/customers'
    },
    'version': '1.0.0'
  },
  'servers': [
    {
      'url': 'http://ec2-54-152-102-37.compute-1.amazonaws.com:8081/api'
    },
    {
      'url': 'http://localhost:8081/api'
    }
  ],
  'tags': [
    {
      'name': 'Customer',
      'description': 'Everything about Customers'
    }
  ],
  'paths': {
    '/customers': {
      'get': {
        'tags': [
          'Customer'
        ],
        'summary': 'Find all Customers',
        'description': 'Returns all Customers data.',
        'operationId': 'findAllCustomers',
        'parameters': [
          {
            'name': 'page',
            'in': 'query',
            'description': 'Page to return',
            'required': true,
            'schema': {
              'type': 'integer',
              'default': 1
            }
          },
          {
            'name': 'pageSize',
            'in': 'query',
            'description': 'Page rows limit',
            'required': true,
            'schema': {
              'type': 'integer',
              'default': 5
            }
          },
          {
            'name': 'searchName',
            'in': 'query',
            'description': 'Value to be filtered',
            'required': false,
            'schema': {
              'type': 'string',
              'default': null
            }
          }
        ],
        'responses': {
          '200': {
            'description': 'successful operation',
            'content': {
              'application/json': {
                'schema': {
                  'items': {
                    '$ref': '#/components/schemas/CustomerResponse'
                  }
                }
              }
            }
          },
          '204': {
            'description': 'No Content',
            'content': {
              'application/json': {
                'example': 'No Content'
              }
            }
          },
          '500': {
            'description': 'Error loading data',
            'content': {
              'application/json': {
                'example': 'content-length: 0'
              }
            }
          }
        }
      },
      'post': {
        'tags': [
          'Customer'
        ],
        'summary': 'Add a new Customer with form data',
        'description': 'Save a new Customer',
        'operationId': 'addCustomer',
        'requestBody': {
          'description': 'Customer object that needs to be added',
          'content': {
            'application/json': {
              'schema': {
                '$ref': '#/components/schemas/CustomerRequest'
              }
            }
          },
          'required': true
        },
        'responses': {
          '201': {
            'description': 'successful created',
            'content': {
              'application/json': {
                'schema': {
                  'items': {
                    '$ref': '#/components/schemas/CustomerResponse'
                  }
                }
              }
            }
          },
          '500': {
            'description': 'Error saving data',
            'content': {
              'application/json': {
                'example': 'Internal Server Error'
              }
            }
          }
        }
      }
    },
    '/customers/{id}': {
      'get': {
        'tags': [
          'Customer'
        ],
        'summary': 'Find Customer by Id',
        'description': 'Returns a single Customer',
        'operationId': 'findCustomerById',
        'parameters': [
          {
            'name': 'id',
            'in': 'path',
            'description': 'ID of Customer to return',
            'required': true,
            'schema': {
              'type': 'string'
            }
          }
        ],
        'responses': {
          '200': {
            'description': 'successful operation',
            'content': {
              'application/json': {
                'schema': {
                  'items': {
                    '$ref': '#/components/schemas/CustomerResponse'
                  }
                }
              }
            }
          },
          '204': {
            'description': 'No Content',
            'content': {
              'application/json': {
                'example': 'No Content'
              }
            }
          },
          '500': {
            'description': 'Error loading data',
            'content': {
              'application/json': {
                'example': 'content-length: 0'
              }
            }
          }
        }
      },
      'put': {
        'tags': [
          'Customer'
        ],
        'summary': 'Update an existing Customer with form data',
        'description': 'Update an existing Customer',
        'operationId': 'updateCustomer',
        'parameters': [
          {
            'name': 'id',
            'in': 'path',
            'description': 'ID of Customer to be updated',
            'required': true,
            'schema': {
              'type': 'string'
            }
          }
        ],
        'requestBody': {
          'description': 'Customer object that needs to be updated',
          'content': {
            'application/json': {
              'schema': {
                '$ref': '#/components/schemas/CustomerRequest'
              }
            }
          },
          'required': true
        },
        'responses': {
          '204': {
            'description': 'successful updated',
            'content': {
              'application/json': {
                'example': 'No Content'
              }
            }
          },
          '500': {
            'description': 'Error updating data',
            'content': {
              'application/json': {
                'example': 'Internal Server Error'
              }
            }
          }
        }
      },
      'delete': {
        'tags': [
          'Customer'
        ],
        'summary': 'Delete an existing Customer',
        'description': 'Delete, logically, an existing Customer',
        'operationId': 'deleteCustomer',
        'parameters': [
          {
            'name': 'id',
            'in': 'path',
            'description': 'ID of Customer to be deleted',
            'required': true,
            'schema': {
              'type': 'string'
            }
          }
        ],
        'responses': {
          '200': {
            'description': 'successful operation',
            'content': {
              'application/json': {
                'schema': {
                  'items': {
                    '$ref': '#/components/schemas/CustomerResponse'
                  }
                }
              }
            }
          },
          '204': {
            'description': 'No Content',
            'content': {
              'application/json': {
                'example': 'No Content'
              }
            }
          },
          '500': {
            'description': 'Error updating data',
            'content': {
              'application/json': {
                'example': 'Internal Server Error'
              }
            }
          }
        }
      }
    },
    '/customers/activate/{id}': {
      'patch': {
        'tags': [
          'Customer'
        ],
        'summary': 'Activate an existing Customer',
        'description': 'Activate an existing Customer',
        'operationId': 'activateCustomer',
        'parameters': [
          {
            'name': 'id',
            'in': 'path',
            'description': 'ID of Customer to be activate',
            'required': true,
            'schema': {
              'type': 'string'
            }
          }
        ],
        'responses': {
          '200': {
            'description': 'successful operation',
            'content': {
              'application/json': {
                'schema': {
                  'items': {
                    '$ref': '#/components/schemas/CustomerResponse'
                  }
                }
              }
            }
          },
          '204': {
            'description': 'No Content',
            'content': {
              'application/json': {
                'example': 'No Content'
              }
            }
          },
          '500': {
            'description': 'Error updating data',
            'content': {
              'application/json': {
                'example': 'Internal Server Error'
              }
            }
          }
        }
      }
    },
    '/customers/activateAll': {
      'patch': {
        'tags': [
          'Customer'
        ],
        'summary': 'Activate all Customers',
        'operationId': 'activateAllCustomers',
        'responses': {
          '204': {
            'description': 'No Content',
            'content': {
              'application/json': {
                'example': 'No Content'
              }
            }
          },
          '500': {
            'description': 'Error updating data',
            'content': {
              'application/json': {
                'example': 'Internal Server Error'
              }
            }
          }
        }
      }
    },
    '/customers/disable/{id}': {
      'patch': {
        'tags': [
          'Customer'
        ],
        'summary': 'Disable an existing Customer',
        'description': 'Disable an existing Customer',
        'operationId': 'disableCustomer',
        'parameters': [
          {
            'name': 'id',
            'in': 'path',
            'description': 'ID of Customer to be disabled',
            'required': true,
            'schema': {
              'type': 'string'
            }
          }
        ],
        'responses': {
          '200': {
            'description': 'successful operation',
            'content': {
              'application/json': {
                'schema': {
                  'items': {
                    '$ref': '#/components/schemas/CustomerResponse'
                  }
                }
              }
            }
          },
          '204': {
            'description': 'No Content',
            'content': {
              'application/json': {
                'example': 'No Content'
              }
            }
          },
          '500': {
            'description': 'Error updating data',
            'content': {
              'application/json': {
                'example': 'Internal Server Error'
              }
            }
          }
        }
      }
    },
    '/customers/disableAll': {
      'patch': {
        'tags': [
          'Customer'
        ],
        'summary': 'Disable all Customers',
        'operationId': 'disableAllCustomers',
        'responses': {
          '204': {
            'description': 'No Content',
            'content': {
              'application/json': {
                'example': 'No Content'
              }
            }
          },
          '500': {
            'description': 'Error updating data',
            'content': {
              'application/json': {
                'example': 'Internal Server Error'
              }
            }
          }
        }
      }
    }
  },
  'components': {
    'schemas': {
      'Customer': {
        'type': 'object',
        'properties': {
          'databaseId': {
            'type': 'object'
          },
          'idCustomer': {
            'type': 'string'
          },
          'firstName': {
            'type': 'string'
          },
          'lastName': {
            'type': 'string'
          },
          'email': {
            'type': 'string'
          },
          'birthDate': {
            'type': 'string'
          },
          'state': {
            'type': 'string'
          },
          'city': {
            'type': 'string'
          },
          'createAt': {
            'type': 'string'
          },
          'updateAt': {
            'type': 'string'
          },
          'enable': {
            'type': 'boolean',
            'default': true
          },
          'removed': {
            'type': 'boolean',
            'default': false
          },
          'removedAt': {
            'type': 'string'
          },
          'age': {
            'type': 'integer'
          }
        }
      },
      'CustomerResponse': {
        'type': 'object',
        'properties': {
          'customers': {
            'type': 'array',
            'items': {
              '$ref': '#/components/schemas/Customer'
            }
          },
          'page': {
            'type': 'integer'
          },
          'count': {
            'type': 'integer'
          },
          'pageSize': {
            'type': 'integer'
          },
          'totalItens': {
            'type': 'integer'
          },
          'searchName': {
            'type': 'string'
          },
          'note': {
            'type': 'string'
          },
          'success': {
            'type': 'string'
          },
          'error': {
            'type': 'array',
            'items': {
              'type': 'string'
            }
          }
        }
      },
      'CustomerRequest': {
        'type': 'object',
        'properties': {
          'customer': {
            '$ref': '#/components/schemas/Customer'
          },
          'page': {
            'type': 'integer'
          },
          'count': {
            'type': 'integer'
          },
          'pageSize': {
            'type': 'integer'
          },
          'totalItens': {
            'type': 'integer'
          },
          'searchName': {
            'type': 'string'
          }
        }
      }
    }
  }
}\" > openapi.json" 

sudo chmod 666 /var/www/customers/files/swagger/openapi.json

cd /var/www/customers/

docker-compose up -d