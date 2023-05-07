# Install Tomcat & openjdk 11 (openjdk has java and javac)
FROM tomcat:9-jdk11-openjdk
ARG JAR_FILE=target/*.war
# Copy source files to tomcat folder structure
ENV MY_CONTEXT=ROOT
COPY ${JAR_FILE} /usr/local/tomcat/webapps/${MY_CONTEXT}.war

EXPOSE 9090
