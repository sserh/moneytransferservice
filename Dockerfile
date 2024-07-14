FROM openjdk:17-jdk-alpine

EXPOSE 5500

ADD build/libs/moneytransferservice-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
