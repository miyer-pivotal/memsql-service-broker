#!/bin/bash 

mvn clean
mvn package
cf apps
cf delete memsql-broker --f --r
cf service-brokers
cf delete-service-broker memsql --f
cf delete-orphaned-routes --f
cf push
cf enable-service-access MemSQLDB
cf marketplace
