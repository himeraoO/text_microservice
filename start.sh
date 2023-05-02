#!/bin/bash

# Pull new changes
git pull

# Prepare Jar
mvn clean
mvn package

# Ensure, that docker-compose stopped
docker-compose stop

## Add environment variables
#export DB_USERNAME=$1
#export DB_PASSWORD=$2
export DB_USERNAME='dev_textms_db_user'
export DB_PASSWORD='dev_textms_db_password'

# Start new deployment
docker-compose up --build -d