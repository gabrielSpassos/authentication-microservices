FROM java:8
WORKDIR /
COPY . .
CMD ./gradlew clean build
CMD java -jar /build/libs/user-service-0.0.1-SNAPSHOT.jar
EXPOSE 9000