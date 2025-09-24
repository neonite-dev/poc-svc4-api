#베이스 이미지 + 별칭
FROM gradle:8.5-jdk21-alpine AS builder
#gradle 복사
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon 

#베이스 이미지 생성
FROM openjdk:21-jdk

RUN mkdir /app
ARG SPRING_PROFILES_ACTIVE
ENV SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE

#build이미지에서 build/libs/*.jar 파일을 /app/spring-boot-application.jar로 복사
COPY --from=builder /home/gradle/src/build/libs/*.jar /app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "/app.jar"]
# ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "/app.jar"]

# 아래 설정 파일은 jenkins에서 추가 되므로 local은 환경변수 변경 후 입력 하시면 됩니다. 기본포트는 application-{server-mode}.yml 에 설정
# docker build -t poc-svc4-api:latest .
# docker run -d --rm --name pocsvc4api -p 8080:8080 -e SPRING_PROFILES_ACTIVE=local poc-svc4-api:latest
# docker run -d --rm --name pocsvc4api -p 4000:4000 -e SPRING_PROFILES_ACTIVE=dev poc-svc4-api:latest
# docker run -d --rm --name pocsvc4api -p 5000:5000 -e SPRING_PROFILES_ACTIVE=stg poc-svc4-api:latest
# docker run -d --rm --name pocsvc4api -p 7000:7000 -e SPRING_PROFILES_ACTIVE=prod poc-svc4-api:latest

# docker stop pocsvc4api
# docker rm pocsvc4api
# docker rmi poc-svc4-api:latest .
# docker build -t poc-svc4-api:latest .
# docker run --name pocsvc4api -d -p 8080:8080 poc-svc4-api:latest




# ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/spring-boot-application.jar"]

# #베이스 이미지 + 별칭
# # FROM gradle:8.9.0-jdk21 AS builder
# # FROM gradle:8.5-jdk21-alpine AS builder
# FROM gradle:8.7-jdk-21-and-22-alpine AS builder
# WORKDIR /home/gradle/src
# #gradlew 복사
# COPY gradlew .
# #gradle 복사
# COPY gradle gradle
# #build.gradle 복사
# COPY build.gradle .
# #settings.gradle 복사
# COPY settings.gradle .
# #웹어플리케이션 소스 복사
# COPY src src
# #gradlew 실행 권한 부여
# RUN chmod +x ./gradlew
# #gradlew를 통해 실행 가능한 jar파일 생성
# # RUN ./gradlew bootJar
# RUN gradlew build -x test
# #베이스 이미지 생성
# FROM openjdk:21-jdk
# #build이미지에서 build/libs/*.jar 파일을 app.jar로 복사
# COPY --from=builder build/libs/*.jar app.jar
# #jar 파일 실행
# ENTRYPOINT ["java", "-jar", "/app.jar"]
# #볼륨 지정
# VOLUME /tmp

# # FROM openjdk:21-jdk
# # VOLUME /tmp
# # ARG JAVA_OPTS
# # ENV JAVA_OPTS=$JAVA_OPTS
# # COPY build/libs/poc-svc4-api-0.0.1-SNAPSHOT.jar pocsvc4api.jar
# # EXPOSE 3000
# # ENTRYPOINT exec java $JAVA_OPTS -jar pocsvc4api.jar
# # # For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
# # #ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar pocsvc4api.jar

# # Use a base image with Java installed
# # FROM openjdk:21-jdk-slim
# # ARG JAVA_OPTS
# # ENV JAVA_OPTS=$JAVA_OPTS
# # ADD /build/libs/*.jar app.jar
# # ENTRYPOINT ["java","-jar","/app.jar"]
# # ENTRYPOINT ["java",$JAVA_OPTS,"-jar","/app.jar"]

# # # Use a base image with Java installed
# # FROM openjdk:21-jdk-slim
# # # Set the working directory in the container
# # WORKDIR /app

# # # Copy the JAR file into the container at /app
# # COPY target/*.jar app.jar

# # # Specify the command to run your application
# # CMD ["java", "-jar", "pocsvc4api.jar"]

