# Card clearance CRUD APP

### Info
This APP acts as the source of truth for Card clearing cost based on card BIN number (firts 6 digits)

Specifically 
<br>
1. Enables CRUD (Create-Read-Update-Delete) operations on a structure 
<br>that holds the country of origin and the cost of clearance

2. Offers clearance cost based on card BIN number 
<br>(e.g card 1234560000000000 has BIN number **123456** and has country of origin **us** (United States) 
<br>In our system the cost for **us** is **15** so in case app receive a 
<br>request for cost on BIN **123456** will respond with **us** and **15**

### Tech stack
Java17
<br>SpringBoot3
<br>Maven
<br>Redis (caching)
<br>MySql
<br>Docker
<br>Elastic/Kibana (Optional)
<br>FluentD (Optional)

### Test Coverage
After mvn clean package a folder is created under 

`target\site\jacoco`

Sited there you will find the [Jacoco](https://www.eclemma.org/jacoco/) code coverage report (index.html)

### How to build
On project root folder run `mvn clean package`

### How to run
1. On folder named docker run `docker-compose -f backing-services.yml up`
<br>the above command will start **mysql** and **redis** backing services
<br>
2. On project root folder run `mvn spring-boot:run`

### How to test
You may use a rest client (e.g. Insomnia) and/or check the exposed API on the following URL 

[swagger 2.0](http://localhost:9090/swagger-ui/index.html#/)

Provided that app is up and running

## How code is organized

api (contain exposed rest services)


domain (contain db entities and dto classes)


services (contain services implement business logic and communication with backing services)

exceptions


config (app congiguration classes e.g. cache configuration)


## Flow

### Create/Delete/Update/Retrieve clearance costs (admin only)

![plot](./images/crud.png)

### Retrieve clearance costs (user only)
![plot](./images/bin.png)

# Dockerized

## DB 

App has data some initial config items along with their details in dockerized image.

In case you need to test to another MySql-DB instance check init.sql under docker/db folder.
### Tables


**BIN_INFO**

<br>
 Column: id varchar PK (holds bin value)


<br> Column: country varchar  (holds country of bin)
<br>


**CLEARANCE**
<br>
 Column: id varchar PK (holds country value)
<br> Column: cost bigdecimal  (holds cost for country)
<br>




## Lets play with docker

1) cd  to project root folder

```
mvn clean package
```

2) `docker build -t cardcost .`

3) cd to docker

```
docker-compose -f elastic.yml up
docker-compose -f services.yml up
```

> In case you prefer to have app run via command line use the follwoing

1. run :  `docker-compose -f backing-services.yml up`

2. run :  `env.bat` 

3. run :  `java -jar target\app.jar` or `mvn spring-boot:run`

> app runs on port 9090, in case you have another service running on this port use another port with

```
java -jar target\app.jar --server.port={YOUR_FREE_PORT}
```

### How to see logs (in case you need to start app with kibana)

1) Open [kibana](http://localhost:5601/)

2) Click on Discover in the left-hand navigation menu:

You should see the following configuration window:

![plot](./images/kibana_discover.png)

3) This allows you to define the Elasticsearch indices you’d like to explore in Kibana. To learn more, consult Defining your index patterns in the official Kibana docs. For now, we’ll just use the logstash-* wildcard pattern to capture all the log data in our Elasticsearch cluster. Enter logstash-* in the text box and click on Next step.

You’ll then be brought to the following page:
![plot](./images/kibana_index.png)

4) This allows you to configure which field Kibana will use to filter log data by time. In the dropdown, select the @timestamp field, and hit Create index pattern.

Now, hit Discover in the left hand navigation menu.

You should see a histogram graph and some recent log entries:

![plot](./images/kibana_logs.png)


## Improvements

* Api official definition

* Stress/Load test in actual environment

* Exception handling

* Validations

* caching strategy

* API calls safeguard with anither tool like keycloak or an RBAC fmw (basically a gateway would be the proper way)


## SAMPLES

### authenticate

> BO LOGIN

```
curl --request POST \
  --url http://localhost:9090/api/v1/auth/authenticate \
  --header 'Content-Type: application/json' \
  --data '{
	"username":"adminuser",
	"password" : "password"
}'
```

> USER LOGIN

```
curl --request POST \
  --url http://localhost:9090/api/v1/auth/authenticate \
  --header 'Content-Type: application/json' \
  --data '{
	"username":"simpleuser",
	"password" : "password"
}'
```



> REFRESH TOKEN

```
curl --request POST \
  --url http://localhost:9090/api/v1/auth/refresh-token \
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaW1wbGV1c2VyIiwiaWF0IjoxNjg0NTk0MzAzLCJleHAiOjE2ODQ2ODA3MDN9.DTweMDWr7cOVs12znUi6q_PS-x6AZEcXIWNizD09AD0' 
```

#### For the following use token from authentication

### Create clearance

```
curl --request POST \
  --url http://localhost:9090/api/v1/admin \
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbnVzZXIiLCJpYXQiOjE2ODQ2MTE5MTYsImV4cCI6MTY4NDY5ODMxNn0.Im2Haf7KSYsjNNQxWytgl3nv-SoWJkzaLQov9cUqnNM' \
  --header 'Content-Type: application/json' \
  --data '{
	"country":"us",
	"cost" : 52
}'
```

### Update clearance

```
curl --request PATCH \
  --url http://localhost:9090/api/v1/admin/ol \
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbnVzZXIiLCJpYXQiOjE2ODQ2MTE5MTYsImV4cCI6MTY4NDY5ODMxNn0.Im2Haf7KSYsjNNQxWytgl3nv-SoWJkzaLQov9cUqnNM' \
  --header 'Content-Type: application/json' \
  --data '{
			"cost": 202
		}'
```

### Delete clearance

```
curl --request DELETE \
  --url http://localhost:9090/api/v1/admin/us \
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbnVzZXIiLCJpYXQiOjE2ODQ2MTE5MTYsImV4cCI6MTY4NDY5ODMxNn0.Im2Haf7KSYsjNNQxWytgl3nv-SoWJkzaLQov9cUqnNM' 
```
### Get clearances

```
curl --request GET \
  --url 'http://localhost:9090/api/v1/admin?page=0&size=9' \
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbnVzZXIiLCJpYXQiOjE2ODQ2MTE5MTYsImV4cCI6MTY4NDY5ODMxNn0.Im2Haf7KSYsjNNQxWytgl3nv-SoWJkzaLQov9cUqnNM' 
```
### Retrieve clearance for BIN (you need user token)


```
curl --request POST \
  --url http://localhost:9090/payment-cards-cost \
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaW1wbGV1c2VyIiwiaWF0IjoxNjg0NjA1MzcxLCJleHAiOjE2ODQ2OTE3NzF9.fTWZXnc_Doctb0UpRcgWhhxtX163USws1-7y6GMnuYQ' \
  --header 'Content-Type: application/json' \
  --data '{
	"card_number":"4422541111111111"
}'
```


