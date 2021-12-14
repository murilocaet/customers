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
		enable: true,
		removed: false,
		removedAt: null
	} 
]);