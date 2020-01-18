FROM openjdk:13-slim
RUN addgroup --gid 1001 --system java && adduser --uid 1001 --group --system java
RUN mkdir -p /opt && chown -R java:java /opt
RUN mkdir -p /logs && chown -R java:java /logs

WORKDIR /home/java

COPY build/back-end/app.jar app.jar
COPY build/front-end ./front-end/

USER java

CMD [ "sh", "-c", "java -XX:InitialRAMPercentage=50 -XX:MaxRAMPercentage=90 -jar app.jar" ]
EXPOSE 8080