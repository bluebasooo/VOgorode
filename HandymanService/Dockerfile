FROM openjdk:17

WORKDIR /app

COPY gradle ./gradle
COPY src ./src
COPY *.kts ./
COPY gradlew ./
COPY gradlew.bat ./

RUN microdnf install findutils
RUN tr -d '\015' <gradlew >gradlew-new && chmod 777 gradlew-new
RUN ./gradlew-new bootJar
RUN cd build/libs && java -jar HandymanService-0.3.0.jar
#COPY build/libs/*.jar Handyman.jar
#CMD ["cd","build/libs"]
#CMD ["java","-jar","/*.jar"]

EXPOSE 80



