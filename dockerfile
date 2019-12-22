FROM openjdk:11
COPY ./build /otakushelter_manga_parser
WORKDIR /otakushelter_manga_parser
ENTRYPOINT ["java", "-jar", "application.jar"]