#!/usr/bin/env bash

mvn package -Popenshift



sudo chmod 755 api.war
sudo chown tomcat8:tomcat8 api.war
sudo mv api.war /var/lib/tomcat8/webapps/



sudo chmod 755 ROOT.war
sudo chown tomcat8:tomcat8 ROOT.war
sudo mv ROOT.war /var/lib/tomcat8/webapps/






sudo service tomcat8 start
sudo service tomcat8 stop
tail -f /var/lib/tomcat8/logs/catalina.out