FROM openjdk:17-jdk-alpine

EXPOSE 5500

ADD build/libs/moneytransferservice-1.1.jar app.jar

CMD ["java", "-jar", "app.jar"]
