FROM openjdk:11
EXPOSE 8080:8080
RUN mkdir /app
COPY ./build/libs/*-all.jar /app/EnvidualCodingChallenge.jar
ENTRYPOINT ["java","-jar","/app/EnvidualCodingChallenge.jar"]