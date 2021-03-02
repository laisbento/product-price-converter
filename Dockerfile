FROM gradle:6.7.1-jdk11 as builderr

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew clean build -x test

LABEL maintainer="Centauro"
LABEL version="1.0"

EXPOSE 8080 8081

RUN mkdir /newrelic
COPY ./newrelic/newrelic.jar /newrelic/newrelic.jar
COPY ./newrelic/newrelic.yml /newrelic/newrelic.yml
RUN cp ./build/libs/product-price-converter.jar product-price-converter.jar

ADD entrypoint.sh /
ENTRYPOINT ["sh", "/entrypoint.sh"]