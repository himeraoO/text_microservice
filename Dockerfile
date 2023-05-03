# Install Tomcat    & openjdk 11 (openjdk has java and javac)
FROM tomcat:9-jdk11-openjdk
ENV DB_USERNAME='dev_textms_db_user'
ENV DB_PASSWORD='dev_textms_db_password'
ARG JAR_FILE=target/*.war
# Copy source files to tomcat folder structure
#ENV MY_CONTEXT=''
#COPY ${JAR_FILE} /usr/local/tomcat/webapps/${MY_CONTEXT}.war
COPY ${JAR_FILE} /usr/local/tomcat/webapps/

EXPOSE 8181

#CMD ["catalina.sh", "run"]