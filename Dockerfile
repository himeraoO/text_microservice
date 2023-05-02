# Install Tomcat    & openjdk 11 (openjdk has java and javac)
FROM tomcat:tomcat:9-jdk11-openjdk
ENV DB_USERNAME='dev_textms_db_user'
ENV DB_PASSWORD='dev_textms_db_password'
ARG JAR_FILE=target/*.war
# Copy source files to tomcat folder structure
COPY ${JAR_FILE} app.war /usr/local/tomcat/webapps/

EXPOSE 8181