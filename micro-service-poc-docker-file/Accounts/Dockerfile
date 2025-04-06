#base image to doanload java17 in docker
FROM openjdk:17-jdk-slim
# MAINTAINER instruction is deprecated in favor of using label
# MAINTAINER shivam
#Information around who maintains the image
LABEL "org.opencontainers.image.authors"="shivampurwar"
# Add the application's jar to the image
COPY target/Accounts-0.0.1-SNAPSHOT.jar Accounts-0.0.1-SNAPSHOT.jar

# execute the application
ENTRYPOINT [ "java","-jar","Accounts-0.0.1-SNAPSHOT.jar" ]
