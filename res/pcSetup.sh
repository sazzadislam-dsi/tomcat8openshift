#!/usr/bin/env bash

# Software setup
sudo apt update
sudo apt install -y wget git vim leafpad

# Java Setup
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install -y oracle-java8-installer

# Maven Setup
wget http://www.eu.apache.org/dist/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz
tar -xvf apache-maven-3.3.9-bin.tar.gz
# add the following line to /etc/bash.bashrc
# PATH=/home/sazzad/apache-maven/bin:$PATH
# export INVENTORY_MANAGEMENT=LINUX


# Setup tomcat 8
sudo apt install -y tomcat8
# tomcat 8 dir /var/lib/tomcat8
sudo service tomcat8 stop
sudo service tomcat8 start
sudo service tomcat8 restart

# setup ssh
ssh-keygen -t rsa


# deploy war file
mvn package -Popenshift
sudo chmod 755 api.war
sudo chown tomcat8:tomcat8 api.war
sudo mv api.war /var/lib/tomcat8/webapps/


