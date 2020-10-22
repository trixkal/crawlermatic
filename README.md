## Crawlermatic Overview
REST endpoint Web crawler service to go upto provided depth (max limits apply) and basic HTTP authentication.
Caching: Caffeine to improve performance for repeated urls.

## Implementation
The solution delivered here is a Java project implemented as a Spring Boot / Gradle project.


## Building the program
In order to build the program, the following is required

- Java 8 JDK
- Gradle 4.1.x
 
In order to start a project build this project to your development machine then at the top-level directory type:
$ ./gradlew clean build


## Running the program in local mode
After building the application you can run the service by performing the following steps:

1. At the top-level directory run the following command to start the Spring Boot executable which launches the application:
$ java -jar ./build/web-crawlermatic-service-*[1.0.0-SNAPSHOT]*-exec.jar

To run the service in a different profile use, say local
$ java -jar -Dspring.profiles.active=local ./build/web-crawlermatic-service-*[1.0.0-SNAPSHOT]*-exec.jar

Now the service is available at:
http://localhost:8090/web-crawlermatic-service/crawler?url=<pageUrl>&depth=<depthValue>

Example for a quick test over PostMan or any rest client:

http://localhost:8090/web-crawlermatic-service/crawler?url=https://pubmatic.com/&depth=3
