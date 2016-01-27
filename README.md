[![Build Status](https://travis-ci.org/PavelSynek/DeliveryService.svg)](https://travis-ci.org/PavelSynek/DeliveryService)

# Delivery Service

## Spring MVC

The application runs at http://pa165-deliveryservice.herokuapp.com/

### Admin
Username: admin@admin.cz
Password: Admin123

### Customer
Username: tester@zakaznik.cz
Password: Tester123

## REST
To properly use REST layer as an example, please run in second terminal:
```sh
1. mvn clean install from root directory
2. cd rest
3. mvn tomcat7:run
```

### REST example usage
To list all products:
```sh
    curl -i -X GET  http://localhost:8080/pa165/rest/products
```

To list only product with id=4:
```sh
    curl -i -X GET  http://localhost:8080/pa165/rest/products/4
```

To delete only product with id=4:
```sh
    curl -i -X DELETE  http://localhost:8080/pa165/rest/products/4
```

To change price of product with id=4:
```sh
    curl -X PUT -i -H "Content-Type: application/json" --data '3' http://localhost:8080/pa165/rest/products/4/price
```

To change weight of product with id=4:
```sh
    curl -X PUT -i -H "Content-Type: application/json" --data '45' http://localhost:8080/pa165/rest/products/4/weight
```
To create product:
```sh
    curl -X POST -i -H "Content-Type: application/json" --data '{"name":"test2", "price":"200", "weight":"215"}' http://localhost:8080/pa165/rest/products/create
```

## Spring MVC
