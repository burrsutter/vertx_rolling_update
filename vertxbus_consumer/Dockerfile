FROM fabric8/java-jboss-openjdk8-jdk:1.0.10

ENV JAVA_APP_JAR vertxbusconsumer-1.0.0-fat.jar
ENV AB_JOLOKIA_HTTPS true

EXPOSE 8080

ADD target/$JAVA_APP_JAR /app

WORKDIR /app/
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar $JAVA_APP_JAR -cluster"]
