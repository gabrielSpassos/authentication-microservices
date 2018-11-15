FROM java:8
WORKDIR /
COPY ./build/libs/*.jar ./user-service.jar
EXPOSE 9000
CMD java -jar /user-service.jar
