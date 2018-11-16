FROM java:8
WORKDIR /
CMD ./gradlew clean build
COPY ./build/libs/*.jar ./user-service.jar
CMD java -jar /user-service.jar
EXPOSE 9000
