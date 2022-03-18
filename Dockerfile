FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} recipes.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/recipes.jar"]