FROM openjdk:12
VOLUME /tmp
ADD ./target/Interventor-0.0.1-SNAPSHOT.jar Interventor.jar
ENTRYPOINT ["java","-jar","/Interventor.jar"]