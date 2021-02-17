#Spring Boot REST API - Fernanda Belchior.
Desenvolvido com as seguintes tecnologias e ferramentas: Java 8, Maven,
Spring Boot, Junit, Mockito, HSQLDB in memory, Lombok, Intellij IDEA.

URL para testes no Swagger:
http://localhost:8090/swagger-ui.html

Paths - City API:

- POST localhost:8090/city/save
*** Há validações para o salvamento ***
    {
        "name": "RECIFE",
        "state": "PE"
    }

- GET localhost:8090/city/findByName/{name}

- GET localhost:8090/city/findByState/{state}

____________________________________________________________________________________


Paths Client API:

- GET localhost:8090/client/findById/{id}

- GET localhost:8090/client/findByName/{name}

- POST localhost:8090/client/save
*** É necessário cadastrar a cidade antes de seguir com o 
cadastro do cliente! ****
*** Há validações dos dados para o salvamento. ***
    {
    "age": 40,
    "birthDate": "1980-05-05",
    "city": {
    "name": "RECIFE",
    "state": "PE"
    },
    "gender": "F",
    "name": "FATIMA"
    }

- DELETE localhost:8090/client/remove/{id}

- PUT localhost:8090/client/updateName/{id}
*** Recebe o novo nome no body ***






