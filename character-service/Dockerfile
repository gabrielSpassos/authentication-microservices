FROM java:8
WORKDIR /
COPY ./build/libs/*.jar ./character-service.jar
EXPOSE 9001
CMD java -jar /character-service.jar
