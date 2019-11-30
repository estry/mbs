FROM java:8
EXPOSE 8080
VOLUME /tmp
ARG JAR_FILE=target/mbs-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]