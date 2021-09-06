FROM openjdk:11
ADD build/libs/alpha-bank-test-task-0.0.1-SNAPSHOT.jar alpha-bank-test-task-0.0.1-SNAPSHOT.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "alpha-bank-test-task-0.0.1-SNAPSHOT.jar"]