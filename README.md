# Github Statistics App - AMG

Application which helps to find the most active day of the week, average commits made in the day of the week for over an year.

## Techstack

1. Spring Boot
2. Java 8
3. Maven

## Insights

Application exposes **REST endpoints** which consumes repository owner and repository name as mandatory inputs and responds with the commit statistics.

### APIs

- Calculates the most active day of the week as well as the average
number of commits for that day of the week over the last year of activity

     > /api/v1/github/stat/**{owner}**/**{repo}**/activeday&week=**{range}**

     - owner - Name of the repository owner
	 - repo   -  Name of the repository
	 - range -  Number Weeks to be calculated. Default is 52(One Year)

- Calculates the average commit value for all the days of the week for an year.

     > /api/v1/github/stat/**{owner}**/**{repo}**/weeklyaverage&sort=**{sorting}**

  - owner - Name of the repository owner
  - repo   -  Name of the repository
  - sorting - Values - asc/desc -  Order in which the average commit value to be sorted.   
 
## Run Application

Download the code in your local system. Get into the base project location and run the below command in bash to start the spring app.

`mvn spring-boot:run`

### Accessing through Swagger

Application runs with **port 8080**  Use below command to access the swagger in local.

`http://localhost:8080/swagger-ui.html`

Both the APIs will be available under Githhub Statistics. Click *Try it out*  button to execute the APIs 

## Building Application

Maven is used to bundle the code as spring boot jar. Below command builds the jar and will be available in target folder in the base project location

`mvn clean install`

Spring boot jar can we started using,

`java -jar githubstat-0.0.1-SNAPSHOT.jar`
