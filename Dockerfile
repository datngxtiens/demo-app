FROM jenkins:1.596

USER root
RUN apt-get update \
      && apt-get install -y sudo \
      && rm -rf /var/lib/apt/lists/*
RUN echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers

USER jenkins
# COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/plugins.sh /usr/share/jenkins/plugins.txt

FROM maven:3.9.6-amazoncorretto-17 AS jar
WORKDIR /tmp
COPY . .

FROM amazoncorretto:17-alpine3.18-jdk
RUN apk upgrade --available
WORKDIR /tmp
COPY --from=jar /tmp/target/*.jar app.jar
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /tmp/app.jar"]