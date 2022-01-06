FROM openjdk:11

ADD target/TheCollectiveTask-1.0.jar theCollectiveTask-1.0.jar

ENTRYPOINT ["java", "-jar", "theCollectiveTask-1.0.jar"]