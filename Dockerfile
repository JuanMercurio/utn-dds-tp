FROM ubuntu:16.04

RUN apt update
RUN apt install openjdk-8-jdk -y
RUN apt install maven -y

COPY . .

RUN mvn install

CMD ["java", "-cp", "/target/tp-dds-g8-1.0-SNAPSHOT-jar-with-dependencies.jar", "utn.ddsG8.impacto_ambiental.server.Server"]
