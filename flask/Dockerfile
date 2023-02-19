# Second stage: build the Flask application
FROM python:3.8-alpine
COPY --from=build-spring /app/target/*.jar /app/lib/
COPY src/main/python /app/src/main/python
RUN pip install -r /app/src/main/python/requirements.txt
ENV SPRING_APP_JAR=/app/lib/app.jar
CMD ["flask", "run"]