# desafio-ze-delivery
# Creating Rest APIs with Spring and MongoDB

## Requirements

For building and running the application you need:

- [Maven](https://maven.apache.org/)
- [MongoDB](https://www.mongodb.com/)
- [Docker](https://www.docker.com/)

## Steps to Setup

**1. Database
In this project I used mongodb on Ubuntu, to install just follow the commands below.
Or we can use the docker-compose file just run and the application will run on port 8080.
```bash - Ubunto
sudo apt update
sudo apt install -y mongodb
sudo systemctl status mongodb
mongo --eval 'db.runCommand({ connectionStatus: 1 })'
```

For other operating systems access [this link](https://treehouse.github.io/installation-guides/).

**2. Clone the application**

```bash
git clone https://github.com/Jonajor/ze-challenge.git
cd desafioze
```

**3. Build and run the application using the docker, using the docker-compose**
```bash
docker-compose up
```

**4. Build and run the app using maven**

```bash
mvn spring-boot:run

or
java -jar target/desafioze-0.0.1-SNAPSHOT.jar
```

The server will start at <http://localhost:8080>.

## Executing requests

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/b33a8f974d34dd117bd7)

## Exploring the Rest APIs

The application defines following REST APIs

```
1. GET /partners{id} - Retrieve a Partner by Id

2. GET /partners/location?latitude={}&longitude={}&distance={} - Recovers partners by the parameters of latitude, longitude and distance.

3. POST /partners/batch - Cria um parceiros através de uma lista. Basta utilizar a lista do disponibilizada<https://github.com/ZXVentures/ze-code-challenges/blob/master/files/pdvs.json>

```

**5. Commands to access the mongoDB base**

```bash
docker exec -it mongodb mongo
use zedelivery
db.partners.find()

or

mongo "mongodb://127.0.0.1:27017"
use zedelivery
db.partners.find()
```


## Running integration tests

The project also contains integration tests for all the Rest APIs. For running the integration tests, go to the root directory of the project and type `mvn test` in your terminal.
```shell
mvn test
```

## Built With

- [Kotlin](https://kotlinlang.org/) - Programming language
- [IntelliJ](https://www.jetbrains.com/idea/) - IDE
- [Spring](https://spring.io/) - Java Framework
- [Maven](https://maven.apache.org/) - Dependency Management
- [MongoDB](https://www.mongodb.com/) - DataBase
- [Docker](https://www.docker.com/) - Containerization Platform