FROM amazoncorretto:17

WORKDIR /app

COPY . ./

RUN ./gradlew bootJar
RUN cd build/libs && java -jar HandymanService*.jar
