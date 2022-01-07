# Getting Started :)

### How the Application works?

* The application load data from csv file and load it into H2 DB
* Plants data is loaded into DB as patches to avoid memory consumption
* Application is containerized using docker
* Application is deployed in AWS as a running container on EC2 instance
* Application is secured with basic auth as 
>  username: sana ,password: sana

### Run the Application
* `docker run -p8080:8080 amrhashem/thecollective-task:latest`

### Build and Run locally
#### clone the code then in the project directory run these 2 commands
* `docker build -t thecollective-task:latest .`
* `docker run -p8080:8080 thecollective-task:latest`
### Access Data in aws 
* [swagger](http://44.202.62.44:8080/swagger-ui.html#/)
* [H2-console](http://44.202.62.44:8080/h2-console)

#### ToDo
* add redis for cache
* add docker-compose file 