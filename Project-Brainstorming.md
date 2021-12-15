[Ler em Português-Brasil](https://github.com/murilocaet/customers/blob/master/Project-Brainstorming-ptbr.md)

[README](https://github.com/murilocaet/customers/blob/master/README.md)

# Overview about the project steps

## Architecture Ideas

**1. Redis**

**2. Databases: MongoDB or MySQL**

**3. Docker**

**4. Spring Boot**

**5. ReactJS or AngularJS**

**6. Nginx**

**7. AWS**

**8. Load Balance**

**9. Terraform**

**10. Swagger**


### Redis - Used

I decided to use it to control the cache. It was created a flag to trigger the cache update. When needs update, this update is always performed before the next request.
I used a Redis Container

### Databases - Used MongoDB

I decided to use MongoDB instead of MySQL just for studying
I used a MongoDB Container

### Docker - Used 

I decided to use a Compose file to share the project easily 

### Spring Boot - Used

Just because I love it

### ReactJS - Used

I chose it because I like it better than Angular, but it's okay to work with Angular. I use both in my work

### Nginx - Used

I was thinking to create a proxy_pass with upstream, but I give up and used it only to store the site on Ubuntu.
Depending on project goals It would be interesting to use

### AWS - Used

Used to share a running version of the project

### Load Balance - Not Used

The same case of Nginx load balancer. Depends on project goals

### Terraform - Used

I decided to use it to practice more and because it makes provisioning faster

### Swagger - Used

I decided to use it to share an online documentation