FROM java:8
WORKDIR /
COPY . .
CMD ./gradlew clean build
COPY ./build/libs/*.jar ./character-service.jar
CMD java -jar /character-service.jar
EXPOSE 9001
