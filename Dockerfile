FROM gradle

RUN git clone https://github.com/healthenabled/gdhi-service

WORKDIR /home/gradle/gdhi-service

USER root

RUN apt-get update
RUN apt-get install -y postgresql

RUN /etc/init.d/postgresql start &&  ps -ef | grep postgres

USER postgres
RUN /etc/init.d/postgresql start && psql -c "CREATE USER gdhi_test WITH PASSWORD 'testpassword';"
RUN /etc/init.d/postgresql start && psql -c "CREATE DATABASE gdhi_test OWNER gdhi_test;"
RUN ps -ef | grep postgres

USER root

RUN /etc/init.d/postgresql start && gradle clean build

FROM openjdk:8

COPY --from=0 /home/gradle/gdhi-service .

RUN mkdir -p /opt/gdhi/service

RUN mkdir /opt/gdhi/service/logs

COPY --from=0 /home/gradle/gdhi-service/build/libs/gdhi-1.0.0.jar /opt/gdhi/service

COPY application.yml /opt/gdhi/service  

COPY logback-spring.xml /opt/gdhi/service  

EXPOSE 8888

ENTRYPOINT java -jar /opt/gdhi/service/gdhi-1.0.0.jar --spring.config.location=/opt/gdhi/service/application.yml


