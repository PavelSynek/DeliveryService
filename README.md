[![Build Status](https://travis-ci.org/PavelSynek/DeliveryService.svg)](https://travis-ci.org/PavelSynek/DeliveryService)

# Delivery Service

## REST
To properly use REST layer as an example, please run in second terminal:
```sh
1. mvn clean install from root directory
2. cd rest
3. mvn tomcat7:run
```

### REST example usage
To create product:
```sh
    curl -X POST -i -H "Content-Type: application/json" --data '{"name":"test2", "price":"200", "weight":"215"}' http://localhost:8080/pa165/rest/products/create
```

To change price of product with id=1:
```sh
    curl -X PUT -i -H "Content-Type: application/json" --data '3' http://localhost:8080/pa165/rest/products/1/price
```

To change weight of product with id=1:
```sh
    curl -X PUT -i -H "Content-Type: application/json" --data '45' http://localhost:8080/pa165/rest/products/1/weight
```

To list all products:
```sh
    curl -i -X GET  http://localhost:8080/pa165/rest/products
```

To list only product with id=1:
```sh
    curl -i -X GET  http://localhost:8080/pa165/rest/products/1
```

To delete only product with id=1:
```sh
    curl -i -X DELETE  http://localhost:8080/pa165/rest/products/1
```

## Spring MVC
Username: admin@admin.cz
Password: Admin_employee