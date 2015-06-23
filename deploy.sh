#!/bin/bash

TOMCAT_HOME=/usr/local/apache-tomcat-7.0.57

echo "remove all the files under the tomcat/lib"
rm -fr $TOMCAT_HOME/lib/*

echo "copy tomcat lib files"
cp ../lcp_libs/tomcat_lib/* $TOMCAT_HOME/lib

echo "copy LCP lib files"
cp ../lcp_libs/lib/* $TOMCAT_HOME/lib

services=("cfm")

for service in "${services[@]}"
do
        echo "rm -fr $TOMCAT_HOME/webapps/$service.war"
        rm -fr $TOMCAT_HOME/webapps/$service.war

        echo "rm -fr $TOMCAT_HOME/webapps/$service"
        rm -fr $TOMCAT_HOME/webapps/$service

        echo "cp docker_scripts/user/tomcat/$service.war $TOMCAT_HOME/webapps/"
        cp ./$service.war $TOMCAT_HOME/webapps/
done

CMS_PID=`/bin/ps -ef | grep apache-tomcat-7.0.57 | grep -v grep | awk '{print $2}'`
if [ ! -z "${CMS_PID}" ]; then
        kill ${CMS_PID}
        if [ $? -gt 0 ]; then
                echo "CMS Server cannot be stopped."
                exit
        fi
        sleep 1
        CMS_PID=`/bin/ps -ef | grep CMS | grep -v grep | awk '{print $2}'`
        if [ ! -z "${CMS_PID}" ]; then
                kill -9 ${CMS_PID}
                if [ $? -gt 0 ]; then
                        echo "CMS Server cannot be stopped."
                        exit
                fi
        fi
        echo "CMS Server stopped."
fi

# tomcat
echo "1st time starting tomcat"
$TOMCAT_HOME/bin/startup.sh
