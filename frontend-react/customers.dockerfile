FROM nginx:latest
MAINTAINER Murilo Costa
WORKDIR /var/www/customers
COPY ./build/ /var/www/customers
EXPOSE 80