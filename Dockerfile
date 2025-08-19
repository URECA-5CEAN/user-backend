# 빌드
#빌드 타임 이미지
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar -x test #bootJar : plainJar 빼고 fatJar (실행에 필요한 것만) 포함하여 build

#실행
#런타임 이미지
FROM eclipse-temurin:17-jdk
WORKDIR /app

# 빌드된 JAR 복사 (가장 최근 빌드된 파일 사용)
COPY --from=builder /app/build/libs/*.jar myapp.jar

# 애플리케이션에서 사용하는 포트
EXPOSE 8082

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "myapp.jar"]