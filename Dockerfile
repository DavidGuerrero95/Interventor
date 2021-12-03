FROM openjdk:12
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} Interventor.jar
ENTRYPOINT ["java","-jar","/Interventor.jar"]