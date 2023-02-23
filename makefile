
PORT=9078
IMAGE_NAME=dds-tp-g8
SINGLEDEPENDENCYJAR=target/tp-dds-g8-1.0-SNAPSHOT-jar-with-dependencies.jar
MAINCLASS=utn.ddsG8.impacto_ambiental.server.Server


all:
	mvn install

run:
	java -cp $(SINGLEDEPENDENCYJAR) $(MAINCLASS)

docker-build:
	sudo docker -t $(IMAGE_NAME) .

docker_run:
	sudo docker run $(IMAGE_NAME) -p $(PORT):$(PORT)

clean:
	mvn clean

.PHONY: run, docker-build, docker-run, compile, all
