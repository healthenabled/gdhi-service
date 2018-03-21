FROM gradle

RUN git clone https://github.com/healthenabled/gdhi-service

WORKDIR /home/gradle/gdhi-service

USER root

RUN apt-get update
#RUN sh -c 'echo "deb http://apt.postgresql.org/pub/repos/apt/ `/usr/bin/lsb_release -cs`-pgdg main" >> /etc/apt/sources.list.d/pgdg.list'
#RUN wget -q https://www.postgresql.org/media/keys/ACCC4CF8.asc -O - | apt-key add -
#RUN apt-get update && apt-get install openjfx postgresql -y
#RUN apt-get -y install postgresql-9.6 postgresql-client-9.6

#RUN nano /etc/apt/sources.list.d/pgdg.list
#RUN deb http://apt.postgresql.org/pub/repos/apt/ wheezy-pgdg main
#RUN wget https://www.postgresql.org/media/keys/ACCC4CF8.asc
#RUN apt-key add ACCC4CF8.asc
RUN apt-get install -y postgresql

RUN /etc/init.d/postgresql start &&  ps -ef | grep postgres

USER root

#RUN service postgresql start
#RUN ps -ef  | grep postgres

#RUN mkdir -p /var/lib/pgsql/data 
#ENV PGDATA="/var/lib/pgsql/data"
#ENV LOGDIR="${PGDATA}/postgresql.log"
#ENV LANG=“en_AU.UTF-8”

#RUN service postgresql start
#RUN ps -ef  | grep postgres

#RUN echo $PGDATA

#RUN su - postgres -c '/usr/lib/postgresql/9.6/bin/pg_ctl -w start'

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

COPY --from=0 /home/gradle/gdhi-service/application.yml /opt/gdhi/service  

EXPOSE 8888

ENTRYPOINT java -jar /opt/gdhi/service/gdhi-1.0.0.jar --spring.config.location=/opt/gdhi/service/application.yml


