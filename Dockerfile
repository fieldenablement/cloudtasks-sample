FROM openjdk:8
VOLUME /tmp
RUN mkdir /application
COPY . /application
WORKDIR /application
RUN chmod +x mvnw
RUN /application/mvnw install 
RUN mv /application/target/*.jar /application/app.jar
ENTRYPOINT ["java","-jar","/application/app.jar"]